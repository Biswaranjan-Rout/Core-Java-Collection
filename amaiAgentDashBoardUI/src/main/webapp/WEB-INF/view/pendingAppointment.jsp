<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Pending Appointments</title>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<jsp:include page="menu.jsp" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>



<style>
html,body,h1,h2,h3,h4,h5 {font-family: "Raleway", sans-serif}
</style>


</head>
<body>

<body class="w3-light-grey">


<!-- Overlay effect when opening sidebar on small screens -->
<div class="w3-overlay w3-hide-large w3-animate-opacity" onclick="w3_close()" style="cursor:pointer" title="close side menu" id="myOverlay"></div>

<!-- !PAGE CONTENT! -->
<div class="w3-main" style="margin-left:300px;margin-top:43px;">

  <!-- Header -->
  <header class="w3-container" style="padding-top:22px">
    <h5><b><i class="fa fa-dashboard"></i> Pending Appointments</b></h5>
  </header>

<!-- Start -->
	<!--Table-->
		<div>
			<div class='table-responsive'>
				<table id="pendingApntmtTable_id" class="table table-hover btn-table">
				<!--Table head-->
				  <thead>
				    <tr style="background-color: #009688!important;">
				      <th>#</th>
				      <th>Appointment Id</th>
				      <th>Time</th>
				      <th>Customer Name</th>
				      <th>Gender</th>
				      <th>Address</th>
				      <th>Mobile</th>
				      <th>something</th>
				      <th>Order Id</th>
				      <th>Action</th>
				    </tr>
				  </thead>
				  <!--Table head-->
				  <!--Table body-->
				  <tbody id = "pendingApntmtTbody_id">
				    <tr>
				      <th scope="row">1</th>
				      <td>Appointment1</td>
				      <td>11/04/2020, 3:00 PM - 5:00 PM</td>
				      <td>Biswaranjan Rout</td>
				      <td>Male</td>
				      <td>12th Cross, lakshmi Layout, Near Munekollal govt. High School, 560037 </td>
				      <td>9741956143</td>
				      <td>Order123</td>
				       <td>
				        <button type="button" class="btn btn-outline-primary btn-sm m-0 waves-effect" style="background-color: lightseagreen;">Update Measurement</button>
				      </td>
				    
				    </tr>
				    <tr>
				      <th scope="row">2</th>
				      <td>Appointment2</td>
				      <td>11/04/2020, 1:00 PM - 3:00 PM</td>
				      <td>Biswa Rout</td>
				      <td>Male</td>
				      <td>15th Cross, lakshmi Layout, Near Munekollal govt. High School, 560037 </td>
				      <td>9741956143</td>
				      <td>Order1234</td>
				       <td>
				        <button type="button" class="btn btn-outline-primary btn-sm m-0 waves-effect" style="background-color: lightseagreen;">Update Measurement</button>
				      </td>
				    </tr>
				    <tr>
				      <th scope="row">3</th>
				      <td>Appointment3</td>
				      <td>11/04/2020, 9:00 AM - 12:00 PM</td>
				      <td>Biswa Ranjan Rout</td>
				      <td>Male</td>
				      <td>18th Cross, lakshmi Layout, Near Munekollal govt. High School, 560037 </td>
				      <td>9741956143</td>
				      <td>Order12345</td>
				       <td>
				        <button type="button" class="btn btn-outline-primary btn-sm m-0 waves-effect" style="background-color: lightseagreen;">Update Measurement</button>
				      </td>
				    </tr>
				  </tbody>
				  <!--Table body-->
				</table>
			</div>
		</div>
		<!--Table-->
		<!-- End -->


  <!-- End page content -->
</div>


</body>

</body>
</html>