
<%@page import="com.model.Post"%>
<% request.setAttribute("title", "Details"); %>
<%@ include file="./inc/header.jsp" %>

<!-- Monster info --> 
  <img class="card-img-top" src="data:image/jpg;base64,${Post.photo}" alt="Card image cap">
  <div class="card-body">
    <h5 class="card-title">${Post.title}</h5>
    <p class="card-text">${Post.desc }</p>
    <p class="card-text"><small class="text-muted"><a class="btn btn-primary btn-lg" href="${context}/" role="button">Go Back</a></small></p>
  </div>
<%@ include file="./inc/footer.jsp" %>