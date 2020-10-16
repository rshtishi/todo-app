package com.github.rshtishi.todoservice.service;

import java.util.List;

import org.modelmapper.Condition;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.github.rshtishi.todoservice.TaskRepository;
import com.github.rshtishi.todoservice.dto.TaskDto;
import com.github.rshtishi.todoservice.entity.Task;
import com.github.rshtishi.todoservice.enums.StatusType;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Flux<List<Task>> findAllTasksGroupedByScheduledDesc() {
		Flux<Task> taskFlux = taskRepository.findAll(Sort.by(Sort.Direction.DESC, "scheduled"));
		return taskFlux.groupBy(task -> task.getScheduled().toLocalDate()).flatMapSequential(Flux::collectList);
	}

	@Override
	public Mono<Task> createTask(TaskDto taskDto) {
		Task task = modelMapper.map(taskDto, Task.class);
		task.setStatus(StatusType.PENDING);
		return taskRepository.save(task);
	}

	@Override
	public Mono<Task> updateTask(String id, TaskDto taskDto) {
		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		Task task = modelMapper.map(taskDto, Task.class);
		Mono<Task> updatedTaskMono = taskRepository.save(task).zipWith(taskRepository.findById(id)).map(tuple -> {
			modelMapper.map(tuple.getT1(), tuple.getT2());
			return tuple.getT2();
		});
		;
		return updatedTaskMono;
	}

	@Override
	public Mono<Void> deleteTaskById(String id) {
		 return taskRepository.deleteById(id);
	}
	

}
