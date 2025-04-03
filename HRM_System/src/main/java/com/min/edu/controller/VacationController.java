package com.min.edu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.min.edu.dto.EmpPageDto;
import com.min.edu.dto.EmployeeDto;
import com.min.edu.dto.LeaveDto;
import com.min.edu.dto.VacationDto;
import com.min.edu.model.service.ILeaveService;
import com.min.edu.model.service.IVacationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class VacationController {

	private final IVacationService vacationService;
	private final ILeaveService leaveService;
	
	@GetMapping(value = "/vacation")
	public String vacationListByEmpId(HttpSession session, Model model) {
	    EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");
	    String empId = loginVo.getEmp_id();

	    Map<String, Object> vacationMap = vacationService.vacationListByEmpId(empId);
	    List<Map<String, Object>> leaveList = leaveService.leaveListByEmpId(empId, null, null);

	    model.addAttribute("vacationMap", vacationMap);
	    model.addAttribute("leaveList", leaveList);

	    return "vacation";
	}

	@RequestMapping(value = "/vacation/filter", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> filterVacationList(HttpSession session,
	                                                    @RequestParam(required = false) String startDate,
	                                                    @RequestParam(required = false) String endDate) {
	    EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");
	    String empId = loginVo.getEmp_id();

	    if (startDate == null || endDate == null) {
	        return leaveService.leaveListByEmpId(empId, null, null); // 전체 데이터 조회
	    } else {
	        return leaveService.leaveListByEmpId(empId, startDate, endDate); // 날짜 범위에 맞는 데이터 조회
	    }
	}

	@GetMapping(value = "/vacation_admin")
	public String vacation_admin(HttpSession session, Model model, HttpServletRequest req,
								 @RequestParam(value = "type",required = false) String type,
								 @RequestParam(value = "keyword", required = false) String keyword) {
		
		String pageParam = req.getParameter("page");
 	    int selectPage = (pageParam == null) ? 1 : Integer.parseInt(pageParam);
		
		EmpPageDto pageDto = new EmpPageDto();
		pageDto.setCountList(10); // 한 페이지에 표시될 로우 개수
		pageDto.setCountPage(5); // 한 번에 표시될 페이지 개수
		
 	    // Map을 사용하여 first, last 값을 담아 전달
 	    Map<String, Object> map = new HashMap<>();
 	    if (keyword != null && !keyword.trim().isEmpty()) {
	    	map.put("type", type);
	        map.put("keyword", keyword);
	    }
 	    
 	    int first = (selectPage - 1) * pageDto.getCountList() + 1;
 	    int last = selectPage * pageDto.getCountList();
 	    
 	    map.put("first", first);
 	    map.put("last", last);
 	    
 	    // 전체 로우 개수 조회 후, totalPage 계산
 	    int totalCount = leaveService.countLeavePage(map); // 전체 로우 개수
 	    pageDto.setTotalCount(totalCount); // 전체 로우 개수 설정
 	    pageDto.setTotalPage(totalCount); // 전체 페이지 수 계산

 	    // 현재 페이지 설정
 	    pageDto.setPage(selectPage); // 현재 페이지 설정
 	    pageDto.setStagePage(pageDto.getPage()); // 페이지 그룹 시작 번호 계산
 	    pageDto.setEndPage(); // 페이지 그룹 끝 번호 계산
 	    
 	    List<Map<String, Object>> leaveLists = leaveService.selectLeavePage(map);
 	    
 	    model.addAttribute("leaveLists", leaveLists);
 	    model.addAttribute("page", pageDto);
 	    model.addAttribute("type", type);
 	    model.addAttribute("keyword", keyword);
 	    model.addAttribute("first", first);
 	    model.addAttribute("last", last);
 	   
		return "vacation_admin";
	}
	
	
//	@PostMapping(value = "/insertVacation") 
//	public String insertVacation(@RequestParam("leaveId") String leaveId,
//								 @RequestParam("empId") String empId,
//								 @RequestParam("startDate") String startDate,
//								 @RequestParam("endDate") String endDate,
//								 @RequestParam("leaveTotal") String leaveTotal,
//								 @RequestParam("leaveUse") String leaveUse,
//								 @RequestParam("leaveRemain") String leaveRemain) {
//		
//		log.info("{} 사원에게 연차 부여", empId);
//		
//		Map<String, Object> map = new HashMap<>();
//		map.put("leaveId", leaveId);
//		map.put("empId", empId);
//		map.put("startDate", startDate);
//		map.put("endDate", endDate);
//		
//		// String -> double 변환
//		map.put("leaveTotal", Double.parseDouble(leaveTotal));
//		map.put("leaveUse", Double.parseDouble(leaveUse));
//		map.put("leaveRemain", Double.parseDouble(leaveRemain));
//		
//		vacationService.insertVacation(map);
//		
//		return "redirect:/vacationList"; 
//	}
	

}














