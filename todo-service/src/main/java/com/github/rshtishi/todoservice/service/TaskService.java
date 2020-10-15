package com.github.rshtishi.todoservice.service;

import java.util.List;

import com.github.rshtishi.todoservice.entity.Task;

import reactor.core.publisher.Flux;

public interface TaskService {

	public Flux<List<Task>> findAllTasksGroupedByScheduledDesc();

}
