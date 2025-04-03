package com.min.edu.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.min.edu.dto.DocumentDto;
import com.min.edu.dto.EmployeeDto;
import com.min.edu.dto.FileUpDto;
import com.min.edu.dto.LeaveDto;
import com.min.edu.dto.TripDto;
import com.min.edu.model.service.IApprovalService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ApprovalFormController {
	
	private final IApprovalService approvalService;

	// 결재문서 폼 이동------------------------------------------------------
	@GetMapping(value = "/vacationForm.do")   // 휴가 작성하기
	public String vacation_form(Model model, HttpSession session) {
		
		EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");
		String ename = loginVo.getName();
		
		return "vacationApproval";
	}

	@GetMapping(value = "/tripForm.do")    // 출장 작성하기
	public String trip_form() {
		return "tripApproval";
	}

	@GetMapping(value = "/reportForm.do")    // 보고서 작성하기
	public String report_form() {
		return "reportApproval";
	}
	
	// 결재문서 임시저장------------------------------------------------------
	@PostMapping(value = "/TempReport.do")
	public String report_temp(Model model, HttpSession session,
			@RequestParam(value = "title", required = false) String title,

								@RequestParam("content") String content,
								@RequestParam("doc_type") String doc_type,
								@RequestParam(value = "file", required = false) MultipartFile file,
								@RequestParam(value = "leave_start", required = false) String leave_start,
								@RequestParam(value = "leave_end", required = false) String leave_end,
								@RequestParam(value = "type", required = false) String type,
								@RequestParam(value = "trip_start", required = false) String trip_start,
								@RequestParam(value = "trip_end", required = false) String trip_end,
								@RequestParam(value = "destination", required = false) String destination,
								HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException {
		
		// 로그인 세션 불러오기
		EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");
		String name = loginVo.getName();
		
		if (title == null || title.trim().isEmpty()) {
		    response.getWriter().print("<script>alert('제목을 입력하세요.'); window.history.back();</script>");
		    return null;
		}

		
		// 공통 문서양식 저장
		DocumentDto dto = DocumentDto.builder()
					.name(name)
					.doc_type(doc_type)
					.title(title)
					.content(content)
					.build();
		
		int resultTemp = approvalService.insertSaveDoc(dto);
		int doc_id = approvalService.getDocId();
		
		if (doc_type.trim().equals("출장") && 
			    (trip_start != null && !trip_start.isEmpty()) || 
			    (trip_end != null && !trip_end.isEmpty()) || 
			    (destination != null && !destination.isEmpty())) {
			
			TripDto tripDto = TripDto.builder()
					.doc_id(doc_id)
					.trip_start(trip_start)
					.trip_end(trip_end)
					.destination(destination)
					.build();
			
			int resultTrip = approvalService.insertSaveTrip(tripDto);
			
			session.setAttribute("doc_id", doc_id);		
			
			return "redirect:/approval.do";
			
		} else if (doc_type.trim().equals("휴가") && 
		         (leave_start != null && !leave_start.isEmpty()) && 
		         (leave_end != null && !leave_end.isEmpty()) && 
		         (type != null && !type.isEmpty())) {
			
			LeaveDto leaveDto = LeaveDto.builder()
					.doc_id(doc_id)
					.leave_start(leave_start)
					.leave_end(leave_end)
					.type(type)
					.build();
			
			approvalService.insertSaveLeave(leaveDto);
			
			session.setAttribute("doc_id", doc_id);
			
			return "redirect:/approval.do";
			
		} else if(doc_type.trim().equals("보고서")) {

			if(file != null && !file.isEmpty()) {
				try {
					
					String uploadDir = request.getSession().getServletContext().getRealPath("fileup");
		            File uploadFolder = new File(uploadDir);
		            
		            if(!uploadFolder.exists()) {
		            	uploadFolder.mkdirs();
		            }
		            
		            String origin_name = file.getOriginalFilename();
		            String store_name = System.currentTimeMillis() + "_" + origin_name;
		            String file_path = uploadDir + File.separator + store_name;

		            File dest = new File(file_path);
		            file.transferTo(dest);

		            int size = (int) file.getSize();
		            
		            int resultTempFile = approvalService.insertTempFile(FileUpDto.builder()
						            		.doc_id(doc_id)
						            		.origin_name(origin_name)
						            		.store_name(store_name)
						            		.size(size)
						            		.file_path(file_path)
						            		.build());
		            
		            approvalService.updateTempFileExist(doc_id);
		            
		            model.addAttribute("origin_name", origin_name);
		            model.addAttribute("store_name", store_name);
		            model.addAttribute("file_path", file_path);
		            
				} catch (Exception e) {
					return "approval";
				}
			}
			
			session.setAttribute("doc_id", doc_id);		
			
			return "approval";
			
		}
		
		return "redirect:/approval.do";
		
	}
	
	// 보고서 상신------------------------------------------------------
	@PostMapping(value = "/ApprovalReport.do")
	public String report_approval(Model model,
								  HttpSession session,
								  HttpServletRequest request,
								  @RequestParam("title") String title,
								  @RequestParam("content") String content,
								  //@RequestParam("appLine") List<String> appLine,
								  @RequestParam(value = "appLine", required = false) List<String> appLine, 
								  @RequestParam("doc_type") String doc_type,
								  @RequestParam(value = "file", required = false) MultipartFile file,
								  @RequestParam(value = "doc_status", required = false) String doc_status,
							      @RequestParam(value = "docId", required = false) Integer docId,//임시저장함에서 받아온 doc_id
								  HttpServletResponse response) throws IOException {
		System.out.println("-------------------전달되는 보고서 입력 값 -----------------------");
		 response.setContentType("text/html; charset=UTF-8;");
		 
		 System.out.println("doc_status : " + doc_status);
		 System.out.println("docId : " + docId);
		
		String fileName = "";
		if(file != null && !file.isEmpty()) {
			fileName = file.getOriginalFilename();
		}
		EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");
		


		
		String emp_id = loginVo.getEmp_id();
		System.out.println("title : "+ title);
		System.out.println("content : " + content);
		System.out.println("appLine : " + appLine);
		System.out.println("doc_type : " + doc_type);
		System.out.println("file : " + fileName);
		
	    if (appLine == null || appLine.isEmpty()) {
	    	response.getWriter().print("<script>alert('결재선을 선택해주세요'); window.history.back();</script>");
	    	return null;
	    }
		
		
		
		Map<String, Object>  docMap =  new HashMap<String, Object>();
		docMap.put("doc_id", "");
		docMap.put("emp_id", emp_id);
		docMap.put("doc_type", doc_type);
		docMap.put("title", title);
		docMap.put("content", content);
		
		Map<String, Object>  appMap =  new HashMap<String, Object>();
		appMap.put("approval", appLine);
		appMap.put("doc_id", "");
		
		int result =  approvalService.insertDocument(docMap, appMap);
		System.out.println(result>0 ?"결재성공":"결재실패");
		

	    if ("T".equals(doc_status)) { //임시문서함에서 삭제를 하면 -> 삭제되고 결재가 상신됨
			String name = loginVo.getName();
			
			DocumentDto dto = DocumentDto.builder()
								.doc_id(docId)
								.name(name)
								.build();
	    	
	        int resultDoc = approvalService.deleteSaveDoc(dto); 
	    }
		
	
		
		if(result >0  && file != null && !file.isEmpty()) {
			try {
				int doc_id=  (Integer)docMap.get("doc_id");
				String uploadDir = request.getSession().getServletContext().getRealPath("fileup");
	            File uploadFolder = new File(uploadDir);
	            
	            if(!uploadFolder.exists()) {
	            	uploadFolder.mkdirs();
	            }
	            
	            String origin_name = file.getOriginalFilename();
	            String store_name = System.currentTimeMillis() + "_" + origin_name;
	            String file_path = uploadDir + File.separator + store_name;

	            File dest = new File(file_path);
	            file.transferTo(dest);

	            int size = (int) file.getSize();
	            
	            int resultTempFile = approvalService.insertTempFile(FileUpDto.builder()
					            		.doc_id(doc_id)
					            		.origin_name(origin_name)
					            		.store_name(store_name)
					            		.size(size)
					            		.file_path(file_path)
					            		.build());
	            
	            approvalService.updateTempFileExist(doc_id);
	       	
	      
	            
			} catch (Exception e) {
				return "approval";
			}
		}
		
		return "redirect:/approval.do";
	}
	
	
	
//	 휴가 상신------------------------------------------------------
	@PostMapping(value = "/leaveReport.do" )
	public String approvalLeave(Model model,
									  HttpSession session,
									  HttpServletRequest request,
									  @RequestParam("title") String title,
									  @RequestParam("content") String content,
									  @RequestParam(value = "appLine", required = false) List<String> appLine, 
									  @RequestParam("doc_type") String doc_type,
									  @RequestParam("leave_start") String leave_start,
									  @RequestParam("leave_end") String leave_end,
									  @RequestParam("type") String type, 
									  @RequestParam(value = "doc_status", required = false) String doc_status,
								      @RequestParam(value = "docId", required = false) Integer docId,//임시저장함에서 받아온 doc_id
									  HttpServletResponse response,
									  LeaveDto leaveDto
									  ) throws IOException {
		
		EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");
		 response.setContentType("text/html; charset=UTF-8;");
		 System.out.println("doc_status : " + doc_status);
		 System.out.println("docId : " + docId);
		 
		String emp_id = loginVo.getEmp_id();
		System.out.println("-------------------전달되는 보고서 입력 값 -----------------------");
		System.out.println("title : "+ title);
		System.out.println("content : " + content);
		System.out.println("appLine : " + appLine);
		System.out.println("doc_type : " + doc_type);
		System.out.println("-------------------전달되는 휴가 입력 값 -----------------------");
		System.out.println("leave_start : " + leave_start);
		System.out.println("leave_end : " + leave_end);
		System.out.println("type : " + type);
		
		 if (appLine == null || appLine.isEmpty()) {
		    	response.getWriter().print("<script>alert('결재선을 선택해주세요'); window.history.back();</script>");
		    	return null;
		    }
		 
		// 날짜 계산
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    LocalDate startDate = LocalDate.parse(leave_start, formatter);
	    LocalDate endDate = LocalDate.parse(leave_end, formatter);
	    
	    double leave_days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
	    if ("오전반차".equals(type) || "오후반차".equals(type)) {
	        leave_days = leave_days / 2;
	    }
	    
	    leaveDto = LeaveDto.builder()
	            .leave_start(leave_start)
	            .leave_end(leave_end)
	            .type(type)
	            .leave_days(leave_days)
	            .build();
		
		// 문서 저장
		Map<String, Object>  docMap =  new HashMap<String, Object>();
		docMap.put("doc_id", "");
		docMap.put("emp_id", emp_id);
		docMap.put("doc_type", doc_type);
		docMap.put("title", title);
		docMap.put("content", content);
		
		// 결재선 저장 
		Map<String, Object>  appMap =  new HashMap<String, Object>();
		appMap.put("approval", appLine);
		appMap.put("doc_id", "");
		
		// 공통문서 입력
		int result =  approvalService.insertDocumentLeave(docMap, appMap, leaveDto);
		  
		if ("T".equals(doc_status)) { //임시문서함에서 삭제를 하면 -> 삭제되고 상신됨
				String name = loginVo.getName();
				
				DocumentDto dto = DocumentDto.builder()
									.doc_id(docId)
									.name(name)
									.build();
		    	
				int resultLeave = approvalService.deleteSaveLeave(docId);
				int resultDoc = approvalService.deleteSaveDoc(dto);
		    }
		
		// 휴가 저장
//		Map<String, Object> leaMap = new HashMap<String, Object>();
//		leaMap.put("doc_id", "");
//		leaMap.put("leave_start", leave_start);
//		leaMap.put("leave_end", leave_end);
//		leaMap.put("type", type);
		
		System.out.println("leave_days : " + leave_days);
		System.out.println("result : " + result);
		
		return "redirect:/approval.do";
	}
	
	
	
	// 출장 상신------------------------------------------------------
	@PostMapping(value = "/tripReport.do" )
	public String trip_approval(Model model,
								  HttpSession session,
								  HttpServletRequest request,
								  @RequestParam("title") String title,
								  @RequestParam("content") String content,
								  @RequestParam(value = "appLine", required = false) List<String> appLine,
								  @RequestParam("doc_type") String doc_type,
								  @RequestParam(value = "doc_status", required = false) String doc_status,
							      @RequestParam(value = "docId", required = false) Integer docId,//임시저장함에서 받아온 doc_id
								  HttpServletResponse response,
								  TripDto tripDto) throws IOException {
		EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");
		 response.setContentType("text/html; charset=UTF-8;");
		 System.out.println("doc_status : " + doc_status);
		 System.out.println("docId : " + docId);
		 
		String emp_id = loginVo.getEmp_id();
		System.out.println("-------------------전달되는 보고서 입력 값 -----------------------");
		System.out.println("title : "+ title);
		System.out.println("content : " + content);
		System.out.println("appLine : " + appLine);
		System.out.println("doc_type : " + doc_type);
		
		System.out.println("-------------------전달되는 출장 입력 값 -----------------------");
		System.out.println("trip_start : " + tripDto.getTrip_start());
		System.out.println("trip_end : " + tripDto.getTrip_end());
		System.out.println("destination : " + tripDto.getDestination());
		
		 if (appLine == null || appLine.isEmpty()) {
		    	response.getWriter().print("<script>alert('결재선을 선택해주세요'); window.history.back();</script>");
		    	return null;
		    }
		
		// 문서 저장
				Map<String, Object>  docMap =  new HashMap<String, Object>();
		docMap.put("doc_id", "");
		docMap.put("emp_id", emp_id);
		docMap.put("doc_type", doc_type);
		docMap.put("title", title);
		docMap.put("content", content);
		
		// 결재선 저장 
		Map<String, Object>  appMap =  new HashMap<String, Object>();
		appMap.put("approval", appLine);
		appMap.put("doc_id", "");		
		
		
		int result = approvalService.insertDocumentTrip(docMap, appMap, tripDto);
		if ("T".equals(doc_status)) { //임시문서함에서 삭제를 하면 -> 삭제되고 상신됨
			String name = loginVo.getName();
			
			DocumentDto dto = DocumentDto.builder()
								.doc_id(docId)
								.name(name)
								.build();
	    	
			int resultTrip = approvalService.deleteSaveTrip(docId);
			int resultDoc = approvalService.deleteSaveDoc(dto);
	    }
		
		
		
		System.out.println(result);
		
		return "redirect:/approval.do";
	}
	
	// 결재선------------------------------------------------------
	@GetMapping(value = "/tree.do")
	public String tree() {
		return "tree";
	}
	
	public String getMethodName(@RequestParam String param) {
		return new String();
	}
	
}
