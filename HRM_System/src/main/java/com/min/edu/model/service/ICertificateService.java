package com.min.edu.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.min.edu.dto.CertificateDto;

public interface ICertificateService {

	public List<CertificateDto> selectCertTypeUser(Map<String, Object> map);
	
	public int insertCert(CertificateDto dto);
	
	public int updateCertAccept(CertificateDto dto);
	
	public List<CertificateDto> selectCertDown(String certnum);
	
	public boolean updateDownload(String dto);
	
	public int deleteCert(CertificateDto dto);
	
	public String selectCertDate(Map<String, Object> map);
	
	public List<CertificateDto> selectCertEmpAdmin(String empid);
	
	public List<CertificateDto> selectCertTypeAdmin(String type);
	
	String getCertNum(@Param("name") String name);
	
    public int countCert(Map<String, Object> map);
    public int countCertAdminId(Map<String, Object> map);
    public int countCertAdminType(Map<String, Object> map);
    
    public List<CertificateDto> selectCertTypeUserPage(Map<String, Object> map);
    public List<CertificateDto> selectCertIdAdminPage(Map<String, Object> map);
    public List<CertificateDto> selectCertTypeAdminPage(Map<String, Object> map);
    
}
