package com.leeacademy.board.entity;

//댓글 Entity

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "REPLY")
public class Reply {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long no;

    @Column
    private String writer;

    @Column @Lob
    private String content;

    @CreationTimestamp
    private Timestamp writeTime;

    //게시글과 n:1 관계 설정 및 삭제 시 자동 소멸 처리
    //외래키 컬럼명을 board_no로 설정
    @ManyToOne(targetEntity = Board.class, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "board_no")
    private Board board;
}
