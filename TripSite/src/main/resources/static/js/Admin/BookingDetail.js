'use strict';

jQuery(function($){
	
	$(window).ready(function(){
		onLoad();
	});
	
	$('#statusBtn').click(function(event){
		$('#statusPopup').addClass('show').fadeIn();
	});
	
	$('#not-finish').click(function(event){
		changeStatus("未完了");
		updateBooking();
	})
	
	$('#finish').click(function(event){
		changeStatus("完了");
		updateBooking();
	})
	
	$('#customerBtn').click(function(event){
		$('#customerPopup').addClass('show').fadeIn();
	});
	
	$(document).on('click','#changeCustomer',function(event){
		var customer=$(this).attr('name');
		$('#inputCustomer').val(customer);
		$('#customer').text(customer);
		updateBooking();
	});
	
	$('#closeStatus').on('click',function(){
	    $('.popup').fadeOut();
	});
	
	$('#closeCustomer').on('click',function(){
	    $('.popup').fadeOut();
	});
	
});

function onLoad() {
	
	$.ajax({
		type:"GET",
		url:'/restAdmin/GetCustomers',
	}).done(function(data){
		const customers = document.getElementById("customers");
		
		for(var i = 0; i < data.length; i++){
			var customer = data[i];
			
			let button = document.createElement("button");
			button.classList.add("btn", "btn-sm", "btn-primary","w-50","m-2");
			button.setAttribute("id","changeCustomer");
			button.setAttribute("name",customer.name);
			button.append(customer.name);
			customers.append(button);
		}
	})
}

function changeStatus(status) {
	
	$('#inputStatus').val(status);
	$('#status').text(status);
	
}

function updateBooking() {
	
	var form = $('#bookingDisplayForm').serialize();
	
	$.ajax({
		type:"POST",
		url:'/restAdmin/UpdateBooking',
		data:form,
		datatype:'json',
	}).done(function(){
		$('.popup').fadeOut();
	})
	
}