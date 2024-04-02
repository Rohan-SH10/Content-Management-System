package com.example.cms.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PublishRequest {

	private String seoTitle;
	private String seoDescription;
	private String[] seoTopics;
}
