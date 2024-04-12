package com.example.board.domain.auth.entity;

import com.example.board.domain.auth.model.RoleType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Role {
	
    @Id
    @Column(name = "role_id")
    private String roleId;
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
