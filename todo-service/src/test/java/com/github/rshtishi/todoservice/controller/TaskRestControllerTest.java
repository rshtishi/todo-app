package com.github.rshtishi.todoservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.github.rshtishi.todoservice.service.TaskService;

@WebFluxTest(controllers = TaskRestController.class)
class TaskRestControllerTest {
	
	@Autowired
	WebTestClient webClient;
	@MockBean
	TaskService taskService;

	@Test
	void testFindRecentTasks() {
		
	}

}
