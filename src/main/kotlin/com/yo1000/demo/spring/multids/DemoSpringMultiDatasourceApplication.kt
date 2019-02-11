package com.yo1000.demo.spring.multids

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.sql.DataSource

@SpringBootApplication
class DemoSpringMultiDatasourceApplication

fun main(args: Array<String>) {
	runApplication<DemoSpringMultiDatasourceApplication>(*args)
}

/*
 * Primary JDBC Config
 */
@Primary
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
class PrimaryDataSourceProperties : DataSourceProperties()

interface PrimaryNamedParameterJdbcOperations : NamedParameterJdbcOperations

class PrimaryNamedParameterJdbcTemplate(dataSource: DataSource)
	: NamedParameterJdbcTemplate(dataSource), PrimaryNamedParameterJdbcOperations

/*
 * Secondary JDBC Config
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource-secondary")
class SecondaryDataSourceProperties : DataSourceProperties()

interface SecondaryNamedParameterJdbcOperations : NamedParameterJdbcOperations

class SecondaryNamedParameterJdbcTemplate(dataSource: DataSource)
	: NamedParameterJdbcTemplate(dataSource), SecondaryNamedParameterJdbcOperations

/*
 * Bean Config
 */
@Configuration
@EnableConfigurationProperties
class JdbcConfig {
	@Bean
	fun primaryNamedParameterJdbcOperations(
			props: PrimaryDataSourceProperties
	): PrimaryNamedParameterJdbcOperations = PrimaryNamedParameterJdbcTemplate(
			DataSourceBuilder.create()
					.driverClassName(props.driverClassName)
					.url(props.url)
					.username(props.username)
					.password(props.password)
					.build()
	)

	@Bean
	fun secondaryNamedParameterJdbcOperations(
			props: SecondaryDataSourceProperties
	): SecondaryNamedParameterJdbcOperations = SecondaryNamedParameterJdbcTemplate(
			DataSourceBuilder.create()
					.driverClassName(props.driverClassName)
					.url(props.url)
					.username(props.username)
					.password(props.password)
					.build()
	)
}
