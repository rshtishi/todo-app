package com.github.rshtishi.todoservice;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.github.rshtishi.todoservice.entity.Task;
import com.github.rshtishi.todoservice.enums.PriorityType;
import com.github.rshtishi.todoservice.enums.StatusType;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TaskRepositoryTest {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@BeforeEach
	public void setUp() {
		mongoTemplate.dropCollection(Task.class);
		mongoTemplate.insert(new Task("1", "Check emails", LocalDateTime.of(2020, 10, 15, 1, 0), StatusType.PENDING,
				PriorityType.MEDIUM));
		mongoTemplate.insert(new Task("2", "Congratulate Krist for his birthday", LocalDateTime.of(2020, 10, 16, 1, 0),
				StatusType.PENDING, PriorityType.MEDIUM));
	}

	@Test
	@Order(1)
	void testSave() {
		// setup
		Task task = new Task("3", "Meet with friend at Pub", LocalDateTime.now(), StatusType.PENDING,
				PriorityType.MEDIUM);
		// execute
		Mono<Task> taskSaved = taskRepository.save(task);
		// verify
		StepVerifier.create(taskSaved).expectNextMatches(t -> t.getId().equals(task.getId())).verifyComplete();
	}

	@Test
	@Order(2)
	void testFindAll() {
		// execute
		Flux<Task> taskFlux = taskRepository.findAll();
		// verify
		StepVerifier.create(taskFlux).expectNextCount(2).verifyComplete();
	}

	@Test
	@Order(3)
	void testFindAllOrdered() {
		// setup
		List<Task> expectedTask = Arrays.asList(
				new Task("2", "Congratulate Krist for his birthday", LocalDateTime.of(2020, 10, 16, 1, 0),
						StatusType.PENDING, PriorityType.MEDIUM),
				new Task("1", "Check emails", LocalDateTime.of(2020, 10, 15, 1, 0), StatusType.PENDING,
						PriorityType.MEDIUM));
		// execute
		Flux<Task> taskFlux = taskRepository.findAll(Sort.by(Sort.Direction.DESC, "scheduled"));
		// verify
		StepVerifier.create(taskFlux).expectNext(expectedTask.get(0)).expectNext(expectedTask.get(1)).expectComplete()
				.verify();
	}

	@Test
	@Order(4)
	void testDeleteById() {
		// setup
		String id = "1";
		// execute
		Mono<Void> result = taskRepository.deleteById(id);
		// verify
		StepVerifier.create(result).verifyComplete();
	}

	@Test
	@Order(5)
	void testFindById() {
		// setup
		String id = "1";
		Task expectedTask = new Task("1", "Check emails", LocalDateTime.of(2020, 10, 15, 1, 0), StatusType.PENDING,
				PriorityType.MEDIUM);
		// execute
		Mono<Task> monoTask = taskRepository.findById(id);
		// verify
		StepVerifier.create(monoTask).expectNext(expectedTask).verifyComplete();
	}

}
