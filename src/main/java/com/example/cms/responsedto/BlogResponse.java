package com.example.cms.responsedto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogResponse {

	private int blogId;
	private String title;
	private String[] topics;
	private String about;
	
}
