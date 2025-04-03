package com.min.edu.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class FileUploadController {

	@PostMapping(value = "/upload.do")
	public String filiUpload(@RequestParam List<MultipartFile> file, 
											String desc, 
											HttpServletRequest request, // 파일업로드 상대 경로를 위한 서버 path 처리
											Model model) {// 처리 결과를 담아서 페이로 전송할 객체
	
		log.info("업로드 파일의 갯수 : {}", file.size());
		log.info("전송된 form요소의 text : {}", desc);

		List<String> originFileNames = new ArrayList<String>(); // 화면에 전송할 : 파일명
		List<String> saveFileNames = new ArrayList<String>(); // 화면에 전송할 : 저장된 파일의 파일명
		String path = "";

		for (MultipartFile f : file) {
			log.info("파일의 이름 : {}", f.getOriginalFilename());
			String originFileName = f.getOriginalFilename(); // 보여줄 파일명
			String saveFileName = UUID.randomUUID().toString()
					.concat(originFileName.substring(originFileName.lastIndexOf("."))); // 저장할 파일명
			log.info("기존 파일명 : {}", originFileName);
			log.info("저장 파일명 : {}", saveFileName);

			originFileNames.add(originFileName);
			saveFileNames.add(saveFileName);

			InputStream inputStream = null;
			OutputStream outputStream = null;

			try {
				// 1)파일을 읽는다
				inputStream = f.getInputStream();

				// 2) 저장 위치를 만든다
				path = WebUtils.getRealPath(request.getSession().getServletContext(), "/storage"); // 상대 경로
				String path02 = request.getSession().getServletContext().getRealPath("storage");
				log.info("저장경로 :  \n {}\n {}", path, path02);

				// 3) 파일 저장 위치를 판단하여 생성한다
				File storage = new File(path);
				if (!storage.exists()) {
					storage.mkdirs();
				}

				// 4) 저장 공간에 저장할 파일이 없다면 생성하고 없다면 오버라이드 한다
				File newFile = new File(path + "/" + saveFileName);
				if (!newFile.exists()) {
					newFile.createNewFile();
				}

				// 5) 읽은 파일을 써주기 (저장)
				outputStream = new FileOutputStream(newFile);

				// 6) 파일을 읽어서 대상파일에 써줌
				int read = 0;
				byte[] b = new byte[(int) f.getSize()];
				while ((read = inputStream.read(b)) != -1) {
					outputStream.write(b, 0, read);
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					inputStream.close();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} // multipart while

		model.addAttribute("originFileNames", originFileNames);
		model.addAttribute("saveFileNames", saveFileNames);
		model.addAttribute("path", path);

		return "uploadFile";
	}

}
