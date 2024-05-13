'use strict';

var tags = new Array();
var prefectures = new Array();

jQuery(function($){
	
	$(window).ready(function(){
		onLoad();
	});
	
	$('#inputPicture').on('change',function(event){
		displayImg();
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
	
	$('#btnAdd').click(function(event){
		placeAdd();
	});
});

function onLoad(){
	
	$.ajax({
		type:"GET",
		url:'/restAdmin/DefaultTags',
	}).done(function(data){
		const feature = document.getElementById('feature');
		for(var i = 0; i < data.length; i++){
			var tag = data[i];
			feature.append(makeButton(tag.tag));
		}
	});
}

function makeButton(name) {
	let button = document.createElement("button");
	button.setAttribute("id","add");
	button.setAttribute("name",name);
	button.classList.add("btn", "btn-outline-secondary","m-1");
	button.append(name);
	
	return button;
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

async function displayImg(){
	
	const uploadImage = document.querySelector('#inputPicture');
	let file = uploadImage.files[0];
	var fileReader = new FileReader();
	fileReader.onload = function() {
		var imageUrl = this.result;
		document.getElementById('pictureImg').src = imageUrl;
	}
	fileReader.readAsDataURL(file);
};

function placeAdd(){
	 alert('観光地を登録します');
}