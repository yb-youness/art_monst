
<% request.setAttribute("title", "Home"); %>
<%@ include file="./inc/header.jsp" %>

<!-- Monster info --> 
<!-- Check if data base is Not empty  --> 
 <c:if test="${record.size() >0}">
<c:forEach var="list" items="${record}">
<div class="card mb-3 mt-3">
  <img class="card-img-top" src="data:image/jpg;base64,${list.photo}" alt="Card image cap">
  <div class="card-body">
    <h5 class="card-title">${list.title}</h5>
    <p class="card-text">${list.desc }</p>
    <p class="card-text"><small class="text-muted"><a class="btn btn-primary btn-lg" href="showPost?id=${list.id}" role="button">Details</a></small></p>
  </div>
  </div>
 </c:forEach>
<div>
  <ul class="pagination pagination-lg">
  <!-- This to Dislay the prev page -->
   <c:if test="${currentPage != 1}">
    <li class="page-item ">
      <a class="page-link"href="posts?page=${currentPage - 1}">&laquo;</a>
    </li>
    </c:if>
     <c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                    <li class="page-item active">
                      <a class="page-link" href="#">${i}</a>
                   </li>
                    </c:when>
                    <c:otherwise>
                    <li class="page-item active">
                      <a class="page-link" href="posts?page=${i}">${i}</a>
                   </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            
            
    <!-- To display next page -->
    <c:if test="${currentPage lt noOfPages}">
    <li class="page-item">
      <a class="page-link" href="posts?page=${currentPage + 1}">&raquo;</a>
    </li>
    </c:if>
  </ul>
</div>
 </c:if>
  <c:if test="${record.size()==0}">
  <h1>Data Base Empty !</h1>
  </c:if>

 
<%@ include file="./inc/footer.jsp" %>