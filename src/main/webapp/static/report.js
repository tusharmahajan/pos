var brandData={};
var categoryData={};

function getReportUrl(){
  var baseUrl = $("meta[name=baseUrl]").attr("content")
  return baseUrl + "/api/salesreport";
}

function getReport(event){
  $('#report-form input[name=brand]').val(brandTemp);
  $('#report-form input[name=category]').val(categoryTemp);
  if($('#report-form input[name=startdate]').val()==""){
    alert("Enter startDate");
    return false;
  }
    //$('#report-form')[0].reset();
    fillBrandDrop($('#selectBrand'));
    fillCategoryDropInit($('#selectCategory'));
    var $form = $("#report-form");
    var json = toJson($form);
    // console.log(json);
    var url = getReportUrl();
    console.log(url);
     //console.log(json.productName+ ' ' + json.category);
     $.ajax({
      url: url,
      type: 'POST',
      data: json,
      headers: {
        'Content-Type': 'application/json'
      },
      success: function(response) {
        displaySalesReport(response)
      },
      error: handleAjaxError
    });
    //return false;
  }

  function displaySalesReport(data){
    console.log(data);
    var $thead = $('#report-table').find('thead');
    $thead.empty();
    var $tbody = $('#report-table').find('tbody');
    var row = '<tr>'
    + '<th scope="col">Category</th>'
    + '<th scope="col">Quantity</th>'
    + '<th scope="col">Revenue</th>'
    + '</tr>'
    $thead.append(row);
    $tbody.empty();

    for(var i in data){
      var e = data[i];
      var row = '<tr>'
      + '<td>' + e.category + '</td>'
      + '<td>' + e.quantity + '</td>'
      + '<td>'  + e.revenue + '</td>'
      + '</tr>';
      $tbody.append(row);
    }
    $('#sales-report-modal').modal('toggle');

  }

  function displayInventoryReport(data){
    var $thead= $('#report-table').find('thead');
    $thead.empty();
    var $tbody = $('#report-table').find('tbody');
    var row='<tr>'
    + '<th scope="col">Brand</th>'
    + '<th scope="col">Category</th>'
    + '<th scope="col">Quantity</th>'
    + '</tr>'
    $thead.append(row);
    $tbody.empty();
    for(var i in data){
      var e = data[i];
      var row = '<tr>'
      + '<td>' + e.brand+ '</td>'
      + '<td>' + e.category + '</td>'
      + '<td>'  + e.quantity + '</td>'
      + '</tr>';
      $tbody.append(row);
    }
  }

  function getBrandList(){
    $('#sales-report-modal').modal('toggle');
    var url = "/pos/api/brand";
    $.ajax({
      url: url,
      type: 'GET',
      success: function(data) {
        createBrandData(data);
      },
      error: handleAjaxError
    });
  }

  function getInventoryReport(){
    var url = "/pos/api/inventoryreport";
    $.ajax({
      url: url,
      type: 'GET',
      success: function(data) {
        displayInventoryReport(data);
      },
      error: handleAjaxError
    });
  }

  function createBrandData(data)
  {
    var brandTemp="";
    for(var i in data){
      var e=data[i];
      brandTemp=e.brand;
    //console.log(brandTemp);
    if(!brandData.hasOwnProperty(brandTemp))
     brandData[brandTemp]={};
   brandData[brandTemp][e.category]=e.id;
   if(!categoryData.hasOwnProperty(e.category))
    categoryData[e.category]=1;
}
    //console.log(brandData);
    fillBrandDrop($('#selectBrand'));
    fillCategoryDropInit($('#selectCategory'))
  }

  function fillCategoryDropInit(selectbody){
    var $selectbody=selectbody;
    $selectbody.empty();
    $selectbody.append("<option>-- --</option>");
    for(var i in categoryData){
      var row="<option>"+i+"</option>";
      $selectbody.append(row);
    }
  }

  function fillBrandDrop(selectbody){
    var $selectbody=selectbody;
//console.log(brandData);

  for(var i in brandData){
    var row="<option>"+i+"</option>";
    $selectbody.append(row);
  } 
}

function fillCategoryDrop(selectbody,brandTemp){
  var $selectbody=selectbody;
  $selectbody.empty();
  $selectbody.append("<option>-- --</option>");
  for(var i in brandData[brandTemp]){
    var row="<option>"+i+"</option>";
    $selectbody.append(row);
  }
}

var brandTemp="";
$('#selectBrand').on('change',function(){
  brandTemp = $("#selectBrand option:selected").text();
  fillCategoryDrop($('#selectCategory'),brandTemp);
});

var categoryTemp="";
$('#selectCategory').on('change',function(){
  categoryTemp = $("#selectCategory option:selected").text();
});

function getBrandReport(){
  var url = "/pos/api/brand";
  $.ajax({
    url: url,
    type: 'GET',
    success: function(data) {
      displayBrandReport(data);  
    },
    error: handleAjaxError
  });
}
function displayBrandReport(data){
  var $thead= $('#report-table').find('thead');
  $thead.empty();
  var $tbody = $('#report-table').find('tbody');
  var row='<tr>'
  + '<th scope="col">Id</th>'
  + '<th scope="col">Brand</th>'
  + '<th scope="col">Category</th>'
  + '</tr>'
  $thead.append(row);
  $tbody.empty();
  for(var i in data){
    var e = data[i];
    var row = '<tr>'
    + '<td>' + e.id + '</td>'
    + '<td>' + e.brand + '</td>'
    + '<td>'  + e.category + '</td>'
    + '</tr>';
    $tbody.append(row);
  }
}
function init(){
  $('#generate-report').click(getReport);
  $('#sales-report').click(getBrandList);
  $('#inventory-report').click(getInventoryReport);
  $('#brand-report').click(getBrandReport);}

  $(document).ready(init);
