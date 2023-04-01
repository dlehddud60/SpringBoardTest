package com.leeacademy.board.service;

import com.leeacademy.board.entity.Board;
import com.leeacademy.board.entity.BoardFile;
import com.leeacademy.board.properties.BoardFileProperties;
import com.leeacademy.board.repository.BoardFileRepository;
import com.leeacademy.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Calendar;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{

    private BoardRepository boardRepository;
    private BoardFileProperties boardFileProperties;
    private BoardFileRepository boardFileRepository;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository, BoardFileProperties boardFileProperties, BoardFileRepository boardFileRepository) {
        this.boardRepository = boardRepository;
        this.boardFileProperties = boardFileProperties;
        this.boardFileRepository = boardFileRepository;
    }


    @Override
    public Board write(Board board, List<Long> images) {

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

        //추가 images에 들어있는 이미지 번호의 정보에 게시글 정보(result)를 주입
        if(images != null && !images.isEmpty()) {
            for (long seq : images) {
                BoardFile boardFile = boardFileRepository.findById(seq).orElseThrow();//찾아서
                boardFile.setBoard(result);//게시글 정보 추가하고
                boardFileRepository.save(boardFile);//저장 (수정)
            }
        }

        return result;
    }

    @Override
    public void delete(Long no) {
        //게시글 정보 찾아와서
        Board board = boardRepository.findById(no).orElseThrow();
        //이미지 찾고(메소드 없으므로 JPA 작명정책 맞춰서 생성)
        List<BoardFile> list = boardFileRepository.findAllByBoard(board);

        //이미지 지우고 나서
        File dir = boardFileProperties.getImagePath();
        for (BoardFile boardFile : list) {
            File target = new File(dir,String.valueOf(boardFile.getSeq()));
            target.delete();
        }
        boardFileRepository.deleteAll(list);
        //게시글 삭제
        boardRepository.delete(board);

    }

    @Override
//    @Scheduled(cron = "* * * * * *")//테스트(1초간격)
    @Scheduled(cron = "0 0 * * * *")//실제(1시간간격, 매시 정각)
    public void autoClearTempFile() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);//1일 전으로 설정
        List<BoardFile> list = boardFileRepository.getOldData(c.getTime());//오래된 파일 조회
        File dir = boardFileProperties.getImagePath();
        for(BoardFile boardFile : list) {
            File target = new File(dir, String.valueOf(boardFile.getSeq()));
            target.delete();
            boardFileRepository.delete(boardFile);
        }
    }
}
