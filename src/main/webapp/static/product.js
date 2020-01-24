function getProductUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/product";
}

function getBrandUrl()
{
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    	return baseUrl + "/api/brand";
}

//BUTTON ACTIONS
function addProduct(event){
	//Set the values to update
	var $form = $("#product-form");
	var json = toJson($form);
//	console.log(json);
	var url = getProductUrl();
//console.log(json.name+" "+json.category);
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		getProductList();
	   		$("#selectCategory").empty();
	   },
	   error: handleAjaxError
	});
        $("#product-form")[0].reset();
    	return false;
}

function updateProduct(event){
	$('#edit-product-modal').modal('toggle');
	//Get the PRODUCT ID
	var id = $("#product-edit-form input[name=product_id]").val();
	var url = getProductUrl() + "/" + id;

	//Set the values to update
	var $form = $("#product-edit-form");
	var json = toJson($form);
//    console.log(json);
	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	        console.log(response);
	        if(Number(response.mrp) <= 0){
	        	alert("You entered wrong value");
	        }
	        else{
	            alert("Entry updated");
	        }
	   		getProductList();
	   },
	   error: handleAjaxError
	});

	return false;
}

function getProductList(){
	var url = getProductUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
//	        console.log(data);
	   		displayProductList(data);
	   },
	   error: handleAjaxError
	});
}

//function deleteProduct(id){
//	var url = getproductUrl() + "/" + id;
//
//	$.ajax({
//	   url: url,
//	   type: 'DELETE',
//	   success: function(data) {
//	   		getProductList();
//	   },
//	   error: handleAjaxError
//	});
//}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#productFile')[0].files[0];
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
	var url = getProductUrl();

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

//UI DISPLAY METHODS

//GET BRAND DATA
function getBrandList(){
    var url = "/pos/api/brand";
    $.ajax({
        url:url,
        type:'GET',
        success: function(data){
            createBrandData(data);
        },
        error: handleAjaxError
    });
}

//CREATE BRAND DATA AND FILL DROP DOWN
var brandData = {};

function createBrandData(data){
    var brandTemp = "";
//    console.log(data);
    for(var i in data){
        var e = data[i];
        brandTemp = e.brand;
        if(!brandData.hasOwnProperty(brandTemp)){
                brandData[brandTemp] = [];
        }
        brandData[brandTemp].push([e.category,e.id]);
    }
    fillBrandDrop();
}


function fillBrandDrop(){
    var $selectbody = $('#selectBrand');
    var row = "<option selected>" + "Select Brand" + "</option>"
    $selectbody.append(row);
    for(var i in brandData){
         row = "<option>" + i + "</option>";
        $selectbody.append(row);
    }
}



$('#selectBrand').on('change',function(){
    var brandTemp;
    brandTemp = $("#selectBrand option:selected").text();

    fillCategoryDrop(brandTemp);
});

function fillCategoryDrop(brandTemp){
    var $selectbody = $('#selectCategory');

    $selectbody.empty();
    for(var i=0;i<brandData[brandTemp].length;i++){
        var row = "<option>" + brandData[brandTemp][i][0] + "</option>";
        $selectbody.append(row);
    }
}

$('#selectBrand1').on('change',function(){
    var brandTemp;
    brandTemp = $("#selectBrand1 option:selected").text();

    fillCategoryDrop1(brandTemp);
});

function fillBrandDrop1(brand){
    var $selectbody = $('#selectBrand1');
    console.log("inside brand drop");
    $selectbody.empty();
    for(var i in brandData){
        var row;
        if(i==brand){
          row = "<option selected>" + i + "</option>";
        }
        else{
            row = "<option>" + i + "</option>";
        }
        $selectbody.append(row);
    }
}
var default_category;

function fillCategoryDrop1(brandTemp){
    var $selectbody = $('#selectCategory1');
    $selectbody.empty();
    console.log("inside");
    for(var i=0;i<brandData[brandTemp].length;i++){
        var row;

        if(brandData[brandTemp][i][0]==default_category){
//            console.log(default_category);
//            console.log(brandData[brandTemp][i][0]);
            row = "<option selected>" + brandData[brandTemp][i][0] + "</option>";
        }
        else{
           row =  "<option>" + brandData[brandTemp][i][0] + "</option>";
        }
        console.log(row);
        $selectbody.append(row);
    }
}

//function fillCategoryDrop2(category){
//
//}

function displayProductList(data){
	var $tbody = $('#product-table').find('tbody');

	$tbody.empty();
//	console.log(data);
	for(var i in data){
		var e = data[i];
		var buttonHtml = ' <button onclick="displayEditProduct(' + e.product_id   + ')">edit</button>'
		var row = '<tr>'
		+ '<td>' + e.product_id + '</td>'
		+ '<td>' + e.name + '</td>'
		+ '<td>'  + e.barcode + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>' + e.category + '</td>'
		+ '<td>' + e.mrp + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
	return false;
}

function displayEditProduct(id){
	var url = getProductUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayProduct(data);
	   },
	   error: handleAjaxError
	});
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#productFile');
	$file.val('');
	$('#productFileName').html("Choose File");
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
	var $file = $('#productFile');
	var fileName = $file.val();
	$('#productFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog();
	$('#upload-product-modal').modal('toggle');
}

function displayProduct(data){
	$("#product-edit-form input[name=name]").val(data.name);
	$("#product-edit-form input[name=category]").val(data.category);
	$("#product-edit-form input[name=brand]").val(data.brand);
	$("#product-edit-form input[name=mrp]").val(data.mrp);
	$("#product-edit-form input[name=barcode]").val(data.barcode);
	$("#product-edit-form input[name=product_id]").val(data.product_id);
	default_category  = data.category;
	fillBrandDrop1(data.brand);
	fillCategoryDrop1(data.brand);
	$('#edit-product-modal').modal('toggle');
}


function refreshButton()
{
$('#product-form')[0].reset();
$('#selectBrand').empty();
$('#selectCategory').empty();
getBrandList();

//getProductList();
}
//INITIALIZATION CODE
function init(){
	$('#add-product').click(addProduct);
	$('#update-product').click(updateProduct);
	$('#refresh-data').click(refreshButton);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#productFile').on('change', updateFileName)
}

$(document).ready(init);
$(document).ready(getBrandList);
$(document).ready(getProductList);
//$(document).ready(displayProductList);
