'use strict';

var all = new Array();

jQuery(function($){
	
	$(window).ready(function(){
		onLoad();
	});
});

function onLoad() {
	
	$.ajax({
		type:"GET",
		url:'/restAdmin/BookingAll',
	}).done(function(data){
		all = data;
		
		const tbody = document.getElementById("tbody");
		
		for(var i = 0; i < all.length; i++) {
			var booking = all[i];
			makeBody(tbody,booking);
		}
	})
}

function makeBody(tbody,booking){
	
	let tr = document.createElement("tr");
	
	let id = document.createElement("td");
	id.append(booking.bookingid);
	
	let name = document.createElement("td");
	name.append(booking.name);
	
	let startDay = document.createElement("td");
	var start = new Date(booking.startDay); 
	startDay.append(start.toLocaleDateString());
	
	let finishDay = document.createElement("td");
	var finish = new Date(booking.finishDay); 
	finishDay.append(finish.toLocaleDateString());
	
	let customer = document.createElement("td");
	customer.append(booking.customer);
	
	let status = document.createElement("td");
	status.append(booking.status);
	
	let detail = document.createElement("td");
	let a = document.createElement("a");
	var url = '/Admin/BookingDetail/' + booking.bookingid;
	a.setAttribute('href',url);
	a.classList.add("btn", "btn-sm", "bg-primary", "text-decoration-none", "link-light");
	a.append("詳細");
	detail.append(a);
	
	tr.append(id,name,startDay,finishDay,customer,status,detail);
	tbody.append(tr);
	
}
