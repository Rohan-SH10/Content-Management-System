package com.example.cms.serviceimpl;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.entity.BlogPost;
import com.example.cms.entity.Publish;
import com.example.cms.entity.Schedule;
import com.example.cms.enums.PostType;
import com.example.cms.exceptions.BlogPostNotFoundById;
import com.example.cms.exceptions.TimeIsInPastException;
import com.example.cms.exceptions.UnAuthorizedException;
import com.example.cms.repository.BlogPostRepository;
import com.example.cms.repository.PublishRepository;
import com.example.cms.repository.ScheduleRepository;
import com.example.cms.requestdto.PublishRequest;
import com.example.cms.requestdto.ScheduleRequest;
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
	private ScheduleRepository scheduleRepository;
	@Override
	public ResponseEntity<ResponseStructure<PublishResponse>> publishBlogPost(PublishRequest publishRequest,
			int postId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return blogPostRepo.findById(postId).map(blogPost->{
			if(!blogPost.getBlog().getUser().getEmail().equals(email)&&!blogPost.getCreatedBy().equals(email))
				throw new UnAuthorizedException("Illegal Access Request");
			Publish publish = null;
			if(blogPost.getPublish()!=null)
				publish=mapToPublishEntity(publishRequest, blogPost.getPublish());
			else
				publish = mapToPublishEntity(publishRequest, new Publish());
			if(publishRequest.getSchedule()!=null) {
				if(!publishRequest.getSchedule().getDateTime().isAfter(LocalDateTime.now()))
					throw new TimeIsInPastException("Cannot schedule");
				if(publish.getSchedule()==null) {
				Schedule schedule = mapToScheduleEntity(publishRequest.getSchedule(), new Schedule());
				scheduleRepository.save(schedule);
				publish.setSchedule(schedule);
				blogPost.setPostType(PostType.SCHEDULED);}
				else {
					Schedule schedule = mapToScheduleEntity(publishRequest.getSchedule(), publish.getSchedule());
					scheduleRepository.save(schedule);
					blogPost.setPostType(PostType.SCHEDULED);
				}
			}
			else
			{
				blogPost.setPostType(PostType.PUBLISHED);
			}
			publish.setBlogPost(blogPost);
			publishRepository.save(publish);
			return ResponseEntity.status(HttpStatus.CREATED).body(responseStructure.setMessage("Publish method executed").setStatusCode(HttpStatus.CREATED.value()).setData(mapToPublishResponse(publish)));

		}).orElseThrow(()-> new BlogPostNotFoundById("Cannot convert post to published"));
	}
	public Schedule mapToScheduleEntity(ScheduleRequest scheduleRequest, Schedule schedule) {
		schedule.setDateTime(scheduleRequest.getDateTime());
		return schedule;
	}
	public PublishResponse mapToPublishResponse(Publish publish) {
		if(publish==null)return PublishResponse.builder().build();
		return PublishResponse.builder().publishId(publish.getPublishId()).seoTitle(publish.getSeoTitle())
				.seoDescription(publish.getSeoDescription()).seoTopics(publish.getSeoTopics())
				.schedule(publish.getSchedule()).build();	}


	private Publish mapToPublishEntity(PublishRequest publishRequest, Publish publish) {
		publish.setSeoTitle(publishRequest.getSeoTitle());
		publish.setSeoDescription(publishRequest.getSeoDescription());
		publish.setSeoTopics(publishRequest.getSeoTopics());
		return publish;
	}

}
