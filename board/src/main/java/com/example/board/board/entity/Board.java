package com.example.board.board.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.example.board.auth.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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

	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp updateTime;

	private Boolean isDelete;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USER_ID")
	private User writer;

	@PrePersist
	private void onCreate() {
		writeTime = Timestamp.valueOf(LocalDateTime.now());
	}

	@PreUpdate
	private void onUpdate() {
		updateTime = Timestamp.valueOf(LocalDateTime.now());
	}
}
