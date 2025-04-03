package com.min.edu.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.min.edu.dto.ApprovalDto;
import com.min.edu.dto.DocumentDto;
import com.min.edu.dto.EmployeeDto;
import com.min.edu.dto.FileUpDto;
import com.min.edu.dto.LeaveDto;
import com.min.edu.dto.RejectionDto;
import com.min.edu.dto.SignDto;
import com.min.edu.dto.TripDto;

public interface IApprovalService {

	public int insertSign(SignDto dto);

	public List<SignDto> selectSign();
	public SignDto selectSignOne(String emp_id);

	public int deleteSign(String name);

	// 결재문서 임시저장
	public int insertSaveDoc(DocumentDto dto);
	public int insertTempFile(FileUpDto dto);
	public int getDocId();
	public int updateTempFileExist(int docId);
	// + 출장 추가
	public int insertSaveTrip(TripDto dto);
	public int insertDocumentTrip(Map<String, Object> docMap, Map<String, Object> appMap, TripDto tripDto);
	// + 휴가 추가
	public int insertSaveLeave(LeaveDto dto);
	// 문서형식만 조회하기
	String getDocType(@RequestParam("doc_id") int doc_id);
	public DocumentDto continuePriview(int doc_id);
	// 출장 조회하기
	public TripDto continuePrviewTrip(int doc_id);
	// 휴가 조회하기
	public LeaveDto continuePreviewLeave(int doc_id);

	//	사용자는 결재를 상신할 수 있다.
	public int insertApprovalDoc(DocumentDto dto);
	// + 임시 저장한 문서 상신할 때
	public int insertTempDoc(int doc_id);

	public int deleteSaveDoc(DocumentDto dto);
	// + 출장삭제
	public int deleteSaveTrip(int doc_id);
	// + 휴가삭제
	public int deleteSaveLeave(int doc_id);

	public List<ApprovalDto> selectSuccessDoc(Map<String, Object> map);
	public List<ApprovalDto> selectPreviewDoc(Map<String, Object> map);

	public List<ApprovalDto> continuePreviewDoc(Map<String, Object> map);

	String getDocNum(@RequestParam("name") String name);

	public List<Map<String, Object>> selectTree();
	public int updateDocumentStatus(ApprovalDto dto);

	
	  
	  //결재순서의 마지막번호 조회
	  public int selectApprovalMax(int doc_id);

	// 2025 03 13 기안서 입력 및 결재선 입력
	public int insertDocument(Map<String, Object> docMap, Map<String, Object> appMap);

	public int insertApproval(Map<String, Object> map);

	
	// 2025 03 14
	public int insertDocumentLeave(Map<String, Object> docMap, Map<String, Object> appMap, LeaveDto leaveDto);
	
	
	
	public List<ApprovalDto> getApprovalList(String emp_id); 

	 //본인이 결재를 보낸 문서들
	 public List<DocumentDto> selectApprvMine(String emp_id);

	public DocumentDto getApprovalDetail(String doc_id);
	public List<ApprovalDto> geteApproval(String doc_id);


	// 2025 03 14 반려
//	public int updateApprovalReject(ApprovalDto dto);

	public int updateApprovalStatus (ApprovalDto dto) ;
	  public int updateDocumentStatus(int doc_id);
	
//	public int insertRejection(RejectionDto dto);
	
	public int approvalRejection(int apprv_id, RejectionDto rejDto);

	 public EmployeeDto getApp(int doc_id);
  
	  //내 결재 순서보다 앞사람들 사인을 조회하기 
	 public List<EmployeeDto> getApproverSignatures(Map<String, Object> map);
	 
	 public FileUpDto getReportFileById(int doc_id);
	 
	  //승인순서가 마지막인지 확인 - 결재완료표시
	  public String selectApprovalLast(int apprv_id);

	 public int updateDocStatus(int doc_id);
	 
	  // 내가 해야할 결재 갯수
	  public int getMyApprovalCount(String emp_id);
	  
	// 내가 상신해서 진행중인 결재 갯수
	  public int getContinueCount(String emp_id);
	  
	  // 내가 임시저장한 문서 갯수
	  public int getTempCount(String emp_id);
	 

}
