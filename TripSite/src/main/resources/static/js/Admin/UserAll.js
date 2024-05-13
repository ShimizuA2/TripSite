'use strict';

var all = new Array();

jQuery(function($){
	
	$(window).ready(function(){
		onLoad();
	});
	
	$(document).on('click','#searchBtn',function(event){
		search();
	});
	
	$(document).on('click','#allBtn',function(event){
		displayAll();
	});
});

function onLoad() {
	
	$.ajax({
		type:"GET",
		url:'/restAdmin/UserAll',
	}).done(function(data){
		all = data;
		
		for(var i = 0; i < all.length; i++) {
			var user = all[i];
			makeBody(user);
		}
	})
}

function makeBody(user){
	
	const tbody = document.getElementById("tbody");
	
	let tr = document.createElement("tr");
	
	let id = document.createElement("td");
	id.append(user.userid);
	
	let name = document.createElement("td");
	name.append(user.name);
	
	let mail = document.createElement("td");
	mail.append(user.mail);
	
	let role = document.createElement("td");
	if(user.role == 1){
		role.append("社員");
	}else{
		role.append("クライアント");
	}
	
	let edit = document.createElement("td");
	let a = document.createElement("a");
	var url = '/Admin/UserEdit/' + user.userid;
	a.setAttribute('href',url);
	a.classList.add("btn", "btn-sm", "bg-primary", "text-decoration-none", "link-light");
	a.append("編集");
	edit.append(a);
	
	tr.append(id,name,mail,role,edit);
	tbody.append(tr);
	
}

function search() {
	
	while(document.getElementById('tbody').firstChild){
		document.getElementById('tbody').removeChild(document.getElementById('tbody').firstChild);
	}
	
	var word = $('#search').val();
	for(var i = 0; i < all.length; i++){
		if(all[i].name.includes(word)){
			makeBody(all[i]);
		}
	}
}

function displayAll() {
	
	while(document.getElementById('tbody').firstChild){
		document.getElementById('tbody').removeChild(document.getElementById('tbody').firstChild);
	}
	
	$('#search').val("");
	for(var i = 0; i < all.length; i++){
		makeBody(all[i]);
	}
}