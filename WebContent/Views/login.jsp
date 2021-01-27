
<%@page import="java.util.HashMap"%>
<% request.setAttribute("title", "Login "); %>
<%@ include file="./inc/header.jsp" %>

<c:if test="${not empty ErrorCrid}">
 <div class="alert alert-dismissible alert-danger">
		  <button type="button" class="close" data-dismiss="alert">&times;</button>
		  <h4 class="alert-heading">Error !</h4>
		  <p class="mb-0">${ErrorCrid}</p>
	</div>
</c:if>
<form action="<%=request.getContextPath() %>/loginlogic" method="POST">
   <div class="form-group">
     <label for="login">Login</label>
     <input type="text" name="login" class="form-control ${not empty loginerrors['login'] ? 'is-invalid' :'' } "
      id="login" aria-describedby="login" placeholder="Enter login">	
     <div class="invalid-feedback">${loginerrors['login']} </div>
     </div>
   <div class="form-group">
     <label for="exampleInputPassword1">Password</label>
     <input type="password"  name="pass" class="form-control ${not empty loginerrors['pass'] ? 'is-invalid' :'' } " id="exampleInputPassword1" placeholder="Password">
     <div class="invalid-feedback">${loginerrors['pass']}</div>
   </div>
   <button type="submit" class="btn btn-primary">Login</button>
</form>
<%@ include file="./inc/footer.jsp" %>