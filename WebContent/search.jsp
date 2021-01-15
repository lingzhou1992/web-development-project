<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ page isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${empty sessionScope['email']}">
    <c:redirect url="index.jsp" />
</c:if>


<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>FabFlix</title>

	    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
		<link href="style.css" rel="stylesheet">
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
   		<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
	</head>
	
	
	
	
	<body>
		
		<div class="container" style="height: 100px">
		  <ul class="nav nav-tabs navbar-fixed-top" style="background: white">
		    <li><a href="home.jsp" class="navbar-brand">FabFlix</a></li>
		    <li><a href="home.jsp">Home</a></li>
		    <li class="active" style="margin-top: 10px"><a href="search">Search</a></li>
		    <li><a href="browse">Browse</a></li>
		    <li class="dropdown" style="float: right">
		      <a class="dropdown-toggle" data-toggle="dropdown" href="#">Hello! <%= session.getAttribute("username") %> <span class="caret"></span></a>
		      <ul class="dropdown-menu">
		        <li><a href="shoppingcart">Checkout</a></li>
		        <li><a href="Logout">Logout</a></li>                        
		      </ul>
		    </li>
		  </ul>
		</div>
    	
		<div class="container" style="margin: auto;">
		<h1 align="center">Search For Movies</h1>
		<table class="table table-striped" style="width: 600px; margin-left: auto; margin-right: auto;">
		<tbody>
			<form method="get" action="validateSearch.jsp">
			<tr/><th>Title</th><td><input type="text" name="title"></td></tr>
			<tr/><th>Year</th><td><input type="number" name="year"></td></tr>
			<tr/><th>Director</th><td><input type="text" name="director"></td></tr>
			<tr/><th>Star First Name</th><td><input type="text" name="firstname"></td></tr>
			<tr/><th>Star Last Name</th><td><input type="text" name="lastname"></td></tr>
			<tr/><td align="center" colspan="2"><label><input type="checkbox" name="partialtext"/>&nbsp;&nbsp;Partial Text Matching</label></td></tr>
			<tr/><td align="center" colspan="2"><button class="btn btn-default" type="submit">Search</button></td></tr>
			</form>
		</tbody>			
		</table>
		</div>
		
		<div class="container">
			<jsp:include page="footer.jsp"/>
		</div>
	</body>
</html>