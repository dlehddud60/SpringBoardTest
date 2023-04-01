package com.leeacademy.board.repository;

import com.leeacademy.board.entity.Board;
import com.leeacademy.board.entity.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


public interface BoardFileRepository extends JpaRepository<BoardFile,Long> {
    List<BoardFile> findAllByBoard(Board board);

//    @Modifying
//    @javax.transaction.Transactional
//    @Query("select bf from BoardFile bf where bf.board is null and bf.uploadTime < :time")
//    void autoClear(@Param("time")Date time);

    @Query("select bf from BoardFile bf where bf.board is null and bf.uploadTime < :time")
    List<BoardFile> getOldData(@Param("time") Date time);

}
