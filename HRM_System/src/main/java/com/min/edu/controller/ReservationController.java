package com.min.edu.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.min.edu.dto.EmployeeDto;
import com.min.edu.dto.ReservationDto;
import com.min.edu.dto.RoomDto;
import com.min.edu.model.mapper.IReservationDao;
import com.min.edu.model.service.IReservationService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ReservationController {
	
	@Autowired
	private final IReservationService service;
	
	@GetMapping(value = "/reservation.do")
	public String searchReservation(Model model, 
			@RequestParam(required = false) String nowDate) {
		
//		화면 이동 후 최초에 /reservationapi.do 호출로 사용하지 않음
//		System.out.println(nowDate);
//		if(nowDate == null) {
//			Date date = new Date();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			nowDate = sdf.format(date);
//			System.out.println(nowDate);
//		}
//
//		List<RoomDto> lists = service.selectReservation(nowDate);
//		model.addAttribute("lists",lists);
//		model.addAttribute("nowDate", nowDate);
		
		return "reservation";
	}
	
	@GetMapping(value = "/myReservation.do")
	public String myReservation(Model model, HttpSession session) {
		EmployeeDto dto = (EmployeeDto)session.getAttribute("loginVo");
	    String emp_id = dto.getEmp_id();
		List<RoomDto> lists = service.myReservation(emp_id);
		model.addAttribute("lists", lists);
		
		return "myReservation";
	}
	
	@PostMapping(value = "/deleteReservation.do")
	public String deleteReservation(@RequestParam String reserv_id,
									@RequestParam String emp_id,
									HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("emp_id", emp_id);
		map.put("reserv_id", reserv_id);
		int n = service.deleteReservation(map);
		
		if (n == 1) {
				response.getWriter().print("<script>alert('삭제완료');</script>");
		    } else {
		    	response.getWriter().print("<script>alert('삭제실패');</script>");
		    }
		
		return "redirect:/myReservation.do";
	}
	
	@GetMapping(value = "/selectRoom.do")
	public String selectRoom(Model model,
							HttpSession session) {
		List<RoomDto> list = service.selectRoom();
		model.addAttribute("lists",list);
		
		return "selectRoom";
	}
	
	@PostMapping(value = "/deleteRoom.do")
	public String deleteRoom(String room_id) {
		service.deleteRoom(room_id);
		return "redirect:/selectRoom.do";
	}
	
	@PostMapping(value = "/insertRoom.do")
	public String insertRoom(@RequestParam Map<String, Object> map) {
		service.insertRoom(map);
		return "redirect:/selectRoom.do";
	}
	


}
