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
@MapperScan(basePackages = {
        "com.supernovacompanies.encryption.dao.mybatis.business",
        "com.supernovacompanies.core.dal.configuration.dao.mapper.universe",
        "com.supernovacompanies.core.dal.utility.dao.mapper",
        "com.supernovacompanies.core.dal.common.dao.mapper"
}, sqlSessionTemplateRef = "businessDBSqlSessionTemplate")
public class BusinessMybatisConfig {

    @Autowired
    private MultiDataSource multiDataSource;

    @Bean(name = "businessDBSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(multiDataSource.getBusinessDataSource());
        List<String> mapperLocations = Arrays.asList(
                "mybatis/mapper/business/*.xml",
                "mybatis/api/doc/*.xml",
                "mybatis/configuration/universe/*.xml",
                "mybatis/universe/*.xml"
        );

        bean.setMapperLocations(DBConfigTool.resolveMapperLocations(mapperLocations));
        return bean.getObject();
    }

    @Bean(name = "businessDBTransactionManager")
    public DataSourceTransactionManager businessTransactionManager() {
        return new DataSourceTransactionManager(multiDataSource.getBusinessDataSource());
    }

    @Bean(name = "businessDBSqlSessionTemplate")
    public SqlSessionTemplate smaSqlSessionTemplate(@Qualifier("businessDBSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
