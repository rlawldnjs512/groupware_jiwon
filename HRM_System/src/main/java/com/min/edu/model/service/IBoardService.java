package com.min.edu.model.service;

import java.util.List;
import java.util.Map;

import com.min.edu.dto.FileUpDto;
import com.min.edu.dto.FreeboardDto;
import com.min.edu.dto.NoticeboardDto;

public interface IBoardService {

	public int insertNotice(NoticeboardDto dto);
	public int insertFile(FileUpDto dto);
	public int getNotId();
	public int updateFileExist(int notId);
	
	public int updateNotice(NoticeboardDto dto);
	
	public int deleteNotice(int notId);
	public int deletefile(int notId);
	
	public int deleteNoticeDead();
	
	public List<NoticeboardDto> selectNotice();
	
	public int insertFree(FreeboardDto dto);
	public int insertFileFree(FileUpDto dto);
	public int getFreeId();
	public int updateFreeFileExist(int freeId);
	
	public int updateFree(FreeboardDto dto);
	
	public int deleteFree(FreeboardDto dto);
	public int deleteFreeFile(int freeId);
	
	public int insertFreeReply(FreeboardDto dto1);
	public int updateReply(FreeboardDto dto2);
	public boolean reply(FreeboardDto dto2);
	
	public int updateFreeReply(FreeboardDto dto);
	
	public int deleteFreeReply(FreeboardDto dto);
	
	public List<FreeboardDto> selectFree();
	
	public FileUpDto selectNoticeFile(int notId);
	
	public FileUpDto selectFreeFile(int freeId);
	
	
	public NoticeboardDto getNoticeById(int notId);
	
	public int countNoticePage();
	public List<NoticeboardDto> selectNoticePage(Map<String, Object> map);
	
	public FreeboardDto getFreeById(int freeId);
	
	public int countFreePage();
	public List<FreeboardDto> selectFreePage(Map<String, Object> map);
	
	// 게시글 내용으로 조회하기 (공지사항/커뮤니티)
	// 공지사항
	public List<NoticeboardDto> searchNotice(Map<String, Object> map);
	public int countSearchNotice(String keyword);

	public List<NoticeboardDto> searchNoticeTitle(Map<String, Object> map);
	public int countSearchNoticeTitle(String keyword);

	// 커뮤니티
	public List<FreeboardDto> searchFree(Map<String, Object> map);
	public int countSearchFree(String keyword);

	public List<FreeboardDto> searchFreeTitle(Map<String, Object> map);
	public int countSearchFreeTitle(String keyword);

	public List<FreeboardDto> searchFreeName(Map<String, Object> map);
	public int countSearchFreeName(String keyword);
}
