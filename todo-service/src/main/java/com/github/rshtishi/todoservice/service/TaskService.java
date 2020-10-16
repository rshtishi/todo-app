package com.github.rshtishi.todoservice.service;

import java.util.List;

import com.github.rshtishi.todoservice.dto.TaskDto;
import com.github.rshtishi.todoservice.entity.Task;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskService {

	public Flux<List<Task>> findAllTasksGroupedByScheduledDesc();

	public Mono<Task> createTask(TaskDto taskDto);

	public Mono<Task> updateTask(String id, TaskDto taskDto);
	
	

}
