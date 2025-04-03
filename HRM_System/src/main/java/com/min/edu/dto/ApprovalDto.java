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
public class ApprovalDto {

	private int apprv_id;
	private int doc_id;
	private String emp_id;
	private String apprv_level;
	private String apprv_status;
	private String apprv_date;
	private String signSaved;
	private String sign;
	
	// 추가
	private String name;
	
	
	
	// 결재할 내역 리스트를 위해 추가됨
    private String doc_type;     
    private String title;        
        
    private String doc_status;   
    private String doc_date;      
    
    private String dept_name;
    private String doc_num;
    
    private String myCnt;
    private String continueCnt;
    private String tempCnt;
    
}
