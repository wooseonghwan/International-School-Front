package com.fw.core.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 *  Common DTO
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonDTO {

    //private FoSessionDTO frontSession;

    private String cd;
    private String cdNm;
    private String order;
    private String groupCd;
    private String fileSeq;
    private int page = 1;           // 현재 페이지
    private Integer startRow;       // 시작 Row
    private Integer endRow;         // 종료 Row
    private int rowSize = 10;       // 페이지당 Row Size
    private int totalCount;         // 총 개수
    private int totalPage;        // 총 페이지 수
    private int startPage;        // 현재 페이징 블록 시작 페이지
    private int endPage;          // 현재 페이징 블록 끝 페이지
    private int pageBlockSize = 5; // 하단에 보일 페이지 수 (1~5, 6~10 등)
    private int rowNum;             // Row Number
    public int getStartRow(){
        return (this.rowSize * this.page) - this.rowSize;
    }
    public int getEndRow() {
        return this.rowSize;
    }
    public int getPageCnt(){
        return this.rowSize;
    }
    public int getPageCount(){
        return this.rowSize;
    }
    public int getCurrentPage() {
        return this.page;
    }

    /** 페이지 블록 : 페이지 블록 사이즈 */
    public int getLastNo(){
        return 10;
    }
    private String orderType;    // 정렬기준
    private String sortColumn;
    //private List<FileDTO> commonFileList;
    private String searchValue;
    private String searchType;
    private String searchType2;
    private MultipartFile[] files;
    private String groupNm;
    private String upperCd;
    private String cdReplace1;
    private String cdLevel;
    private String ip;

    public void calculatePaging() {
        if (page < 1) page = 1;
        if (rowSize < 1) rowSize = 10;

        this.startRow = (page - 1) * rowSize;
        this.endRow = rowSize;

        this.totalPage = (int) Math.ceil((double) totalCount / rowSize);

        this.startPage = ((page - 1) / pageBlockSize) * pageBlockSize + 1;
        this.endPage = Math.min(startPage + pageBlockSize - 1, totalPage);
    }
}