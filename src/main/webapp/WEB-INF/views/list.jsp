<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1>자유게시판</h1>

<h2><a href="write">글쓰기</a></h2>

<table border="1" width="600">
    <thead>
        <tr>
            <th>번호</th>
            <th width="40%">제목</th>
            <th>작성자</th>
            <th>작성일</th>
            <th>조회수</th>
        </tr>
    </thead>

    <tbody align="center">
       <c:forEach var="board" items="${list}">
           <tr>
               <th>${board.no}</th>
               <th align="left">
                       <a href="detail?no=${board.no}">
                               ${board.title}
                       </a>

               </th>
               <th>${board.writer}</th>
               <th>${board.writeTime}</th>
               <th>${board.readcount}</th>
           </tr>
       </c:forEach>
    </tbody>

</table>