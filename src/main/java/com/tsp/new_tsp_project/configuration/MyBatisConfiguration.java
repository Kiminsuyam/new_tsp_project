package com.tsp.new_tsp_project.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Slf4j
@Configuration
@ComponentScan(basePackages = "com.tsp")
public class MyBatisConfiguration {

	@Autowired
	ApplicationContext applicationContext;

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);

		Resource[] arrResource = new PathMatchingResourcePatternResolver()
				.getResources("classpath:mapper/**/*.xml");

		sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:config/mybatis-config.xml"));
		sqlSessionFactoryBean.setMapperLocations(arrResource);
		sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);

		return sqlSessionFactoryBean.getObject();
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
