package com.min.edu.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.min.edu.dto.EmployeeDto;
import com.min.edu.model.service.IEmployeeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MypageController {

	private final IEmployeeService service;
	
	 @GetMapping(value = "/mypage.do")
	    public String myPage(HttpSession session, Model model) {
	        // 세션에서 로그인한 사용자의 사원번호를 가져옴
		 EmployeeDto dto = (EmployeeDto)session.getAttribute("loginVo");
	      String emp_id = dto.getEmp_id();
	      log.info(emp_id);
	      EmployeeDto employee = service.getOne(emp_id);
	        
	        
	        model.addAttribute("employee", employee);

	        
	        
	        
	       
	        return "myPage";
	    }


	@PostMapping("/profileUpload.do")
	@ResponseBody
	public Map<String, Object> uploadProfile(@RequestParam("profileImage") MultipartFile profileImage,
	                                         @RequestParam("emp_id") String empId,
	                                         HttpServletRequest request) {
	    Map<String, Object> response = new HashMap<>();

	    if (profileImage.isEmpty()) {
	        response.put("status", "error");
	        response.put("message", "파일을 선택하세요.");
	        return response;
	    }

	    try {
	        // 파일명 생성 (UUID 활용)
	        String originalFileName = profileImage.getOriginalFilename();
	        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
	        String newFileName = UUID.randomUUID().toString() + extension;

	        // 서버 내 저장 경로 설정
	        String uploadDir = request.getServletContext().getRealPath("/upload/profile");
	        File uploadDirectory = new File(uploadDir);

	        if (!uploadDirectory.exists()) {
	            uploadDirectory.mkdirs(); // 경로 없으면 생성
	        }

	        // 파일 저장
	        File destinationFile = new File(uploadDir, newFileName);
	        profileImage.transferTo(destinationFile);

	        // DB에 저장될 경로 (웹 접근 경로)
	        String profileImagePath = "/upload/profile/" + newFileName;

	        // DB 업데이트 로직
	        Map<String, Object> paramMap = new HashMap<>();
	        paramMap.put("emp_id", empId);
	        paramMap.put("profile_image", profileImagePath);
	        service.updateProfile(paramMap);

	        // 성공 응답
	        response.put("status", "success");
	        response.put("profile_image_path", profileImagePath);

	    } catch (IOException e) {
	        log.error("파일 업로드 실패", e);
	        response.put("status", "error");
	        response.put("message", "파일 업로드 중 오류 발생.");
	    }

	    return response; // JSON 형태로 응답
	}
	
	@PostMapping(value = "/mypageupload.do")
	public String mypageUpload(@RequestParam String name,
			@RequestParam String phone,
			@RequestParam String tel,
			@RequestParam String email,
			@RequestParam String birth,
			HttpSession session,HttpServletResponse response) throws IOException{
		response.setContentType("text/html; charset=UTF-8;");
		 EmployeeDto dto = (EmployeeDto)session.getAttribute("loginVo");
		 String emp_id = dto.getEmp_id();
		 log.info("MypageController의 로그인 된 사원번호 : {}",emp_id);
		 
		 EmployeeDto inVo = EmployeeDto.builder()
				 			.name(name)
				 			.phone(phone)
				 			.tel(tel)
				 			.email(email)
				 			.birth(birth)
				 			.emp_id(dto.getEmp_id())
				 			.build();
		 
		 int n = service.modifyUserInfo(inVo);
		 
		 // 수정 성공 여부에 따라 다른 결과 처리
		    if (n == 1) {
		    	 response.getWriter().print("<script>alert('수정완료'); location.href='./mypage.do';</script>");
		    } else {
		    	response.getWriter().print("<script>alert('수정실패'); location.href='./mypage.do';</script>");  // 실패 시 에러 페이지로 리다이렉트
		    }
			return null;
	}
			

}













