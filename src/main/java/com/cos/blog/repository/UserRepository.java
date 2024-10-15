package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.User;

// DAO
// 자동으로 Bean 으로 등록됨 / @Repository 생략가능
public interface UserRepository extends JpaRepository<User, Integer>{
	
	// JPA Naming 쿼리
	// Select * From user Where username=?1 AND password=?2;
	User findByUsernameAndPassword(String username, String password);
	
	/*
	// 위의 finByUsernameAndPassword 와 동일하게 동작함
	@Query(value="SELECT * FROM user WHERE username=?1 AND password=?2", nativeQuery = true)
	User login(String username, String password);
	*/
}
