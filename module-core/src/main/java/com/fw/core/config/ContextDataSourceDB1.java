package com.fw.core.config;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * 공통 데이터베이스 설정
 * @author sjpaik
 */
@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "db1EntityManagerFactory",
        transactionManagerRef = "db1TransactionManager",
        basePackages = {"com.fw.core.persistence.db1.repository"}
)
@MapperScan(
        sqlSessionFactoryRef = "db1SqlSessionFactory",
        basePackages = {"com.fw.core.mapper.db1"}
)
@RequiredArgsConstructor
public class ContextDataSourceDB1 extends CommonDatabaseConfig {

    private final Environment environment;

    @Primary
    @Bean(name = "db1DataSource")
    @ConfigurationProperties(prefix = "spring.datasource-db1")
    public DataSource dataSource(){
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "db1EntityManagerFactory")
    public EntityManagerFactory entityManagerFactory(@Qualifier("db1DataSource") DataSource dataSource){
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setPackagesToScan("com.fw.core.persistence.db1.domain");
        factory.setPersistenceUnitName("db1");

        ImmutableMap<String, String> map = ImmutableMap.<String, String>builder()
                                .put("hibernate.hbm2ddl.auto", environment.getProperty("spring.datasource-db1.jpa.hibernate.hbm2ddl.auto"))
                                .put("hibernate.dialect", environment.getProperty("spring.datasource-db1.jpa.hibernate.dialect"))
                                .put("hibernate.open-in-view", environment.getProperty("spring.datasource-db1.jpa.hibernate.open-in-view"))
                                .put("hibernate.format_sql", environment.getProperty("spring.datasource-db1.jpa.hibernate.format-sql"))
                                .put("hibernate.implicit_naming_strategy", environment.getProperty("spring.datasource-db1.jpa.hibernate.implicit-naming-strategy"))
                                .put("hibernate.physical_naming_strategy", environment.getProperty("spring.datasource-db1.jpa.hibernate.physical_naming_strategy"))
                                .build();

        setConfigureEntityManagerFactory(factory, map);
        return factory.getObject();
    }

    @Bean(name = "db1SqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("db1DataSource") DataSource dataSource, ApplicationContext context) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        setConfigureSqlSessionFactory(sqlSessionFactoryBean, dataSource, context);
        return sqlSessionFactoryBean.getObject();
    }

    @Primary
    @Bean(name = "db1TransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("db1EntityManagerFactory") EntityManagerFactory entityManagerFactory){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }

}
