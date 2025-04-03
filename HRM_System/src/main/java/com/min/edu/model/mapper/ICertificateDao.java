package com.min.edu.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.min.edu.dto.CertificateDto;

@Mapper
public interface ICertificateDao {

//	사용자는 본인의 증명서 신청이력을 증명서별로 조회할 수 있다.
	public List<CertificateDto> selectCertTypeUser(Map<String, Object> map);
	
//	사용자는 신청할 증명서 종류, 신청 사유를 입력해서 증명서 신청을 할 수 있다.
	public int insertCert(CertificateDto dto);
	
//	관리자는 사용자가 신청한 증명서를 승인할 수 있다.
	public int updateCertAccept(CertificateDto dto);
	
//	사용자는 관리자의 승인을 받은 증명서에 대해서만 다운로드를 할 수 있다.
	public List<CertificateDto> selectCertDown(String certnum);
	
//	사용자는 단 한번만 증명서 다운로드를 할 수 있다.
	public boolean updateDownload(String dto);
	
//	사용자는 본인의 증명서 신청이력을 삭제할 수 있다.
	public int deleteCert(CertificateDto dto);
	
//	사용자가 신청한 증명서에는 신청 날짜가 입력된다.
	public String selectCertDate(Map<String, Object> map);
	
//	관리자는 사원번호를 검색해서 증명서 신청이력을 조회할 수 있다.
	public List<CertificateDto> selectCertEmpAdmin(String empid);
	
//	관리자는 증명서별로 증명서 신청이력을 조회할 수 있다.
	public List<CertificateDto> selectCertTypeAdmin(String type);
	
	String getCertNum(@Param("name") String name);
	
    public int countCert(Map<String, Object> map);
    public int countCertAdminId(Map<String, Object> map);
    public int countCertAdminType(Map<String, Object> map);
	
    public List<CertificateDto> selectCertTypeUserPage(Map<String, Object> map);
    public List<CertificateDto> selectCertIdAdminPage(Map<String, Object> map);
    public List<CertificateDto> selectCertTypeAdminPage(Map<String, Object> map);
	
}
