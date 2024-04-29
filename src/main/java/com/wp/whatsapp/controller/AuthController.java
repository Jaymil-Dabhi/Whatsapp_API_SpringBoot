package com.wp.whatsapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wp.whatsapp.config.TokenProvider;
import com.wp.whatsapp.exception.UserException;
import com.wp.whatsapp.modal.User;
import com.wp.whatsapp.repository.UserRepository;
import com.wp.whatsapp.request.LoginRequest;
import com.wp.whatsapp.response.AuthResponse;
import com.wp.whatsapp.service.CustomUserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private TokenProvider tokenProvider;
	private CustomUserService customUserService;
	
	public AuthController(UserRepository userRepository,PasswordEncoder passwordEncoder,TokenProvider tokenProvider,CustomUserService customUserService) {
		this.userRepository=userRepository;
		this.passwordEncoder=passwordEncoder;
		this.tokenProvider=tokenProvider;
		this.customUserService=customUserService;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException{
		String email=user.getEmail();
		String full_name=user.getFull_name();
		String password=user.getPassword();
		
		User isUser=userRepository.findByEmail(email);
		if(isUser!=null) {
			throw new UserException("Email is used with another account "+email);
		}
		
		User createdUser=new User();
		createdUser.setEmail(email);
		createdUser.setFull_name(full_name);
		createdUser.setPassword(passwordEncoder.encode(password));
		
		userRepository.save(createdUser);
		
		Authentication authentication=new UsernamePasswordAuthenticationToken(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt=tokenProvider.generateToken(authentication);
		
		AuthResponse res=new AuthResponse(jwt, true);
		res.setJwt(jwt);
		return new ResponseEntity<>(res,HttpStatus.CREATED);
	}
	
	@GetMapping("/signin")
	public ResponseEntity<AuthResponse> loginHandler(@RequestBody User user){
		
		String email=user.getEmail();
		String password=user.getPassword();
		
		Authentication authentication=authenticate(email,password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt=tokenProvider.generateToken(authentication);
		
		AuthResponse res=new AuthResponse(jwt, true);
		
		return new ResponseEntity<AuthResponse>(res,HttpStatus.ACCEPTED);
	}
	
	public Authentication authenticate(String Username, String password) {
		UserDetails userDetails=customUserService.loadUserByUsername(Username);
		
		if(userDetails==null) {
			throw new BadCredentialsException("invalid username");
		}
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("invalid password or username");
		}
		
		return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
	}
	
}
