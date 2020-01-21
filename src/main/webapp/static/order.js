
function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order";
}

//BUTTON ACTIONS
function addOrderItem(){
	//Set the values to update
	var barcode = $('#orderitem-form input[name=barcode]').val();
	var quantity = Number($('#orderitem-form input[name=quantity]').val());
//	console.log(barcode);

var url = getOrderUrl() + '/' + barcode ;

$.ajax({
	url: url,
	type: 'GET',
	success: function(data) {
		if(!orderItemTable.hasOwnProperty(barcode)) orderItemTable[barcode] = [data.name ,quantity , data.mrp];
		else orderItemTable[barcode][1] = Number(orderItemTable[barcode][1]) + quantity;

		if(Number(orderItemTable[barcode][1]) > data.quantity){
			orderItemTable[barcode][1] =  Number(orderItemTable[barcode][1]) - quantity;
			alert("Quantity not available");
		}
		console.log(data);
		displayOrderList(data);
//	   		checkQuantity(data);
//	   		make_map(data);
},
error: handleAjaxError
});

$("#orderitem-form")[0].reset();
return false;
}

function addOrder(event){
	//Set the values to update

	var row_entry = {};
	var send_order= [];
//	console.log('sadasd');
	for(var i in orderItemTable){
		row_entry = new Object;
		row_entry['barcode'] = i;
		row_entry['quantity'] = orderItemTable[i][1];
		send_order.push(row_entry);
	}

	var url = getOrderUrl();
//	console.log(send_order);

	$.ajax({
		url: url,
		type: 'POST',
		data: JSON.stringify(send_order),
		headers: {
			'Content-Type': 'application/json'
		},
		success: function(response) {
//			console.log("hi");
//	   		getOrderList();
    alert("Congralutions order created");

},
error: handleAjaxError
});
	return false;
}

function updateOrder(event){
	$('#edit-order-modal').modal('toggle');
	//Get the BARCODE
	var barcode = $("#order-edit-form input[name=barcode]").val();
	var quantity_in_edit = $("#order-edit-form input[name=quantity]").val();
	var url = getOrderUrl() + "/" + barcode;
	//Set the values to update
	var $form = $("#order-edit-form");

	$.ajax({
		url: url,
		type: 'GET',
		success: function(data) {
//	            console.log(data.quantity - Number(orderItemTable[barcode][1])+quantity_in_edit);
if((data.quantity - quantity_in_edit) < 0)  alert("Not available");

else{
	orderItemTable[barcode][1] =  quantity_in_edit;
}
displayOrderList(data);
},
error: handleAjaxError
});

	return false;
}

// DELETE ORDER
function deleteOrder(data){
	delete orderItemTable[data];
	displayOrderList();
}

//function getOrderList(){
//	var url = getOrderUrl();
//	$.ajax({
//	   url: url,
//	   type: 'GET',
//	   success: function(data) {
//	   		displayOrderList(data)  ;
//	   },
//	   error: handleAjaxError
//	});
//}


// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#orderFile')[0].files[0];
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
	fileData = results.data;
	uploadRows();
}

function uploadRows(){
	//Update progress
	updateUploadDialog();
	//If everything processed then return
	if(processCount==fileData.length){
		return;
	}

	//Process next row
	var row = fileData[processCount];
	processCount++;

	var json = JSON.stringify(row);
	var url = getOrderUrl();

	//Make ajax call
	$.ajax({
		url: url,
		type: 'POST',
		data: json,
		headers: {
			'Content-Type': 'application/json'
		},
		success: function(response) {
			uploadRows();
		},
		error: function(response){
			row.error=response.responseText
			errorData.push(row);
			uploadRows();
		}
	});

}

function downloadErrors(){
	writeFileData(errorData);
}



// MAP TO STORE ORDER ITEM LIST
var orderItemTable = {};


//UI DISPLAY METHODS
function displayOrderList(data){

	var $tbody = $('#order-table').find('tbody');

	$tbody.empty();
	var id = 1;
	var total = 0;
	for(var i in orderItemTable){
		var buttonHtml = '<button onclick="deleteOrder(' + i + ')">delete</button>'
		buttonHtml += ' <button onclick="displayOrder(' + i + ')">edit</button>'
		var row = '<tr>'
		+ '<td>' + id++ + '</td>'
		+ '<td>' + orderItemTable[i][0] + '</td>'
		+ '<td>'  + i + '</td>'
		+ '<td>'  + orderItemTable[i][1] + '</td>'
		+ '<td>'  + orderItemTable[i][2] + '</td>'
		+ '<td>'  +(orderItemTable[i][1])*(orderItemTable[i][2]) + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
		total += (orderItemTable[i][1])*(orderItemTable[i][2]);
		
		$tbody.append(row);
	}
	var row2 = '<tr>'
	+ '<td></td>'
	+ '<td></td>'
	+ '<td></td>'
	+ '<td></td>'
	+ '<td>' + 'total' + '</td>'
	+ '<td>'  + total + '</td>'
	+ '</tr>';
	$tbody.append(row2);

}
//function editdisplayOrder(barcode){
//    var url = getOrderUrl() + "/" + barcode;
//    var quantity_in_edit = $("#order-edit-form input[name=quantity]").val();
//	    $.ajax({
//	        url: url,
//	        type: 'GET',
//	        success: function(data) {
//	   		    displayOrder(data ,barcode);
//	        },
//	        error: handleAjaxError
//	    });
//}

function displayOrder(barcode){
//	$("#order-edit-form input[name=name]").val(orderItemTable[data][0]);
$("#order-edit-form input[name=quantity]").val(orderItemTable[barcode][1]);
$("#order-edit-form input[name=barcode]").val(barcode);
$('#edit-order-modal').modal('toggle');

}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#orderFile');
	$file.val('');
	$('#orderFileName').html("Choose File");
	//Reset various counts
	processCount = 0;
	fileData = [];
	errorData = [];
	//Update counts
	updateUploadDialog();
}

function updateUploadDialog(){
	$('#rowCount').html("" + fileData.length);
	$('#processCount').html("" + processCount);
	$('#errorCount').html("" + errorData.length);
}

function updateFileName(){
	var $file = $('#orderFile');
	var fileName = $file.val();
	$('#orderFileName').html(fileName);
}

function displayUploadData(){
	resetUploadDialog();
	$('#upload-order-modal').modal('toggle');
}

function refreshlist(){

	var $tbody = $('#order-table').find('tbody');
	$tbody.empty();
}

//INITIALIZATION CODE
function init(){
	$('#add-order').click(addOrderItem);
	$('#order-form').click(addOrder);
	$('#update-order').click(updateOrder);
	$('#refresh-page').click(refreshlist);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
	$('#orderFile').on('change', updateFileName)
}

$(document).ready(init);
//$(document).ready(getOrderList);

