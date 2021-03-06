package com.kico.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kico.demo.auth.MyUserDetail;
import com.kico.demo.entity.User;
import com.kico.demo.repository.ExRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExService implements UserDetailsService {
	private final ExRepository repository;

	//트랜잭션 자동 수행
	@Transactional(rollbackFor = Exception.class)
	public void joinUser(User user){
		
		// security를 위해서 암호화
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		repository.saveUser(user);
		
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		log.info(email);
		
		//여기서 받은 유저 패스워드와 비교하여 로그인 인증
		User user = repository.findUserByEmail(email);
		
		return new MyUserDetail(user);
	}
}
