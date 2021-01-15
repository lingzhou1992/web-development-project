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
		<link href="style.css" rel="stylesheet" />
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
   		<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
	</head>

	
	<body>
				
		<div class="container" style="height: 100px">
		  <ul class="nav nav-tabs navbar-fixed-top" style="background: white">
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
		
		
		<div class="container">
			<form method="get" action="displayMovie">
				Sort by:&nbsp;&nbsp;
				<input type="submit" class="btn btn-default" name="sortbytitleA" value="Title" id="sortbytitleA"/>&nbsp;<label for="sortbytitleA" class="glyphicon glyphicon-sort-by-alphabet"></label>&nbsp;&nbsp;
				<input type="submit" class="btn btn-default" name="sortbytitleD" value="Title"/>&nbsp;<label for="sortbytitleD" class="glyphicon glyphicon-sort-by-alphabet-alt"></label>&nbsp;&nbsp;
				<input type="submit" class="btn btn-default" name="sortbyyearA" value="Year"/>&nbsp;<label for="sortbyyearA" class="glyphicon glyphicon-sort-by-order"></label>&nbsp;&nbsp;
				<input type="submit" class="btn btn-default" name="sortbyyearD" value="Year"/>&nbsp;<label for="sortbyyearD" class="glyphicon glyphicon-sort-by-order-alt"></label>
				<input type="hidden" name="partialtext" value="${partialtext}"/>
				<input type="hidden" name="title" value="${title}"/>
				<input type="hidden" name="year" value="${year}"/>
				<input type="hidden" name="director" value="${director}"/>
				<input type="hidden" name="firstname" value="${firstname}"/>
				<input type="hidden" name="lastname" value="${lastname}"/>		
				<input type="hidden" name="number" value="${number}"/>	
			</form>
		</div>
		<hr>
    	
		<div class="container">
			  <span class="error"><center>${notFound}</center></span>
			  <c:forEach var="item" items="${item_list}">${item}<br/><hr><br/></c:forEach>
		</div>
		
		<div class="container">
			<center>
			<form method="get" action="displayMovie">
			<nav>
			  <ul class="pagination">
			    <li><input type="submit" class="btn btn-default ${disabledprev}" name="prev" value="&laquo;&nbsp;Previous"></li>
				<li><input type="submit" class="btn btn-default ${disablednext}" name="next" value="Next&nbsp;&raquo;"></li>
				<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Results Per Page: &nbsp;</li>
				<li><input type="submit" class="btn btn-default ${disabledpage}" name="perpage5" value="5"></li>
				<li><input type="submit" class="btn btn-default ${disabledpage}" name="perpage10" value="10"></li>
				<li><input type="submit" class="btn btn-default ${disabledpage}" name="perpage15" value="15"></li>
				<li><input type="submit" class="btn btn-default ${disabledpage}" name="perpage20" value="20"></li>
				<li><input type="submit" class="btn btn-default ${disabledpage}" name="perpage25" value="25"></li>
				<input type="hidden" name="partialtext" value="${partialtext}"/>
				<input type="hidden" name="title" value="${title}"/>
				<input type="hidden" name="year" value="${year}"/>
				<input type="hidden" name="director" value="${director}"/>
				<input type="hidden" name="firstname" value="${firstname}"/>
				<input type="hidden" name="lastname" value="${lastname}"/>
				<input type="hidden" name="genre" value="${genre}"/>
				<input type="hidden" name="letter" value="${letter}"/>
				<input type="hidden" name="offset" value="${offset}"/>
				<input type="hidden" name="number" value="${number}"/>
			  </ul>
			</nav>
			</form>
			</center>
		</div>
		 
		<div class="container">
			<jsp:include page="footer.jsp"/>
		</div>
		
	</body>
</html>