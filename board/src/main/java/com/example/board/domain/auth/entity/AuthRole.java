package com.example.board.domain.auth.entity;

import com.example.board.domain.auth.model.RoleType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class AuthRole {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private String roleId;
	@Enumerated(EnumType.STRING)
	@Column(name = "role_name")
	private RoleType roleName;
	@ManyToOne
	private User user;

	@Builder
	public AuthRole(RoleType roleName) {
		this.roleName = roleName;
	}

}
