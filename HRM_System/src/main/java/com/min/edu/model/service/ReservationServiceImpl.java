package com.min.edu.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.min.edu.dto.RoomDto;
import com.min.edu.model.mapper.IReservationDao;

@Service
public class ReservationServiceImpl implements IReservationService {

	@Autowired
	private IReservationDao dao;
	
	@Override
	public List<RoomDto> selectReservation(String currDay) {
		return dao.selectReservation(currDay);
	}

	@Override
	public List<RoomDto> myReservation(String emp) {
		return dao.myReservation(emp);
	}

	@Override
	public int deleteReservation(Map<String, Object> map) {
		return dao.deleteReservation(map);
	}

	@Override
	public boolean insertReservation(Map<String, Object> map) {
		return dao.insertReservation(map);
	}

	@Override
	public int insertRoom(Map<String, Object> map) {
		return dao.insertRoom(map);
	}

	@Override
	public List<RoomDto> selectRoom() {
		return dao.selectRoom();
	}

	@Override
	public int deleteRoom(String room_id) {
		return dao.deleteRoom(room_id);
	}

	public List<Map<String, Object>> getReservation(String empId) {
		return dao.getReservation(empId);
	}

}
