package com.example.board.board.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.example.board.auth.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
	private String content;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	private Timestamp writeTime;
	
	private Boolean isDelete;
	
	private User writer;
	
	
	@PrePersist
	private void onCreate() {
		writeTime = Timestamp.valueOf(LocalDateTime.now());
	}
}
