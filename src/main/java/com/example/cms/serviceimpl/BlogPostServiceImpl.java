package com.example.cms.serviceimpl;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.cms.entity.Blog;
import com.example.cms.entity.BlogPost;
import com.example.cms.entity.ContributionPanel;
import com.example.cms.enums.PostType;
import com.example.cms.exceptions.BlogNotFoundByIdException;
import com.example.cms.exceptions.BlogPostAlreadyInDraftTypeException;
import com.example.cms.exceptions.BlogPostNotFoundById;
import com.example.cms.exceptions.UnAuthorizedException;
import com.example.cms.exceptions.UserNotFoundByIdException;
import com.example.cms.repository.BlogPostRepository;
import com.example.cms.repository.BlogRepository;
import com.example.cms.repository.ContributionPanelRepository;
import com.example.cms.repository.UserRepository;
import com.example.cms.requestdto.BlogPostRequest;
import com.example.cms.responsedto.BlogPostResponse;
import com.example.cms.service.BlogPostService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BlogPostServiceImpl implements BlogPostService{


	private BlogPostRepository blogPostRepo;
	private PublishServiceImpl publishServiceImpl;
	private ResponseStructure<BlogPostResponse> responseStructure;
	private BlogRepository blogRepo;
	private UserRepository userRepo;
	private ContributionPanelRepository panelRepo;



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
				.summary(post.getSummary()).createdAt(post.getCreatedAt())
				.createdBy(post.getCreatedBy()).lastModifiedAt(post.getLastModifiedAt()).lastModifiedBy(post.getLastModifiedBy())
				.publishResponse(publishServiceImpl.mapToPublishResponse(post.getPublish()))
				.build();
	}



	private BlogPost mapToBlogPostEntity(BlogPostRequest blogRequest, BlogPost blog) {
		blog.setTitle(blogRequest.getTitle());
		blog.setSubTitle(blogRequest.getSubTitle());
		blog.setSummary(blogRequest.getSummary());

		return blog;
	}



	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updateBlogPost(int blogId,
			BlogPostRequest blogPostRequest) {
		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		return blogPostRepo.findById(blogId).map(blogPost->{
			Blog blog = blogPost.getBlog();
			return userRepo.findByEmail(email).map(user->{
				if(!(blog.getUser().getEmail().equals(email)||panelRepo.existsByBlogAndContributors(blog,user)))
					throw new UnAuthorizedException("Cannot update post");
				BlogPost blogPostEntity = mapToBlogPostEntity(blogPostRequest, blogPost);
				BlogPost post = blogPostRepo.save(blogPostEntity);
				return ResponseEntity.ok(responseStructure.setData(mapToPostResponse(post)).setMessage("Blog Post Updated")
						.setStatusCode(HttpStatus.OK.value()));
			}).orElseThrow(()->new UsernameNotFoundException("Cannot update The blog post"));

		}).orElseThrow(()-> new BlogPostNotFoundById("cannot update the blog post"));
		//		return blogPostRepo.findById(blogId).map(blogPost->{
		//			BlogPost blogPostEntity = mapToBlogPostEntity(blogPostRequest, blogPost);
		//			BlogPost post = blogPostRepo.save(blogPostEntity);
		//			return ResponseEntity.ok(responseStructure.setData(mapToPostResponse(post)).setMessage("Blog Post Updated").setStatusCode(HttpStatus.OK.value()));
		//		}).orElseThrow(()-> new BlogPostNotFoundById("Cannot update the BlogPost"));
	}



	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> deletePost(int blogPostId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		return blogPostRepo.findById(blogPostId).map(blogPost->{
			if(!blogPost.getBlog().getUser().getEmail().equals(email)&&
					!blogPost.getCreatedBy().equals(email))
				throw new UnAuthorizedException("Cannot delete");
			BlogPostResponse response = mapToPostResponse(blogPost);
			blogPostRepo.delete(blogPost);
			return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value()).setMessage("Deleted").setData(response));
		}).orElseThrow(()->new BlogPostNotFoundById("Not deleted"));
	}



	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updatePostType(int blogPostId) {
		String email=SecurityContextHolder.getContext().getAuthentication().getName();

		return blogPostRepo.findById(blogPostId).map(blogPost->{
			if(!blogPost.getBlog().getUser().getEmail().equals(email)&&
					!blogPost.getCreatedBy().equals(email))
				throw new UnAuthorizedException("Cannot unpublish");
			if(blogPost.getPostType()==PostType.DRAFT)
				throw new BlogPostAlreadyInDraftTypeException("Cannot Unpublish");
			blogPost.setPostType(PostType.DRAFT);
			blogPostRepo.save(blogPost);
			return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value()).setMessage("Deleted").setData(mapToPostResponse(blogPost)));
		}).orElseThrow(()->new BlogPostNotFoundById("Cannot Unpublish"));
	}



	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> findBlogPostById(int postId) {
		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		
		return userRepo.findByEmail(email).map(user->{
			
			return blogPostRepo.findById(postId).map(blogPost->{
				if(!blogPost.getBlog().getUser().getEmail().equals(email)&&
						!panelRepo.existsByBlogAndContributors(blogPost.getBlog(),user))
					throw new UnAuthorizedException("Cannot fetch the blog post");
				//return ResponseEntity.ok(responseStructure.setData(mapToPostResponse(blogPost)).setStatusCode(HttpStatus.OK.value()).setMessage("Fetched the blog post"));
				return ResponseEntity.status(HttpStatus.FOUND.value())
						.body(responseStructure
								.setData(mapToPostResponse(blogPost))
								.setMessage("Blog is ready to be read")
								.setStatusCode(HttpStatus.FOUND.value()));
			}).orElseThrow(()->new BlogPostNotFoundById("cannot fetch the blog post"));
		}).orElseThrow(()-> new UserNotFoundByIdException("cannot fetch the post"));
	}



	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> findBlogPostByIdIfPublished(int postId) {
		
		return blogPostRepo.findByPostIdAndPostType(postId, PostType.PUBLISHED).map(blogPost->
			
			 //ResponseEntity.ok(responseStructure.setData(mapToPostResponse(blogPost)).setMessage("Blog is ready to be read").setStatusCode(HttpStatus.OK.value()))
			ResponseEntity.status(HttpStatus.FOUND.value())
			.body(responseStructure
					.setData(mapToPostResponse(blogPost))
					.setMessage("Blog is ready to be read")
					.setStatusCode(HttpStatus.FOUND.value()))
		).orElseThrow(()-> new BlogPostAlreadyInDraftTypeException("post is not published"));
	}

	


}
