package com.github.rshtishi.todoservice;

import java.time.LocalDateTime;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.rshtishi.todoservice.entity.Task;
import com.github.rshtishi.todoservice.enums.PriorityType;
import com.github.rshtishi.todoservice.enums.StatusType;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TaskRepositoryTest {

	private TaskRepository taskRepository;

	@Test
	@Order(1)
	void testSave() {
		// setup
		Task task = new Task("1", "Meet with friend at Pub", LocalDateTime.now(), StatusType.PENDING,
				PriorityType.MEDIUM);
		// execute
		Mono<Task> taskSaved = taskRepository.save(Mono.just(task));
		// verify
		StepVerifier.create(taskSaved).expectNextMatches(t -> t.getId().equals(task.getId()));
	}

}
