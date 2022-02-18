package com.supernovacompanies.api.config;

import com.supernovacompanies.multidb.config.MultiDataSource;
import com.supernovacompanies.venus.util.DBConfigTool;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.util.Arrays;
import java.util.List;

@Configuration
@MapperScan(basePackages =
        {
                "com.supernovacompanies.core.dal.configuration.dao.mapper.common",
                "com.supernovacompanies.encryption.dao.mybatis.common",        },
        sqlSessionTemplateRef = "commonDBSqlSessionTemplate")
public class CommonMybatisConfig {
    @Autowired
    private MultiDataSource multiDataSource;

    @Bean("commonDBSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(multiDataSource.getCommonDbDataSource());
        List<String> mapperLocations = Arrays.asList(
                "mybatis/configuration/common/*.xml",
                "mybatis/mapper/common/*.xml"
        );

        bean.setMapperLocations(DBConfigTool.resolveMapperLocations(mapperLocations));
        return bean.getObject();
    }

    @Bean(name = "commonDBTransactionManager")
    public DataSourceTransactionManager commonTransactionManager() {
        return new DataSourceTransactionManager(multiDataSource.getCommonDbDataSource());
    }

    @Bean(name = "commonDBSqlSessionTemplate")
    public SqlSessionTemplate commonSqlSessionTemplate(@Qualifier("commonDBSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
