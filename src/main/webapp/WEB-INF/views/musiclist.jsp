<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="frm" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.tune.model.TunePost" %>
<%@ page import="com.example.tune.model.TuneComment" %>
<%@ page import="com.example.tune.repo.TunePostRepository" %>
<%@ page import="com.example.tune.repo.TuneCommentRepository" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tune Post List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
          crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-warning">
    <div class="container">
        <a class="navbar-brand fs-1 fw-medium" href="#">tickBit's little full stack experiment in Java</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item"><a class="nav-link" href="home">Home</a></li>
                <li class="nav-item"><a class="nav-link" href="musiclist">All tunes</a></li>
            </ul>
        </div>
    </div>
</nav>


<c:set var="tunePosts" value="${tunePosts}" />
<div class="container mt-5">
    <h2 class="mb-4 text-center font-weight-bold">Tune Post List</h2>
    
    <div class="row row-cols-2">

        <!-- add new tune form -->
        <frm:form action="addPost" method="post" modelAttribute="newTunePost">
    <div class="mb-3">
        <!-- name of the tune -->
        <label for="postName" class="form-label">Post Name</label>
        <input type="text" class="form-control" id="postName" name="postName" required>
    </div>

    <div class="mb-3">
        <!-- comment (optional) -->
        <label for="commentText" class="form-label">Comment (optional)</label>
        <textarea class="form-control" id="commentText" name="commentText" rows="2"></textarea>
    </div>

    <button type="submit" class="btn btn-success">Add Post</button>
</frm:form>

<c:forEach var="tunePost" items="${tunePosts}">
    <div class="col mb-4">
        
        <div class="card border-dark bg-dark text-white">
            <div class="card-body">

                <h5 class="card-title">${tunePost.getPostName()}</h5>
                <p class="card-text">
                    
                    <strong>Comments:</strong>
                    <c:set var="comments" value="${tunePost.getComments()}" />
                    <ul>
                        <c:forEach var="comment" items="${tunePost.getComments()}">
                            <li>${comment.getText()}</li>
                        </c:forEach>
                    </ul>
                </p>
                <frm:form action="handleForm" method="post" modelAttribute="tuneComment">
                    <div class="mb-1">
                    <!-- add hidden field to postID -->
                    <input type="hidden" name="postID" value="${tunePost.id}" />
        
                    <!-- text of comment -->
                    <textarea class="form-control" id="commentText" name="text" rows="2" required></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary">Submit</button>
                    </frm:form>

            </div>
            
            
        </div>
    </div>
</c:forEach>

    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
</body>
</html>
