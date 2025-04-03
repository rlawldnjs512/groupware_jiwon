package com.min.edu.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.min.edu.dto.EmpPageDto;
import com.min.edu.dto.EmployeeDto;
import com.min.edu.dto.FreeboardDto;
import com.min.edu.dto.NoticeboardDto;
import com.min.edu.model.service.IApprovalService;
import com.min.edu.model.service.IAttendanceService;
import com.min.edu.model.service.IBoardService;
import com.min.edu.model.service.IEmployeeService;
import com.min.edu.model.service.IReservationService;
import com.min.edu.model.service.IVacationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {
	
	private final IEmployeeService employeeService;
	private final IAttendanceService attendanceService;
	private final IVacationService vacationService;
	private final IReservationService reservationService;
	private final IBoardService boardService;
	private final IApprovalService approvalService;
	
	@GetMapping(value = "/homeList.do")
	public String homeList(Model model, HttpServletResponse response, HttpSession session, HttpServletRequest req) {
		log.info("EmployeeController homeList GET 요청");

		////////////////////////// 사원 로그인 시 //////////////////////////
		
		// 캐시 삭제 코드 작성
//		response.setHeader("Pragma", "no-cache");
//		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
//		response.setHeader("Expires", "0");
		
		// 세션에서 로그인 정보 가져오기
	    EmployeeDto loginVo = (EmployeeDto)session.getAttribute("loginVo");
	    String empId = loginVo.getEmp_id();
	    String empName = loginVo.getName();
	    String deptName = employeeService.selectDeptName(empId);
	    String profileImg = employeeService.selectProfileImg(empId);
	    
	    model.addAttribute("empName", empName);
	    model.addAttribute("deptName", deptName);
	    model.addAttribute("profileImg", profileImg);
	    
	    // 출근 여부 확인
	    boolean isClockedIn = attendanceService.checkAttendance(empId);
	    model.addAttribute("isClockedIn", isClockedIn);
	    
	    // 보상시간 조회
 		int extraTime = vacationService.selectExtraTime(empId);
 		model.addAttribute("extraTime", extraTime);
 		
 		// 지각여부 조회
 		String attendType = attendanceService.selectAttendtype(empId);
 		model.addAttribute("attendType", attendType);
 		
 		// 출퇴근 시간 출력
 		Map<String, String> clockTimes = attendanceService.printClock(empId);
 		String clockIn = clockTimes.get("clockIn");
 		String clockOut = clockTimes.get("clockOut");
 		model.addAttribute("clockIn", clockIn);
 		model.addAttribute("clockOut", clockOut);
 		
 		// 근무 진행률
 		if(clockIn != null) {
 			int progress = (int)attendanceService.calProgress(empId, clockIn);
 	 		model.addAttribute("progress", progress);
 		}
 		
 		// 게시판
 		List<NoticeboardDto> noticeLists = boardService.selectNotice();
 		List<FreeboardDto> freeLists = boardService.selectFree();
 		model.addAttribute("noticeLists", noticeLists);
 		model.addAttribute("freeLists", freeLists);
 		
 		// 내가 결재해야할거
 		int myCnt = approvalService.getMyApprovalCount(empId);
 		// 진행중인 결재 갯수
 		int continueCnt = approvalService.getContinueCount(empId);
 		// 임시저장 문서 갯수
 		int tempCnt = approvalService.getTempCount(empId);
 		model.addAttribute("myCnt",myCnt);
 		model.addAttribute("continueCnt",continueCnt);
 		model.addAttribute("tempCnt",tempCnt);
 		
 	    
 	    ////////////////////////// 관리자 로그인 시 //////////////////////////
 	    
 	    String avgClockInTimeAll = attendanceService.avgClockInTimeAll();
		String avgClockOutTimeAll = attendanceService.avgClockOutTimeAll();
		String avgWorkTimeAll = attendanceService.avgWorkTimeAll();
		int lateToday = attendanceService.selectLateToday();
	    
		model.addAttribute("avgClockInTimeAll", avgClockInTimeAll);
		model.addAttribute("avgClockOutTimeAll", avgClockOutTimeAll);
		model.addAttribute("avgWorkTimeAll", avgWorkTimeAll);
		model.addAttribute("lateToday", lateToday);
		
		return "homeList";
	}
	
	@RequestMapping(value = "/homeList.do/calendar", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> scheduleReservation(HttpSession session) {
		
		EmployeeDto loginVo = (EmployeeDto)session.getAttribute("loginVo");
		String empId = loginVo.getEmp_id();
		List<Map<String, Object>> list = reservationService.getReservation(empId);
		System.out.println(list);
		return ResponseEntity.ok(list);
	}
	
	@RequestMapping(value = "/homeList.do/donutChart", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> donutChart(){
		
		List<Map<String, Object>> deptWorkTimeData = attendanceService.avgWorkTimeByDept();
		
		List<String> labels = new ArrayList<>();
	    List<Double> data = new ArrayList<>();
	    List<String> formattedData = new ArrayList<>();
	    
	    // 평균 근무 시간을 "HH:mm" 형식으로 받아서 "시간"으로 변환하여 저장
	    for (Map<String, Object> deptData : deptWorkTimeData) {
	    	 String deptName = (String) deptData.get("DEPTNAME");
	         String avgTime = (String) deptData.get("AVERAGETIME");
	         String[] timeParts = avgTime.split(":");
	         double hours = Double.parseDouble(timeParts[0]);
	         double minutes = Double.parseDouble(timeParts[1]);
	         double totalTimeInHours = hours + (minutes / 60);
	         
	         // 숫자 데이터로 저장 (차트에서 사용)
	         labels.add(deptName);
	         data.add(totalTimeInHours);
	         
	         // "시간:분" 형식으로 저장 (툴팁에서 사용)
	         int hourPart = (int) totalTimeInHours;
	         int minutePart = (int) ((totalTimeInHours - hourPart) * 60);
	         formattedData.add(hourPart + "시간 " + minutePart + "분");
	    }
	    
	    Map<String, Object> response = new HashMap<>();
	    response.put("departmentLabels", labels);
	    response.put("departmentData", data);
	    response.put("formattedData", formattedData);
		
		return response;
	}
	
	@RequestMapping(value = "/homeList.do/lateEmpChart", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> lateEmpChart(){
		
		List<Map<String, Object>> lateList = attendanceService.getLateEmpRank();
		
		List<String> name = new ArrayList<>();
	    List<Integer> count = new ArrayList<>();
	    
	    for (Map<String, Object> latedata : lateList) {
	    	name.add((String) latedata.get("NAME"));
	    	BigDecimal Bigcount = (BigDecimal) latedata.get("LATECOUNT");
	    	count.add(Bigcount.intValue());
	    }
	    
	    Map<String, Object> response = new HashMap<>();
	    response.put("name", name);
	    response.put("count", count);

		return response;
	}
	
	
	
}








