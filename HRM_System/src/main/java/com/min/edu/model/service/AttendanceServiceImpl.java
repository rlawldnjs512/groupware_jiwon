package com.min.edu.model.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.min.edu.dto.AttendanceDto;
import com.min.edu.model.mapper.IAttendanceDao;
import com.min.edu.model.mapper.IVacationDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttendanceServiceImpl implements IAttendanceService {

	private final IAttendanceDao dao;
	private final IVacationDao Vdao;

	@Override
	public List<AttendanceDto> attendanceList() {
		return dao.attendanceList();
	}
	
	public List<AttendanceDto> attendanceListByEmpId(String empId){
		return dao.attendanceListByEmpId(empId);
	}

	@Override
	public int insertAttendance(String empId) {
		return dao.insertAttendance(empId);
	}

	@Override
	public int updateAttendance(String empId) {
		return dao.updateAttendance(empId);
	}
	
	@Override
	public boolean checkAttendance(String empId) {
		// 출근했으면 true 반환, 출근 안 했으면 false 반환
		return dao.checkAttendance(empId) > 0;
	}
	
	@Override
	public String selectClockIn(String empId) {
		return dao.selectClockIn(empId);
	}
	
	@Override
	public String selectClockOut(String empId) {
		return dao.selectClockOut(empId);
	}
	
	@Override
	public Map<String, String> printClock(String empId) {
		String clockIn = dao.selectClockIn(empId);
		String clockOut = dao.selectClockOut(empId);
		
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 		SimpleDateFormat outputFormat = new SimpleDateFormat("HH시 mm분");
 		
 		Map<String, String> clockTimes = new HashMap<>();
 		
 		try {
			if(clockIn != null) {
				Date clockInDate = inputFormat.parse(clockIn);
				clockTimes.put("clockIn", outputFormat.format(clockInDate));
			}
			if(clockOut != null) {
				Date clockOutDate = inputFormat.parse(clockOut);
				clockTimes.put("clockOut", outputFormat.format(clockOutDate));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return clockTimes;
	}

	@Override
	public int calAttendance(String empId) {
		return dao.calAttendance(empId);
	}
	
	@Override
	public String selectAttendtype(String empId) {
		return dao.selectAttendtype(empId);
	}

	@Override
	public int updateAttendtype(Map<String, Object> map) {
		return dao.updateAttendtype(map);
	}
	
	@Override
	public int updateUseExtraTime(Map<String, Object> map) {
		return dao.updateUseExtraTime(map);
	}

	@Override
	public void insertAttendanceLogic(String empId) {
		log.info("출근 로직 처리 시작 : {}", empId);
		
		// 출근 여부 확인
		int isClockedIn = dao.checkAttendance(empId);
		log.info("출근 여부 확인 : {}", isClockedIn);
		
		// 출근 기록 삽입
		int result = dao.insertAttendance(empId);
		if(result > 0) {
			log.info("출근 기록 저장 완료");
		} else {
			log.info("출근 기록 저장 실패");
			return;
		}
		
		// 출근 시간 조회
		String checkInDateStr = dao.selectClockIn(empId);
		
		// 기준 출근 시간 (09시)
		Calendar cal = new GregorianCalendar();
		String checkOutDateStr = String.format("%d%02d%02d090000",
			cal.get(Calendar.YEAR),
			cal.get(Calendar.MONTH) + 1,
			cal.get(Calendar.DATE)
		);
		
		// Date로 변환할 Format
		SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat customFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		
		try {
			Date checkInDate = dbFormat.parse(checkInDateStr);
			Date checkOutDate = customFormat.parse(checkOutDateStr);
			
			if(checkInDate.after(checkOutDate)) { // 지각
				Map<String, Object> AttendtypeMap = new HashMap<>();
				AttendtypeMap.put("attendType", "지각");
				AttendtypeMap.put("empId", empId);
				dao.updateAttendtype(AttendtypeMap);
			}
		} catch (ParseException e) {
			log.error("날짜 변환 오류", e);
		}
	}
	
	@Override
	public void updateAttendanceLogic(String empId, Map<String, String> infoAtten) {
		log.info("퇴근 로직 처리 시작 : {}", empId);
		
		// 퇴근 기록 업데이트
		int result = dao.updateAttendance(empId);
		if(result > 0) {
			log.info("퇴근 기록 저장 완료");
		} else {
			log.info("퇴근 기록 저장 실패");
			return;
		}
		
		// 퇴근 시간 조회
		String checkInDateStr = dao.selectClockOut(empId);
		
		// 근무형태 확인 (지각 여부)
		String checkLate = dao.selectAttendtype(empId);
		
		String exitHour = infoAtten.get("exitHour");
		String useBonusTime = infoAtten.get("useBonusTime");
		
		if(checkLate == null) {
			if(exitHour == "N") {
				if(useBonusTime == "Y") { // 보상시간 사용하여 퇴근
					calAttendance(empId);
					calculateExtraTime(empId, checkInDateStr);
				} else { // 조퇴
					calAttendance(empId);
					Map<String, Object> attendtypeMap = new HashMap<>();
					attendtypeMap.put("attendType", "조퇴");
					attendtypeMap.put("empId", empId);
					dao.updateAttendtype(attendtypeMap);
				}
			} else { // 정상퇴근
				calAttendance(empId);
				calculateExtraTime(empId, checkInDateStr);
			}
		} else {
			calAttendance(empId);
		}
	}

	@Override
	public void calculateExtraTime(String empId, String checkInDateStr) {
		log.info("보상시간 계산 시작");
		
		// 기준 퇴근 시간 (18시)
		Calendar cal = new GregorianCalendar();
		String checkOutDateStr = String.format("%d%02d%02d180000",
			cal.get(Calendar.YEAR),
			cal.get(Calendar.MONTH) + 1,
			cal.get(Calendar.DATE)
		);
		
		// Date로 변환할 Format
		SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat customFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		
		try {
			Date checkInDate = dbFormat.parse(checkInDateStr);
			Date checkOutDate = customFormat.parse(checkOutDateStr);
			
			// 차이값을 시간 단위로 변경
			int diffHour = (int)(checkInDate.getTime() - checkOutDate.getTime())/(60*60*1000); // 60초*60분*밀리세컨드
			log.info("퇴근 시간 차이 : {}", diffHour);
			
			// 보상시간 로직
			if(checkInDate.after(checkOutDate)) { // 야근 및 정상근무
				Map<String, Object> ExtraTimeMap = new HashMap<>();
				ExtraTimeMap.put("extraTime", diffHour);
				ExtraTimeMap.put("empId", empId);
				
				Map<String, Object> AttendtypeMap = new HashMap<>();
				AttendtypeMap.put("attendType", "정상근무");
				AttendtypeMap.put("empId", empId);
				
				Vdao.updateExtraTime(ExtraTimeMap);
				updateAttendtype(AttendtypeMap);
			} else { // 18시보다 일찍 퇴근했을 때
				
				diffHour--;
				int bonus = Vdao.selectExtraTime(empId);
				log.info("보유한 보상시간 : {}", bonus);
				
				if((-1)*diffHour <= bonus) { // 보상시간을 사용할 수 있을 때
					log.info("사용한 보상시간 : {}", diffHour);
					
					Map<String, Object> extraTimeMap = new HashMap<>();
					extraTimeMap.put("empId", empId);
					extraTimeMap.put("extraTime", diffHour);
					Vdao.updateExtraTime(extraTimeMap);
					
					Map<String, Object> useExtraTimeMap = new HashMap<>();
					useExtraTimeMap.put("empId", empId);
					useExtraTimeMap.put("useExtraTime", (-1)*diffHour);
					updateUseExtraTime(useExtraTimeMap);
					
					int useBonus = Vdao.selectExtraTime(empId);
					log.info("남은 보상시간 : {}", useBonus);
					
					Map<String, Object> attendtypeMap = new HashMap<>();
					attendtypeMap.put("attendType", "정상근무");
					attendtypeMap.put("empId", empId);
					updateAttendtype(attendtypeMap);
					
				} else {
					log.info("보유한 보상시간 부족, 조퇴처리");
					
					Map<String, Object> attendtypeMap = new HashMap<>();
					attendtypeMap.put("attendType", "조퇴");
					attendtypeMap.put("empId", empId);
					
					updateAttendtype(attendtypeMap);
				}
			}
			
		} catch (ParseException e) {
			log.error("날짜 변환 오류", e);
		}
	}

	@Override
	public String avgClockInTime(String empId) {
		String clockInTime = dao.avgClockInTime(empId);
		
		if (clockInTime == null) {
			return "";
		}
		
		try {
			SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm:ss");
			Date date = inputFormat.parse(clockInTime);
			
			SimpleDateFormat outputFormat = new SimpleDateFormat("a h시 mm분");
			return outputFormat.format(date); 
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@Override
	public String avgClockInTimeAll() {
		String clockInTime = dao.avgClockInTimeAll();

		try {
			SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm:ss");
			Date date = inputFormat.parse(clockInTime);
			
			SimpleDateFormat outputFormat = new SimpleDateFormat("a h시 mm분");
			return outputFormat.format(date); 
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
	}

	@Override
	public String avgClockOutTime(String empId) {
		String clockInTime = dao.avgClockOutTime(empId);
		
		if (clockInTime == null) {
			return "";
		}
		
		try {
			SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm:ss");
			Date date = inputFormat.parse(clockInTime);
			
			SimpleDateFormat outputFormat = new SimpleDateFormat("a h시 mm분");
			return outputFormat.format(date); 
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@Override
	public String avgClockOutTimeAll() {
		String clockInTime = dao.avgClockOutTimeAll();

		try {
			SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm:ss");
			Date date = inputFormat.parse(clockInTime);
			
			SimpleDateFormat outputFormat = new SimpleDateFormat("a h시 mm분");
			return outputFormat.format(date); 
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
	}

	@Override
	public String avgWorkTime(String empId) {
		String clockInTime = dao.avgWorkTime(empId);
		
		if (clockInTime == null) {
			return "";
		}
		
		try {
			SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm");
			Date date = inputFormat.parse(clockInTime);
			
			SimpleDateFormat outputFormat = new SimpleDateFormat("h시간 mm분");
			return outputFormat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@Override
	public String avgWorkTimeAll() {
		String clockInTime = dao.avgWorkTimeAll();

		try {
			SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm");
			Date date = inputFormat.parse(clockInTime);
			
			SimpleDateFormat outputFormat = new SimpleDateFormat("h시간 mm분");
			return outputFormat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@Override
	public List<Map<String, Object>> avgWorkTimeByDept() {
		return dao.avgWorkTimeByDept();
	}

	@Override
	public int selectLate(String empId) {
		return dao.selectLate(empId);
	}
	
	@Override
	public int selectLateToday() {
		return dao.selectLateToday();
	}
	
	@Override
	public List<Map<String, Object>> getLateEmpRank(){
		return dao.getLateEmpRank();
	}

	@Override
	public double calProgress(String empId, String ClockIn) {
		
		String chkClockOut = dao.selectClockOut(empId);
		String clockIn = dao.selectClockIn(empId);
		
		// 기준 퇴근 시간 (18시)
		Calendar cal = new GregorianCalendar();
		String checkOut = String.format("%d%02d%02d180000",
			cal.get(Calendar.YEAR),
			cal.get(Calendar.MONTH) + 1,
			cal.get(Calendar.DATE)
		);
		
		// Date로 변환할 Format
		SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat customFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		
		try {
			Date checkInTime = dbFormat.parse(clockIn);
			Date checkOutTime = customFormat.parse(checkOut);
			
			if (chkClockOut != null) {
				return 100;
			}
			
			// 현재시간을 가져옴
            Date currentTime = new Date();
            
            // 출근시간부터 기준퇴근시간까지의 총 시간 차이
            long totalWorkTime = checkOutTime.getTime() - checkInTime.getTime();
            
            // 현재시간까지의 진행된 시간
            long elapsedTime = currentTime.getTime() - checkInTime.getTime();
            
            // 진행률 계산 (0~100%)
            double progress = (double) elapsedTime / totalWorkTime * 100;
            
            // 반올림 처리
            progress = Math.round(progress); 
            
            return (int)progress;
            
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<Map<String, Object>> getCalendar(String empId) {
		return dao.getCalendar(empId);
	}

	

	

}
