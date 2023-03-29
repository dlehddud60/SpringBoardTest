<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<!-- container -->
    <div class="container-fluid">
<%--            제목--%>
        <div class="row mt-4">
            <div class="col-md-10 offset-md-1">
                <h1>자유 게시판</h1>
                <p>
                    타인에 대한 비방 또는 모욕은 사전 예고 없이 삭제됩니다.
                </p>
            </div>
        </div>
<%--    복합 검색을 위한 아코디언 검색창--%>
    <div class="row mt-4">
        <div class="col-md-10 offset-md-1">
            <div class="card border-dark">
                <div class="card-header">
                    <a class="btn" data-bs-toggle="collapse" href="#collapse-body">검색 도구 상자</a>
                </div>
<%--                검색인지 아목록인지에 따라 옵션 펼침 여부를 다르게 처리--%>
                <c:choose>
                <c:when test="${boardVO.search}">
                <div class="collapse show" id="collapse-body">
                </c:when>
                <c:otherwise>
                <div class="collapse" id="collapse-body">
                </c:otherwise>
                </c:choose>

                    <div class="card-body">
<%--                        검색 form--%>
                        <form method="post" class="search-form">

                            <%--번호검색--%>
                            <div class="row"><div class="col">
                                <div class="form-floating">

                                    <input name="no" class="form-control" type="text" placeholder="번호" value="${boardVO.no}">
                                    <label class="form-label text-secondary">번호</label>
                                </div>
                            </div></div>

                            <%--작성자 검색--%>
                            <div class="row mt-4"><div class="col">
                                <div class="form-floating">
                                    <input name="writer" class="form-control" type="text" placeholder="작성자" value="${boardVO.writer}">
                                    <label class="form-label text-secondary">작성자</label>
                                </div>
                            </div></div>

                            <%--제목 검색--%>
                            <div class="row mt-4"><div class="col">
                                <div class="form-floating">
                                    <input name="title" class="form-control" type="text" placeholder="제목" value="${boardVO.title}">
                                    <label class="form-label text-secondary">제목에 포함된 글자</label>
                                </div>
                            </div></div>

                            <%--내용 검색--%>
                            <div class="row mt-4"><div class="col">

                                <div class="form-floating">
                                    <input name="content" class="form-control" type="text" placeholder="내용" value="${boardVO.content}">
                                    <label class="form-label text-secondary">내용에 포함된 글자</label>
                                </div>

                            </div></div>

                                <%--기간 검색--%>
                            <div class="row mt-4">
                                <div class="col">
                                    <div class="form-floating">
                                        <input name="begin" class="form-control" type="text" placeholder="시작일" value="${boardVO.begin}">
                                        <label class="form-label text-secondary">검색 시작일</label>
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="form-floating">
                                        <input name="end" class="form-control" type="text" placeholder="시작일" value="${boardVO.end}">
                                        <label class="form-label text-secondary">검색 종료일</label>
                                    </div>
                                </div>
                            </div>

                            <div class="row mt-4">
                                <div class="col text-end">
                                    <input type="reset" class="btn btn-warning btn-lg" value="초기화">
                                    <a href="./" class="btn btn-secondary btn-lg">목록</a>
                                    <button type="submit" class="btn btn-success btn-lg">검색</button>
                                </div>
                            </div>


                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

<%--    게시글 개수 및 글쓰기 버튼--%>
    <div class="row mt-4">
        <div class="col-md-10 offset-md-1">
            <div class="row">
                <div class="col text-start">
                    (1/275)
                </div>
                <div class="col text-end">
                    <a href="write" class="btn btn-primary">글쓰기</a>
                </div>
            </div>
        </div>
    </div>

<%--    게시글--%>
        <div class="row mt-4">
            <div class="col-md-10 offset-md-1">
                <table class="table table-hover">
                    <thead class="bg-dark text-light">
                        <tr>
                            <th>번호</th>
                            <th width="40%">제목</th>
                            <th>작성자</th>
                            <th>작성일</th>
                            <th>조회수</th>
                        </tr>
                    </thead>
                    <tbody class="text-center">
                    <c:forEach var="board" items="${data.content}">
                        <tr>
                            <th>${board.no}</th>
                           <td align="left"><a href="detail?no=${board.no}">
                                   ${board.title}</a>
                           </td>
                           <td>${board.writer}</td>
                           <td>${board.writeTime}</td>
                           <td>${board.readcount }</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <%--페이지 네이게이터--%>
        <div class="row mt-4">
            <div class="col-md-10 offset-md-1">
                <ul class="pagination justify-content-center">
                    <li class="page-item"><a class="page-link" href="#">&laquo;</a></li>
                    <li class="page-item"><a class="page-link" href="#">&lt;</a></li>
                    <li class="page-item active "><a class="page-link" href="#">1</a></li>
                    <li class="page-item"><a class="page-link" href="#">2</a></li>
                    <li class="page-item"><a class="page-link" href="#">3</a></li>
                    <li class="page-item"><a class="page-link" href="#">4</a></li>
                    <li class="page-item"><a class="page-link" href="#">5</a></li>
                    <li class="page-item"><a class="page-link" href="#">6</a></li>
                    <li class="page-item"><a class="page-link" href="#">7</a></li>
                    <li class="page-item"><a class="page-link" href="#">8</a></li>
                    <li class="page-item"><a class="page-link" href="#">9</a></li>
                    <li class="page-item"><a class="page-link" href="#">10</a></li>
                    <li class="page-item"><a class="page-link" href="#">&gt;</a></li>
                    <li class="page-item"><a class="page-link" href="#">&raquo;</a></li>
                </ul>
            </div>
        </div>

<%--        검색창--%>
        <div class="row mt-4">
            <div class="col-md-10 offset-md-1">
                <form class="form" method="get">
                    <div class="row">
                        <div class="col-2 offset-2">
                            <select class="form-select">
                                <option>제목</option>
                                <option>작성자</option>
                            </select>
                        </div>
                            <div class="col-4">
                                <input type="search" class="form-control" placeholder="검색어" required>
                            </div>

                        <div class="col-2">
                            <button class="btn btn-success w-100" type="submit">검색</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>




