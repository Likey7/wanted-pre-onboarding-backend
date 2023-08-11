package com.sehun.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sehun.backend.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer>{
}
