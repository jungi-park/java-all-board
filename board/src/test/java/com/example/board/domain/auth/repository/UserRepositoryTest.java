package com.example.board.domain.auth.repository;

import java.util.Collection;
import java.util.Collections;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.example.board.domain.auth.entity.Role;
import com.example.board.domain.auth.entity.User;
import com.example.board.domain.auth.model.RoleType;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@MockBean
	BCryptPasswordEncoder encoder;

	@Test
	@DisplayName("repository 회원가입 테스트")
	void saveMember() {
		// given
		User user = User.builder().name("박준기").userId("qmqqqm").password(encoder.encode("123456!")).build();
		Collection<Role> userRole = Collections.singleton(Role.builder().roleName(RoleType.USER).user(user).build());
		user.updateRole(userRole);
		// when
		User savedMember = userRepository.save(user);
		// then
		Assertions.assertThat(user.getName()).isEqualTo(savedMember.getName());
		Assertions.assertThat(savedMember.getId()).isNotNull();
		Assertions.assertThat(encoder.matches("123456!", savedMember.getPassword()));
		Assertions.assertThat(user.getUserId()).isEqualTo(savedMember.getUserId());
	}

}
