package com.example;

import org.springframework.context.annotation.Configuration;

//@Profile("h2")
@Configuration
public class H2DataSource {

	/*@ConfigurationProperties(prefix = "spring.datasource")
	@Bean
	@Primary
	public DataSource dataSource() {
		return DataSourceBuilder
				.create()
				.build();
	}*/

	// jdbc:h2:mem:testdb
	//@Bean
	/*public DataSource dataSource() {
		//return DataSourceBuilder.create().build();
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder
				.setType(EmbeddedDatabaseType.H2)
				//.addScript("schema.sql")
				//.addScript("data.sql")
				.build();
		return db;
	}*/

	/*@Bean
	public DataSource dataSource1(){
		DataSource dataSource;
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		dataSource = builder.build();
		Flyway flyway = new Flyway();
		flyway.setInitVersion("1.5.2");
		flyway.setLocations("classpath:/org/cloudfoundry/identity/uaa/db/hsqldb/");
		flyway.setDataSource(dataSource);
		flyway.migrate();
	}*/

	//@Bean
	/*public DataSource dataSource() throws Exception {
		return createH2DataSource();
	}*/

	/*public DataSource createH2DataSource(){
		String H2_JDBC_URL_TEMPLATE = "jdbc:h2:test";
		//String H2_JDBC_URL_TEMPLATE = "jdbc:h2:~/test";
		String jdbcUrl = String.format(H2_JDBC_URL_TEMPLATE, System.getProperty("user.dir"));
		JdbcDataSource ds = new JdbcDataSource();
		ds.setURL(jdbcUrl);
		ds.setUser("sa");
		ds.setPassword("");
		return ds;
	}*/

	// Start WebServer, access http://localhost:8082
	//@Bean(initMethod = "start", destroyMethod = "stop")
	/*public Server startDBManager() throws SQLException {
		return Server.createWebServer();
	}*/

}



