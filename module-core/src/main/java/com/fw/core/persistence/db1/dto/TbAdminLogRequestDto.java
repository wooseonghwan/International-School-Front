package com.fw.core.persistence.db1.dto;

import com.fw.core.persistence.db1.domain.TbAdminLog;
import com.fw.core.persistence.db1.domain.TbApiLog;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * tb_admin_log 요청 DTO
 * @author sjpaik
 */
@Getter
@NoArgsConstructor
public class TbAdminLogRequestDto {

	private Long apiLogSeq;						// Sequence
    private String profile;						// 접속 환경 유형 (local, dev, real)
    private String host;						// 서버 호스트
    private String url;							// 접속 API URL
    private String requestHeader;				// Request Header
    private String errorLog;					// 오류 로그
    private String parameter;					// Parameter
    private String queryString;					// 쿼리스트링
    private String requestBody;					// 요청본문
    private String result;						// 결과
    private String accessIp;					// 접속 아이피
    private Long processTime;					// 실행 시간
    private LocalDateTime createDt;				// 생성일시

    @Builder
    public TbAdminLogRequestDto(String profile, String host, String url, String requestHeader, String errorLog, String parameter, String queryString, String requestBody, String result, String accessIp, Long processTime, LocalDateTime createDt) {
		this.profile = profile;
		this.host = host;
		this.url = url;
		this.requestHeader = requestHeader;
		this.errorLog = errorLog;
		this.parameter = parameter;
		this.queryString = queryString;
		this.requestBody = requestBody;
		this.result = result;
		this.accessIp = accessIp;
		this.processTime = processTime;
		this.createDt = createDt;
	}

    public TbAdminLog toEntity(){
        return TbAdminLog.builder().profile(profile)
        					     .host(host)
        					     .url(url)
        					     .requestHeader(requestHeader)
        					     .errorLog(errorLog)
        					     .parameter(parameter)
        					     .queryString(queryString)
        					     .requestBody(requestBody)
        					     .result(result)
								 .accessIp(accessIp)
        					     .processTime(processTime)
        					     .createDt(createDt)
        					     .build();
    }

}
