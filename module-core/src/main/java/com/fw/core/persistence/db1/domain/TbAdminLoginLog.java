package com.fw.core.persistence.db1.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * tb_admin_login_log DOMAIN
 * @author sjpaik
 */
@Entity
@Getter
@NoArgsConstructor
@Table
public class TbAdminLoginLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순서")
    private Long adminLoginLogSeq;

    @Comment("아이디")
    private String adminId;

    @Comment("결과 여부")
    private String resultFlag;

    @Comment("접속 아이피")
    private String accessIp;

    @Comment("생성 일시")
    private LocalDateTime createDt;

    @Builder
    public TbAdminLoginLog(Long adminLoginLogSeq, String adminId, String resultFlag, String accessIp, LocalDateTime createDt) {
        this.adminLoginLogSeq = adminLoginLogSeq;
        this.adminId = adminId;
        this.resultFlag = resultFlag;
        this.accessIp = accessIp;
        this.createDt = createDt;
    }

}
