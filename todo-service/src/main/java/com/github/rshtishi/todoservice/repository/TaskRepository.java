package com.github.rshtishi.todoservice.repository;

import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;

import com.github.rshtishi.todoservice.entity.Task;

import reactor.core.publisher.Mono;

@Repository
public interface TaskRepository extends ReactiveSortingRepository<Task, String> {
	
	Mono<Task> findById(String id);

}
