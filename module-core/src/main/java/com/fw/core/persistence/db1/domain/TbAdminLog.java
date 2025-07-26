package com.fw.core.persistence.db1.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * tb_admin_log DOMAIN
 * @author sjpaik
 */
@Entity
@Getter
@NoArgsConstructor
@Table
public class TbAdminLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순서")
    private Long apiLogSeq;

    @Comment("접속 환경 유형 (local, dev, prod)")
    private String profile;

    @Comment("호스트")
    private String host;

    @Comment("URL")
    private String url;

    @Comment("요청 헤더")
    @Lob
    private String requestHeader;

    @Comment("오류 로그")
    @Lob
    private String errorLog;

    @Comment("파라미터")
    @Lob
    private String parameter;

    @Comment("쿼리스트링")
    @Lob
    private String queryString;

    @Comment("요청본문")
    @Lob
    private String requestBody;

    @Comment("실행 결과")
    @Lob
    private String result;

    @Comment("접속 아이피")
    private String accessIp;

    @Comment("수행 시간")
    private Long processTime;

    @Comment("생성 일시")
    private LocalDateTime createDt;

    @Builder
    public TbAdminLog(Long apiLogSeq, String profile, String host, String url, String requestHeader, String errorLog, String parameter, String queryString, String requestBody, String result, String accessIp, Long processTime, LocalDateTime createDt) {
        this.apiLogSeq = apiLogSeq;
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

}
