package com.fw.core.config;

import com.google.common.collect.ImmutableMap;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * 공통 데이터베이스 설정
 * @author sjpaik
 */
public class CommonDatabaseConfig {

    /**
     * JPA 정보 설정
     * @param factory LocalContainerEntityManagerFactoryBean
     * @param map ImmutableMap<String, String>
     */
    protected void setConfigureEntityManagerFactory(LocalContainerEntityManagerFactoryBean factory, ImmutableMap<String, String> map){
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(false);     // Hibernate 수행 SQL 쿼리 보기 옵션
        factory.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        factory.setJpaPropertyMap(map);
        factory.afterPropertiesSet();
    }

    /**
     * Mybatis 정보 설정
     * @param sqlSessionFactoryBean SqlSessionFactoryBean
     * @param dataSource DataSource
     * @throws IOException
     */
    protected void setConfigureSqlSessionFactory(SqlSessionFactoryBean sqlSessionFactoryBean, DataSource dataSource, ApplicationContext context) throws IOException {
        sqlSessionFactoryBean.setDataSource(dataSource);

        Configuration configuration = new Configuration();
        configuration.setCacheEnabled(true);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setDefaultExecutorType(ExecutorType.SIMPLE);

        sqlSessionFactoryBean.setConfiguration(configuration);
        sqlSessionFactoryBean.setMapperLocations(context.getResources("classpath*:sqlmap/**/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.fw");
    }

}
