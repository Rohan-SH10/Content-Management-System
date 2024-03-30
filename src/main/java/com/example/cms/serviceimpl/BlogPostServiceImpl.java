package com.example.cms.serviceimpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.cms.entity.BlogPost;
import com.example.cms.enums.PostType;
import com.example.cms.exceptions.BlogNotFoundByIdException;
import com.example.cms.repository.BlogPostRepository;
import com.example.cms.repository.BlogRepository;
import com.example.cms.requestdto.BlogPostRequest;
import com.example.cms.responsedto.BlogPostResponse;
import com.example.cms.service.BlogPostService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BlogPostServiceImpl implements BlogPostService{
	
	
	private BlogPostRepository blogPostRepo;
	private ResponseStructure<BlogPostResponse> responseStructure;
	private BlogRepository blogRepo;



	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> createPost(int blogId, BlogPostRequest blogRequest) {
		return blogRepo.findById(blogId).map(blog->{
			BlogPost blogPost = mapToBlogPostEntity(blogRequest,new BlogPost());
			blogPost.setPostType(PostType.DRAFT);
			blogPost.setBlog(blog);
			BlogPost post = blogPostRepo.save(blogPost);
			return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value()).setMessage("Post created").setData(mapToPostResponse(post)));
			
			
		}).orElseThrow(()->new BlogNotFoundByIdException("Couldn't create Blog Post"));
	}



	private BlogPostResponse mapToPostResponse(BlogPost post) {
		
		return BlogPostResponse.builder().postId(post.getPostId()).title(post.getTitle())
				.subTitle(post.getSubTitle()).postType(post.getPostType())
				.summary(post.getSummary()).seoTitle(post.getSeoTitle())
				.seoTags(post.getSeoTags()).seoDescription(post.getSeoDescription()).createdAt(post.getCreatedAt())
				.createdBy(post.getCreatedBy()).lastModifiedAt(post.getLastModifiedAt()).lastModifiedBy(post.getLastModifiedBy())
				.blog(post.getBlog())
				.build();
	}



	private BlogPost mapToBlogPostEntity(BlogPostRequest blogRequest, BlogPost blog) {
		blog.setTitle(blogRequest.getTitle());
		blog.setSubTitle(blogRequest.getSubTitle());
		blog.setSummary(blogRequest.getSummary());
		blog.setSeoTitle(blogRequest.getSeoTitle());
		blog.setSeoDescription(blogRequest.getSeoDescription());
		blog.setSeoTags(blogRequest.getSeoTags());
		
		return blog;
	}

}
