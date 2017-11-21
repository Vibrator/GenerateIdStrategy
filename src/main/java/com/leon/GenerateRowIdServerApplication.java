package com.leon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
//@EnableCaching
public class GenerateRowIdServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenerateRowIdServerApplication.class, args);
	}
	
//	@Autowired
//    private Environment env;
//
//    @Bean
//    public DataSource dataSource() {
//    	DataSource  dataSource = new DataSource();
//        dataSource.setUrl(env.getProperty("spring.datasource.url"));
//        dataSource.setUsername(env.getProperty("spring.datasource.username"));//用户名
//        dataSource.setPassword(env.getProperty("spring.datasource.password"));//密码
//        dataSource.setInitialSize(2);
//        dataSource.setMaxActive(20);
//        dataSource.setMinIdle(0);
//        dataSource.setMaxWait(60000);
//        dataSource.setValidationQuery("SELECT 1");
//        dataSource.setTestOnBorrow(false);
//        dataSource.setTestWhileIdle(true);
////        dataSource.setPoolPreparedStatements(false);
//        return dataSource;
//    }
//    
//    @Bean
//	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
//		JdbcTemplate jdbcTemplate = new JdbcTemplate();
//		jdbcTemplate.setDataSource(dataSource);
//		return jdbcTemplate;
//	}
}
