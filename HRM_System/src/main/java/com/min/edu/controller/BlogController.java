package com.min.edu.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.min.edu.dto.EmpPageDto;
import com.min.edu.dto.EmployeeDto;
import com.min.edu.dto.FileUpDto;
import com.min.edu.dto.FreeboardDto;
import com.min.edu.dto.NoticeboardDto;
import com.min.edu.model.service.IBoardService;
import com.min.edu.model.service.ICertificateService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@EnableScheduling
public class BlogController {

	private final IBoardService service;
	
	@GetMapping(value = "/notice.do")
	public String noticeBlog(Model model, HttpSession session, HttpServletRequest req) {

	    // 현재 페이지 가져오기 (기본값: 1)
	    String pageParam = req.getParameter("page");
	    if (pageParam == null) {
	        pageParam = "1";
	    }
	    int selectPage = Integer.parseInt(pageParam);

	    // EmpPageDto 생성 및 설정
	    EmpPageDto d = new EmpPageDto();
	    d.setTotalCount(service.countNoticePage()); // 전체 공지사항 개수
	    d.setCountList(10); // 한 페이지에 표시될 글 개수
	    d.setCountPage(5); // 한 번에 표시될 페이지 개수
	    d.setTotalPage(d.getTotalCount()); // 전체 페이지 수 계산
	    d.setPage(selectPage); // 현재 페이지 설정
	    d.setStagePage(d.getPage()); // 페이지 그룹 시작 번호 계산
	    d.setEndPage(); // 페이지 그룹 끝 번호 계산

	    // 페이징을 위한 first, last 설정
	    int first = (d.getPage() - 1) * d.getCountList() + 1;
	    int last = d.getPage() * d.getCountList();

	    // Map을 사용하여 first, last 값을 담아 전달
	    Map<String, Object> map = new HashMap<>();
	    map.put("first", first);
	    map.put("last", last);

	    // 페이징 적용된 공지사항 리스트 가져오기
	    List<NoticeboardDto> lists = service.selectNoticePage(map);

	    // 모델에 데이터 추가
	    model.addAttribute("lists", lists);
	    model.addAttribute("page", d); // 페이징 데이터 추가

	    return "notice"; // notice.jsp 반환
	}

	
	@GetMapping(value = "/newNotice.do")
	public String noticeBlog_new() {
		return "newNotice";
	}
	
	@PostMapping(value = "/submitNotice.do")
	public String noticeBlog_insert(Model model, HttpSession session,
	                                 @RequestParam("title") String title,
	                                 @RequestParam("content") String content,
	                                 @RequestParam("expired") String expired,
	                                 @RequestParam(value = "file", required = false) MultipartFile file,
	                                 HttpServletRequest request) {

	    EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");


	    // 공지사항 DTO 생성
	    NoticeboardDto dto = NoticeboardDto.builder()
	            .title(title)
	            .content(content)
	            .expired(expired)
	            .build();

	    // 공지사항 등록
	    int resultNot = service.insertNotice(dto);
	    int not_id = service.getNotId(); // 방금 등록한 공지사항의 ID 가져오기

	    // 파일이 있을 경우 저장
	    if (file != null && !file.isEmpty()) {
	        try {
	            String uploadDir = request.getSession().getServletContext().getRealPath("fileup");
	            File uploadFolder = new File(uploadDir);

	            // 디렉토리가 없으면 생성
	            if (!uploadFolder.exists()) {
	                uploadFolder.mkdirs();
	            }

	            // 파일 저장 정보 설정
	            String origin_name = file.getOriginalFilename();
	            String store_name = System.currentTimeMillis() + "_" + origin_name;
	            String file_path = uploadDir + File.separator + store_name;

	            File dest = new File(file_path);
	            file.transferTo(dest);

	            int size = (int) file.getSize();

	            // 파일 등록
	            int resultFile = service.insertFile(FileUpDto.builder()
	                    .not_id(not_id)
	                    .origin_name(origin_name)
	                    .store_name(store_name)
	                    .size(size)
	                    .file_path(file_path)
	                    .build());

	            // 첨부파일이 등록되었으면 file_exist 컬럼 업데이트
	            service.updateFileExist(not_id);

	            model.addAttribute("origin_name", origin_name);
	            model.addAttribute("store_name", store_name);
	            model.addAttribute("file_path", file_path);

	        } catch (IOException e) {
	            e.printStackTrace();
	            return "redirect:/notice.do";
	        }
	    }

	    // 세션에 공지사항 ID 저장
	    session.setAttribute("not_id", not_id);

	    return "redirect:/notice.do";
	}

	@GetMapping(value = "/getFileInfo.do")
	public ResponseEntity<byte[]> noticeFileDownload(HttpServletRequest request, 
							         HttpServletResponse response,
							         @RequestParam("not_id") int notId,
							         Model model
									) throws IOException {
			
		FileUpDto files = service.selectNoticeFile(notId);

		 // 파일 저장 경로 설정 (절대 경로를 사용하는 방식)
	    String uploadDir = System.getProperty("user.dir") + File.separator + "src\\main\\webapp\\fileup";  // 절대 경로로 관리
	    
	    System.out.println(notId);
	    System.out.println(uploadDir);
	    System.out.println(files.getStore_name());
	    System.out.println(files.getOrigin_name());
	    
	    File file = new File(uploadDir, files.getStore_name());

	    // 파일이 존재하지 않을 경우 예외 발생 (파일 없음 방지)
	    if (!file.exists()) {
	        throw new FileNotFoundException("파일을 찾을 수 없습니다: " + file.getAbsolutePath());
	    }

	    System.out.println(file);
	    // 파일을 바이트 배열로 변환
	    byte[] fileBytes = FileCopyUtils.copyToByteArray(file);

	    // 파일명 인코딩 (브라우저 호환성 고려)
	    String encodedFileName = URLEncoder.encode(files.getOrigin_name(), StandardCharsets.UTF_8)
	            .replaceAll("\\+", "%20"); // 공백 처리


	    // 파일을 브라우저에 응답해준다.
	    response.setHeader("Content-Disposition", "attachment; filename=\""+encodedFileName+"\"");
		response.setContentLength(fileBytes.length);
		response.setContentType("application/octet-stream");
		
		model.addAttribute("file", file);

	    // 파일 데이터 응답 반환
	    return ResponseEntity.ok().body(fileBytes);
	}
	
	@GetMapping(value = "/noticeModify.do")
	public String noticeBlog_modify(@RequestParam("not_id") int notId, Model model, HttpSession session) {

	    EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");

	    // not_id에 해당하는 공지사항을 조회
	    NoticeboardDto notice = service.getNoticeById(notId);
	    FileUpDto file = service.selectNoticeFile(notId);

	    if (notice == null) {
	        return "redirect:/notice.do"; // 해당 공지가 없으면 리스트로 이동
	    }

	    model.addAttribute("notice", notice);
	    model.addAttribute("file", file);
	    
	    return "modifyNotice"; // 수정 페이지로 이동
	}
	
	@PostMapping(value = "/modifyNotice.do")
	public String modify_noficesuccess(@RequestParam("not_id") int notId,
	                                    @RequestParam("content") String content,
	                                    HttpSession session) {
	    
	    EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");

	    NoticeboardDto dto = NoticeboardDto.builder()
	                                        .not_id(notId)
	                                        .content(content)
	                                        .build();

	    int result = service.updateNotice(dto);
	    
	    if (result > 0) {
	        return "redirect:/notice.do";
	    } else {
	        return "redirect:/modifyNotice.do?not_id=" + notId;
	    }
	}
	
	@GetMapping(value = "/noticeDelete.do")
	public String noticeDelete(@RequestParam("not_id") int notId,
								HttpSession session) {

	    EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");

	    int fileResult = service.deletefile(notId);
	    int result = service.deleteNotice(notId);

	    if (result > 0) {
	        return "redirect:/notice.do";
	    } else {
	        return "redirect:/notice.do"; 
	    }
	}
	
//	@Scheduled(cron = "0/20 * * * * *") 스케줄링 실행되는지 테스트용
	@Scheduled(cron = "0 0 12 1/1 * *")
	public void notice_expired() {
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date now = new Date();
		String strDate = date.format(now);
		System.out.println("스케쥴러 동작 => " + strDate);
		
		service.deleteNoticeDead();
	}
	
	@GetMapping(value = "/searchNotice.do")
	public String notice_search(@RequestParam(value = "type",required = false) String type,
								@RequestParam(value = "keyword",required = false) String keyword,
								HttpServletRequest request, 
								HttpServletResponse response,
								Model model,
								HttpSession session) {
		
		List<NoticeboardDto> lists = new ArrayList<NoticeboardDto>();
		
		// 현재 페이지 가져오기 (기본값: 1)
	    String pageParam = request.getParameter("page");
	    if (pageParam == null) {
	        pageParam = "1";
	    }
	    int selectPage = Integer.parseInt(pageParam);

	    // EmpPageDto 생성 및 설정
	    EmpPageDto d = new EmpPageDto();
	    d.setTotalCount(service.countNoticePage()); // 전체 공지사항 개수
	    d.setCountList(10); // 한 페이지에 표시될 글 개수
	    d.setCountPage(5); // 한 번에 표시될 페이지 개수
	    d.setTotalPage(d.getTotalCount()); // 전체 페이지 수 계산
	    d.setPage(selectPage); // 현재 페이지 설정
	    d.setStagePage(d.getPage()); // 페이지 그룹 시작 번호 계산
	    d.setEndPage(); // 페이지 그룹 끝 번호 계산

	    // 페이징을 위한 first, last 설정
	    int first = (d.getPage() - 1) * d.getCountList() + 1;
	    int last = d.getPage() * d.getCountList();

	    // Map을 사용하여 first, last 값을 담아 전달
	    Map<String, Object> map = new HashMap<>();
	    map.put("first", first);
	    map.put("last", last);
	    map.put("keyword", keyword);

	    if(type == null || keyword == null || keyword.trim().isEmpty()) {
	    	return "redirect:/notice.do";
	    } else {
	    	int totalCount = 0;
	    	if("title".equals(type)) {
	    		lists = service.searchNoticeTitle(map);
	    		totalCount = service.countSearchNoticeTitle(keyword);
	    	} else if("content".equals(type)) {
	    		lists = service.searchNotice(map);
	    		totalCount = service.countSearchNotice(keyword);
	    	}
	    	
	    	d.setTotalCount(totalCount); // 전체 공지사항 개수
		    d.setCountList(10); // 한 페이지에 표시될 글 개수
		    d.setCountPage(5); // 한 번에 표시될 페이지 개수
		    d.setTotalPage(totalCount); // 전체 페이지 수 계산
		    d.setPage(selectPage); // 현재 페이지 설정
		    d.setStagePage(d.getPage()); // 페이지 그룹 시작 번호 계산
		    d.setEndPage(); // 페이지 그룹 끝 번호 계산
	    }
	    
	    
	    // 모델에 데이터 추가
	    model.addAttribute("lists", lists);
	    model.addAttribute("type", type);
	    model.addAttribute("keyword", keyword);
	    model.addAttribute("page", d); // 페이징 데이터 추가

	    return "notice"; // notice.jsp 반환
	    
	}
	

//	-------------- 공지사항 end --------------
	
	
//	-------------- 커뮤니티 end --------------
	
	
	@GetMapping(value = "/free.do")
	public String freeBlog_move(Model model, HttpSession session, HttpServletRequest req) {
		
		List<FreeboardDto> freeBoards = service.selectFree();
		model.addAttribute("freeBoards", freeBoards);
		
		// 현재 페이지 가져오기 (기본값: 1)
	    String pageParam = req.getParameter("page");
	    if (pageParam == null) {
	        pageParam = "1";
	    }
	    int selectPage = Integer.parseInt(pageParam);

	    // EmpPageDto 생성 및 설정
	    EmpPageDto d = new EmpPageDto();
	    d.setTotalCount(service.countFreePage()); // 전체 공지사항 개수
	    d.setCountList(10); // 한 페이지에 표시될 글 개수
	    d.setCountPage(5); // 한 번에 표시될 페이지 개수
	    d.setTotalPage(d.getTotalCount()); // 전체 페이지 수 계산
	    d.setPage(selectPage); // 현재 페이지 설정
	    d.setStagePage(d.getPage()); // 페이지 그룹 시작 번호 계산
	    d.setEndPage(); // 페이지 그룹 끝 번호 계산

	    // 페이징을 위한 first, last 설정
	    int first = (d.getPage() - 1) * d.getCountList() + 1;
	    int last = d.getPage() * d.getCountList();

	    // Map을 사용하여 first, last 값을 담아 전달
	    Map<String, Object> map = new HashMap<>();
	    map.put("first", first);
	    map.put("last", last);

	    // 페이징 적용된 공지사항 리스트 가져오기
	    List<FreeboardDto> lists = service.selectFreePage(map);

	    // 모델에 데이터 추가
	    model.addAttribute("lists", lists);
	    model.addAttribute("page", d); // 페이징 데이터 추가
		
		System.out.println("Lists size: " + lists.size());
		
		return "free";
	}
	
	@GetMapping(value = "/newFree.do")
	public String FreeBlog_new() {
		return "newFree";
	}
	
	@PostMapping(value = "/submitFree.do")
	public String FreeBlog_insert(Model model, HttpSession session,
								RedirectAttributes redirectAttributes,
								@RequestParam("title") String title,
								@RequestParam("content") String content,
								@RequestParam(value = "file" , required = false) MultipartFile file,
								HttpServletRequest request) {

		EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");
		
		String name = loginVo.getName();
		
		// 커뮤니티 DTO 생성
		FreeboardDto dto = FreeboardDto.builder()
			.name(name)
			.title(title)
			.content(content)
			.build();
		
		int result = service.insertFree(dto);
		int free_id = service.getFreeId();
		
		if(file != null && !file.isEmpty()) {
			try {
				String uploadDir = request.getSession().getServletContext().getRealPath("fileup");
				File uploadFolder = new File(uploadDir);
				
				if(!uploadFolder.exists()) {
					uploadFolder.mkdirs();
				}
				
				String origin_name = file.getOriginalFilename();
				String store_name = System.currentTimeMillis() + "_" + origin_name;
				String file_path = uploadDir + File.separator + store_name;
				
				File dest = new File(file_path);
				file.transferTo(dest);
				
				int size = (int)file.getSize();
				
				int resultFile = service.insertFileFree(FileUpDto.builder()
						.free_id(free_id)
						.origin_name(origin_name)
						.store_name(store_name)
						.size(size)
						.file_path(file_path).build());
				
				service.updateFreeFileExist(free_id);
				
				model.addAttribute("origin_name",origin_name);
				model.addAttribute("store_name",store_name);
				model.addAttribute("file_path",file_path);
				
			} catch (Exception e) {
				e.printStackTrace();
				return "redirect:/free.do";
			}
		}
		session.setAttribute("free_id", free_id);
		
		return "redirect:/free.do";
	}
	
	@GetMapping(value = "/freeModify.do")
	public String freeeBlog_modify(@RequestParam("free_id") int freeId, Model model, HttpSession session) {

	    EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");

	    if (loginVo == null) {
	        return "redirect:/login";
	    }

	    // free_id에 해당하는 공지사항을 조회
	    FreeboardDto free = service.getFreeById(freeId);

	    if (free == null) {
	        return "redirect:/free.do"; // 해당 공지가 없으면 리스트로 이동
	    }

	    model.addAttribute("free", free);
	    
	    return "modifyFree"; // 수정 페이지로 이동
	}
	
	@PostMapping(value = "/modifyFree.do")
	public String modify_freesuccess(@RequestParam("free_id") int freeId,
	                                    @RequestParam("content") String content,
	                                    HttpSession session) {
	    
	    EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");
	    
	    String name = loginVo.getName();
	    
	    if (loginVo == null) {
	        return "redirect:/login";
	    }

	    FreeboardDto dto = FreeboardDto.builder()
	    					.name(name)
	    					.free_id(freeId)
	    					.content(content)
	    					.build();

	    int result = service.updateFree(dto);
	    
	    if (result > 0) {
	        return "redirect:/free.do";
	    } else {
	        return "redirect:/modifyFree.do?free_id=" + freeId;
	    }
	}
	
	@GetMapping(value = "/freeDelete.do")
	public String freeDelete(@RequestParam("free_id") int freeId,
								HttpSession session) {

	    EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");
	    
	    String name = loginVo.getName();

	    FreeboardDto dto = FreeboardDto.builder()
						.name(name)
						.free_id(freeId)
						.build();

	    int fileResult = service.deleteFreeFile(freeId);
	    int result = service.deleteFree(dto);

	    if (result > 0) {
	        return "redirect:/free.do";
	    } else {
	        return "redirect:/free.do"; 
	    }
	}
	
	@GetMapping(value = "/freeReply.do")
	public String freeeBlog_reply(@RequestParam("free_id") int freeId, Model model, HttpSession session) {

	    EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");

	    // free_id에 해당하는 공지사항을 조회
	    FreeboardDto free = service.getFreeById(freeId);

	    if (free == null) {
	        return "redirect:/free.do"; // 해당 공지가 없으면 리스트로 이동
	    }

	    model.addAttribute("free", free);
	    
	    return "replyFree"; // 답글 페이지로 이동
	}
	
	@PostMapping(value = "/replyFree.do")
	public String FreeBlog_replyInsert(Model model, HttpSession session,
								RedirectAttributes redirectAttributes,
								@RequestParam("title") String title,
								@RequestParam("content") String content,
								@RequestParam("free_id") int freeId,
								@RequestParam(value = "file",required = false) MultipartFile file,
								HttpServletRequest request) {

		EmployeeDto loginVo = (EmployeeDto) session.getAttribute("loginVo");
		
		String name = loginVo.getName();
		
		// 커뮤니티 답글 DTO 생성
		FreeboardDto dto = FreeboardDto.builder()
					.name(name)
					.title(title)
					.content(content)
					.free_id(freeId)
					.build();
		
		boolean isc = service.reply(dto);
//		int result = service.insertFreeReply(dto);
		int freeFile_id = service.getFreeId();
		
		System.out.println("freeFile_id : " + freeFile_id);
		
		if(file != null && !file.isEmpty()) {
			try {
				String uploadDir = request.getSession().getServletContext().getRealPath("fileup");
				File uploadFolder = new File(uploadDir);
				
				if(!uploadFolder.exists()) {
					uploadFolder.mkdirs();
				}
				
				String origin_name = file.getOriginalFilename();
				String store_name = System.currentTimeMillis() + "_" + origin_name;
				String file_path = uploadDir + File.separator + store_name;
				
				File dest = new File(file_path);
				file.transferTo(dest);
				
				int size = (int)file.getSize();
				
				int resultFile = service.insertFileFree(FileUpDto.builder()
										.free_id(freeFile_id)
										.origin_name(origin_name)
										.store_name(store_name)
										.size(size)
										.file_path(file_path)
										.build());
				
				service.updateFreeFileExist(freeFile_id);
				
				model.addAttribute("origin_name",origin_name);
				model.addAttribute("store_name",store_name);
				model.addAttribute("file_path",file_path);
				
			} catch (Exception e) {
				e.printStackTrace();
				return "redirect:/free.do";
			}
		}
		session.setAttribute("freeFile_id", freeFile_id);
		
		return "redirect:/free.do";
	}
	
	@GetMapping(value = "/getFileInfoFree.do")
	public ResponseEntity<byte[]> FreeFileDownload(HttpServletRequest request, 
							         			   HttpServletResponse response,
							         			   @RequestParam("free_id") int freeId,
							         			   Model model) throws IOException {
			
		FileUpDto files = service.selectFreeFile(freeId);

		 // 파일 저장 경로 설정 (절대 경로를 사용하는 방식)
	    String uploadDir = System.getProperty("user.dir") + File.separator + "src\\main\\webapp\\fileup";  // 절대 경로로 관리
	    
	    System.out.println(uploadDir);
	    System.out.println(files.getStore_name());
	    System.out.println(files.getOrigin_name());
	    
	    File file = new File(uploadDir, files.getStore_name());

	    // 파일이 존재하지 않을 경우 예외 발생 (파일 없음 방지)
	    if (!file.exists()) {
	        throw new FileNotFoundException("파일을 찾을 수 없습니다: " + file.getAbsolutePath());
	    }

	    System.out.println(file);
	    // 파일을 바이트 배열로 변환
	    byte[] fileBytes = FileCopyUtils.copyToByteArray(file);

	    // 파일명 인코딩 (브라우저 호환성 고려)
	    String encodedFileName = URLEncoder.encode(files.getOrigin_name(), StandardCharsets.UTF_8)
	            .replaceAll("\\+", "%20"); // 공백 처리


	    // 파일을 브라우저에 응답해준다.
	    response.setHeader("Content-Disposition", "attachment; filename=\""+encodedFileName+"\"");
		response.setContentLength(fileBytes.length);
		response.setContentType("application/octet-stream");
		
		model.addAttribute("file", file);

	    // 파일 데이터 응답 반환
	    return ResponseEntity.ok().body(fileBytes);
	}
	
	@GetMapping(value = "/searchFree.do")
	public String free_search(@RequestParam(value = "type",required = false) String type,
								@RequestParam(value = "keyword",required = false) String keyword,
								HttpServletRequest request, 
								HttpServletResponse response,
								Model model,
								HttpSession session) {
		
		List<FreeboardDto> lists = new ArrayList<FreeboardDto>();
		
		// 현재 페이지 가져오기 (기본값: 1)
	    String pageParam = request.getParameter("page");
	    if (pageParam == null) {
	        pageParam = "1";
	    }
	    int selectPage = Integer.parseInt(pageParam);

	    // EmpPageDto 생성 및 설정
	    EmpPageDto d = new EmpPageDto();
	    d.setTotalCount(service.countFreePage()); // 전체 공지사항 개수
	    d.setCountList(10); // 한 페이지에 표시될 글 개수
	    d.setCountPage(5); // 한 번에 표시될 페이지 개수
	    d.setTotalPage(d.getTotalCount()); // 전체 페이지 수 계산
	    d.setPage(selectPage); // 현재 페이지 설정
	    d.setStagePage(d.getPage()); // 페이지 그룹 시작 번호 계산
	    d.setEndPage(); // 페이지 그룹 끝 번호 계산

	    // 페이징을 위한 first, last 설정
	    int first = (d.getPage() - 1) * d.getCountList() + 1;
	    int last = d.getPage() * d.getCountList();

	    // Map을 사용하여 first, last 값을 담아 전달
	    Map<String, Object> map = new HashMap<>();
	    map.put("first", first);
	    map.put("last", last);
	    map.put("keyword", keyword);

	    if(type == null || keyword == null || keyword.trim().isEmpty()) {
	    	return "redirect:/free.do";
	    } else {
	    	int totalCount = 0;
	    	if("title".equals(type)) {
	    		lists = service.searchFreeTitle(map);
	    		totalCount = service.countSearchFreeTitle(keyword);
	    	} else if("content".equals(type)) {
	    		lists = service.searchFree(map);
	    		totalCount = service.countSearchFree(keyword);
	    	} else if("name".equals(type)) {
	    		lists = service.searchFreeName(map);
	    		totalCount = service.countSearchFreeName(keyword);
	    	}
	    	
	    	d.setTotalCount(totalCount); // 전체 공지사항 개수
		    d.setCountList(10); // 한 페이지에 표시될 글 개수
		    d.setCountPage(5); // 한 번에 표시될 페이지 개수
		    d.setTotalPage(totalCount); // 전체 페이지 수 계산
		    d.setPage(selectPage); // 현재 페이지 설정
		    d.setStagePage(d.getPage()); // 페이지 그룹 시작 번호 계산
		    d.setEndPage(); // 페이지 그룹 끝 번호 계산
	    }
	    
	    // 모델에 데이터 추가
	    model.addAttribute("lists", lists);
	    model.addAttribute("type", type);
	    model.addAttribute("keyword", keyword);
	    model.addAttribute("page", d); // 페이징 데이터 추가

	    return "free"; // free.jsp 반환
		
	}
	
	
	
}