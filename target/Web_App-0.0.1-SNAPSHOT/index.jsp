<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

   <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
   <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>
<title>Missing Persons Complaint WebApp</title>

</head> 
<body>

<form action="uploadObject" method="POST" id="msform" enctype="multipart/form-data">
   
<fieldset>
    <h2 class="fs-title">Details About Missing People</h2>
    <input type="text" name="name" placeholder="Full Name" />
    <input type="text" name="address" placeholder="Address" />
    <input type="text" name="contact" placeholder="Contact" />
    <input type="file" name="photo" accept="image/*">
    <input type="submit" class="submit action-button" value="File Complaint!" />  <br>
    <a href="search.jsp">Find Your Complaint Details</a> <br>
    <a href="report.jsp">Report Missing Persons</a>   
    
</fieldset>

</form>
<div align="center"><h2>Made by - SAGAR PARIKH</h2></div>
	<link rel="stylesheet" type="text/css" href="1.css">
     
     <script type="text/javascript" src="1.js"> </script>
</body>
</html>
