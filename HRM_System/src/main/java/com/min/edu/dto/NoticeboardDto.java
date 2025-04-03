package com.min.edu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeboardDto {

	private int not_id;
	private String emp_id;
	private String title;
	private String content;
	private String regdate;
	private String delflag;
	private int file_id;
	private String expired;
	private String file_exist;
	
	private String name;
	
	 // 파일 관련 필드 추가
    private String origin_name;
    private String store_name;
    private int size;
    private String file_path;
    
    private int seq;
    
}
