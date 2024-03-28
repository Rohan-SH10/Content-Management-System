package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.requestdto.BlogRequest;
import com.example.cms.responsedto.BlogResponse;
import com.example.cms.service.BlogService;
import com.example.cms.utility.ResponseStructure;


import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class BlogController {

	private BlogService blogService;
	
	
	@PostMapping("/user/{userId}/blogs")
	public ResponseEntity<ResponseStructure<BlogResponse>> createBlogs(@RequestBody BlogRequest blog,@PathVariable int userId){
		return blogService.createBlogs(blog,userId);
	}
	
	@GetMapping("/title/{title}/blogs")
	public ResponseEntity<Boolean> isBlogPresent(@PathVariable String title) {
		return blogService.isBlogPresent(title);
	}
	
	@GetMapping("/blogs/{blogId}")
	public ResponseEntity<ResponseStructure<BlogResponse>> findBlogById(@PathVariable int blogId){
		return blogService.findBlogById(blogId);
	}
	
}
