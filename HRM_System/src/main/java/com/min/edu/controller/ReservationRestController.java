package com.min.edu.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.min.edu.dto.RoomDto;
import com.min.edu.model.mapper.IReservationDao;
import com.min.edu.model.service.IReservationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ReservationRestController {

	@Autowired
	private IReservationService service;
	
	@PostMapping("/insertReserv.do")
	public ResponseEntity<Map<String, Boolean>> insertReservation
						(@RequestBody Map<String, Object> requestReq) {
		System.out.println(requestReq);
        
		boolean isSuccess = service.insertReservation(requestReq);
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put("isc", isSuccess);
        return ResponseEntity.ok(map);
    }
	
	@GetMapping(value = "/reservationapi.do")
    public ResponseEntity<Map<String, Object>> searchReservation
    				(@RequestParam(required = false) String nowDate) {
		
		System.out.println("Rest 날짜 전달 값 : " + nowDate);
		if (nowDate == null || nowDate.equals("")) {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            nowDate = sdf.format(date);
        }

        List<RoomDto> lists = service.selectReservation(nowDate);

        Map<String, Object> response = new HashMap<>();
        response.put("lists", lists);
        response.put("nowDate", nowDate);

        return ResponseEntity.ok(response);
    }
	
}
