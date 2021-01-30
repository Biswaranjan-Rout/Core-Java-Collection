var url = {
		uri: 'http://localhost:8081/api',
		//uri: 'http://localhost:9092/api'
}
$(document).ready(function(){
	alert('hi');
});

$("button").click(function(){
    $("p").slideToggle();
  });

function getPendingAppointments(){
	$.ajax({
		url: "demo_test.txt",
		async: false,
		type: "GET",
		dataType:"text",
		success: function(result){
			console.log('-----Validation failed: '+result+'-----');
	    },
		error : function(response) {
			console.log('-----Validation failed: '+response+'-----');
			swal({
				title : "Error",
				text : "Authentication failed",
				type : "error",
				showCancelButton : true,
				closeOnConfirm : true
			});
		}
	});
}

function getNewAppointments(){
	$.ajax({
		url: "demo_test.txt",
		async: false,
		type: "GET",
		dataType:"text",
		success: function(result){
			console.log('-----Validation failed: '+result+'-----');
	    },
		error : function(response) {
			console.log('-----Validation failed: '+response+'-----');
			swal({
				title : "Error",
				text : "Authentication failed",
				type : "error",
				showCancelButton : true,
				closeOnConfirm : true
			});
		}
	});
}

function getCompletedAppointments(){
	$.ajax({
		url: "demo_test.txt",
		async: false,
		type: "GET",
		dataType:"text",
		success: function(result){
			console.log('-----Validation failed: '+result+'-----');
	    },
		error : function(response) {
			console.log('-----Validation failed: '+response+'-----');
			swal({
				title : "Error",
				text : "Authentication failed",
				type : "error",
				showCancelButton : true,
				closeOnConfirm : true
			});
		}
	});
}

function getCancelledAppointments(){
	$.ajax({
		url: "demo_test.txt",
		async: false,
		type: "GET",
		dataType:"text",
		success: function(result){
			console.log('-----Validation failed: '+result+'-----');
	    },
		error : function(response) {
			console.log('-----Validation failed: '+response+'-----');
			swal({
				title : "Error",
				text : "Authentication failed",
				type : "error",
				showCancelButton : true,
				closeOnConfirm : true
			});
		}
	});
}
function logout(){
	
}