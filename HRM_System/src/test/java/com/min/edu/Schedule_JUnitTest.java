package com.min.edu;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.edu.model.service.IReservationService;

@SpringBootTest
class Schedule_JUnitTest {

	@Autowired
	private IReservationService service;
	
	@Test
	void getReservation_Test() {
		
		String empId = "20180050";
		
		List<Map<String, Object>> list = service.getReservation(empId);
		
		System.out.println(list);
	}

}
