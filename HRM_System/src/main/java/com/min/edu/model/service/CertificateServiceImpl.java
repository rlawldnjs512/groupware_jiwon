package com.min.edu.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.min.edu.dto.CertificateDto;
import com.min.edu.model.mapper.ICertificateDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements ICertificateService {

	@Autowired
	private ICertificateDao dao;

	@Override
	public List<CertificateDto> selectCertTypeUser(Map<String, Object> map) {
		return dao.selectCertTypeUser(map);
	}

	@Override
	public int insertCert(CertificateDto dto) {
		return dao.insertCert(dto);
	}

	@Override
	public int updateCertAccept(CertificateDto dto) {
		return dao.updateCertAccept(dto);
	}

	@Override
	public List<CertificateDto> selectCertDown(String certnum) {
		return dao.selectCertDown(certnum);
	}
	
	@Override
	public boolean updateDownload(String dto) {
		return dao.updateDownload(dto);
	}

	@Override
	public int deleteCert(CertificateDto dto) {
		return dao.deleteCert(dto);
	}

	@Override
	public String selectCertDate(Map<String, Object> map) {
		return dao.selectCertDate(map);
	}

	@Override
	public List<CertificateDto> selectCertEmpAdmin(String empid) {
		return dao.selectCertEmpAdmin(empid);
	}

	@Override
	public List<CertificateDto> selectCertTypeAdmin(String type) {
		return dao.selectCertTypeAdmin(type);
	}

	@Override
	public String getCertNum(String name) {
		 return dao.getCertNum(name);
	}

	@Override
	public int countCert(Map<String, Object> map) {
		return dao.countCert(map);
	}

	@Override
	public List<CertificateDto> selectCertTypeUserPage(Map<String, Object> map) {
		return dao.selectCertTypeUserPage(map);
	}

	@Override
	public int countCertAdminId(Map<String, Object> map) {
		return dao.countCertAdminId(map);
	}

	@Override
	public int countCertAdminType(Map<String, Object> map) {
		return dao.countCertAdminType(map);
	}

	@Override
	public List<CertificateDto> selectCertIdAdminPage(Map<String, Object> map) {
		return dao.selectCertIdAdminPage(map);
	}

	@Override
	public List<CertificateDto> selectCertTypeAdminPage(Map<String, Object> map) {
		return dao.selectCertTypeAdminPage(map);
	}
	
}
