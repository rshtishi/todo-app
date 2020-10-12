package com.github.rshtishi.todoservice;

import java.time.LocalDateTime;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.github.rshtishi.todoservice.entity.Task;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskRepository extends ReactiveCrudRepository<Task, String> {

	Flux<Task> findAllOrderByScheduledDesc();

	Flux<Task> findByScheduledOrderByScheduledDesc(Mono<LocalDateTime> scheduled);

	Mono<Task> save(Mono<Task> task);

}
