package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;


@Service	// 스프링이 컴포넌트 스캔을 통해 Bean 등록 해줌
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public void 회원가입(User user) {
		String rawPassword = user.getPassword();
		String encPassword = encoder.encode(rawPassword);	// 비밀번호 해시화
		
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);	// DB에 Insert 
	}
	
	@Transactional
	public void 회원수정(User user) {
		// 수정시에는 영속성 컨텍스트에 User 오브젝트를 영속화 시키고, 영속화된 User 오브젝트를 수정
		// Select 를 해서 User 오브젝트를 DB로 부터 가져옴 => User 영속화
		// 영속화된 오브젝트를 변경하면 자동으로 update 문을 DB에 날려줌
		User persistance = userRepository.findById(user.getId()).
				orElseThrow(()->{
					return new IllegalArgumentException("회원 찾기 실패");
				});
		String rawPassword = user.getPassword();
		String encPassword = encoder.encode(rawPassword);
		persistance.setPassword(encPassword);
		persistance.setEmail(user.getEmail());
		// 회원수정 함수 종료시 = 서비스 종료 = 트랜잭션 종료 = commit 자동으로 됨 (더티체킹)
	}
	
//	@Transactional(readOnly = true)	// select 할때 트랜잭션 시작, 서비스 종료시 트랜잭션 종료 (정합성)
//	public User 로그인(User user) {
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//	}
}
