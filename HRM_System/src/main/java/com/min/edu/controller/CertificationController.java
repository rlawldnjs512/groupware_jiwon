	package com.min.edu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.min.edu.dto.CertificateDto;
import com.min.edu.dto.EmpPageDto;
import com.min.edu.dto.EmployeeDto;
import com.min.edu.model.service.ICertificateService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CertificationController {

	private final ICertificateService service;
	
//	@GetMapping(value = "/mypage.do")
//	public String mypage_cert_move() {
//		return "mypage";
//	}
	
	@GetMapping(value = "/certification.do")
	public String cert_move(HttpSession session, Model model,
							HttpServletRequest req) {
	    String cert_num = (String) session.getAttribute("cert_num");

	    if (cert_num != null) {
	        System.out.println("cert_num from session: " + cert_num);  // 👉 디버깅 출력
	    } else {
	        System.out.println("cert_num is null in session!");
	    }

	    model.addAttribute("cert_num", cert_num);  // JSP로 전달

	    return "certification"; 
	}
	
	@GetMapping(value = "/select.do")
	public String certSelect(Model model, HttpSession session, 
	                         @RequestParam(value = "type", defaultValue = "defaultType") String type,
	                         HttpServletRequest req, 
	                         @RequestParam(value = "emp_id", required = false) String emp_id) {  // emp_id로 변경

	    EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");

	    if (loginVo != null) {
	        String loggedInEmpId = loginVo.getEmp_id();  // 로그인한 사원번호 가져오기
	        String role = loginVo.getRole();  // 로그인한 사용자의 역할 (관리자 체크)

	        System.out.println("empId from session: " + loggedInEmpId);

	        // map 생성
	        Map<String, Object> map = new HashMap<>();
	        map.put("emp_id", loggedInEmpId);  // 로그인한 사원번호를 map에 추가
	        map.put("type", type);

	        // 페이지 정보 가져오기 (기본값: 1)
	        String pageParam = req.getParameter("page");
	        if (pageParam == null) {
	            pageParam = "1";
	        }
	        int selectPage = Integer.parseInt(pageParam);
	        int totalPages = 0;

	        // EmpPageDto 생성하여 페이지 관련 정보 설정
	        EmpPageDto d = new EmpPageDto();

	        // 전체 데이터 개수 가져오기 (selectCertTypeUser가 아닌 전체 데이터 카운트)
	        // 전체 페이지 수 계산 (전체 데이터 수 / 페이지 크기)
	        if ("A".equals(role)) {
	        	if (emp_id != null && !emp_id.trim().isEmpty()) {
	        		d.setTotalCount(service.countCertAdminId(map));
	        		totalPages = service.countCertAdminId(map) == 0 ? 1 : d.getCountPage();
	        		d.setTotalPage(totalPages);
	        	} else {
	        		d.setTotalCount(service.countCertAdminType(map));
	        		totalPages = service.countCertAdminType(map) == 0 ? 1 : d.getCountPage();
	        		d.setTotalPage(totalPages);
	        	}
	        	d.setCountList(10);
	        	d.setCountPage(5);
	        } else {
	        	d.setTotalCount(service.countCert(map)); 
	        	totalPages = service.countCert(map) == 0 ? 1 : d.getTotalCount();
	 	        d.setTotalPage(totalPages);
	        	d.setCountList(2);  // 한 페이지에 표시될 글 갯수
	        	d.setCountPage(5);
	        }
	        
		    
	        d.setPage(selectPage);  // 현재 페이지 설정
	        d.setStagePage(d.getPage());  // 현재 페이지 그룹의 시작 번호 계산

	        d.setEndPage();  // 기존의 setEndPage() 사용하여 처리

	        // 페이징 처리를 위한 first, last 값 계산
	        int first = (d.getPage() - 1) * d.getCountList() + 1;  // 시작 번호
			int last = d.getPage() * d.getCountList();             // 끝 번호

	        // 마지막 페이지 번호는 totalCount를 초과할 수 없으므로, 제한
	        if ("A".equals(role)) {
	        	if (emp_id != null && !emp_id.trim().isEmpty()) {
	        		last = Math.min(last, service.countCertAdminId(map));
	        	} else {
	        		last = Math.min(last, service.countCertAdminType(map));
	        	}
	        } else {
	        	last = Math.min(last, service.countCert(map)); 
	        }

	        map.put("first", first);
	        map.put("last", last);

	        List<CertificateDto> lists = new ArrayList<CertificateDto>();
	        
	        if ("A".equals(role)) {
	        	lists = service.selectCertTypeAdminPage(map);
	        } else {
	        	lists = service.selectCertTypeUserPage(map);
	        }
	        

	        System.out.println("Lists size: " + (lists == null ? 0 : lists.size()));  // 리스트의 크기 출력
	        System.out.println("first:" + first);
	        System.out.println("last:" + last);
	        System.out.println("totalPage:" + totalPages);
	        System.out.println("countCert:" + service.countCert(map));
	        System.out.println("getPage:" + d.getPage());

	        // 모델에 데이터 추가
	        model.addAttribute("lists", lists);
	        model.addAttribute("page", d);
	        model.addAttribute("type", type);
	        model.addAttribute("emp_id", emp_id);

	        // 페이징 표시 여부 결정 (리스트가 없을 경우, 페이징을 표시하지 않음)
	        if (lists.isEmpty()) {
	            model.addAttribute("noPagination", true);
	        } else {
	        	model.addAttribute("noPagination",false);
	        }

	    } else {
	        model.addAttribute("error", "로그인이 필요합니다.");
	    }

	    return "certification";
	}

	@PostMapping(value = "/pdf.do")
	public String pdf(@RequestParam("certificateType") String certificateType, 
	                  @RequestParam("reason") String reason,
	                  HttpSession session, RedirectAttributes redirectAttributes) {
	    
	    EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");
	    
	    if (loginVo == null) {
	        redirectAttributes.addFlashAttribute("errorMessage", "로그인이 필요합니다.");
	        return "redirect:/login"; 
	    }

	    String ename = loginVo.getName();

	    CertificateDto certificateVo = CertificateDto.builder()
	            .name(ename)
	            .type(certificateType)
	            .reason(reason)
	            .build();

	    int result = service.insertCert(certificateVo);
	    
	    if (result <= 0) {
	        redirectAttributes.addFlashAttribute("errorMessage", "증명서 신청에 실패했습니다.");
	        return "redirect:/certification"; 
	    }

	    String cert_num = service.getCertNum(ename);
	    if (cert_num == null) {
	        redirectAttributes.addFlashAttribute("errorMessage", "발급번호를 가져오는 데 실패했습니다.");
	        return "redirect:/certification";
	    }

	    certificateVo.setCert_num(cert_num);

	    System.out.println("Saving cert_num in session: " + cert_num);
	    session.setAttribute("cert_num", cert_num);

	    return "redirect:/certification.do";
	}
	
	@PostMapping("/updateDownload")
	public ResponseEntity<String> updateDownloadStatus(@RequestParam("certNum") String certNum) {
	    try {
	        // 다운로드 상태를 'Y'로 업데이트
	        boolean isUpdated = service.updateDownload(certNum);

	        // 업데이트 성공 여부에 따라 응답 처리
	        if (isUpdated) {
	            return ResponseEntity.ok("다운로드 상태가 업데이트되었습니다.");
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 인증서가 존재하지 않거나 이미 다운로드되었습니다.");
	        }
	    } catch (Exception e) {
	        // 예외 처리: 서버 오류 발생 시
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
	    }
	}
	
	@GetMapping(value = "/status.do")
	public String status_Accept(
	        @RequestParam("emp_id") String emp_id,  // 사원 ID
	        @RequestParam("cert_status") String cert_status,  // 증명서 상태
	        @RequestParam("cert_num") String cert_num,  // 증명서 번호
	        Model model) {

	    // 파라미터 값 검증
	    if (emp_id == null || emp_id.isEmpty() || cert_status == null || cert_status.isEmpty() || cert_num == null || cert_num.isEmpty()) {
	        model.addAttribute("error", "필수 파라미터가 누락되었습니다.");
	        return "redirect:/errorPage";  // 오류 페이지로 리디렉션 (혹은 다른 적합한 페이지)
	    }

	    // CertificateDto 객체 생성
	    CertificateDto dto = CertificateDto.builder()
	                                       .emp_id(emp_id)
	                                       .cert_status(cert_status)
	                                       .cert_num(cert_num)
	                                       .build();

	    try {
	        // 증명서 승인 처리
	        service.updateCertAccept(dto);
	        model.addAttribute("message", "증명서 승인 완료!");
	    } catch (Exception e) {
	        model.addAttribute("error", "승인 처리 중 오류가 발생했습니다.");
	        e.printStackTrace();  // 에러 로그 출력
	    }

	    return "redirect:/select.do?emp_id=" + emp_id;
	}






	
	
}
