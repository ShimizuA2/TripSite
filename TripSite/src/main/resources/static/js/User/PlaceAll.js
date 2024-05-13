'use strict';

var user;
var all;
var allTags = new Array();
var prefectures = new Array();
var tags = new Array();

jQuery(function($){
	
	$(window).ready(function(){
		onLoad();
	});
	
	$(document).on('click','#unfavorite',function(event){
		favoriteAdd($(this));
	});
	
	$(document).on('click','#favorite',function(event){
		favoriteDelete($(this));
	});
	
	$(document).on('click','#addPrefecture',function(event){
		$(this).attr('id','deletePrefecture');
		addPrefecture($(this));
	});
	
	$(document).on('click','#deletePrefecture',function(event){
		$(this).attr('id','addPrefecture');
		deletePrefecture($(this));
	});
	
	$(document).on('click','#add',function(event){
		$(this).attr('id','delete');
		addTags($(this));
	});
	
	$(document).on('click','#delete',function(event){
		$(this).attr('id','add');
		deleteTags($(this));
	});
	
	$('#Search').click(function(event){
		Search();
	})
	
	$('#displayAll').click(function(event){
		displayAll();
	})
	
});

function onLoad() {

	$.ajax({
		type:"GET",
		url:'/restUser/LoginInfo',
	}).done(function(data){
		user = data
		const header = document.getElementById('header');
		if(user != "") {
			
			let booking = document.createElement("a");
			booking.setAttribute("href", "/User/Booking");
			booking.classList.add("nav-link","active","mx-3");
			booking.append("予約する");
			
			let logout = document.createElement("a");
			logout.setAttribute("href", "../logout");
			logout.classList.add("nav-link","active","mx-3");
			logout.append("ログアウト");
			
			header.append(booking,logout);
		}else{
			
			let login = document.createElement("a");
			login.setAttribute("href", "/User/Login");
			login.classList.add("nav-link","active","mx-3");
			login.append("ログイン");
			
			header.append(login);
		}
	})
	
	$.ajax({
		type:"GET",
		url:'/restAdmin/DefaultTags',
	}).done(function(data){
		allTags = data
		const feature = document.getElementById('feature');
		for(var i = 0; i < data.length; i++){
			var tag = data[i];
			feature.append(makeButton(tag.tag));
		}
	});
	
	$.ajax({
		type:"GET",
		url:'/restUser/PlaceAll',
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
	
	let nameAndHeart = document.createElement("div");
	nameAndHeart.classList.add("row","pt-2")
	
	let name = document.createElement("h5");
	name.classList.add("col-sm-11");
	name.append(place.name);
	
	let heartdiv = document.createElement("div")
	heartdiv.classList.add("col-sm-1","px-4");
	if(user != ""){
		let favoriteBtn=document.createElement("button");
		if(place.favorite == "yes") {
			favoriteBtn.setAttribute("id","favorite");
		}else{
			favoriteBtn.setAttribute("id","unfavorite");
		}
		favoriteBtn.setAttribute("name",place.placeid);
		favoriteBtn.style.border='none';
		favoriteBtn.style.background='none';
		
		let heart = document.createElement("i");
		if(place.favorite == "yes") {
			heart.classList.add("bi","bi-heart-fill","text-danger");
		}else{
			heart.classList.add("bi","bi-heart");
		}
		heart.setAttribute("id",place.placeid);
		heart.style.fontSize='1.5em';
		
		favoriteBtn.append(heart);
		heartdiv.append(favoriteBtn);
	}
	
	let address = document.createElement("div");
	address.classList.add("fw-light");
	address.append(place.address);
	
	let detail = document.createElement("div");
	detail.append(place.detail);
	
	nameAndHeart.append(name,heartdiv);
	text.append(nameAndHeart,address,detail);
	row.append(img,text);
	content.append(row);
	cards.append(content);
	
}

function favoriteAdd(favoriteBtn) {
	var placeid = {"placeid":favoriteBtn.attr('name')};
	$.ajax({
		type:"POST",
		url:'/restUser/FavoriteAdd',
		data:placeid,
		dataType:'text',
	}).done(function(){
		favoriteBtn.attr("id","favorite");
		const heart = document.getElementById(favoriteBtn.attr('name'));
		heart.classList.remove("bi-heart");
		heart.classList.add("bi-heart-fill","text-danger");
	});
}

function favoriteDelete(favoriteBtn) {
	var placeid = {"placeid":favoriteBtn.attr('name')};
	$.ajax({
		type:"POST",
		url:'/restUser/FavoriteDelete',
		data:placeid,
		dataType:'text',
	}).done(function(){
		favoriteBtn.attr("id","unfavorite");
		const heart = document.getElementById(favoriteBtn.attr('name'));
		heart.classList.remove("bi-heart-fill","text-danger");
		heart.classList.add("bi-heart");
	});
}

function addPrefecture(Prefecture) {
	prefectures.push(Prefecture.attr('name'));
	$('#inputPrefectures').val(prefectures);
	
	Prefecture.removeClass("btn-outline-secondary");
	Prefecture.addClass("btn-success");
}

function deletePrefecture(Prefecture) {
	prefectures.splice(prefectures.indexOf(Prefecture.attr('name')),1);
	$('#inputPrefectures').val(prefectures);
	
	Prefecture.removeClass("btn-success");
	Prefecture.addClass("btn-outline-secondary");
}

function addTags(tag) {
	tags.push(tag.attr('name'));
	$('#inputTags').val(tags);
	
	tag.removeClass("btn-outline-secondary");
	tag.addClass("btn-primary");
}

function deleteTags(tag) {
	tags.splice(tags.indexOf(tag.attr('name')),1);
	$('#inputTags').val(tags);
	
	tag.removeClass("btn-primary");
	tag.addClass("btn-outline-secondary");
}

function makeButton(name) {
	let button = document.createElement("button");
	button.setAttribute("id","add");
	button.setAttribute("name",name);
	button.classList.add("btn", "btn-outline-secondary","m-1");
	button.append(name);
	
	return button;
}

function Search() {
	$.ajax({
		type:"GET",
		url:'/restUser/PlaceSearch',
		data:$('#PlaceSearchForm').serialize(),
		datatype:'json',
	}).done(function(data){
		while(document.getElementById('placeCards').firstChild){
			document.getElementById('placeCards').removeChild(document.getElementById('placeCards').firstChild);
		}
		for(var i = 0; i < data.length; i++){
			makeCard(data[i]);
		}
	})
}

function displayAll() {
	if(prefectures.length > 0) {
		for(var i = 0; i < prefectures.length; i++) {
			var Prefecture = document.getElementsByName(prefectures[i]).item(0);
			Prefecture.setAttribute("id","addPrefecture");
			Prefecture.classList.remove("btn-success");
			Prefecture.classList.add("btn-outline-secondary");
		}
		prefectures.length = 0;
		$('#inputPrefectures').val(prefectures);
	}
	if(tags.length > 0) {
		for(var i = 0; i < tags.length; i++) {
			var tag = document.getElementsByName(tags[i]).item(0);
			tag.setAttribute("id","add");
			tag.classList.remove("btn-primary");
			tag.classList.add("btn-outline-secondary");
		}
		tags.length = 0;
		$('#inputTags').val(tags);
	}
	while(document.getElementById('placeCards').firstChild){
		document.getElementById('placeCards').removeChild(document.getElementById('placeCards').firstChild);
	}
	for(var i = 0; i < all.length; i++){
		makeCard(all[i]);
	}
}