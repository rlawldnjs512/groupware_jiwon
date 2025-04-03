package com.min.edu.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.min.edu.dto.EmployeeDto;
import com.min.edu.dto.VacationDto;
import com.min.edu.model.mapper.IEmployeeDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements IEmployeeService {

	private final IEmployeeDao dao;

	@Override
	public EmployeeDto getLogin(Map<String, Object> map) {
		log.info("EmployeeServiceImpl {}, {}", "getLogin", map);
		return dao.getLogin(map); 
	}

	@Override
	public EmployeeDto findById(String emp_id) {
		log.info("EmployeeServiceImpl {}", "findById");
		return dao.findById(emp_id);
	}

	@Override
	public int modifyPw(Map<String, Object> map) {
		log.info("EmployeeServiceImpl {}", "modifyPw");
		return dao.modifyPw(map);
	}

	@Override
	public String checkAd(String role) {
		log.info("EmployeeServiceImpl {}", "checkAd");
		return dao.checkAd(role);
	}

	@Override
	public List<EmployeeDto> userSelectAll() {
		log.info("EmployeeServiceImpl {}", "userSelectAll");
		return dao.userSelectAll();
	}

	@Override
	public EmployeeDto getOneUser(String emp_id) {
		log.info("EmployeeServiceImpl {}", "getOneUser");
		return dao.getOneUser(emp_id);
	}

	@Override
	public List<EmployeeDto> sortDept(Map<String, Object >map) {
		log.info("EmployeeServiceImpl {}", "getEmployeeByNameOrDept");
		return dao.sortDept(map);
	}

	@Override
	public List<EmployeeDto> sortName(Map<String, Object> map) {
		log.info("EmployeeServiceImpl {}", "sortName");
		return dao.sortName(map);
	}

	@Override
	public int insertEmployee(EmployeeDto dto) {
		log.info("EmployeeServiceImpl {}", "insertEmployee");
		return dao.insertEmployee(dto);
	}

	@Override
	public int updateUser(EmployeeDto dto) {
		log.info("EmployeeServiceImpl {}", "updateUser");
		return dao.updateUser(dto);
	}

	@Override
	public int modifyUserInfo(EmployeeDto dto) {
		log.info("EmployeeServiceImpl {}", "modifyUserInfo");
		return dao.modifyUserInfo(dto);
	}

	@Override
	public EmployeeDto getOne(String emp_id) {
		log.info("EmployeeServiceImpl {}", "getOne");
		return dao.getOne(emp_id);
	}

	@Override
	public int updateProfile(Map<String, Object> map) {
		log.info("EmployeeServiceImpl {}", "uploadProfile");
		return dao.updateProfile(map);
	}

	@Override

	public boolean isAdmin(String emp_id) {
		// 매퍼에서 관리자인지 확인
		String result = dao.checkAd(emp_id);
		return result != null; // 결과가 null이 아니면 관리자임
	}

	@Override
	public List<EmployeeDto> selectAllUser(Map<String, Object> map) {
		log.info("EmployeeServiceImpl {}", "selectAllUser");
		log.info("전달된 파라미터", map);
		return dao.selectAllUser(map);
	}

	@Override
	public int countUser() {
		log.info("게시글 전체 글 갯수");
		return dao.countUser();
	}

	@Override
	public List<EmployeeDto> getEmployeesByDept(String dept_name, int selectPage, int countList) {
		int first = (selectPage - 1) * countList + 1; // 시작 인덱스
		int last = first + countList - 1; // 끝 인덱스

		Map<String, Object> params = new HashMap<>();
		params.put("dept_name", dept_name);
		params.put("first", first);
		params.put("last", last);

		return dao.sortDept(params);
	}

	@Override
	public List<EmployeeDto> getEmployeesByName(String name, int selectPage, int countList) {
		int first = (selectPage - 1) * countList + 1;
		int last = first + countList - 1;

		Map<String, Object> params = new HashMap<>();
		params.put("name", name);
		params.put("first", first);
		params.put("last", last);

		return dao.sortName(params);
	}

	@Override
	public int countEmployeesByDeptName(String keyword) {
		return dao.countEmployeesByDeptName(keyword);
	}

	@Override
	public int countEmployeesByName(String keyword) {
		return dao.countEmployeesByName(keyword);
	}

	@Override
	public String selectDeptName(String empId) {
		return dao.selectDeptName(empId);
	}

	@Override
	public String selectProfileImg(String empId) {
		return dao.selectProfileImg(empId);
	}

	@Override
	public int insertVacation(VacationDto dto) {
		return dao.insertVacation(dto);
	}

	@Override
	public String getNotId() {
		return dao.getNotId();
	}

	    
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}