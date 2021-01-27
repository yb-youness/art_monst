
<%@page import="com.model.Post"%>
<% request.setAttribute("title", "Contact Us"); %>
<%@ include file="./inc/header.jsp" %>
<c:if test="${not empty param.suc }">
<div class="alert alert-dismissible alert-success">
  <button type="button" class="close" data-dismiss="alert">&times;</button>
    Email Send  !
</div>
</c:if>
<form action="${context}/contactuslogic" method="POST">
<div class="form-group">
      <label for="exampleInputEmail1">Email address</label>
      <input type="text" name="from" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email">
 </div>
 <div class="form-group">
      <label for="subject">Subject</label>
      <input type="text" name="obj" class="form-control" id="sub"  placeholder="Enter subject">
 </div>
<div class="form-group">
      <label for="exampleTextarea">Message</label>
      <textarea class="form-control" name="cont" id="exampleTextarea" rows="3"></textarea>
    </div>
  <input type="submit" value="Send">  
</form>  

<%@ include file="./inc/footer.jsp" %>