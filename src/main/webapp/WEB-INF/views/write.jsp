<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>

<div class="container-fluid">
    <div class="col-md-10 offset-md-1">
        <h1>게시글 작성</h1>
        <p class="text-secondary">
            글은 자신의 인격입니다.
        </p>
    </div>

    <form method="post">
        <c:if test="${param.no != null}">
    <%--no가 있을 경우 답글이므로 전달을 위한 hidden field 생성--%>
            <input type="hidden" name="no" value="${param.no}">
        </c:if>

        <div class="row mt-4">
            <div class="col-md-10 offset-md-1">
                <div class="form-floating mt-4">
                    <input name="writer" type="text" class="form-control" placeholder="작성자" required>
                    <label class="form-label" text-secondary>작성자</label>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-10 offset-md-1">
                <div class="form-floating mt-4">
                    <input name="title" type="text" class="form-control" placeholder="제목" required>
                    <label class="form-la bel" text-secondary>제목</label>
                </div>
            </div>
        </div>

        <div class="row mt-4">
            <div class="col-md-10 offset-md-1">
                <textarea name="content" class="form-control" rows="15" placeholder="내용 작성" required></textarea>
            </div>
        </div>
<%--        비밀번호 입력창 추가--%>
        <div class="row mt-4">
            <div class="col-md-10 offset-md-1">
                <div class="form-floating">
                    <input type="password" name="password" class="form-control" placeholder="비밀번호" required>
                    <label class="form-label text-secondary">비밀번호</label>
                </div>
            </div>
        </div>


        <div class="row mt-4">
            <div class="col-md-10 offset-md-1 text-end">
                <a href="./" type="button" class="btn btn-secondary btn-lg">목록</a>
                <button type="submit" class="btn btn-primary btn-lg">등록</button>
            </div>
        </div>
    </form>

</div>




<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>