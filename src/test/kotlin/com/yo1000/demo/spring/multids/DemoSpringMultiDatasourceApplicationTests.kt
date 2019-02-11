package com.yo1000.demo.spring.multids

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class DemoSpringMultiDatasourceApplicationTests {
	@Autowired
	lateinit var primaryNamedParameterJdbcOperations: PrimaryNamedParameterJdbcOperations
	@Autowired
	lateinit var secondaryNamedParameterJdbcOperations: SecondaryNamedParameterJdbcOperations

	@Test
	fun contextLoads() {
		primaryNamedParameterJdbcOperations.execute("""
    	CREATE TABLE demo_primaries(
    		id		INT(10)			NOT NULL,
    		name	VARCHAR(80)		NOT NULL,
    		PRIMARY KEY(id)
    	)
		""".trimIndent()) {}

		secondaryNamedParameterJdbcOperations.execute("""
    	CREATE TABLE demo_secondaries(
    		id		INT(10)			NOT NULL,
    		name	VARCHAR(80)		NOT NULL,
    		PRIMARY KEY(id)
    	)
		""".trimIndent()) {}
	}
}
