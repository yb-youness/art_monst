
<%@page import="java.util.HashMap"%>
<% request.setAttribute("title", "DashBord"); %>
<%@ include file="./inc/header.jsp" %>



<%
// Use jstl insted see the login page for a example 
String eroorclass="";
String Error= (String)request.getSession().getAttribute("errorC");
String ErrorMsg= (String)request.getSession().getAttribute("Msg");      
HashMap <String,String> errorMsgs = (HashMap<String,String>) request.getAttribute("Errors");
    
if(errorMsgs!=null){
    eroorclass="errors";
}

     if(Error !=null){

%>

<div class="alert alert-dismissible <%= Error%>">
  <button type="button" class="close" data-dismiss="alert">&times;</button>
  <p class="mb-0"><%= ErrorMsg %></p>
</div>

<% 
// Remove The Session 
    session.removeAttribute("errorC");
   session.removeAttribute("Msg");
     
     } %>
<div class="row mb-3">
 <button  data-toggle="modal" data-target="#frommodel" class="btn btn-primary"><fmt:message key="labeldashAction.add" /></button>
</div>
<div class="row">
    <div class="col-9">
    <!-- Check If List Is Empty -->
  <c:if test="${AllPosts.size() >0}">
      <table class="table table-hover" id="Posts">
    
  <thead>
    <tr>
      <th scope="col">Id</th>
      <th scope="col">Title</th>
      <th scope="col">Desc</th>
      <th scope="col">Image</th>
      <th scope="col" colspan="2">Actions</th>
    </tr>
  </thead>
  <tbody>
   <c:forEach var="list" items="${AllPosts}">
    <tr class="table">
      <td>${list.id}</td>
      <td> ${list.title}</td>
      <!-- This Requires Processing  -->
      <td>${list.desc}</td>
      <td class="w-25">
      <img  style="width: 10rem; height: 9rem;" src="data:image/jpg;base64,${list.photo}" alt="Card image cap">
      </td>
       <td><a class="btn btn-warning btn-lg" href="edit?id=${list.id}"  role="button"><fmt:message key="labeldashAction.edit" /></a></td>
       
       <!-- Modal Confirmation  -->
       <td>
         <button class="btn btn-danger btn-lg" data-toggle="modal" data-target="#confirmmodel" onclick="bind(this)"  name="delet" id="${list.id}"><fmt:message key="labeldashAction.del" /></button>
       </td>
    </tr>
     </c:forEach>
  </tbody>
</table>
</c:if>
  <c:if test="${AllPosts.size()==0}">
  <h1>Data Base Empty !</h1>
  </c:if>
    </div>
  </div>
  <!--  Model Add Form (Use The Include )-->
   <div class="modal fade <%=eroorclass %>" id="frommodel" tabindex="-1" role="dialog" aria-labelledby="frommodel" aria-hidden="true" >
  <!-- Refactor this To His Own File -->
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="title">Add Post</h5>
        <button type="button" class="close" data-dismiss="modal" onclick="redirect()" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
       <form action="${context}/addlogic" id="formlogic" method="post" enctype="multipart/form-data">
      <div class="modal-body">
	 
	    <div class="form-group">
	      <label for="Title"><fmt:message key="labeldash.title" /></label>
	      <input type="text" class="form-control <%= errorMsgs!=null && errorMsgs.containsKey("title") ? "is-invalid":"" %> " id="Title" name="title" placeholder="Enter Post Title" value="${OldPost.title}">
	      <div class="invalid-feedback"><c:out value="${Errors['title']}"></c:out></div>
	    </div>
	     <div class="form-group">
	      <label for="body"><fmt:message key="labeldash.desc" /></label>
	      <textarea rows="" cols=""  name="body" class="form-control  <%= errorMsgs!=null && errorMsgs.containsKey("desc") ? "is-invalid":"" %>    ">${OldPost.desc}</textarea>
	       <div class="invalid-feedback"><c:out value="${Errors['desc']}"></c:out></div>
	      
	    </div>
	    <div  class="form-group">
	    <label for="photo"><fmt:message key="labeldash.photo" /></label>
	   	  <input type="file" class="photo" name="photo" class="form-control ">
	   	    <div class="text-danger"><c:out value="${Errors['photo']}"></c:out></div>
	    </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" onclick="redirect()" data-dismiss="modal"><fmt:message key="labeldash.quite" /></button>
         <input type="submit" class="btn btn-primary" value="<fmt:message key="labeldash.save" />">
      </div>
      </form>
    </div>
  </div>
</div>

<!-- Confirm Model -->
  <div class="modal fade" id="confirmmodel" tabindex="-1" role="dialog" aria-labelledby="confirmmodel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Delete Post</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
       <form action="${context}/remove"  method="post" id="dele">
       
      <div class="modal-body">
	    <p>You Want To Delete This Post?</p>
	    </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary"  data-dismiss="modal">Close</button>
         <input type="submit" class="btn btn-danger " value="Delete!">
      </div>
      </form>
    </div>
  </div>
</div>
<!-- Script For Binding Data  -->
<script type="text/javascript">
document.addEventListener("DOMContentLoaded", function(event) {
               if(document.getElementById('frommodel').classList.contains("errors")){
            	   $("#frommodel").modal(); 
               }
	
	let query = document.location.search.substr(1).split('&'); // get The Url 
	query = query.toString();  // get The Url 
	    if(query.includes("id")){
	    	let id =query.split("=")[1];
	    	
	    	// Triger Model 
	    	  document.getElementById("title").innerText="Update";
	    	 let form = document.getElementById("formlogic");
	    	     form.action ="${context}/update";
	    	     form.innerHTML +="<input type='hidden' name='id'  value="+id+">";
	    	  $("#frommodel").modal();
	    }
	
});
   function redirect(){
	   let query = document.location.search.substr(1).split('&'); // get The Url 
		query = query.toString();  // get The Url 
		   
	       window.location.href = "${context}/dashbord"; 
		 
   }
     function bind(e){
    	 let id = e.id;
    	 let deletDestination= document.getElementById("dele");
    	  deletDestination.innerHTML +="<input type='hidden' name='id'  value="+id+">";
     }
</script>
<%@ include file="./inc/footer.jsp" %>