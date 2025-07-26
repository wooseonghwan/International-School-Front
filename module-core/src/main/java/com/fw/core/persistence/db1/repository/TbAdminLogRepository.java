package com.fw.core.persistence.db1.repository;

import com.fw.core.persistence.db1.domain.TbAdminLog;
import com.fw.core.persistence.db1.domain.TbApiLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * tb_admin_log Repository
 * @author sjpaik
 */
public interface TbAdminLogRepository extends JpaRepository<TbAdminLog, Long> {

}