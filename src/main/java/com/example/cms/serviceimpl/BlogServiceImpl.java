package com.example.cms.serviceimpl;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.cms.entity.Blog;
import com.example.cms.entity.User;
import com.example.cms.exceptions.BlogNotFoundByIdException;
import com.example.cms.exceptions.TitleAlreadyExistsException;
import com.example.cms.exceptions.TopicsNotSpecifiedException;
import com.example.cms.exceptions.UserNotFoundByIdException;
import com.example.cms.repository.BlogRepository;
import com.example.cms.repository.UserRepository;
import com.example.cms.requestdto.BlogRequest;
import com.example.cms.responsedto.BlogResponse;
import com.example.cms.service.BlogService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepo;
    private final UserRepository userRepo;
    private final ResponseStructure<BlogResponse> responseStructure;
    
    @Override
    public ResponseEntity<ResponseStructure<BlogResponse>> createBlogs( BlogRequest blogRequest,int userId) {

        return userRepo.findById(userId).map(user->{
        	if(blogRepo.existsByTitle(blogRequest.getTitle()))
        		throw new TitleAlreadyExistsException("failed to create the blog");
        	if(blogRequest.getTopics().length<1)
        		throw new TopicsNotSpecifiedException("failed to create blog");
        	Blog blog=mapToBlogEntity(blogRequest, new Blog());
        	blog.setUsers(Arrays.asList(user));
        	blogRepo.save(blog);
        	return ResponseEntity.ok(responseStructure.setData(mapToBlogResponse(blog)).setMessage("Blog created").setStatusCode(HttpStatus.OK.value()));
        			}).orElseThrow(()-> new UserNotFoundByIdException("failed to create blog"));
       
    }

    private Blog mapToBlogEntity( BlogRequest blogRequest, Blog blog) {

        blog.setTitle(blogRequest.getTitle());
        blog.setAbout(blogRequest.getAbout());
        blog.setTopics(blogRequest.getTopics());
        
        return blog;
    }

    private BlogResponse mapToBlogResponse(Blog blog) {
        return BlogResponse.builder()
                .blogId(blog.getBlogId())
                .title(blog.getTitle())
                .topics(blog.getTopics())
                .about(blog.getAbout())
                .build();
    }

	@Override
	public ResponseEntity<Boolean> isBlogPresent(String title) {
		boolean b = blogRepo.existsByTitle(title);
			return ResponseEntity.ok(b);
				
			
		
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> findBlogById(int blogId) {
		
		return blogRepo.findById(blogId).map(blog->ResponseEntity.ok(responseStructure.setData(mapToBlogResponse(blog))
				.setMessage("User Found successfully")
				.setStatusCode(HttpStatus.OK.value()))
					
				)
				.orElseThrow(()-> new BlogNotFoundByIdException("Failed to fetch blog by id"));
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> updateBlogById(int blogId, BlogRequest blogRequest) {
		
		return blogRepo.findById(blogId).map(exBlog->{
			if(blogRepo.existsByTitle(blogRequest.getTitle()))
        		throw new TitleAlreadyExistsException("failed to update the blog");
        	if(blogRequest.getTopics().length<1)
        		throw new TopicsNotSpecifiedException("failed to update blog");
        	Blog blog = mapToBlogEntity(blogRequest, new Blog());
        	blog.setBlogId(exBlog.getBlogId());
        	blogRepo.save(blog);
        	return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value())
        			.setMessage("Updated Successfully")
        			.setData(mapToBlogResponse(blog)));
        	
		}).orElseThrow(()-> new BlogNotFoundByIdException("Failed to update blog"));
	}

	
}
