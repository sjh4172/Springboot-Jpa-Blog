package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service	// 스프링이 컴포넌트 스캔을 통해 Bean 등록 해줌
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public Integer 회원가입(User user) {
		try {
			userRepository.save(user);
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("UserService: 회원가입(): " + e.getMessage());
		}
		return -1;
	}
	
	
}
