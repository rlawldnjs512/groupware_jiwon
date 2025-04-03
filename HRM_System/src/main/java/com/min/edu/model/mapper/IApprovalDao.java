package com.min.edu.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.min.edu.dto.ApprovalDto;
import com.min.edu.dto.DocumentDto;
import com.min.edu.dto.EmployeeDto;
import com.min.edu.dto.FileUpDto;
import com.min.edu.dto.LeaveDto;
import com.min.edu.dto.RejectionDto;
import com.min.edu.dto.SignDto;
import com.min.edu.dto.TripDto;

@Mapper
public interface IApprovalDao {

//	사용자별로 한개의 서명만 등록할 수 있다.
	public int insertSign(SignDto dto);
	
	public List<SignDto> selectSign();
	public SignDto selectSignOne(String emp_id);
	
//	사용자는 자신의 서명을 삭제할 수 있다.
	public int deleteSign(String name);
	
//	사용자는 작성중인 결재문서를 임시저장할 수 있다.
	public int insertSaveDoc(DocumentDto dto);
	public int insertTempFile(FileUpDto dto);
	public int getDocId();
	public int updateTempFileExist(int docId);
	
	
	
	// + 출장 추가
	public int insertSaveTrip(TripDto dto);
	// + 휴가 추가
	public int insertSaveLeave(LeaveDto dto);
	
	// 문서형식만 조회하기
	public DocumentDto continuePriview(int doc_id);

	String getDocType(@Param("doc_id") int doc_id);
	// 출장 조회하기
	public TripDto continuePrviewTrip(int doc_id);
	// 휴가 조회하기
	public LeaveDto continuePreviewLeave(int doc_id);
	
//	사용자는 결재를 상신할 수 있다.
	public int insertApprovalDoc(DocumentDto dto);
	// + 임시 저장한 문서 상신할 때
	public int insertTempDoc(int doc_id);
	
//	사용자는 임시저장한 문서를 삭제할 수 있다.
	public int deleteSaveDoc(DocumentDto dto);
	// + 출장삭제
	public int deleteSaveTrip(int doc_id);
	// + 휴가삭제
	public int deleteSaveLeave(int doc_id);
	
	
//	사용자는 작성한 결재문서를 결재하기 전에 미리보기를 할 수 있다.
	public List<ApprovalDto> selectSuccessDoc(Map<String, Object> map);
	public List<ApprovalDto> selectPreviewDoc(Map<String, Object> map);
	
	public List<ApprovalDto> continuePreviewDoc(Map<String, Object> map);

	String getDocNum(@Param("name") String name);

	public List<Map<String, Object>> selectTree();
	
	public int insertDocument(Map<String, Object> map);
	
	public int insertApproval(Map<String, Object> map);
	
	//본인이 결재해야할 문서 리스트
  public List<ApprovalDto> getApprovalList(String emp_id); 
  
  public int insertDocumentLeave(Map<String, Object> docMap, Map<String, Object> appMap, LeaveDto leaveDto);
  
  public int insertDocumentTrip(Map<String, Object> docMap, Map<String, Object> appMap, LeaveDto leaveDto);
  
  //본인이 결재를 보낸 문서들
  public List<DocumentDto> selectApprvMine(String emp_id);
  
  // 2025 03 14 상세보기 된 문서
  public DocumentDto getApprovalDetail(String doc_id);
  
  public List<ApprovalDto> geteApproval(String doc_id);
  // 2025 03 14 승인
  public int updateApprovalStatus (ApprovalDto dto);
  public int updateDocumentStatus(int doc_id);
  
  //결재순서의 마지막번호 조회
  public int selectApprovalMax(int doc_id);
  
  public int updateDocumentStatus(ApprovalDto dto);
  
  
  // 2025 03 14 반려
  public int updateApprovalReject(int doc_id, int apprv_id);
  
  public int updateDocStatusReject(int doc_id);
  
  public int insertRejection(RejectionDto dto);
  
  //결재문서 보낸사람 정보 표시
  public EmployeeDto getApp(int doc_id);

  
  //내 결재 순서보다 앞사람들 사인을 조회하기 
  public List<EmployeeDto> getApproverSignatures(Map<String, Object> map);
  
  //파일조회
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




