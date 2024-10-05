package com.cos.blog.model;

import java.security.Timestamp;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity	// user 클래스가 Mysql에 테이블이 자동으로 생성됨
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	@Id	// Primary Key
	@GeneratedValue(strategy = GenerationType.IDENTITY)	//프로젝트에서 연결된 DB 넘버링 전략을 따라감
	private int id;	// 시퀀스, Auto_increment
	
	@Column(nullable = false, length = 30)
	private String username;

	@Column(nullable = false, length = 100)
	private String password;

	@Column(nullable = false, length = 50)
	private String email;
	
	@ColumnDefault("'user'")
	private String role; // Enum을 쓰는게 좋다
	
	@CreationTimestamp	// 시간 자동 입력
	private Timestamp createDate;
	
}
