package com.min.edu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.min.edu.model.mapper.IApprovalDao;
import com.min.edu.model.service.IApprovalService;

@RestController
public class ApprovalRestController {
	
	@Autowired
	private IApprovalService service;
	
//	@GetMapping(value = "/tree.do")
//	public List<String> tree () {
//		
//		return List.of("{  id: \"school\",   parent: \"#\",     text: \"<b class='btn'>전체직원</b>\",   state: { opened: true }",
//				 "{  id: \"grade_1\",        parent: \"school\",        text: \"개발부\",    }",
//				 "{  id: \"class_1_1\",        parent: \"grade_1\",        text: \"개발1팀\",    }",
//				 "{  id: \"student_1\",        parent: \"class_1_1\",       text: \"김철수\",    }",
//				 "{  id: \"student_2\",        parent: \"class_1_1\",       text: \"이영희\",    }"
//				 );
//	}
	
	@GetMapping(value = "/getTree.do")
	public List<Map<String, Object>> selectTree () {
		
		return service.selectTree();
	}
	
	
}
