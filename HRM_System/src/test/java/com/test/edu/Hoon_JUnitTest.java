package com.test.edu;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.min.edu.HrmSystemApplication;
import com.min.edu.dto.ApprovalDto;
import com.min.edu.dto.DocumentDto;
import com.min.edu.dto.EmployeeDto;
import com.min.edu.dto.LeaveDto;
import com.min.edu.dto.RejectionDto;
import com.min.edu.dto.RoomDto;
import com.min.edu.dto.TripDto;
import com.min.edu.model.mapper.IApprovalDao;
import com.min.edu.model.mapper.IReservationDao;

@SpringBootTest
@ContextConfiguration(classes = HrmSystemApplication.class)
class Hoon_JUnitTest {

	@Autowired
	IReservationDao dao;
	@Autowired
	IApprovalDao apprDao;
	
//	@Test
	public void myReservation() {
		List<RoomDto> lists = dao.myReservation("20240002");
		System.out.println(lists);
	}
	
	
//	@Test
	public void selectReservation() {
		List<RoomDto> list = dao.selectReservation("2025-02-27");
		System.out.println(list);
		
	}
	
	
//	@Test
	public void deleteReservation() {
		Map<String, Object> map = new HashMap<String, Object>(){{
			put("reserv_id", "10");
			put("emp_id", "20250005");
		}};
		
		int cnt = dao.deleteReservation(map);
		System.out.println(cnt);
		
	}
	
//	@Test
	public void insertReservation() {
		Map<String, Object> map = new HashMap<String, Object>(){{
			put("room_id", "R01");
			put("emp_id", "20250005");
			put("rev_date", "2025-02-27");
			put("slot", "3");
		}};
		
		boolean cnt = dao.insertReservation(map);
		System.out.println(cnt);
		
	}
	
//	@Test
	public void deleteRoom() {
		String room = "R05";
		int cnt = dao.deleteRoom(room);
		System.out.println(cnt);
	}
	
	
//	@Test
//	public void insertRoom() {
//		String name = "회의실E";
//		
//		int cnt = dao.insertRoom(name);
//		System.out.println(cnt);
//	}
	
//	@Test
	public void insertDocument() {
		Map<String, Object> map = new HashMap<String, Object>(){{
			put("emp_id", "20250050");
			put("doc_type", "휴가");
			put("title", "휴가신청제목");
			put("content", "휴가신청내용");
			put("doc_num", "VACA_2025_1");
		}};
		
		int cnt = apprDao.insertDocument(map);
		System.out.println(cnt);
	}
	
//	@Test
//	public void insertApproval() {
//		Map<String, Object> map = new HashMap<String, Object>(){{
//			
//			
//			List<ApprovalDto>  lists = new ArrayList<ApprovalDto>();
//			
//			ApprovalDto dto1 = new ApprovalDto();
//			dto1.setEmp_id("20240002");
//			dto1.setSign("사인1");
//			ApprovalDto dto2 = new ApprovalDto();
//			dto2.setEmp_id("20220001");
//			dto2.setSign("사인2");
//			ApprovalDto dto3 = new ApprovalDto();
//			dto3.setEmp_id("20180057");
//			dto3.setSign("사인3");
//			lists.add(dto1);
//			lists.add(dto2);
//			lists.add(dto3);
//			
//			put("doc_id", "71");
//			put("approval", lists);
//			put("apprv_id", 0);
//	}};
//	
//	
//		int cnt = apprDao.insertApproval(map);
//		System.out.println(map.get("apprv_id"));
//		System.out.println(cnt);
//	}
	
	// 2025 03 13 결재선 입력 테스트
//	@Test
	public void insertApproval(){
		//92
		Map<String, Object>  appMap =  new HashMap<String, Object>();
		appMap.put("approval", List.of("20310081","20240004","20240002"));
		appMap.put("doc_id", "92");
		int m = apprDao.insertApproval(appMap);
		System.out.println("입력된 결재선  :" + m);
	}
	
	// 2025 03 14 문서 상세 (문서 정보 + 결재라인 조회)
//	@Test
	public void detailApproval() {
		DocumentDto detailDto = apprDao.getApprovalDetail("203");
		List<ApprovalDto> apprList = apprDao.geteApproval("203");
		
		System.out.println(detailDto.getTitle());
		System.out.println(detailDto.getContent());
		System.out.println(detailDto.getOrigin_name()); //화면출력
		System.out.println(detailDto.getFile_id()); // 다운로드 식별자
		
		System.out.println(apprList);
		
		
		
		
	}
	
	// 반려
//	@Test
	public void updateApprovalReject() {
		ApprovalDto dto = new ApprovalDto();
		dto.setApprv_status("반려");
		dto.setDoc_id(209);
		dto.setApprv_id(38);
		int n = apprDao.updateApprovalReject(0, 0);
		System.out.println(n);
		
	}
	
//	@Test
	public void insertDocumentLeave() {
		
		Map<String, Object>  docMap =  new HashMap<String, Object>();
		docMap.put("doc_id", "");
		docMap.put("emp_id", 20220001);
		docMap.put("doc_type", "휴가");
		docMap.put("title", "휴가삽입테스트");
		docMap.put("content", "휴가삽입테스트");
		
		List<String> appLine = new ArrayList<String>();
		appLine.add("20250040");
		appLine.add("20250034");
		appLine.add("20250024");
		
		Map<String, Object>  appMap =  new HashMap<String, Object>();
		appMap.put("approval", appLine);
		appMap.put("doc_id", "");
		
		
		LeaveDto dto = new LeaveDto();
		dto.setLeave_start("2025-03-12");
		dto.setLeave_end("2025-03-21");
		dto.setType("오전반차");
		
		apprDao.insertDocumentLeave(docMap, appMap, dto);
		
		
		
	}
	
//	@Test
	public void insertSaveTrip() {
		TripDto dto = new TripDto();
		dto.setDoc_id(100);
		dto.setTrip_start("2025-03-17");
		dto.setTrip_end("2025-03-17");
		dto.setDestination("서울어딘가");
		
		apprDao.insertSaveTrip(dto);
		
		System.out.println(dto);
		
	}
	
//	@Test
	public void insertRejection() {
		RejectionDto dto = new RejectionDto();
		dto.setDoc_id(295);
		dto.setReject_name("홍길은");
		dto.setReject_text("어딘가가 어디인가?");
		dto.setReject_date("");
		apprDao.insertRejection(dto);
		
		System.out.println(dto);
		
	}
	
	
//	@Test
	public void updateDocStatus() {
		int doc_id = 363;
		
		apprDao.updateDocStatus(doc_id);
	}
	
	
}









