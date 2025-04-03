package com.min.edu.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.min.edu.dto.EmployeeDto;
import com.min.edu.model.service.IEmployeeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class LoginController {

	private final IEmployeeService service;

	// ------ 로그인 폼 -----
	@GetMapping(value = "/")
	public String loginForm(HttpServletRequest request) {
		log.info("EmployeeController loginForm 로그인화면 이동");
		request.getSession().invalidate();
		return "loginForm";
	}

	// ------ 로그인 -----
	@PostMapping(value = "/login.do")
	public void login(@RequestParam Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		log.info("EmployeeController login 로그인 : {}", map.get("emp_id"));


		String password = (String) map.get("password");

		response.setContentType("text/html; charset=UTF-8;");

		EmployeeDto loginVo = service.getLogin(map);

		if(loginVo == null){
			response.getWriter()
			.print("<script>alert('로그인정보 없음'); location.href='./logout.do';</script>");
		}
		else if(password.equals("a12345678")) {
			log.info("초기 비밀번호 사용으로 인한 비밀번호 재설정 페이지로 이동");
			response.getWriter()
					.print("<script>alert('초기 비밀번호입니다. 비밀번호를 재설정해주세요.'); location.href='./newPw.do';</script>");
		}
		else if(loginVo.getSignSaved() == null) {
			log.info("사인 등록 화면으로 이동");
			response.getWriter()
					.print("<script>alert('사인등록은 필수 입니다'); location.href='./signature_manage.do';</script>");
		}else {
			log.info("로그인 정보 : {}", loginVo);
			HttpSession session = request.getSession();
			session.setAttribute("loginVo", loginVo);
			response.getWriter()
			.print("<script>alert('" + loginVo.getName() + "님 반갑습니다'); location.href='./homeList.do';</script>");
		}
		
	}

	// 비밀번호 재설정 GetMapping
	@GetMapping(value = "/newPw.do")
	public String newPw() {
		log.info("초기 비밀번호 변경을 위한 비밀번호 재설정");
		return "newPw";
	}

	@GetMapping(value = "/logout.do")
	public String logout(HttpSession session, Model model) {

		log.info("LoginController logout.do 요청");
//		EmployeeDto sessionVo = (EmployeeDto) session.getAttribute("loginVo");
//
//		if (sessionVo != null) {
//			log.info("HttpSession은 삭제되기 전까지 유지된다. : {}", sessionVo);
//		} else {
//			log.info("세션에서 loginVo를 찾을 수 없습니다.");
//		}

		session.invalidate();

		// 세션에서 loginVo를 제거한 후 다시 가져옴 (null 확인)
//	    	    EmployeeDto removedVo = (EmployeeDto)session.getAttribute("loginVo");
//	    	    log.info("HttpSession에서 loginVo 제거 후 확인: {}", removedVo);  // null

		// return "redirect:/homeList.do"; //** 세션 지워지는 것 확인
		return "redirect:/";
	}

	// -------비밀번호를 잊어버렸을 때---------
	@GetMapping(value = "/forgot.do")
	public String forgotPassword() {

		// 비밀번호 재설정 화면으로 이동
		return "forgot";
	}

	// ------비밀번호 재설정(사원조회)---------
	@GetMapping("/check.do")
	public String checkEmpId(@RequestParam String emp_id, @RequestParam String name, Model model, HttpSession session) {

		// 사원번호로 사원 조회
		EmployeeDto dto = service.findById(emp_id); // emp_id로 사원 조회

		log.info("조회된 사원: {}", dto); // 조회된 사원 확인

		// 사원 정보가 존재하는지 확인
		if (dto != null && dto.getName().equals(name)) {

			model.addAttribute("empIdExists", true); // 이메일 필드 보이도록 설정
			model.addAttribute("empName", dto.getName()); // 사원 이름 전달
			model.addAttribute("alertMessage", "확인");
			model.addAttribute("alertType", "success"); // 알림의 타입

			session.setAttribute("emp_id", emp_id);

			model.addAttribute("name", name); // 이름 파라미터 전달
		} else {
			// 사원번호나 이름이 일치하지 않으면
			model.addAttribute("empIdExists", false);
			model.addAttribute("alertMessage", "조회 불가");
			model.addAttribute("alertType", "error"); // 알림의 타입
		}

		return "forgot"; // 비밀번호 재설정 화면으로 이동
	}

	@PostMapping(value = "/resetPassword.do")
	public String resetPassword(@RequestParam Map<String, Object> map, HttpServletResponse response)
			throws IOException {

		response.setContentType("text/html; charset=UTF-8;");

		int n = service.modifyPw(map);
		System.out.print(map);

		if (n == 1) {
			response.getWriter().print("<script>alert('수정완료'); location.href='/';</script>");

		} else {
			response.getWriter().print("<script>alert('수정실패'); window.history.back();</script>");
		}

		return null;
	}

	@PostMapping("/updatePassword.do")
	public String updatePasswor(@RequestParam String newPassword, HttpSession session, HttpServletResponse response)
			throws IOException {

		response.setContentType("text/html; charset=UTF-8;");

		EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("emp_id", loginVo.getEmp_id());
		paramMap.put("password", newPassword);

		int result = service.modifyPw(paramMap);

		if (result == 1) {
			response.getWriter()
					.print("<script>alert('사인등록은 필수 입니다'); location.href='./signature_manage.do';</script>");
		}
		return null;
	}
}