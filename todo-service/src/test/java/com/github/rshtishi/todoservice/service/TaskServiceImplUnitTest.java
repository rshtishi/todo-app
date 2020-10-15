package com.github.rshtishi.todoservice.service;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.github.rshtishi.todoservice.TaskRepository;
import com.github.rshtishi.todoservice.dto.TaskDto;
import com.github.rshtishi.todoservice.entity.Task;
import com.github.rshtishi.todoservice.enums.PriorityType;
import com.github.rshtishi.todoservice.enums.StatusType;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class TaskServiceImplUnitTest {

	@InjectMocks
	private TaskServiceImpl taskService;
	@Mock
	private TaskRepository taskRepository;
	@Mock
	private ModelMapper modelMapper;

	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testFindAllTasksGroupedByScheduledDesc() {
		// setup
		List<Task> tasks = Arrays.asList(
				new Task("3", "Congratulate Krist for his birthday", LocalDateTime.of(2020, 10, 16, 1, 0),
						StatusType.PENDING, PriorityType.MEDIUM),
				new Task("2", "Check emails", LocalDateTime.of(2020, 10, 15, 1, 0), StatusType.PENDING,
						PriorityType.MEDIUM),
				new Task("1", "Write email to x,y,z", LocalDateTime.of(2020, 10, 15, 6, 0), StatusType.PENDING,
						PriorityType.MEDIUM));
		Flux<Task> taskFlux = Flux.fromIterable(tasks);
		when(taskRepository.findAll(Mockito.any())).thenReturn(taskFlux);
		List<Task> expectedFirst = Arrays.asList(new Task("3", "Congratulate Krist for his birthday",
				LocalDateTime.of(2020, 10, 16, 1, 0), StatusType.PENDING, PriorityType.MEDIUM));
		List<Task> expectedSecond = Arrays.asList(
				new Task("2", "Check emails", LocalDateTime.of(2020, 10, 15, 1, 0), StatusType.PENDING,
						PriorityType.MEDIUM),
				new Task("1", "Write email to x,y,z", LocalDateTime.of(2020, 10, 15, 6, 0), StatusType.PENDING,
						PriorityType.MEDIUM));
		// execute
		Flux<List<Task>> taskListFluxReturned = taskService.findAllTasksGroupedByScheduledDesc();
		// verify
		StepVerifier.create(taskListFluxReturned).expectNext(expectedFirst).expectNext(expectedSecond).expectComplete()
				.verify();

	}

	@Test
	void testCreateTask() {
		// setup
		ModelMapper mapper = new ModelMapper();
		TaskDto taskDto = new TaskDto("Send Email to Customer", LocalDateTime.now(), PriorityType.LOW);
		Task task = mapper.map(taskDto, Task.class);
		task.setId("1");
		when(modelMapper.map(taskDto, Task.class)).thenReturn(task);
		when(taskRepository.save(task)).thenReturn(Mono.just(task));
		// execute
		Mono<Task> taskMono = taskService.createTask(taskDto);
		// verify
		StepVerifier.create(taskMono).expectNextMatches(t -> t.getId().equals(task.getId())).expectComplete().verify();
	}

}
