package com.leeacademy.board.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name="BOARD_FILE")
public class BoardFile {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long seq;//파일번호 겸 저장될 파일명

    @Column(length = 256)
    private String name;//원래 업로드된 파일명

    @Column(length = 100)
    private String type;//업로드된 파일의 MIME-TYPE(Content Type)

    @Column
    private long size;//업로드된 파일의 크기

    @CreationTimestamp
    private Timestamp uploadTime;//업로드 시기

    @UpdateTimestamp
    private Timestamp confirmTime;//최종저장 시기

    @ManyToOne(targetEntity = Board.class)//게시글과 N:1관계
    @JoinColumn(name = "board_no")//외래키 저장 컬럼명 설정
    private Board board;//게시글 번호(최종 저장 후 설정가능)
}
