package com.leeacademy.board.controller;

//비동기 댓글 처리 컨트롤러
//별도의 rich text editor는 부티잊 않을 예정

import com.leeacademy.board.entity.Board;
import com.leeacademy.board.entity.Reply;
import com.leeacademy.board.repository.BoardRepository;
import com.leeacademy.board.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reply")
public class ReplyController {

    private BoardRepository boardRepository;
    private ReplyRepository replyRepository;

    @Autowired
    public ReplyController(BoardRepository boardRepository, ReplyRepository replyRepository) {
        this.boardRepository = boardRepository;
        this.replyRepository = replyRepository;
    }
    //댓글 등록 - 게시글 번호(no), 댓글 작성자(writer), 댓글 내용(content) 필요
    @PostMapping("/insert")
    public Reply insert(@RequestBody Reply reply) {
        Board board = boardRepository.findById(reply.getNo()).orElseThrow();

       Reply result = replyRepository.save(Reply.builder()
                                            .writer(reply.getWriter())
                                            .content(reply.getContent())
                                            .board(board)
                                            .build());
       //댓글 개수 갱신
       board.setReplyCount(replyRepository.countAllByBoard(board));
       boardRepository.save(board);
       return result;
    }

    @GetMapping("/list/{boardNo}")
    public List<Reply> list(@PathVariable long boardNo) {
        Board board = boardRepository.findById(boardNo).orElseThrow();
        return replyRepository.findAllByBoardOrderByNoAsc(board);//최신순
//        return replyRepository.findAllByBoardOrderByNoDesc(board); //작성순
    }

    @GetMapping("/count/{boardNo}")
    public long count(@PathVariable long boardNo){
        Board board = boardRepository.findById(boardNo).orElseThrow();
        return replyRepository.countAllByBoard(board);
    }
}
