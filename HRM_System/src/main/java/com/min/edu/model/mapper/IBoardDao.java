package com.min.edu.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.min.edu.dto.FileUpDto;
import com.min.edu.dto.FreeboardDto;
import com.min.edu.dto.NoticeboardDto;

@Mapper
public interface IBoardDao {

//	관리자는 공지사항 게시글을 등록할 수 있다.
	public int insertNotice(NoticeboardDto dto);
	public int insertFile(FileUpDto dto);
	public int getNotId();
	public int updateFileExist(int notId);
	
//	관리자는 공지사항 게시글을 수정할 수 있다.
	public int updateNotice(NoticeboardDto dto);
	
//	관리자는 공지사항 게시글을 삭제할 수 있다.
	public int deleteNotice(int notId);
	public int deletefile(int notId);
	
//	기한이 지난 게시글은 자동으로 삭제된다.
	public int deleteNoticeDead();
	
//	공지사항 게시글을 조회할 수 있다.
	public List<NoticeboardDto> selectNotice();
	
//	사용자와 관리자는 자유게시글을 등록할 수 있다.
	public int insertFree(FreeboardDto dto);
	public int insertFileFree(FileUpDto dto);
	public int getFreeId();
	public int updateFreeFileExist(int freeId);
	
//	사용자와 관리자는 자신이 등록한 자유게시글만 수정할 수 있다.
	public int updateFree(FreeboardDto dto);
	
//	사용자와 관리자는 자신이 등록한 자유게시글만 삭제할 수 있다.
	public int deleteFree(FreeboardDto dto);
	public int deleteFreeFile(int freeId);
	
//	사용자와 관리자는 등록된 자유게시글에 답글을 달 수 있다.
	public int insertFreeReply(FreeboardDto dto1);
	public int updateReply(FreeboardDto dto2);
	
//	사용자와 관리자는 자신이 등록한 답글을 수정할 수 있다.
	public int updateFreeReply(FreeboardDto dto);
	
//	사용자와 관리자는 자신이 등록한 답글을 삭제할 수 있다.
	public int deleteFreeReply(FreeboardDto dto);
	
//	자유게시판을 조회할 수 있다.
	public List<FreeboardDto> selectFree();
	
//	사용자는 공지사항 게시글에 등록된 첨부파일을 여러 번 다운로드할 수 있다.
	public FileUpDto selectNoticeFile(int notId);
	
//	사용자와 관리자는 자유게시글에 등록된 첨부파일을 여러 번 다운로드할 수 있다.
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
