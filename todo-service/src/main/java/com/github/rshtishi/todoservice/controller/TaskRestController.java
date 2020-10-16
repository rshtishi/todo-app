package com.github.rshtishi.todoservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.rshtishi.todoservice.entity.Task;
import com.github.rshtishi.todoservice.service.TaskService;

import reactor.core.publisher.Flux;

@RequestMapping("/tasks")
@RestController
public class TaskRestController {
	
	@Autowired
	private TaskService taskService;
	
	@GetMapping("/recent")
	public Flux<List<Task>> findRecentTasks(){
		return taskService.findAllTasksGroupedByScheduledDesc();
	}

}
