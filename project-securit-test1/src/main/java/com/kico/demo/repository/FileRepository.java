package com.kico.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kico.demo.entity.File;

public interface FileRepository extends JpaRepository<File, Long>{

}
