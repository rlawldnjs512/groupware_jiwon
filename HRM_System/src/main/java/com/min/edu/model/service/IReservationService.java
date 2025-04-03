package com.min.edu.model.service;

import java.util.List;
import java.util.Map;

import com.min.edu.dto.RoomDto;

public interface IReservationService {

	public List<RoomDto> selectReservation(String currDay);
	
	public List<RoomDto> myReservation(String emp);
	
	public int deleteReservation(Map<String, Object> map);
	
	public boolean insertReservation(Map<String, Object> map);
	
	public int insertRoom(Map<String, Object> map);
	

	public int deleteRoom(String room_id);
	
	public List<RoomDto> selectRoom();

	public List<Map<String, Object>> getReservation(String empId);


}
