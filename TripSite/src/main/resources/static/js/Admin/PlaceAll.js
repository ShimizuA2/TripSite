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
		url:'/restAdmin/PlaceAll',
	}).done(function(data){
		all = data;
		for(var i = 0; i < data.length; i++) {
			makeCard(data[i]);
		}
	})
}

function makeCard(place) {
	
	const cards = document.getElementById('placeCards');
	
	let content = document.createElement("div");
	content.classList.add("container-fluid");
	
	let row = document.createElement("div");
	row.classList.add("row","border","border-4","rounded","m-2")
	
	let img = document.createElement("img");
	img.classList.add("col-sm-3","my-1")
	img.setAttribute('src',place.picture);
	
	let text = document.createElement("div");
	text.classList.add("col-sm-9","container-fluid");
	
	let name = document.createElement("h5");
	name.append(place.name);
	
	let address = document.createElement("div");
	address.classList.add("fw-light");
	address.append(place.address);
	
	let detail = document.createElement("div");
	detail.append(place.detail);
	text.append(name,address,detail);
	row.append(img,text);
	content.append(row);
	cards.append(content);
	
}

function search() {
	
	while(document.getElementById('placeCards').firstChild){
		document.getElementById('placeCards').removeChild(document.getElementById('placeCards').firstChild);
	}
	
	var word = $('#search').val();
	for(var i = 0; i < all.length; i++){
		if(all[i].name.includes(word)){
			makeCard(all[i]);
		}
	}
}

function displayAll() {
	
	while(document.getElementById('placeCards').firstChild){
		document.getElementById('placeCards').removeChild(document.getElementById('placeCards').firstChild);
	}
	
	$('#search').val("");
	for(var i = 0; i < all.length; i++){
		makeCard(all[i]);
	}
}