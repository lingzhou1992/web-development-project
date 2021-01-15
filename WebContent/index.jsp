<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>FabFlix Login</title>
		
		<!-- Bootstrap core CSS -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">		
		<link href="http://fonts.googleapis.com/css?family=Open+Sans:300,600,700" rel="stylesheet" type="text/css">
		<link href="style.css" rel="stylesheet" />

		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
		<script scr="http://getbootstrap.com/assets/js/ie-emulation-modes-warning.js"></script>
		<script scr="http://getbootstrap.com/assets/js/ie10-viewport-bug-workaround.js"></script>

	</head>

	<body background="http://images.susu.org/unionfilms/films/backgrounds/hd/robots.jpg" style="background-size: 100%;" style="opacity:0.6; filter:alpha(opacity=60);">
		<div class="container">		
		    <center>	
		    <div class="jumbotron vertical-center" style="border-radius: 25px; width: 500px; margin-top: 100px; height: 400px; background: rgba(255, 255, 255, 0.7);">
		      <form class="form-signin" role="form" method="post" action="FabFlix" style="margin-top: -40px">
		        <h1 class="form-signin-heading">Welcome!</h1><hr/>
		        <input type="email" class="form-control" name="email" placeholder="Email address" required autofocus><br/>
		        <input type="password" class="form-control" name="pwd" placeholder="Password" required><br/>
		        ${message}
		        <button class="btn btn-lg btn-default" type="submit">Login</button>
		      </form>
		      </div>	
		    </center>
		</div>
	</body>
</html>

