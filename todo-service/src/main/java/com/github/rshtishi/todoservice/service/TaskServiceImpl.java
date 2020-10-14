package com.github.rshtishi.todoservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.rshtishi.todoservice.TaskRepository;
import com.github.rshtishi.todoservice.entity.Task;

import reactor.core.publisher.Flux;

@Service
public class TaskServiceImpl implements TaskService{

	@Autowired
	private TaskRepository taskRepository;

	public Flux<Task> findAllTasksOrderByScheduledDesc() {
		return null;
	}

}
