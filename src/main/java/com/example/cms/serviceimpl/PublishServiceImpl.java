package com.example.cms.serviceimpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.entity.BlogPost;
import com.example.cms.entity.Publish;
import com.example.cms.enums.PostType;
import com.example.cms.exceptions.BlogPostNotFoundById;
import com.example.cms.exceptions.UnAuthorizedException;
import com.example.cms.repository.BlogPostRepository;
import com.example.cms.repository.PublishRepository;
import com.example.cms.requestdto.PublishRequest;
import com.example.cms.responsedto.PublishResponse;
import com.example.cms.service.PublishService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PublishServiceImpl implements PublishService {

	private BlogPostRepository blogPostRepo;
	private ResponseStructure<PublishResponse> responseStructure;
	private PublishRepository publishRepository;
	@Override
	public ResponseEntity<ResponseStructure<PublishResponse>> publishBlogPost(PublishRequest publishRequest,
			int postId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return blogPostRepo.findById(postId).map(blogPost->{
			if(!blogPost.getBlog().getUser().getEmail().equals(email)&&!blogPost.getCreatedBy().equals(email))
				throw new UnAuthorizedException("Illegal Access Request");
			if(blogPost.getPublish()==null) {
				blogPost.setPostType(PostType.PUBLISHED);
				BlogPost post = blogPostRepo.save(blogPost);
				Publish publish= mapToPublishEntity(publishRequest, new Publish());
				publish.setBlogPost(post);
				Publish publish2 = publishRepository.save(mapToPublishEntity(publishRequest,publish));
				return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value()).setMessage("").setData(mapToPublishResponse(publish2)));
			}
			
//			else if(blogPost.getPublish()!=null && blogPost.getPostType()==PostType.DRAFT) {
			else {	
				blogPost.setPostType(PostType.PUBLISHED);
				BlogPost post = blogPostRepo.save(blogPost);
				Publish publish = post.getPublish();
				return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value()).setMessage("").setData(mapToPublishResponse(publish)));

			}
//			else {
//				blogPost.setPostType(PostType.DRAFT);
//				BlogPost post = blogPostRepo.save(blogPost);
//				Publish publish = post.getPublish();
//				return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value()).setMessage("").setData(mapToPublishResponse(publish)));
//			}


		}).orElseThrow(()-> new BlogPostNotFoundById("Cannot convert post to published"));
	}
	private PublishResponse mapToPublishResponse(Publish publish) {

		return PublishResponse.builder().publishId(publish.getPublishId()).seoTitle(publish.getSeoTitle())
				.seoDescription(publish.getSeoDescription()).seoTopics(publish.getSeoTopics())
				.blogPost(publish.getBlogPost()).build();
	}
	private Publish mapToPublishEntity(PublishRequest publishRequest, Publish publish) {
		publish.setSeoTitle(publishRequest.getSeoTitle());
		publish.setSeoDescription(publishRequest.getSeoDescription());
		publish.setSeoTopics(publishRequest.getSeoTopics());
		return publish;
	}

}
