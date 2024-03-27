package com.example.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.entity.User;
import com.example.cms.requestdto.UserRequest;
import com.example.cms.responsedto.UserResponse;
import com.example.cms.service.UserService;
import com.example.cms.utility.ErrorStructure;
import com.example.cms.utility.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestController
public class UserController {

	@Autowired
	UserService userService;
	
	
	
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}



	@Operation(description = "Insert User",responses = {
			@ApiResponse(responseCode = "200",description = "Inserted Successfully"),
			@ApiResponse(responseCode = "400",description = "Invalid Inputs",content = {
					@Content(schema = @Schema(implementation = ErrorStructure.class))
			})
	})
	@PostMapping("/user/register")
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(@RequestBody UserRequest userRequest){
		return userService.saveUser(userRequest);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> findUserById(@PathVariable int userId){
		return userService.findUserById(userId);
	}
	@DeleteMapping("/user/{userId}")
	private ResponseEntity<ResponseStructure<UserResponse>> deleteUserById(@PathVariable int userId){
		return userService.deleteUserById(userId);
	}
}
