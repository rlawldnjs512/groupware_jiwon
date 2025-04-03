package com.min.edu;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import com.min.edu.dto.ApprovalDto;
import com.min.edu.dto.CertificateDto;
import com.min.edu.dto.DocumentDto;
import com.min.edu.dto.EmployeeDto;
import com.min.edu.dto.FileUpDto;
import com.min.edu.dto.FreeboardDto;
import com.min.edu.dto.NoticeboardDto;
import com.min.edu.dto.SignDto;
import com.min.edu.model.mapper.IApprovalDao;
import com.min.edu.model.mapper.IBoardDao;
import com.min.edu.model.mapper.ICertificateDao;
import com.min.edu.model.service.IBoardService;

@SpringBootTest
class QueryTest_JUnitTest {
	
	@Autowired
	private ICertificateDao dao;
	
	@Autowired
	private IApprovalDao dao2;
	
	@Autowired
	private IBoardDao dao3;
	
	@Autowired
	private IBoardService dao4;
	
// 증명서 관리 쿼리 테스트 ------------------------------------------------
	
	@Test
	public void selectCertTypeUser_test() {
	    Map<String, Object> map = new HashMap<String, Object>();
		map.put("emp_id", "20220549");
		map.put("type", "퇴직");
		List<CertificateDto> dto = dao.selectCertTypeUser(map);
		assertNotNull(dto);
	}
	
//	@Test
	public void insertCert_test() {
		CertificateDto dto = new CertificateDto().builder()
							.name("홍길동")
							.type("재직")
							.reason("재직증명서입니다.")
							.build();

		int n = dao.insertCert(dto);
	    assertEquals(1, n);
	}

//	@Test
	public void updateCertAccept() {
		CertificateDto dto = new CertificateDto().builder()
							.name("홍길동")
							.cert_status("N")
							.build();
		
		int n = dao.updateCertAccept(dto);
		assertNotEquals(1, n);
	}
	
//	@Test
	public void selectCertDown() {
		List<CertificateDto> lists = dao.selectCertDown("CERT_2025_21");
		System.out.println(lists);
		assertNotNull(lists);
	}
	 
//	@Test
	public void updateDownload() {
		Boolean n = dao.updateDownload("CERT_2025_21");
		assertEquals(1, n);
	}
	
//	@Test
	public void deleteCert() {
		CertificateDto dto = new CertificateDto().builder()
					.name("홍길동")
					.cert_num("CERT_2025_42")
					.build();
		
		int n = dao.deleteCert(dto);
		assertEquals(1, n);
							
	}
	
//	@Test
	public void selectCertDate() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "홍길동");
		map.put("cert_num", "EMP_2025_39");
		
		System.out.println("Name: " + map.get("name"));
		System.out.println("Cert Num: " + map.get("cert_num"));
		
		String certdate = dao.selectCertDate(map);
		System.out.println(certdate);
	}
	
//	@Test
	public void selectCertEmpAdmin() {
		List<CertificateDto> dto = dao.selectCertEmpAdmin("20240002");
		
		System.out.println(dto);
		assertNotNull(dto);		
	}
	
//	@Test
	public void selectCertTypeAdmin() {
		List<CertificateDto> dto = dao.selectCertTypeAdmin("경력");
		
		System.out.println(dto);
		assertNotNull(dto);
	}
	
// 전자결재 서명/임시저장/미리보기 쿼리 테스트 ------------------------------------------------
	
//	@Test
	public void insertSign() {
		SignDto dto = new SignDto().builder()
					.sign("101010")
					.name("주사원")
					.build();
		
		int n = dao2.insertSign(dto);
		assertEquals(1, n);
	}
	
//	@Test
	public void selectSign() {
		List<SignDto> lists = dao2.selectSign();
		System.out.println(lists);
		assertNotNull(lists);
	}
	
//	@Test
	public void deleteSign() {
		SignDto dto = new SignDto().builder()
					.name("홍길동")
					.build();
//		int n = dao2.deleteSign(dto);
//		assertEquals(1, n);
	}
	
//	@Test
	public void insertSaveDoc() {
		DocumentDto dto = new DocumentDto().builder()
							.name("홍길동")
							.doc_type("보고서")
							.title("하반기매출보고서")
							.content("작년도 하반기 매출에 관해서 내용 작성하려고 합니다.~~")
							.build();
		int n = dao2.insertSaveDoc(dto);
		assertEquals(1, n);
	}
	
//	@Test
	public void deleteSaveDoc() {
		DocumentDto dto = new DocumentDto().builder()
						.doc_num("TRIP_2025_5")
						.name("홍길동")
						.build();
		int n = dao2.deleteSaveDoc(dto);
		assertEquals(1, n);
	}
	
//	@Test
	public void selectPreviewDoc() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "홍길동");
		map.put("doc_num", "VACA_2025_1");
		
		List<ApprovalDto> dto = dao2.selectPreviewDoc(map);
		System.out.println(dto);
		assertNotNull(dto);
	}
	
// 공지사항게시판/자유게시판 쿼리 테스트 ------------------------------------------------
	
	
//	@Test
	public void insertNotice() {
		NoticeboardDto dto = new NoticeboardDto().builder()
						.title("테스트제목")
						.content("오늘의 공지사항입니다.")
						.regdate("2025-02-28")
						.build();
		int n = dao3.insertNotice(dto);
		System.out.println(dto);
		assertEquals(1, n);
	}
	
//	@Test
	public void updateNotice() {
		NoticeboardDto dto = new NoticeboardDto().builder()
						.content("공지내용일부가 변경되었습니다.")
						.not_id(5)
						.build();
		int n = dao3.updateNotice(dto);
		System.out.println(dto);
		assertEquals(1, n);
	}
	
//	@Test
	public void deleteNotice() {
		int n = dao3.deleteNotice(4);
		assertEquals(1, n);
	}
	
//	@Test
	public void deleteNoticeDead() {
		int n = dao3.deleteNoticeDead();
		assertNotNull(n);
	}
	
//	@Test
	public void selectNotice() {
		List<NoticeboardDto> lists = dao3.selectNotice();
		System.out.println(lists);
		assertNotNull(lists);
	}
	
//	@Test
	public void insertFree() {
		FreeboardDto dto = new FreeboardDto().builder()
						.name("이영희")
						.title("오늘의 날씨")
						.content("봄비가 내리내요. 우산들 챙기세요.")
						.regdate("2025-03-02")
						.build();
		int n = dao3.insertFree(dto);
		System.out.println("dto");
		assertEquals(1, n);
	}
	
//	@Test
	public void updateFree() {
		FreeboardDto dto = new FreeboardDto().builder()
						.content("오타수정했어요.")
						.free_id(2)
						.name("홍길동")
						.build();
		int n = dao3.updateFree(dto);
		System.out.println(dto);
		assertEquals(1, n);
	}
	
//	@Test
	public void deleteFree() {
		FreeboardDto dto = new FreeboardDto().builder()
						.free_id(23)
						.name("홍길동")
						.build();
		int n = dao3.deleteFree(dto);
		System.out.println(dto);
		assertEquals(1, n);
	}
	
//	@Test
	public void reply() {
		FreeboardDto dto = new FreeboardDto().builder()
						.name("홍길동")
						.title("봄비")
						.content("그렇게 춥진 않네요.")
						.regdate("2025-03-02")
						.free_id(22)
						.build();
		boolean isc = dao4.reply(dto);
		assertTrue(isc,"답글이 입력되었습니다.");
	}
	
//	@Test
	public void updateFreeReply() {
		FreeboardDto dto = new FreeboardDto().builder()
						.content("곧 벚꽃이 피겠죠?")
						.free_id(24)
						.name("홍길동")
						.build();
		int n = dao3.updateFreeReply(dto);
		System.out.println(dto);
		assertEquals(1, n);
	}
	
//	@Test
	public void deleteFreeReply() {
		FreeboardDto dto = new FreeboardDto().builder()
						.free_id(25)
						.name("홍길동")
						.build();
		int n = dao3.deleteFreeReply(dto);
		System.out.println(dto);
		assertEquals(1, n);
	}
	
//	@Test
	public void selectFree() {
		List<FreeboardDto> lists = dao3.selectFree();
		for (FreeboardDto d : lists) {
			System.out.println(d);
		}
		assertNotNull(lists);
	}
	
//	@Test
	public void selectNoticeFile() {
////		List<FileUpDto> lists = dao3.selectNoticeFile();
//		for (FileUpDto d : lists) {
//			System.out.println(d);
//		}
//		assertNotNull(lists);
	}
	
//	@Test
	public void selectFreeFile() {
//		List<FileUpDto> lists = dao3.selectFreeFile();
//		for (FileUpDto d : lists) {
//			System.out.println(d);
//		}
//		assertNotNull(lists);
	}
	
	
	

}
