package com.example.board.domain.auth.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.board.domain.auth.entity.Role;
import com.example.board.domain.auth.entity.User;

import lombok.RequiredArgsConstructor;

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
		user.updateAuthorities(getRoles(user.getRoles().stream().toList()));
		return user;
	}

	public List<SimpleGrantedAuthority> getRoles(List<Role> list) {
		return list.stream().map(role -> role.getRoleName().name()).map(SimpleGrantedAuthority::new) // String을 SimpleGrantedAuthority로 변환
		.collect(Collectors.toList());
	}

}