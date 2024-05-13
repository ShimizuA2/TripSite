'use strict';

var places = new Array();
var low = new Array();
var normal = new Array();
var high = new Array();

jQuery(function($){
	
	$(window).ready(function(){
		onLoad();
	});
	
	$('#startDay').on('change',function(event){
		document.getElementById("finishDay").setAttribute("min",$('#startDay').val());
	});
	
	$('#finishDay').on('change',function(event){
		document.getElementById("startDay").setAttribute("max",$('#finishDay').val());
	});
	
	$(document).on('click','#toLow',function(event){
		var placeid=Number($(this).attr('name'));
		document.getElementById(placeid).remove();
		normal.splice(normal.indexOf(placeid),1);
		makePlace(placeid,"low");
	});
	
	$(document).on('click','#lowToNormal',function(event){
		var placeid=Number($(this).attr('name'));
		document.getElementById(placeid).remove();
		low.splice(low.indexOf(placeid),1);
		makePlace(placeid,"normal");
	});
	
	$(document).on('click','#highToNormal',function(event){
		var placeid=Number($(this).attr('name'));
		document.getElementById(placeid).remove();
		high.splice(high.indexOf(placeid),1);
		makePlace(placeid,"normal");
	});
	
	$(document).on('click','#toHigh',function(event){
		var placeid=Number($(this).attr('name'));
		document.getElementById(placeid).remove();
		normal.splice(normal.indexOf(placeid),1);
		makePlace(placeid,"high");
	});
	
	$(document).on('click','#BookingBtn',function(event){
		booking();
	})
	
});

function onLoad(){
	
	$.ajax({
		type:"GET",
		url:'/restUser/FavoriteAll',
	}).done(function(data){
		places = data;
		for(var i = 0; i < places.length; i++){
			var place = places[i];
			makePlace(place.placeid,"normal");
		}
		
		var getToday = new Date();
		var y = getToday.getFullYear();
		var m = getToday.getMonth() + 1;
		var d = getToday.getDate();
		var today = y + "-" + m.toString().padStart(2,'0') + "-" + d.toString().padStart(2,'0');
		document.getElementById("startDay").setAttribute("min",today);
		document.getElementById("finishDay").setAttribute("min",today);
	});
}

function makePlace(placeid,toWhere){
	
	if(toWhere=="low"){
		low.push(placeid);
	}else if(toWhere=="normal"){
		normal.push(placeid);
	}else if(toWhere=="high"){
		high.push(placeid);
	}
	
	const where = document.getElementById(toWhere);
	
	var place = places.find(place => place.placeid === placeid);
	
	let container = document.createElement("div");
	container.classList.add("container-fluid","border","border-4","rounded");
	container.setAttribute("id",place.placeid);
	
	let row = document.createElement("div");
	row.classList.add("row");
	
	
	let leftBtn = document.createElement("button");
	leftBtn.classList.add("btn","col-sm-2");
	if(toWhere!="low"){
		if(toWhere=="normal"){
			leftBtn.setAttribute("id","toLow");
		}else if(toWhere=="high"){
			leftBtn.setAttribute("id","highToNormal");
		}
		leftBtn.setAttribute("name",place.placeid);
		leftBtn.style.border='none';
		leftBtn.append("<<");
	}else{
		leftBtn.setAttribute("data-toggle","collapse");
	};
	
	let img = document.createElement("img");
	img.classList.add("col-sm-2");
	img.setAttribute('src',place.picture);
	
	let content = document.createElement("div");
	content.classList.add("col-sm-6","my-1");
	content.append(place.name);
	
	let rightBtn = document.createElement("button");
	rightBtn.classList.add("btn","col-sm-2");
	if(toWhere!="high"){
		if(toWhere=="low"){
			rightBtn.setAttribute("id","lowToNormal");
		}else if(toWhere=="normal"){
			rightBtn.setAttribute("id","toHigh");
		}
		rightBtn.setAttribute("name",place.placeid);
		rightBtn.style.border='none';
		rightBtn.append(">>");
	}else{
		rightBtn.setAttribute("data-toggle","collapse");
	}
	
	row.append(leftBtn,img,content,rightBtn);
	container.append(row);
	where.append(container);
	
}

function booking() {
	
	$('#inputLow').val(low);
	$('#inputNormal').val(normal);
	$('#inputHigh').val(high);
	
	removeValidResult();
	
	var bookingContent = $('#BookingForm').serialize();
	
	$.ajax({
		type:"POST",
		url:'/restUser/BookingAdd',
		data:bookingContent,
		datatype:'json',
	}).done(function(data){
		if(data.result === 90) {
			$.each(data.errors,function(key,value){
				reflectValidResult(key,value);
			});
		}else if(data.result === 0){
			alert('予約が完了しました。');
			window.location.href='/User/PlaceAll'
		}
	})
}

function removeValidResult() {
	$('.form-control').removeClass('is-invalid');
	$('.invalid-feedback').remove();
}

function reflectValidResult(key,value) {
	$('input[id=' + key + ']').addClass('is-invalid');
	$('input[id=' + key + ']').after('<div class="invalid-feedback text-start">' + value + '</div>');
}