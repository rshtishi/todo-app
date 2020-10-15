package com.github.rshtishi.todoservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.github.rshtishi.todoservice.TaskRepository;
import com.github.rshtishi.todoservice.entity.Task;

import reactor.core.publisher.Flux;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	public Flux<List<Task>> findAllTasksGroupedByScheduledDesc() {
		Flux<Task> taskFlux = taskRepository.findAll(Sort.by(Sort.Direction.DESC, "scheduled"));
		return taskFlux.groupBy(task -> task.getScheduled().toLocalDate()).flatMapSequential(Flux::collectList);
	}

}
