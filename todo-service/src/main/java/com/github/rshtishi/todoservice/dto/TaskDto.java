package com.github.rshtishi.todoservice.dto;

import java.time.LocalDateTime;

import com.github.rshtishi.todoservice.enums.PriorityType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
	
	private String description;
	private LocalDateTime scheduled;
	private PriorityType priority;

}
