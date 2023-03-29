package com.leeacademy.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.leeacademy.board.entity.Board;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long>, JpaSpecificationExecutor<Board> {
//목록 최순순(번호 내림차순)
}
