package com.example.cms.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cms.entity.User;
import com.example.cms.exceptions.UserAlreadyExistByEmailException;
import com.example.cms.exceptions.UserNotFoundByIdException;
import com.example.cms.repository.UserRepository;
import com.example.cms.requestdto.UserRequest;
import com.example.cms.responsedto.UserResponse;
import com.example.cms.service.UserService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
	private ResponseStructure<UserResponse> responseStructure;
	private ResponseStructure<User> respStructure;
	private PasswordEncoder passwordEncoder;
	//private ResponseStructure<List<User>> responseStructureList;

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> saveUser(UserRequest userRequest) {
		if(userRepository.existsByEmail(userRequest.getEmail()))
			throw new UserAlreadyExistByEmailException("failed to register user");
		User user = userRepository.save(mapToUserEntity(userRequest,new User()));

		return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value()).setMessage("User registered Successfully").setData(mapToUserResponse(user)));
	}

	public UserResponse mapToUserResponse(User user) {

		//		UserResponse ur = new UserResponse();
		//		ur.setUserId(user.getUserId());
		//		ur.setEmail(user.getEmail());
		//		ur.setUsername(user.getUsername());
		//		ur.setCreatedAt(user.getCreatedAt());
		//		ur.setLastModifiedAt(user.getLastModifiedAt());
		//		return ur;
		return UserResponse.builder().userId(user.getUserId()).username(user.getUsername())
				.email(user.getEmail()).createdAt(user.getCreatedAt()).lastModifiedAt(user.getLastModifiedAt()).deleted(user.isDeleted()).build();
	}

	public User mapToUserEntity(UserRequest userRequest,User user) {
		user.setUsername(userRequest.getUsername());
		String encode = passwordEncoder.encode(userRequest.getPassword());
		user.setPassword(encode);
		user.setEmail(userRequest.getEmail());

		return user;
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> findUserById(int userId) {
		return userRepository.findById(userId).map(user->ResponseEntity.ok(
				responseStructure.setData(mapToUserResponse(user))
				.setStatusCode(HttpStatus.OK.value())
				.setMessage("User found")
					)
				)
				.orElseThrow(() -> new UserNotFoundByIdException("User Not Found By This Id "+userId));
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUserById(int userId) {
		//		Optional<User> optional = userRepository.findById(userId);
		//		if(optional.isEmpty())
		//			throw new UserNotFoundByIdException("User not found by this id "+userId);
		//		User user = optional.get();
		//		user.setDeleted(true);
		//		User user2 = userRepository.save(user);
		//		return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value()).setMessage("User Deleted Successfully").setData(mapToUserResponse(user2)));
		return userRepository.findById(userId).map(user->{
			User u = updateRegistration(user);
			return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value())
					.setData(mapToUserResponse(u))
					.setMessage("User deleted set to true")
					);
						})
				.orElseThrow(()->new UserNotFoundByIdException("User Not Found By This Id "+userId));
	}
	
	private User updateRegistration(User user) {
		user.setDeleted(true);
		return userRepository.save(user);
	}



}
