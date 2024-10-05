package com.cos.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Getter
//@Setter
@Data // Getter + Setter
@AllArgsConstructor // 전체 생성자
@NoArgsConstructor // 빈 생성자
public class Member {
	private int id;
	private String username;
	private String password;
	private String email;
	
	@Builder	// id 값 자동 증가
	public Member(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	
}
