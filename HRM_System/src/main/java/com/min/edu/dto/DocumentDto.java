package com.min.edu.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentDto {

	private int doc_id;
	private String emp_id;
	private String doc_type;
	private String title;
	private String content;
	private Date create_date;
	private String doc_status;
	private String doc_date;
	private int file_id;
	private String doc_num;
	
	
	// 추가
	private String name;
	private List<ApprovalDto> approvalDtos;
	
	//상세조회에 필요한 내용 추가
	private String position;
	private String signSaved;
	private String dept_name;
	
	private String apprv_level;
	private String apprv_date;
	private String sign;
	private String apprv_name;
	
	//상세조회 파일명 조회
	private String origin_name;
	private String store_name;
	private String file_exist;

}
