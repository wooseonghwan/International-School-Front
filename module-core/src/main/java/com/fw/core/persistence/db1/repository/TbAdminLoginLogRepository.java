package com.fw.core.persistence.db1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fw.core.persistence.db1.domain.TbAdminLoginLog;

/**
 * tb_admin_login_log Repository
 * 
 * @author sjpaik
 */
public interface TbAdminLoginLogRepository extends JpaRepository<TbAdminLoginLog, Long> {

}
