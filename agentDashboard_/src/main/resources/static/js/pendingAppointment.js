$(document).ready(function () {
  $('#dtBasicExample').DataTable();
  $('.dataTables_length').addClass('bs-select');
});

function getPendingAppointments(){
	
}

let pendingApntData = [
	{apnt_id:"apnt_1",date:"11/04/2020, 3:00 PM - 5:00 PM",customer_name:"Lucky",gender:"Male",addressLine1:"Some Address...",mobile:"9861344223",order_id:12345},
	{apnt_id:"apnt_2",date:"11/04/2020, 4:00 PM - 5:00 PM",customer_name:"Luck",gender:"Male",addressLine1:"Some Address...",mobile:"9861344223",order_id:12345},
	{apnt_id:"apnt_3",date:"11/04/2020, 5:00 PM - 5:00 PM",customer_name:"Luc",gender:"Male",addressLine1:"Some Address...",mobile:"9861344223",order_id:12345},
	{apnt_id:"apnt_4",date:"11/04/2020, 6:00 PM - 5:00 PM",customer_name:"Lu",gender:"Male",addressLine1:"Some Address...",mobile:"9861344223",order_id:12345}
	];

windows.onload = () => {
	loadPendingApntData(pendingApntData);
};

function loadPendingApntData(pendingApntData){
	
	const tableBody = document.getElementById('pendingApntmtTbody_id');
	let dataHtml = '';
	
	for(let pData of pendingApntData){
		dataHtml +=   '   <tr>  '  + 
		 ' <th scope="row">1</th>  '  + 
		 ' <td>'  + pData.apnt_id +'</td>  '  + 
		 ' <td>'+ pData.date +'</td>  '  + 
		 ' <td>'+ pData.customer_name +'</td>  '  + 
		 ' <td>'+ pData.gender +'</td>  '  + 
		 ' <td>'+ pData.addressLine1 +'</td>  '  + 
		 ' <td>'+ pendpDataingApntData.mobile +'</td>  '  + 
		 ' <td>'+ pData.order_id +'</td>  '  + 
		 ' <td>  '  + 
		 '  <button type="button" class="btn btn-outline-primary btn-sm m-0 waves-effect" style="background-color: lightseagreen;">Update Measurement</button>  '  + 
		 ' </td>  '  + 
		 '  '  + 
		 ' </tr>  ' ;  
		
		tableBody.innerHTML = dataHtml;
		
	}
	
	 
}