package com.wp.whatsapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.wp.whatsapp.config.TokenProvider;
import com.wp.whatsapp.exception.UserException;
import com.wp.whatsapp.modal.User;
import com.wp.whatsapp.repository.UserRepository;
import com.wp.whatsapp.request.UpdateUserRequest;

@Service
public class UserServiceImplementation implements UserService {
	
	private UserRepository userRepository;
	private TokenProvider tokenProvider;
	
	public UserServiceImplementation(UserRepository userRepository, TokenProvider tokenProvider) {
		this.userRepository=userRepository;
		this.tokenProvider=tokenProvider;
	}

	@Override
	public User findUserById(Integer id) throws UserException {
		Optional<User> opt=userRepository.findById(id);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new UserException("User not found with id "+id);
	}

	@Override
	public User findUserProfile(String jwt) throws UserException {
		String email=tokenProvider.getmailFromToken(jwt);
		
		if(email!=null) {
			throw new BadCredentialsException("received invalid token---");
		}
		User user=userRepository.findByEmail(email);
		
		if(user==null) {
			throw new UserException("user not found with email "+email);
		}
		
		return user;
	}

	@Override
	public User updateUser(Integer userId, UpdateUserRequest req) throws UserException {
		User user=findUserById(userId);
		
		if(req.getFull_name()!=null) {
			user.setFull_name(req.getFull_name());
		}
		if(req.getProfile__picture()!=null) {
			user.setProfile_picture(req.getProfile__picture());
		}
		return userRepository.save(user);
	}

	@Override
	public List<User> searchUser(String query) {
		List<User> users=userRepository.searchUser(query);
		return users;
	}

}
