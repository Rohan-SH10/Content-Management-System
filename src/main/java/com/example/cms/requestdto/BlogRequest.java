package com.example.cms.requestdto;

import java.util.List;

import com.example.cms.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlogRequest {

	private String title;
	private String[] topics;
	private String about;
	private List<User> users;
}
