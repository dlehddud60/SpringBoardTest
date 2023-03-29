package com.leeacademy.board.controller;

public class test {
    public static void main(String[] args) {
        for (int i = 1; i <= 1000; i++) {
            System.out.println("insert into board(no,title,writer,content,password,readcount,write_time) values("+i+",'테스트게시글"+i+"','testuser" + i +"','테스트내용" + i +"','testuser',569,now());");
        }
    }
}
