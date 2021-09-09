package com.kico.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.kico.demo.entity.Board;


public interface BoardRepository extends JpaRepository<Board, Long> {
		
	
}
