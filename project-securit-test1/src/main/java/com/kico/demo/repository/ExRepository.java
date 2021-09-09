package com.kico.demo.repository;

import javax.persistence.EntityManager;
import com.kico.demo.entity.User;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ExRepository {
	
	private final EntityManager em;

	// 객체 저장
	public void saveUser(User user){	
		em.persist(user);
	}

	public User findUserByEmail(String email){
		TypedQuery<User> query = em.createQuery("select m from User as m where m.email = ?1", User.class)
			.setParameter(1, email);
		return query.getSingleResult();
	}
}
