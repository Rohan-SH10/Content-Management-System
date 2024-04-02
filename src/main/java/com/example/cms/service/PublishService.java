package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.requestdto.PublishRequest;
import com.example.cms.responsedto.PublishResponse;
import com.example.cms.utility.ResponseStructure;

public interface PublishService {

	ResponseEntity<ResponseStructure<PublishResponse>> publishBlogPost(PublishRequest publishRequest, int postId);

}
