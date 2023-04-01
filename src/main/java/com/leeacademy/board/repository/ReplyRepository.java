package com.leeacademy.board.repository;


import com.leeacademy.board.entity.Board;
import com.leeacademy.board.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply,Long> {

    //게시글에 따라 처리해야 하므로 다음 기능을 구현
    //특정 게시글의 댓글 목록
    List<Reply> findAllByBoard (Board board);
    List<Reply> findAllByBoardOrderByNoDesc (Board board);
    List<Reply> findAllByBoardOrderByNoAsc (Board board);

    // 특정 게시글의 댓글 개수
    long countAllByBoard(Board board);

}
