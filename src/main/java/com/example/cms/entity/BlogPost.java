package com.example.cms.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.cms.enums.PostType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EntityListeners(value = AuditingEntityListener.class)
public class BlogPost {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int postId;
	@NotNull
	private String title;
	private String subTitle;
	@Enumerated(EnumType.STRING)
	private PostType postType;
	@Column(length = 1000)
	@Size(min = 500)
	private String summary;
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;
	@Column(updatable = false)
	private String createdBy;
	
	@LastModifiedDate
	private LocalDateTime lastModifiedAt;
	private String lastModifiedBy;
	
	@ManyToOne
	private Blog blog;
	
	@JsonIgnore
	@OneToOne(mappedBy = "blogPost")
	private Publish publish;
}
