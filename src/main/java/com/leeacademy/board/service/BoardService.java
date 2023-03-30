package com.leeacademy.board.service;

import com.leeacademy.board.entity.Board;
import org.springframework.stereotype.Service;

@Service
public interface BoardService {
    Board write(Board board);

}
