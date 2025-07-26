package com.fw.core.persistence.db1.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * tb_api_login_log DOMAIN
 * @author jy
 */
@Entity
@Getter
@NoArgsConstructor
@Table
public class TbApiLoginLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순서")
    private Long apiLoginLogSeq;

    @Comment("아이디")
    private String id;

    @Comment("결과 여부")
    private String resultFlag;

    @Comment("접속 아이피")
    private String accessIp;

    @Comment("생성 일시")
    private LocalDateTime createDt;

    @Builder
    public TbApiLoginLog(Long apiLoginLogSeq, String id, String resultFlag, String accessIp, LocalDateTime createDt) {
        this.apiLoginLogSeq = apiLoginLogSeq;
        this.id = id;
        this.resultFlag = resultFlag;
        this.accessIp = accessIp;
        this.createDt = createDt;
    }

}
