package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.requestdto.BlogPostRequest;
import com.example.cms.responsedto.BlogPostResponse;
import com.example.cms.utility.ResponseStructure;

public interface BlogPostService {

	ResponseEntity<ResponseStructure<BlogPostResponse>> createPost(int blogId,BlogPostRequest blogRequest);

	ResponseEntity<ResponseStructure<BlogPostResponse>> updateBlogPost(int blogId, BlogPostRequest blogPostRequest);

	ResponseEntity<ResponseStructure<BlogPostResponse>> deletePost(int blogPostId);

	ResponseEntity<ResponseStructure<BlogPostResponse>> updatePostType(int blogPostId);

	ResponseEntity<ResponseStructure<BlogPostResponse>> findBlogPostById(int postId);

	ResponseEntity<ResponseStructure<BlogPostResponse>> findBlogPostByIdIfPublished(int postId);

}
