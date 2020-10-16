package com.github.rshtishi.todoservice.controller;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

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

	@Test
	void testFindRecentTasks() {
		// setup
		List<Task> firstList = Arrays.asList(
				new Task("3", "Meet with friend at Pub", LocalDateTime.of(2020, 10, 16, 1, 0), StatusType.PENDING,
						PriorityType.MEDIUM),
				new Task("2", "Congratulate Krist for his birthday", LocalDateTime.of(2020, 10, 16, 1, 0),
						StatusType.PENDING, PriorityType.MEDIUM));
		List<Task> secondList = Arrays.asList(new Task("1", "Check emails", LocalDateTime.of(2020, 10, 15, 1, 0),
				StatusType.PENDING, PriorityType.MEDIUM));
		Flux<List<Task>> taskListFlux = Flux.just(firstList, secondList);
		when(taskService.findAllTasksGroupedByScheduledDesc()).thenReturn(taskListFlux);
		// execute
		// verify
		taskListFlux.subscribe(System.out::println);
	}

}
