package com.cos.blog.model;

import java.security.Timestamp;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob // 대용량 데이터
	private String content;
	
	@ColumnDefault("0")
	private int count;
	
	@ManyToOne(fetch = FetchType.EAGER)	// Many = Board, User = One
	@JoinColumn(name="userId")	// Column 명이 userId로 생성 됨
	private User user;	// DB 는 오브젝트를 저장할 수 없음. FK
	
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER)	// mappedBy 연관관계의 주인이 아니다 (FK 아님, DB 컬럼 생성 X)
	private List<Reply> reply;
	
	@CreationTimestamp
	private Timestamp createDate; 
}
