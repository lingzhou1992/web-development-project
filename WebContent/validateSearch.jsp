<html>
	<head>
		<title>Search Validation</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	</head>
	
	<%@ page import="java.*" %>
	
	<%
	if (request.getParameter("year").length()==0 
			&& request.getParameter("title").length()==0
			&& request.getParameter("director").length()==0
			&& request.getParameter("firstname").length()==0
			&& request.getParameter("lastname").length()==0) {
	%> <jsp:forward page="/search" /> <%} else {
	%> <jsp:forward page="displayMovie" /> <%} %>
	
	</body>
	<body bgcolor="#F0FFC3">
</html>