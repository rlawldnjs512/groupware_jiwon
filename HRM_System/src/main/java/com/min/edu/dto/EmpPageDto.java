package com.min.edu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmpPageDto {

	// 현재 페이지
    private int page;     
    // 한 페이지당 보여줄 글 갯수
    private int countList;  
    // 전체 글의 갯수
    private int totalCount; 
    // 화면에 표시할 페이지 그룹의 페이지 수
    private int countPage; 
    // 전체 페이지 수
    private int totalPage;  
    // 현재 페이지 그룹의 시작 페이지 번호
    private int stagePage;  
    // 현재 페이지 그룹의 끝 페이지 번호
    private int endPage;    

    public int getPage() {
        return page;
    }
    /**
     * page가 1보다 작으면 강제로 1로 설정하고, 
     * 총 페이지보다 크면 총 페이지로 설정합니다.
     */
    public void setPage(int page) {
        if (page < 1) {
            page = 1;
        }
        if (totalPage > 0 && page > totalPage) {
            page = totalPage;
        }
        this.page = page;
    }

    public int getCountPage() {
        return countPage;
    }
    public void setCountPage(int countPage) {
        this.countPage = countPage;
    }

    public int getCountList() {
        return countList;
    }
    public void setCountList(int countList) {
        this.countList = countList;
    }

    public int getTotalPage() {
        return totalPage;
    }
    /**
     * 총 페이지 수를 계산할 때, 전체 글 수가 0이면 최소 1페이지로 처리
     */
    public void setTotalPage(int totalCount) {
        this.totalCount = totalCount;
        if(totalCount == 0) {
            this.totalPage = 1;
        } else {
            this.totalPage = (int) Math.ceil((double) totalCount / countList);
        }
    }

    public int getStagePage() {
        return stagePage;
    }
    /**
     * 현재 페이지 그룹의 시작 페이지 번호를 계산합니다.
     * (page - 1) / countPage * countPage + 1
     */
    public void setStagePage(int page) {
        this.stagePage = ((page - 1) / countPage) * countPage + 1;
    }

    public int getEndPage() {
        return endPage;
    }
    /**
     * 현재 페이지 그룹의 끝 페이지 번호 = stagePage + countPage - 1,
     * 단, 이 값이 전체 페이지 수를 초과하면 전체 페이지 수로 설정합니다.
     */
    public void setEndPage() {
        int calculatedEnd = stagePage + countPage - 1;
        if(calculatedEnd > totalPage) {
            calculatedEnd = totalPage;
        }
        this.endPage = calculatedEnd;
    }

    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
