package com.min.edu;


import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.edu.model.service.IVacationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class Vacation_JUnitTest {

	@Autowired
	private IVacationService service;
	
//	@Test
//	void selectExtraTime_Test() {
//		String empId = "20220001";
//		int extraTime = service.selectExtraTime(empId);
//		
//		log.info("보상시간 조회 : {}", extraTime);
//		
//		assertNotEquals(0, extraTime);
//	}
	
//	@Test
//	void updateExtraTime_Test() {
//		Map<String, Object> map = new HashMap<>();
//		map.put("empId", "20240002");
//		map.put("extraTime", -5);
//		
//		int n = service.updateExtraTime(map);
//		
//		assertEquals(1, n);
//	}

}
