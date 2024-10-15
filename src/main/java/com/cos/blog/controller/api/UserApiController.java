package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/api/user")
	public ResponseDto<Integer> save(@RequestBody User user) {
		// 실제로 DB에 insert 하고 return 해주면 됨
		System.out.println("save 호출");
		user.setRole(RoleType.USER);		// 회원가입시 Role 은 회원이 선택 X, 일반적인 경우 USER로 넣어줌
		int result = userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 	// 회원가입 결과 Return
	}
	
	@PostMapping("/api/user/login")
	public ResponseDto<Integer> login(@RequestBody User user, HttpSession session) {
		System.out.println("login 호출");
		User principal = userService.로그인(user); 	// principal = 접근 주체
		if(principal != null) {
			session.setAttribute("principal", principal);	// session 값을 넘겨줌	- session 값을 기준으로 로그인 성공 여부 판단
		}
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 	// 로그인 결과 Return
		
	}
}
