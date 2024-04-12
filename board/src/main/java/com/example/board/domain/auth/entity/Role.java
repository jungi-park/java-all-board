package com.example.board.domain.auth.entity;

import com.example.board.domain.auth.model.RoleType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Long roleId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "role_name")
	private RoleType roleName;
	@ManyToOne
	private User user;

	@Builder
	public Role(RoleType roleName) {
		this.roleName = roleName;
	}

}
