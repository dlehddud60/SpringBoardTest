package com.leeacademy.board.service;

import com.leeacademy.board.entity.Board;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardService {
    Board write(Board board, List<Long> images);

    void delete(Long no);
    void autoClearTempFile();


}
