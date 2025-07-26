package com.fw.core.persistence.db1.repository;

import com.fw.core.persistence.db1.domain.TbApiLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * tb_api_log Repository
 * @author sjpaik
 */
public interface TbApiLogRepository extends JpaRepository<TbApiLog, Long> {

}