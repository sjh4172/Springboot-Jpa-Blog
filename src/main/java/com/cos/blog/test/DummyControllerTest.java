package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import org.hibernate.query.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

import jakarta.transaction.Transactional;

@RestController
public class DummyControllerTest {
	@Autowired	// 의존성 주입 (DI)
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
	    if (!userRepository.existsById(id)) {	// 해당 id 를 가진 user 가 존재하는지 확인 
	        throw new IllegalArgumentException("해당 id는 존재하지 않습니다. id: " + id);
	    }
	    userRepository.deleteById(id);
	    return "삭제 되었습니다. id: " + id;
	}
	@Transactional	// 함수 종료시 자동 Commit
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		System.out.println("id: " + id);
		System.out.println("password: " + requestUser.getPassword());
		System.out.println("email: " + requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()-> {	// id 로 user 를 찾아옴
			return new IllegalArgumentException("수정에 실패 하였습니다.");
		});
		
		user.setPassword(requestUser.getPassword());	// 새로 받아온 password, email 을 update
		user.setEmail(requestUser.getEmail());
		
		//userRepository.save(requestUser);		// @Transactional 으로 인해 필요없어짐
		// @Transactional 로 인해 자동 Commit 됨 
		return user;
	}
	
	@GetMapping("/dummy/user")	// 페이지당 2건의 정보를 받아옴
	public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
		Page<User> pagingUsers = userRepository.findAll(pageable);
		List<User> users = pagingUsers.getContent();
		return users;
	}
	
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	@GetMapping("/dummy/user/{id}") // {id} 주소로 파라레터를 전달받을 수 있음
	public User detail(@PathVariable int id) {
		// user/4 를 찾을때 id가 4인 user 가 없으면 null 이 반환될 수 있음 -> 문제발생
		// 문제를 해결하기 위해 Optional 로 User 객체를 가져올테니 null 인지 확인하고 사용해! 
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 없습니다. id: " + id);
			}
		});
		
		return user;
	}
	@PostMapping("/dummy/join")
	public String join(User user) {
		System.out.println("id: " + user.getId() );
		System.out.println("username: " + user.getUsername());
		System.out.println("password: " + user.getPassword());
		System.out.println("email: " + user.getEmail());
		System.out.println("role: " + user.getRole());
		System.out.println("createDate: " + user.getCreateDate());
		
		user.setRole(RoleType.USER);
		
		userRepository.save(user);
		return "회원가입 완료";
	}
}
