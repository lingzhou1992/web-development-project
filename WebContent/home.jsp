<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${empty sessionScope['email']}">
    <c:redirect url="index.jsp" />
</c:if>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
	<link href="style.css" rel="stylesheet">
	
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
   		<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
	
	
	<style>
	
	.box{
		background-color: #F2F2F2;
		height: auto;
		list-style: none outside none;
		margin-left: 405px;
		margin-top: 0;
		padding-left: 0;
		padding-bottom: 0;
		width: 259px;
		position: absolute;
		z-index:2;
	}
	
	.window{
		width: 400px;
		position: absolute;
	}
	
	
	
	table, td, th {
    	border: 1px solid #C4FFDC;
	}
		
	th {
		font-size: 14px;
		color: #A3A6A4;
		width: 100px;
		background-color: #C4FFDC;
	}
	
	td {
		font-size: 14px;
		background-color: white;
		
	}

	.box ul{
		list-style-type: none;		
	}
	
	.box li{
		margin-left: -20px;
		margin-bottom: 3px;
	}
	
	.box li:hover{
		background: #D8D8D8;
	}
	
	.col_1{
		float: left;
		width: 30%;
	}
	.col_2{
		float: right;
		width: 70%; 
	}
	
	
	
	</style>

<script language="javascript" type="text/javascript">

//Browser Support Code
function ajaxFunction(){
	
	

	var ajaxRequest;  // The variable that makes Ajax possible!

	try{
		// Opera 8.0+, Firefox, Safari
		ajaxRequest = new XMLHttpRequest();
	} catch (e){
		// Internet Explorer Browsers
		try{
			ajaxRequest = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try{
				ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e){
				// Something went wrong
				alert("Your browser broke!");
				return false;
			}
		}
	}

	// Create a function that will receive data sent from the server
	ajaxRequest.onreadystatechange = function(){
		if(ajaxRequest.readyState == 4 && ajaxRequest.status==200){
			document.getElementById("display").innerHTML = ajaxRequest.responseText;
			
		}
	}
	ajaxRequest.open("GET", "/project4_14/home?input="+ encodeURIComponent(document.getElementById("title").value), true);
	ajaxRequest.send(null);	
}

function changeInput(str){	
	document.getElementById("title").value = str;
}
 

function infowindow(str){
    var x = event.clientX;
    var y = event.clientY;
	
	var ajaxRequest;  // The variable that makes Ajax possible!

	try{
		// Opera 8.0+, Firefox, Safari
		ajaxRequest = new XMLHttpRequest();
	} catch (e){
		// Internet Explorer Browsers
		try{
			ajaxRequest = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try{
				ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e){
				// Something went wrong
				alert("Your browser broke!");
				return false;
			}
		}
	}

	// Create a function that will receive data sent from the server
	ajaxRequest.onreadystatechange = function(){
		if(ajaxRequest.readyState == 4 && ajaxRequest.status==200){
			showCoords(x,y);
			var window = document.getElementById("displayinfo"); 
			window. innerHTML = ajaxRequest.responseText;
			window.style.visibility = "visible";
			
/* 			var new_x = x + 400;
			var new_y = y + 400; */
			
			

		}
	}
	ajaxRequest.open("GET", "/project4_14/window?input="+ str, true);
	ajaxRequest.send(null);	
	//document.getElementById("displayinfo").innerHTML = "";

}

function showCoords(x,y) {
    //var coords = "X coords: " + x + ", Y coords: " + y;
   // alert(coords);
    document.getElementById("information").style.left = x +"px";
    document.getElementById("information").style.top = y + "px";
}


function removeInfo(x, y){
	
	
	//document.getElementById("displayinfo").innerHTML = "";
	document.getElementById("displayinfo").style.visibility = "hidden";
}

function showInfo()
{
	document.getElementById("displayinfo").style.visibility = "visible";
	}

//onmouseout="removeInfo();
</script>

</head>

<body background="http://www.backgroundplanet.com/static/images/FreeGreatPicture.com-19820-blue-vintage-wallpaper-background.jpg">
		<div class="container" style="height: 100px">
		  <ul class="nav nav-tabs navbar-fixed-top" style="background: white">
		    <li><a href="home.jsp" class="navbar-brand">FabFlix</a></li>
		    <li class="active"  style="margin-top: 10px"><a href="home.jsp">Home</a></li>
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



<div  class="container" style="margin-top: auto; height: 100px">
	<center>
	<form name='myForm' action="displayMovieM">
	<input type='text' oninput="ajaxFunction();" id='title' name="title" size="35"/> 
	<button type="submit">search</button>
	</form>
	</center>
	
	<div class="box" id="display" ></div>
 	<div onmouseout="removeInfo();">
	<div class="window" id="information" style="z-index: 2" onmouseover="showInfo();"><div id="displayinfo"></div></div>
 	</div> 
	
	
	
	
</div>

		
		<div class="container" style="margin: auto;">
 			<c:forEach var="item" items="${list}">${item}</c:forEach>
		</div>
		
		<div class="container">
			<jsp:include page="footer.jsp"/>
		</div>


</body>
</html> 






