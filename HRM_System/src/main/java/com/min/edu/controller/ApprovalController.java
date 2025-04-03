package com.min.edu.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpHeaders;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.min.edu.dto.ApprovalDto;
import com.min.edu.dto.DocumentDto;
import com.min.edu.dto.EmployeeDto;
import com.min.edu.dto.FileUpDto;
import com.min.edu.dto.LeaveDto;
import com.min.edu.dto.RejectionDto;
import com.min.edu.dto.SignDto;
import com.min.edu.dto.TripDto;
import com.min.edu.model.service.IApprovalService;
import com.min.edu.model.service.ILeaveService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ApprovalController {

	private final IApprovalService service;
	
	private final ILeaveService leaveService;
	
	@GetMapping(value = "/approval.do")
	public String approval_move(Model model,
								HttpSession session,
								HttpServletRequest request) {
		
		EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");
		String name = loginVo.getName();
		String emp_id = loginVo.getEmp_id(); 
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("emp_id", emp_id);
		
		List<ApprovalDto> lists = service.selectPreviewDoc(map);
		List<ApprovalDto> approvalList = service.getApprovalList(emp_id); 
		List<ApprovalDto> successlists = service.selectSuccessDoc(map);

		model.addAttribute("approvalList", approvalList);
		model.addAttribute("lists",lists);
		model.addAttribute("successlists", successlists);
		
		return "approval";
	}
	
	// 결재문서 결재 수신함------------------------------------------------------------
	@GetMapping(value = "/approval_receive.do")
	public String approvalReceive(HttpSession session, Model model) {
	    EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");  
	    String emp_id = loginVo.getEmp_id(); 
	    
	    List<ApprovalDto> approvalList = service.getApprovalList(emp_id); 
	    model.addAttribute("approvalList", approvalList); 
	    

	    return "approval_receive";  
	}
	
	//전자결재 상세정보
	@GetMapping("/approval_detail.do")
	public String approvalDetail(@RequestParam("doc_id") String doc_Id, 
			                      @RequestParam("apprv_id")String apprv_id,
	                             Model model,HttpSession session) {
		log.info("{}",doc_Id);
		log.info("{}",apprv_id);
		model.addAttribute("docId", doc_Id);
		model.addAttribute("apprv_id", apprv_id);
		

	   // 문서정보 // 
	    DocumentDto document = service.getApprovalDetail(doc_Id);
	    List<ApprovalDto> approvalList = service.geteApproval(doc_Id);
	    
	    
	    //기안자 정보 
	    int doc_id = Integer.parseInt(doc_Id);
		EmployeeDto empdto = service.getApp(doc_id);
		model.addAttribute("empdto",empdto);
		
		
		
		//2025 03 15 각 결재자마다 사인 찍기
		EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");
	    String empId = loginVo.getEmp_id();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("docId", doc_id);   
		paramMap.put("empId", empId);   

		List<EmployeeDto> signatures = service.getApproverSignatures(paramMap);
		model.addAttribute("signatures",signatures);
		log.info("{}",signatures);

		       
  
	    if ("휴가".equals(document.getDoc_type())) {
	        LeaveDto leaveDto = service.continuePreviewLeave(Integer.parseInt(doc_Id)); 
	        model.addAttribute("leaveDto", leaveDto);
	        
	    } else if ("출장".equals(document.getDoc_type())) {
	    	 log.info(document.getDoc_type());
	    	
	        TripDto tripDto = service.continuePrviewTrip(Integer.parseInt(doc_Id)); 
	        model.addAttribute("tripDto", tripDto);
	        log.info("{}",tripDto);
	    }
	    
	    
	    model.addAttribute("approvalList", approvalList);  
	    log.info("approvalList {}",approvalList);
	    model.addAttribute("documentDto", document);
	    
	    
	    return "approval_detail";  
	}

	
	@PostMapping(value = "/updateApprov.do")
	public String updateApproval(HttpSession session,@RequestParam int doc_id,
			                   @RequestParam int apprv_id,
			                   @RequestParam String apprv_level,
			                   HttpServletResponse response) throws IOException, InterruptedException {
		
		 response.setContentType("text/html; charset=UTF-8;");
		EmployeeDto edto = (EmployeeDto)session.getAttribute("loginVo");
		String emp_id = edto.getEmp_id();
		
		ApprovalDto dto = ApprovalDto.builder()
				          .emp_id(emp_id)
				          .doc_id(doc_id)
				          .apprv_id(apprv_id)
				          .build();
		
		int level = Integer.parseInt(apprv_level);
		log.info("레벨레벨레벨레벨레벨레벨 : {}",level);
		int n = service.updateApprovalStatus(dto);
		
		int m= Integer.parseInt(service.selectApprovalLast(apprv_id)); //1. 본인의 승인순서가 마지막인지 확인
		log.info("{}",m);
		
		if(m==service.selectApprovalMax(doc_id)) {//2. 본인의 순서랑 결재할 문서의 마지막 승인번호 비교
			service.updateDocumentStatus(doc_id); //3. 마지막 결재자까지 결재가 완료되면 승인상태가 Y로 바뀜 
			String docType = service.getDocType(doc_id);
			System.out.println("docType : " + docType);
			if("휴가".equals(docType)) {
				leaveService.updateVacationLeave(doc_id);
			}
		}
		
		// 위의 if 문을 먼저 하고 아래 if 문 실행 
		if (n == 1) {
			response.getWriter().print("<script>alert('승인완료'); location.href='./approval_receive.do';</script>");

		} else {
			response.getWriter().print("<script>alert('승인실패'); window.history.back();</script>");

		}
		return null;
	}
	
	
	
	
	
	
	
	@PostMapping(value = "/approvalRejection.do")
	public String approvalRejection(HttpSession session,
									HttpServletResponse response,
									@RequestParam int apprv_id,
									RejectionDto rejDto) {
		EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");  
	    String reject_name = loginVo.getName(); 
	    rejDto.setReject_name(reject_name);
		log.info("rejDto : {}", rejDto);
		log.info("apprv_id : {}", apprv_id);
		
		service.approvalRejection(apprv_id, rejDto);
		
		return "redirect:/approval_receive.do";
	}
	

	//전자결재 문서의 파일 다운로드 
	@GetMapping(value = "/downloadFile.do")
	public ResponseEntity<byte[]> FreeFileDownload(HttpServletRequest request, 
							         			   HttpServletResponse response,
							         			   @RequestParam("doc_id") int doc_id,
							         			   Model model) throws IOException {
			
		FileUpDto files = service.getReportFileById(doc_id);
		log.info("{}",files);

		 // 파일 저장 경로 설정 (절대 경로를 사용하는 방식)
	    String uploadDir = System.getProperty("user.dir") + File.separator + "src\\main\\webapp\\fileup";  // 절대 경로로 관리
	    
	    System.out.println(uploadDir);
	    System.out.println(files.getStore_name());
	    System.out.println(files.getOrigin_name());
	    
	    File file = new File(uploadDir, files.getStore_name());

	    // 파일이 존재하지 않을 경우 예외 발생 (파일 없음 방지)
	    if (!file.exists()) {
	        throw new FileNotFoundException("파일을 찾을 수 없습니다: " + file.getAbsolutePath());
	    }

	    System.out.println(file);
	    // 파일을 바이트 배열로 변환
	    byte[] fileBytes = FileCopyUtils.copyToByteArray(file);

	    // 파일명 인코딩 (브라우저 호환성 고려)
	    String encodedFileName = URLEncoder.encode(files.getOrigin_name(), StandardCharsets.UTF_8)
	            .replaceAll("\\+", "%20"); // 공백 처리


	    // 파일을 브라우저에 응답해준다.
	    response.setHeader("Content-Disposition", "attachment; filename=\""+encodedFileName+"\"");
		response.setContentLength(fileBytes.length);
		response.setContentType("application/octet-stream");
		
		model.addAttribute("file", file);

	    // 파일 데이터 응답 반환
	    return ResponseEntity.ok().body(fileBytes);
	}
	
	
	
	
	
	// 결재문서 내 결재함------------------------------------------------------------
	@GetMapping(value = "/approval_mine.do")

	public String approval_mine(HttpSession session, Model model) {
		 EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo"); 
		  String emp_id = loginVo.getEmp_id(); 
		  
		  List<DocumentDto> docList = service.selectApprvMine(emp_id);
		  model.addAttribute("docList",docList);

		return "approval_mine";
	}
	
	
	
	// 결재문서 임시 저장함------------------------------------------------------------
	@GetMapping(value = "/temp_store.do")
	public String approval_store(Model model,
								HttpSession session,
								HttpServletRequest request) {
		
		EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");
		String emp_id = loginVo.getEmp_id();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("emp_id", emp_id);
		
		List<ApprovalDto> lists = service.selectPreviewDoc(map);
		
		model.addAttribute("lists",lists);
		
		return "temp_store";
	}
	
	// 결재문서 임시저장 (보기)
	@GetMapping(value = "/continueTemp.do")
	public String temp_Reportcontinue(@RequestParam("doc_id") int docId, 
									  @RequestParam("doc_type") String doc_type,
									  Model model, 
									  HttpSession session) {
		log.info("continueTemp.do GETMapping 호출");
		EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");
		String name = loginVo.getName();
		String empId = loginVo.getEmp_id();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("doc_id", docId);
		
		// 공통 문서 
		DocumentDto reportDto = service.continuePriview(docId);
		System.out.println(reportDto);
		
		// 출장
		TripDto tripDto = service.continuePrviewTrip(docId);
		System.out.println(tripDto);

		// 휴가
		LeaveDto leaveDto = service.continuePreviewLeave(docId);
		System.out.println(leaveDto);
		
		model.addAttribute("reportDto",reportDto);
		model.addAttribute("tripDto",tripDto);
		model.addAttribute("leaveDto",leaveDto);

		if(doc_type.trim().equals("보고서")) {
			return "continueReport";
		} else if(doc_type.trim().equals("출장")) {
			return "continueTrip";
		} else {
			System.out.println("leaveDto: " + leaveDto); 
			return "continueLeave";
		}
	}
	
	
	
	
	// 결재문서 임시저장 삭제하기
	@GetMapping(value = "/deleteTemp.do")
	public String temp_delete(@RequestParam("doc_id") int docId, 
							  @RequestParam("doc_type") String doc_type,
							  Model model, 
							  HttpSession session) {
		log.info("deleteTemp.do GET매핑");
		
		EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");
		String name = loginVo.getName();
		
		DocumentDto dto = DocumentDto.builder()
					.doc_id(docId)
					.name(name)
					.build();
		
		if(doc_type.trim().equals("출장")) {
			int resultTrip = service.deleteSaveTrip(docId);
			int resultDoc = service.deleteSaveDoc(dto);
		} else if(doc_type.trim().equals("휴가")) {
			int resultLeave = service.deleteSaveLeave(docId);
			int resultDoc = service.deleteSaveDoc(dto);
		} else if(doc_type.trim().equals("보고서")) {
			int resultDoc = service.deleteSaveDoc(dto);
		}
		
		return "redirect:/temp_store.do";
	}
	
	
	// 결재문서 부서 문서함------------------------------------------------------------
	@GetMapping(value = "/dept_store.do")
	public String dept_store(Model model,
							HttpSession session,
							HttpServletRequest request) {

		EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");
		String emp_id = loginVo.getEmp_id();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("emp_id", emp_id);

		List<ApprovalDto> lists = service.selectSuccessDoc(map);

		model.addAttribute("lists", lists);
		return "dept_store";
	}
	
	// 결재완료문서 보기
	@GetMapping(value = "/successDocView.do")
	public String successDoc_View(@RequestParam("doc_id") int docId, 
								  @RequestParam("doc_type") String doc_type,
								  Model model, 
								  HttpSession session) {
		
		EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");
		String empId = loginVo.getEmp_id();
		String name = loginVo.getName();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("doc_id", docId);
		
		// 공통 문서 
		List<ApprovalDto> reportDto = service.continuePreviewDoc(map);
		System.out.println(reportDto);
		
		// 출장
		TripDto tripDto = service.continuePrviewTrip(docId);
		System.out.println(tripDto);

		// 휴가
		LeaveDto leaveDto = service.continuePreviewLeave(docId);
		System.out.println(leaveDto);
		
		model.addAttribute("reportDto",reportDto);
		model.addAttribute("tripDto",tripDto);
		model.addAttribute("leaveDto",leaveDto);

		if(doc_type.trim().equals("보고서")) {
			return "successReport";
		} else if(doc_type.trim().equals("출장")) {
			return "successTrip";
		} else {
			return "successLeave";
		}
		
	}
	
	// 서명------------------------------------------------------------
	@GetMapping(value = "/signature_manage.do")
	public String signature_manage() {
		return "signature_manage";
	}
	
	// 서명 만들기
	@PostMapping(value = "/mySignature.do")
	@ResponseBody
	public Map<String, String> my_signature(@RequestBody Map<String, String> requestData,
							   RedirectAttributes attributes,
							   Model model, HttpSession session) {
		
		String base64Sig = requestData.get("sign");
		
		EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");
		
		Map<String, String> response = new HashMap<>();
		
		if(loginVo != null) {
			String empId = loginVo.getEmp_id();
			String empName = loginVo.getName();
		
			SignDto dto = new SignDto().builder()
					.sign(base64Sig)
					.name(empName)
					.emp_id(empId) 
					.build();
			
			int signSave = service.insertSign(dto);
			
			model.addAttribute("signSaved",base64Sig); 
			System.out.println("signSaved : " + base64Sig);

			session.removeAttribute("loginVo");
			
			EmployeeDto removedVo = (EmployeeDto)session.getAttribute("loginVo");
			
			response.put("redirect", "/");
			
		} else {
			response.put("message", "로그인이 필요합니다.");
		}
		
		return response;
	}
	
	// 서명 조회하기
	@GetMapping(value = "/select_signature.do")
	public String select_signature(Model model, HttpSession session) {
		
		SignDto signSaved = (SignDto)session.getAttribute("signSaved");
		
		if(signSaved != null) {
			System.out.println("signSaved : " + signSaved);
			model.addAttribute("signSaved",signSaved);
		}
		
		List<SignDto> lists = service.selectSign();
		
		model.addAttribute("lists",lists);
		
				
		return "signature_manage";
	}
	
	// 서명 삭제하기
	@PostMapping(value = "/deleteSignature.do")
	@ResponseBody
	public Map<String, String> delete_Signature(HttpSession session,
							@RequestBody Map<String, String> requestData) {

		EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");

		Map<String, String> response = new HashMap<>();
		
	    if (loginVo != null) {
	    	
	        int result = service.deleteSign(loginVo.getName());

	        loginVo.setSignSaved(null);
	        session.setAttribute("loginVo", loginVo);
	        
	        response.put("redirect", "/signature_manage.do");
	        
	    } else {
	    	response.put("message", "로그인이 필요합니다.");
	    }
	    
	    return response;
	}

	
	
}
