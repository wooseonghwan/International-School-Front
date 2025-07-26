package com.fw.core.persistence.db1.dto;

import com.fw.core.persistence.db1.domain.TbApiLoginLog;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * tb_admin_login_log 요청 DTO
 * @author sjpaik
 */
@Getter
@NoArgsConstructor
public class TbApiLoginLogRequestDto {

    private Long apiLoginLogSeq;				// 순서
    private String id;						// 아이디
    private String resultFlag;					// 결과 여부
    private String accessIp;					// 접속 아이피
    private LocalDateTime createDt;				// 생성 일시

    @Builder
    public TbApiLoginLogRequestDto(String id, String resultFlag, String accessIp, LocalDateTime createDt) {
		this.id = id;
		this.resultFlag = resultFlag;
		this.accessIp = accessIp;
		this.createDt = createDt;
	}

    public TbApiLoginLog toEntity(){
        return TbApiLoginLog.builder().id(id)
        					     .resultFlag(resultFlag)
        					     .accessIp(accessIp)
        					     .createDt(createDt)
        					     .build();
    }

}
