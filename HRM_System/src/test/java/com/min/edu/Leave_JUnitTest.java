package com.min.edu;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.edu.model.service.ILeaveService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class Leave_JUnitTest {

	@Autowired
	private ILeaveService leaveService;
	
	@Test
	void selectLeavePage_Test() {
		
		Map<String, Object> map = new HashMap<>();
		map.put("first", 1);
		map.put("last", 5);
		
		List<Map<String, Object>> lists = leaveService.selectLeavePage(map);
		System.out.println(lists);
	}
	
	//@Test
	void leaveListByEmpId_Test() {
		List<Map<String, Object>> list = leaveService.leaveListByEmpId("20240002", "2025-02-28", "2025-02-28");
		System.out.println(list);
	}

}
