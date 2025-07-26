package com.fw.core.persistence.db1.repository;

import com.fw.core.persistence.db1.domain.TbApiLoginLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * tb_api_login_log Repository
 * @author jy
 */
public interface TbApiLoginLogRepository extends JpaRepository<TbApiLoginLog, Long> {

}