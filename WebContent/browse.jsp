<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${empty sessionScope['email']}">
    <c:redirect url="index.jsp" />
</c:if>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>FabFlix</title>

	    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
		<link href="style.css" rel="stylesheet" />
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
   		<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
	</head>

	
	
	<body>		
		<div class="container" style="height: 100px">
		  <ul class="nav nav-tabs navbar-fixed-top">
		    <li><a href="home.jsp" class="navbar-brand">FabFlix</a></li>
		    <li><a href="home.jsp">Home</a></li>
		    <li><a href="search">Search</a></li>
		    <li class="active" style="margin-top: 10px"><a href="browse">Browse</a></li>
		    <li class="dropdown" style="float: right">
		      <a class="dropdown-toggle" data-toggle="dropdown" href="#">Hello! <%= session.getAttribute("username") %> <span class="caret"></span></a>
		      <ul class="dropdown-menu">
		        <li><a href="shoppingcart">Checkout</a></li>
		        <li><a href="Logout">Logout</a></li>                        
		      </ul>
		    </li>
		  </ul>
		</div>
		
	
		<div class="container">
		<h1 align="center">Browse For Movies</h1>
		<table class="table table-striped">
		<tbody>
			<form method="get" action="browse">
			<tr><th>Genre:</th><td>
				<input type="radio" id="genre_label" name="genre" value="All" checked = "checked">
   				<label for="genre_label">All</label>|
				<c:forEach var="genre" items="${genre_list}">${genre}|</c:forEach></td></tr>
			<tr><th>Letter:</th>
			<td>
				<input type="radio" id="letter_label" name="letter" value="All"  checked = "checked">
   				<label for="letter_label">All</label>|
				<c:set var="letter" value="A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,0,1,2,3,4,5,6,7,8,9" />
				<c:forTokens var="l" items="${letter}" delims=",">
				<input type="radio" id="letter_label" name="letter" value="${l}">
   				<label for="letter_label">${l}</label>|				
				</c:forTokens>
			</td></tr>
			<tr/><td align="center" colspan="2"><button class="btn btn-default" type="submit">Browse</button></td></tr>
			</form>
		</tbody>			
		</table>
		</div>
		
		<div class="container">
			<jsp:include page="footer.jsp"/>
		</div>
	
	
	</body>
</html>