package com.example.cms.serviceimpl;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.cms.entity.Blog;
import com.example.cms.entity.User;
import com.example.cms.exceptions.TitleAlreadyExistsException;
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

        if (blogRepo.existsByTitle(blogRequest.getTitle())) {
            throw new TitleAlreadyExistsException("Title already exists");
        }
        
        Blog blog1 = blogRepo.save(mapToBlogEntity(blogRequest, new Blog(),userId));
        
        return ResponseEntity.ok(responseStructure.setData(mapToBlogResponse(blog1)).setMessage("Inserted").setStatusCode(HttpStatus.OK.value()));

       
    }

    private Blog mapToBlogEntity( BlogRequest blogRequest, Blog blog,int userId) {

        blog.setTitle(blogRequest.getTitle());
        blog.setAbout(blogRequest.getAbout());
        blog.setTopics(blogRequest.getTopics());
        User u = userRepo.findById(userId).orElseThrow(()-> new UserNotFoundByIdException("User not found by this id " + userId));
        blog.setUsers(Arrays.asList(u));
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

	
}
