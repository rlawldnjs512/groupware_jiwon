package com.min.edu.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.min.edu.dto.EmployeeDto;
import com.min.edu.model.service.IReservationService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ScheduleContoller {

	private final IReservationService reservationService;
	
	@GetMapping(value = "/schedule")
	public String schedule(HttpSession session, Model model) {
		
		return "schedule";
	}
	
	
	@RequestMapping(value = "/schedule/reservation", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> scheduleReservation(HttpSession session) {
		
		EmployeeDto loginVo = (EmployeeDto)session.getAttribute("loginVo");
		String empId = loginVo.getEmp_id();
		List<Map<String, Object>> list = reservationService.getReservation(empId);
		System.out.println(list);
		return ResponseEntity.ok(list);
	}
	
	
	
}
