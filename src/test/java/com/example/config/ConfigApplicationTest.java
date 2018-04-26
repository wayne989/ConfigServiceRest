package com.example.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * this is test case for spring boot bootstrap initialization
 * added @AutoConfigureTestDatabase to have unique embedded database is used for each test
 * to avoid issues for running the db configuration twice on the same database
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class ConfigApplicationTest {

	@Test
	public void contextLoads() {
	}

}
