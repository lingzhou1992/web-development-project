<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		  <ul class="nav nav-tabs navbar-fixed-top">
		    <li><a href="home.jsp" class="navbar-brand">FabFlix</a></li>
		    <li><a href="home.jsp">Home</a></li>
		    <li><a href="search">Search</a></li>
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
			<center>	
		      <form class="form-signin" role="form" method="post" action="checkout" style="width: 300px">
		        <h1 class="form-signin-heading">Checkout!</h1><hr/>
		        <input type="text" class="form-control" name="firstname" placeholder="Firstname" required autofocus><br/>
		        <input type="text" class="form-control" name="lastname" placeholder="Lastname" required><br/>
		        <input type="number" class="form-control" name="creditcard" placeholder="Creditcard" required><br/>
		        <input type="date" class="form-control" name="expdate" placeholder="Expiration Date mm-dd-yyyy" required><br/>
		        ${message}
		        <button class="btn btn-lg btn-default" type="submit">Submit</button>
		      </form>
		    </center>
		</div>
		
		<div class="container">
			<jsp:include page="footer.jsp"/>
		</div>
		
	</body>
</html>