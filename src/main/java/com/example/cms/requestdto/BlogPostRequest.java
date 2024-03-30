package com.example.cms.requestdto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BlogPostRequest {

	@NotNull
	private String title;
	private String subTitle;
	@Size(min = 500, message = "Field must be at least 500 characters long")
	private String summary;
	private String seoTitle;
	private String seoDescription;
	private String[] seoTopics;
}
