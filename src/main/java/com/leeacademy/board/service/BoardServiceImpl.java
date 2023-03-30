package com.leeacademy.board.service;

import com.leeacademy.board.entity.Board;
import com.leeacademy.board.repository.BoardRepository;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService{

    private BoardRepository boardRepository;

    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public Board write(Board board) {

        //새글일 경우와 답글일 경우를 구분하여 처리(board.no가 존재할 경우 답글
        //-t새글일 경우 - 등록후 grp를 번호와 동일하게 갱신
        // 답글일 경우 - 원본글 정보를 이용하여 grp, seq, dep를 계산
        boolean isReply = board.getNo() != null;

        if(isReply) {//답글일 경우 -grp, seq, dep계산
            Board origin = boardRepository.findById(board.getNo()).orElseThrow();

            Long seq = boardRepository.getAvailableSeq(origin);
            if (seq == null){
                seq = boardRepository.countByGrp(origin.getGrp());
            }else { //찾은 경우 해당 위치 이상의 값들을 증가 처리
                boardRepository.increaseSequence(Board.builder().grp(origin.getGrp()).seq(seq).build());
            }

            //no,grp,seq,dep변경
            board.setNo(null);//번호 초기화(시퀀스)
            board.setGrp(origin.getGrp());//그룹 유지
            board.setSeq(seq);//계산된 seq
            board.setDep(origin.getDep()+1); //차수 증가
        }


        Board result = boardRepository.save(board);

        if(!isReply) {//새글일 경우 - grp 갱성
            result.setGrp(result.getNo());
            boardRepository.save(result);
        }

        return result;
    }
}
