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
public class CertificateDto {

	private int req_id;
	private String emp_id;
	private String type;
	private String req_date;
	private String cert_status;
	private String cert_date;
	private String reason;
	private String cert_num;
	private String cert_path;
	private String is_download;
	
	// 추가
	private String name;
	
}
