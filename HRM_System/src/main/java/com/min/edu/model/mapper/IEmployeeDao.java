package com.min.edu.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.min.edu.dto.EmployeeDto;
import com.min.edu.dto.VacationDto;

@Mapper
@Repository
public interface IEmployeeDao {
	
	// 로그인 처리
    public EmployeeDto getLogin(Map<String, Object> map);
    //사원번호로 사원확인
    public EmployeeDto findById(String emp_id);
    //비밀번호 재설정
    public int modifyPw(Map<String, Object>map);
    //비밀번호 잊었을 때 - 이메일 확인
    public String checkEmail(String emp_id);
    //관리자 확인
    public String checkAd(String role);
    //전체사원조회
    public List<EmployeeDto> userSelectAll();
    //사원상세조회
    public EmployeeDto getOneUser(String emp_id);
    //이름|부서 별 사원조회
    public List<EmployeeDto> sortDept(Map<String, Object> map);
    public List<EmployeeDto> sortName(Map<String, Object> map);
   
    
    //사원수정-관리자
    public int updateUser(EmployeeDto dto);
    //정보수정-마이페이지
    public int modifyUserInfo(EmployeeDto dto);
    //정보조회-마이페이지
    public EmployeeDto getOne(String emp_id);
    //프로필 업로드 -마이페이지
    public int updateProfile(Map<String, Object> map);
    //요청된 범위에 해당하는 글의 리스트를 조회 함 
    public List<EmployeeDto> selectAllUser(Map<String, Object> map);
    //전체글의 갯수를 조회하면 페이지 연산에 사용
    public int countUser();
    
    public int countEmployeesByName(String keyword);
    
    public int countEmployeesByDeptName(String keyword);
    
    // 사원 1명의 부서명 조회
    public String selectDeptName(String empId);
    
    // 사원 1명의 프로필이미지 조회
    public String selectProfileImg(String empId);
    
    

    //사원등록-관리자
    public int insertEmployee(EmployeeDto dto);
    
    
    //신입 사원 연차 부여
    public int insertVacation(VacationDto dto);
    
    
     //마지막 회원 조회
    public String getNotId();    
    
    

 
}