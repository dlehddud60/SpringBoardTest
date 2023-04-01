package com.leeacademy.board.entity;

import java.sql.Date;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "BOARD")
public class Board {
	
	@Id @GeneratedValue(strategy= GenerationType.AUTO)
	private Long no;
	
	@Column(length = 60)
	private String writer;

	@Column(length = 60)
	private String title;
	
	@Column @Lob
	private String content;

	@Column(length = 20)
	private String password;

	@Column
	private int readcount;
	
	@CreationTimestamp
	private Date writeTime;
	
	@UpdateTimestamp
	private Date editTime;
	//계층형 게시판을 위한 상태값
	@Column
	private Long grp;

	@Column
	private long seq, dep;

	//댓글 개수 확인용 컬럼
	//조인을 해도 셀 수 있지만 성능상의 이점을 가지기 위해 별도의 컬럼을 설정
	@Column
	private long replyCount;
}







