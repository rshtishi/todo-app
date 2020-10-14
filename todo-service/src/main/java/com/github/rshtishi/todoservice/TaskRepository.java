package com.github.rshtishi.todoservice;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;

import com.github.rshtishi.todoservice.entity.Task;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TaskRepository extends ReactiveSortingRepository<Task, String> {

	Mono<Task> save(Mono<Task> task);

}
