<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
   <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>
<title>Display Results!</title>
</head>
<body>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<c:set var="cv1" value="${sessionScope.cv1}"></c:set>
<c:set var="path" value="${sessionScope.path}"></c:set>

<form id="msform" >
   
<fieldset>
    <h2 class="fs-title">Missing Person Details</h2>
    <input type="text" name="name" disabled value="${cv1.name}"/>
    <input type="text" name="address" disabled value="${cv1.address}" />
    <input type="text" name="contact" disabled value="${cv1.contact}"/>
	<img src="${path}" alt="Image not found!" width="450px" height="450px" /><br>
    <a href="index.jsp">File Complaint!</a>  <br>
    <a href="search.jsp">Search Missing Person</a><br>
    
</fieldset>

</form>
<div align="center"><h2>Made by - <a href="http://www.linkedin.com/in/parikh-sagar">SAGAR PARIKH</a></h2></div>

	<link rel="stylesheet" type="text/css" href="1.css">
     
     <script type="text/javascript" src="1.js"> </script>
</body>
</html>