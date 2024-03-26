package com.example.cms.responsedto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

	private int userId;
	private String username;
	private String email;
	private LocalDateTime createdAt;
	private LocalDateTime lastModifiedAt;
}
