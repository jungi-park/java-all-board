package com.example.board.auth.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.management.relation.Role;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.board.auth.entity.User;
import com.example.board.auth.model.AuthRole;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserService userService;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		try {
			return userService.findByUserId(userId).map(this::setAuthorities)
					.orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public UserDetails setAuthorities(User user) {
		user.setAuthorities(getRoles(user.getRoles().stream().toList()));
		return user;
	}

	public List<SimpleGrantedAuthority> getRoles(List<AuthRole> list) {
	    return list.stream()
	               .map(authRole -> authRole.name()) // AuthRole을 String으로 변환
	               .map(SimpleGrantedAuthority::new) // String을 SimpleGrantedAuthority로 변환
	               .collect(Collectors.toList());
	}

}