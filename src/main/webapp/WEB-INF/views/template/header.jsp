<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>자유게시 </title>
    <%--    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet">--%>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootswatch/5.2.1/cerulean/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .empty-space {
            height: 50px;
        }
    </style>
<%--    jquery dependency : slim에는 ajax기능이 없음--%>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

    <%-- summernote dependency--%>
    <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.js"></script>
    <script>
        $(function () {
            //섬머노트 생성
            //board-form 안에 있는 name=content에 설정
            $(".board-form").find("[name=content]").summernote({
                height:300,//기본높이
                minHeight:300,//최소높이
                callbacks: {
                onImageUpload: function(files) {
                    //파일을 서버에 업로드하는 코드

                    if(!files || files.length == 0) return; //없으면 차단

                    //첨부를 위한 FormData객체 생성
                    const formData = new FormData();
                    //파일 개수만큼 append(이름은 반드시 files.. 서버에 써있음)
                    for(let i=0; i < files.length; i++) {
                        formData.append("files",files[i]);
                    }

                    //ajax요청
                    //-multipart요청이므로 processData와 contentType 처리가 필요

                    $.ajax({
                        url:"${pageContext.request.contextPath}/rest/image",
                        method: "post",
                        data:formData,
                        processData:false,
                        contentType:false,
                        success:function (resp){
                            // console.log("업로드 성공",resp);

                            // 성공 시 서버에서는 Map<String,List> 형태가 반환됨
                            // 내부에는 두개의 List가ㅏ 존재
                            // urls: 이미지 링크 주소(이미지 태그 생성 후 서머노트에 추가
                            // numbers: 이미지 번호(form에 hidden으로 첨부)

                            const {urls,numbers} = resp;
                            for (let i = 0; i < resp.urls.length; i++) {
                                var image = $("<img>").attr("src",urls[i]);
                                $(".board-form").find("[name=content]").summernote('insertNode', image[0]);

                            }
                            for (let i = 0; i < resp.numbers.length; i++) {
                                $("<input>").attr("type","hidden").attr("name","images").val(numbers[i]).prependTo(".board-form");
                            }
                        }
                    })

                },
                    onMediaDelete:function(files){
                        //console.log(files);//확인(삭제한 태그가 배열로 들어옴)
                        if(!files || files.length == 0) return;//없으면 차단

                        //서버 이미지 주소 - http://호스트:포트/board/rest/image/번호
                        //따라서 마지막 슬래시(/)를 찾아서 잘라내면 번호를 알 수 있습니다.
                        const formData = new FormData();

                        //files 개수만큼 반복 처리
                        for(let i=0; i < files.length; i++){
                            const src = $(files[i]).attr("src");//img 태그 src 추출
                            const idx = src.lastIndexOf("/");//우측 슬래시 탐색
                            formData.append("numbers", src.substring(idx+1));//슬래시 바로 다음을 추출
                        }

                        //ajax 삭제 요청
                        $.ajax({
                            url:"${pageContext.request.contextPath}/rest/image/delete",
                            method:"post",//post로 해야 formData를 전송할 수 있음
                            data:formData,
                            processData:false,
                            contentType:false,
                            success:function(resp){
                                console.log(resp);//확인용(삭제된 번호가 반환됨)

                                for(let i=0; i < resp.length; i++){
                                    $("input[type=hidden][name=images][value="+resp[i]+"]").remove();
                                }
                            }
                        });
                    },
                }

            });
        })

    </script>
</head>
<body>
<!-- navbar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Navbar</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarColor01"
                aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarColor01">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link active" href="#">Home
                        <span class="visually-hidden">(current)</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Features</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Pricing</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">About</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button"
                       aria-haspopup="true" aria-expanded="false">Dropdown</a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="#">Action</a>
                        <a class="dropdown-item" href="#">Another action</a>
                        <a class="dropdown-item" href="#">Something else here</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="#">Separated link</a>
                    </div>
                </li>
            </ul>
            <form class="d-flex">
                <input class="form-control me-sm-2" type="search" placeholder="Search">
                <button class="btn btn-secondary my-2 my-sm-0" type="submit">Search</button>
            </form>
        </div>
    </div>
</nav>
<div class="empty-space"></div>
<div class="empty-space"></div>
</body>
</html>