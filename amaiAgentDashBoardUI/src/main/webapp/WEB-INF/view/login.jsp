<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="Cognitive Testing">
<meta name="author" content="">
<title>Cognitive Testing</title>
<jsp:include page="commonImport.jsp" />
<!-- <script src="js/createCST.js"></script> -->
<!-- <link href="css/createCST.css" rel="stylesheet" /> -->
<link rel="stylesheet" href="css/login.css">
</head>
<body>
<div class="container-fluid container-custom">
<img src="image/BTlogo_RGB_White.png" class="img-responsive bt-img">
        <div id="loginbox" class="mainbox col-md-6 col-md-offset-4">
            <div class="panel panel-info" >
				<div class="panel-heading-custom">
					<div class="panel-title"><img class="cognitive_logo" src="image/logo.png"/></div>
				</div>
				<div class="panel-body">
					<div style="display: none" id="login-alert"
						class="alert alert-danger col-sm-12"></div>
					<form id="loginform" class="form-group" role="form"
						action="./login" method="POST">
						<!-- <div class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                        <input id="login-username" type="text" class="form-control" name="username" placeholder="EIN" required>
						</div> -->
						<div class="input-group mb-3">
					      <div class="input-group-prepend">
					        <span class="input-group-text"><i class="glyphicon glyphicon-user"></i></span>
					      </div>
					      <input type="text" class="form-control  ein" placeholder="EIN" id="login-username" name="username" maxlength="9" required>
					    </div>
					    
					    <div class="input-group mb-3">
					      <div class="input-group-prepend">
					        <span class="input-group-text"><i class="glyphicon glyphicon-lock"></i></span>
					      </div>
					      <input type="password" class="form-control" placeholder="iUser Password" id="login-password" name="password" required>
					      <div class="input-group-prepend">
					        <span class="input-group-text"><i id="showPass" class="glyphicon glyphicon-eye-open"></i></span>
					      </div>
					    </div>

						<!-- <div class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                        <input id="login-password" type="password" class="form-control" name="password" placeholder="iUser Password" required>
                                        <span class="input-group-addon"><i id="showPass" class="glyphicon glyphicon-eye-open"></i></span>                                                               		
						</div> -->
						<div class="form-group">
							<!-- Button -->
							<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
								<font color="white"> Login Failed : <c:out
										value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
								</font>
							</c:if>
							<div class="controls">
								<br>
								<button id="btn-login" type="submit" class="btn btn-primary">Login</button>
								
								<a href="mailto:orcognitive@bt.com?subject=Cognitive-Contact Us"
									style="color: white; font-style: italic;text-decoration: underline;padding-left: 25px;">Contact Us</a>
							</div>
        				</div>
        				</form>
        			</div>
        		</div>
        	</div>
    <c:remove var = "SPRING_SECURITY_LAST_EXCEPTION" scope = "session" />
	<footer class="footer">&#169; British Telecommunications plc 2020. All rights reserved &nbsp;&nbsp;</footer> 
</div>
</body>
<script>
	localStorage.clear();
	$("#showPass").mouseup(function() {
		$("#login-password").attr('type', 'password');
	}).mousedown(function() {
		$("#login-password").attr('type', 'text');
	});
	$('.ein').keyup(function () {
	    if(!($.isNumeric(this.value))){
	    	 this.value = this.value.substring(0,this.value.length-1);
	    }
	});
</script>
</html>