<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"  errorPage="error.jsp"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <!-- Check If the Cookies  Value of the lang is Set if Not Load eng by Default  -->
 <c:set var="curentlang"
    value="${not empty cookie.curentlang.value ? cookie.curentlang.value :'en'}"
    scope="session"/>
  <fmt:setLocale value="${curentlang}" /> 
 <fmt:setBundle basename="com.conf.project.labeles" />
 <!--  Globale Context For The app   -->
 <c:set var="context" value="${pageContext.request.contextPath}" />
 <!-----------------------------------------  -->
<!DOCTYPE html>
<html lang="${curentlang}">
<head>
<meta charset="ISO-8859-1">
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootswatch/4.5.3/darkly/bootstrap.min.css" integrity="sha512-U4WaRm7u3LeQy69FgQcz1CBxA32VsI/OeUdcCC5iBbwdjbfRcE+9E2wnJjXPO/bRfrClPTDYTLgBOekcTiBEgQ==" crossorigin="anonymous" />
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.22/css/dataTables.bootstrap4.min.css">
<title>${title}</title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
  <a class="navbar-brand" href="${context}/">ArtMonster</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarColor01">
    <ul class="navbar-nav mr-auto">
      <c:if test="${sessionScope.user ==null }">
      <li class="nav-item">
        <a class="nav-link" href="${context}/login"><fmt:message key="labelnav.login" /></a>
      </li>
      </c:if>
      
      <!-- Check if The Session Is Set   -->
      <c:if test="${sessionScope.user !=null }">
      <li class="nav-item">
        <a class="nav-link" href="${context}/dashbord"><fmt:message key="labelnav.dashbord" /></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${context}/loogout"><fmt:message key="labelnav.decon" /></a>
      </li>
      </c:if>
      <li class="nav-item">
        <a class="nav-link" href="${context}/contactus"><fmt:message key="labelnav.contact" /></a>
      </li>
         <li class="nav-item">
        <a class="nav-link" href="${context}/about"><fmt:message key="labelnav.about" /></a>
      </li>
           <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="" id="lang" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> Lang</a>
                            <div class="dropdown-menu" aria-labelledby="lang">
                            <a class="dropdown-item" href="${context}/setlang?lang=en">English</a>
                                <a class="dropdown-item" href="${context}/setlang?lang=fr">  French</a>
                                <a class="dropdown-item" href="${context}/setlang?lang=de">German</a>
                              
                            </div>
                        </li>
     </ul> 
  </div>
</nav>
<body>
<div class="container mt-3">




