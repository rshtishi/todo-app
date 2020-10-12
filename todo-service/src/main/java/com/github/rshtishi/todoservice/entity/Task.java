package com.github.rshtishi.todoservice.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.github.rshtishi.todoservice.enums.PriorityType;
import com.github.rshtishi.todoservice.enums.StatusType;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Document
@Data
@RequiredArgsConstructor
public class Task {
	
	@Id
	private String id;
	private String description;
	private LocalDateTime scheduled;
	private StatusType status;
	private PriorityType priority;

}
