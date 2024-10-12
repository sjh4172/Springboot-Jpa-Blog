package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@Controller 사용자요청 -> 응답 (Html)
@RestController // 사용자요청 -> 응답(Data)
public class HttpControllerTest {
	private static final String Tag = "HttpControllerTest: ";
	
	@GetMapping("/http/lombok")
	public String lombokTest() {
		// builder 패턴 적용 -> 순서 상관없이 값 추가가능 / id 값 자동 증가
		Member m = Member.builder().username("test").password("1234").email("test@gmail.com").build();
		
		System.out.println(Tag + "getter" + m.getId());
		m.setId(5000);
		System.out.println(Tag + "setter" + m.getId());
		return "lombok Test 완료";
	}
	@GetMapping("/http/get")
	public String getTest(Member m) { // 요청시 자동으로 스프링이 Member 에 id=1&username="test"를 넣어줌
		return "get요청:" + m.getId() + "," + m.getUsername();
	}
	@PostMapping("/http/post")
	public String postTest(@RequestBody Member m) { // MessageConverter 스프링부트가 자동으로 Member 에 매핑
		return "post요청"+ m.getId() + "," + m.getUsername() +"," + m.getEmail() + "," + m.getPassword();			  
	}
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put요청" + m.getId() + "," + m.getUsername() +"," + m.getEmail() + "," + m.getPassword();
	}
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete요청";
	}
}
