package com.example.cms.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Publish {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int publishId;
	@NotNull
	@NotEmpty
	@NotBlank
	private String seoTitle;
	private String seoDescription;
	private String[] seoTopics;
	
	@OneToOne
	private BlogPost blogPost;
	
	@CreatedDate
	private LocalDateTime createdAt;
}
