<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
   <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>
<title>Search People in database</title>
</head>
<body>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<% //session.invalidate(); %>
<c:set var="d" value="display"></c:set>
<c:set var="n" value="nodata"></c:set>

<c:set var="display" value='<%=request.getParameter("display") %>'></c:set>
<c:if test="${empty display}">
<form action="showComplaintDetails" method="POST" id="msform">
<fieldset>
    <h2 class="fs-title">Look Up people in Database</h2>
    <input type="text" name="name" placeholder="Full Name" />
    <input type="text" name="contact" placeholder="Contact" />
    <input type="submit" class="submit action-button" value="Search!" />  <br>
    
</fieldset>
</form>
<div align="center"><h2>Made by - <a href="http://www.linkedin.com/in/parikh-sagar">SAGAR PARIKH</a></h2></div>

</c:if>
<c:if test="${display eq n}">
<form id="msform">
<fieldset>
    <h2 class="fs-title">No Such Data!</h2>
     <a href="search.jsp">Search Again?</a> <br>
     <a href="index.jsp">File Complaint!</a> 
    
</fieldset>
</form>
<div align="center"><h2>Made by - <a href="http://www.linkedin.com/in/parikh-sagar">SAGAR PARIKH</a></h2></div>

</c:if>


<c:if test="${display eq d}">
<c:set var="name" value='<%=request.getParameter("name") %>'></c:set>
<c:set var="con" value='<%=request.getParameter("con") %>'></c:set>

<form action="displayDetails" method="POST" id="msform">
<fieldset>
    <h2 class="fs-title">Record Found!</h2>
    <h2>Name:<c:out value="${name}"></c:out></h2>
    <input type="hidden" name="con" value="${con}" />
    <input type="submit" class="submit action-button" value="Get More Details!" />  <br>
    
</fieldset>
</form>
<div align="center"><h2>Made by - <a href="http://www.linkedin.com/in/parikh-sagar">SAGAR PARIKH</a></h2></div>

</c:if>

	<link rel="stylesheet" type="text/css" href="1.css">
     
     <script type="text/javascript" src="1.js"> </script>
</body>
</html>