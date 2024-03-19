package com.wp.whatsapp.service;

import java.util.List;

import com.wp.whatsapp.exception.UserException;
import com.wp.whatsapp.modal.User;
import com.wp.whatsapp.request.UpdateUserRequest;

public interface UserService {

	public User findUserById(Integer id) throws UserException;
	
	public User findUserProfile(String jwt) throws UserException;
	
	public User updateUser(Integer userId, UpdateUserRequest req) throws UserException;
	
	public List<User> searchUser(String query);
}
