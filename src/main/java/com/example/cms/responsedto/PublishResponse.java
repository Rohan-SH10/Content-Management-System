package com.example.cms.responsedto;

import com.example.cms.entity.BlogPost;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublishResponse {

	private int publishId;
	private String seoTitle;
	private String seoDescription;
	private String[] seoTopics;
	//private BlogPost blogPost;
}
