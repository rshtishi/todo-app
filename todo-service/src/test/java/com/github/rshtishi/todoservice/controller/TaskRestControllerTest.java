package com.github.rshtishi.todoservice.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.rshtishi.todoservice.entity.Task;
import com.github.rshtishi.todoservice.enums.PriorityType;
import com.github.rshtishi.todoservice.enums.StatusType;
import com.github.rshtishi.todoservice.service.TaskService;

import reactor.core.publisher.Flux;

@WebFluxTest(controllers = TaskRestController.class)
class TaskRestControllerTest {

	@Autowired
	WebTestClient webClient;
	@MockBean
	TaskService taskService;
	
	private JacksonTester<List<List<Task>>> json;
	
	@BeforeEach
	void setup() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		JacksonTester.initFields(this,mapper);
	}

	@Test
	void testFindRecentTasks() throws Exception {
		// setup
		List<Task> firstList = Arrays.asList(
				new Task("3", "Meet with friend at Pub", LocalDateTime.of(2020, 10, 16, 1, 0), StatusType.PENDING,
						PriorityType.MEDIUM),
				new Task("2", "Congratulate Krist for his birthday", LocalDateTime.of(2020, 10, 16, 1, 0),
						StatusType.PENDING, PriorityType.MEDIUM));
		List<Task> secondList = Arrays.asList(new Task("1", "Check emails", LocalDateTime.of(2020, 10, 15, 1, 0),
				StatusType.PENDING, PriorityType.MEDIUM));
		List<List<Task>> expectedResult= new ArrayList<>();
		expectedResult.add(firstList);
		expectedResult.add(secondList);
		String expectedResultString = json.write(expectedResult).getJson();
		Flux<List<Task>> taskListFlux = Flux.just(firstList, secondList);
		when(taskService.findAllTasksGroupedByScheduledDesc()).thenReturn(taskListFlux);
		// execute
		EntityExchangeResult result = webClient.get().uri("/tasks/recent").exchange().expectStatus().isOk()
				.expectBody(String.class).returnResult();
		// verify
		String resultString = new String(result.getResponseBodyContent(), StandardCharsets.UTF_8);
		assertEquals(expectedResultString, resultString);

	}

}
