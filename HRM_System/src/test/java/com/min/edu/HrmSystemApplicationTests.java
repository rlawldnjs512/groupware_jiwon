package com.min.edu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.min.edu.dto.EmployeeDto;
import com.min.edu.dto.VacationDto;
import com.min.edu.model.mapper.IEmployeeDao;
import com.min.edu.model.service.IEmployeeService;
import com.min.edu.model.service.MailSendService;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class HrmSystemApplicationTests {

	
//	@Autowired
//	private IEmployeeDao dao;
	
	@Autowired
	private MailSendService mailSendService;
	

	
	@Autowired
	private IEmployeeService service;
	
	@Test
	void contextLoads() {
			
			EmployeeDto employeeDto
			= service.getLogin(new HashMap<String, Object>(){{
				put("emp_id", "20230023");
				put("password", "PC09876543");	
			}});
			
			System.out.println(employeeDto);
		}
	
	//@Test
	public void test() {
		
//		String empId = service.findById("20240002");
//		assertEquals("20240002", empId); //사원번호로 사원확인
//		System.out.println(empId);	
//		
//		int n = service.modifyPw(new HashMap<String, Object>(){{
//			
//			put("emp_id","20250005");
//			put("password","AT60201765");
//		}});
//		
//		assertNotNull(n);
		
//		String chk = service.checkAd("A");
//		assertNotNull(chk);
//		System.out.println(chk);
		
//		List<EmployeeDto> list = service.userSelectAll();
//		assertNotEquals(0, list.size());
//		System.out.println(list);
		
//		EmployeeDto dto = service.getOneUser("20240002");
//		System.out.println(dto);
		
	
		
		
//		EmployeeDto dto = EmployeeDto.builder()
//						  .dept_id(3)
//						  .name("김사람")
//						  .position("대리")
//						  .birth("20021205")
//						  .phone("010-3456-7813")
//						  .tel("890")
//						  .email("aa156@test.com")
//						  .hire_date("2023-03-04")
//						  .build();
//		int n = service.insertEmployee(dto);
//		System.out.println(dto);
//		assertEquals(1, n);
		
		
		
		 
//        EmployeeDto dto = EmployeeDto.builder()
//                .emp_id("20220001")
//                .dept_id(2)
//                .name("이맹구")
//                .position("사원")
//                .birth("19900501")
//                .phone("010-1234-5678")
//                .tel("5678")
//                .email("test11@test.com")
//                .build();
//
// 
//
//        int result = service.updateUser(dto);
//
//        assertEquals(1, result); 
        
		
//		EmployeeDto emp = service.findById("20250029");
//		log.info("emp : {}", emp);
//		
//		

//		//신입사원 연차 입력 테스트
//		String empId = "20250188" ;
//
//		
//		VacationDto dto = VacationDto.builder()
//				          .emp_id(empId)
//				          .start_date("2025-03-25")
//				          .end_date("2025-12-31")
//				          .build();
//		
//		int result = service.insertVacation(dto);
//		assertEquals(1, result);
		
			
//		
		
		
		
		
    }
	
//	@Test //메일 테스트 성공
//    public void testSendAuthMail() throws MessagingException {
//        // 실제 이메일 주소로 테스트
//        String email = "mw7813@naver.com";  // 실제 이메일 주소
//        String authKey = mailSendService.sendAuthMail(email);  // 인증 메일 전송
//
//        System.out.println("AuthKey: " + authKey);  // 인증번호 출력
//       
//    }
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	



	


