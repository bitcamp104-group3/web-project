<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="papago.APIpapagoTranslate" %>
<%
	request.setCharacterEncoding("UTF-8");

	APIpapagoTranslate papagoAPI = new APIpapagoTranslate();
	String inputText = request.getParameter("inputText");
	String result = papagoAPI.translate(inputText);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PAPAGO</title>
</head>
<body>
	<p>입력한 내용: <%= inputText %></p>
	<p>번역한 내용: <%= result %></p>
</body>
</html>