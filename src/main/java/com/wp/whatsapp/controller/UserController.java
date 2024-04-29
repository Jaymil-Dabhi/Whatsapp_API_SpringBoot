package com.wp.whatsapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wp.whatsapp.exception.UserException;
import com.wp.whatsapp.modal.User;
import com.wp.whatsapp.request.UpdateUserRequest;
import com.wp.whatsapp.response.ApiResponse;
import com.wp.whatsapp.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService=userService;
	}

	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String token) throws UserException{
		
		User user=userService.findUserProfile(token);
		
		return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);
	} 
	
	@GetMapping("/{query}")
	public ResponseEntity<List<User>> searchUserHandler(@PathVariable("query") String q){
		List<User> users=userService.searchUser(q);
		
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
	}
	
	@PutMapping("/update/{userId}")
	public ResponseEntity<ApiResponse>updateUserHandler(@PathVariable Integer userId, @RequestBody UpdateUserRequest req, @RequestHeader("Authorization") String token) throws UserException{
		System.out.println("Update user request: " + req);
		User user=userService.findUserProfile(token);
		System.out.println("User: " + user);
		if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
		
		userService.updateUser(user.getId(), req);
		
		ApiResponse res=new ApiResponse("user updated successfully",true);
		
		return new ResponseEntity<ApiResponse>(res,HttpStatus.OK);
	}
}	
