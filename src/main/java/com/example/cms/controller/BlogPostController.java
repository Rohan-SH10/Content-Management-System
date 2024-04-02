package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.requestdto.BlogPostRequest;
import com.example.cms.responsedto.BlogPostResponse;
import com.example.cms.service.BlogPostService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class BlogPostController {

	private BlogPostService blogPostService;
	
	@PostMapping("/blogs/{blogId}/blog-posts")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> createPost(@PathVariable int blogId ,@RequestBody BlogPostRequest blogRequest){
		return blogPostService.createPost(blogId,blogRequest);
	}
	
	@PutMapping("/blogs/{blogId}")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updatePost(@PathVariable int blogId,@RequestBody BlogPostRequest blogPostRequest){
		return blogPostService.updateBlogPost(blogId,blogPostRequest);
	}
	
	@DeleteMapping("/blog-posts/{blogPostId}")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> deletePost(@PathVariable int blogPostId){
		return blogPostService.deletePost(blogPostId);
	}
	
	@PutMapping("/blog-posts/{blogPostId}")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updatePostType(@PathVariable int blogPostId){
		return blogPostService.updatePostType(blogPostId);
	}
}
