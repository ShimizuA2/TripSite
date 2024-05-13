'use strict';

jQuery(function($){
	
	$(window).ready(function(){
		onLoad();
	});
	
	$('#btn').click(function(event){
		add();
	});
});

function onLoad(){
	
	$.ajax({
		type:"GET",
		url:'/restAdmin/DefaultTags',
	}).done(function(data){
		const nowTags = document.getElementById('nowTags');
		for(var i = 0; i < data.length; i++){
			let span = document.createElement("span");
			span.classList.add("badge" ,"bg-success","mx-2");
			span.append(data[i].tag);
			nowTags.append(span);			
		}
	});
}

function add() {
	
	removeValidResult();
	
	if($('#tag').val() == "") {
		reflectValidResult("tag","タグ名を入力してください")
	}else{	
		var tag = $('#form').serialize();
		
		$.ajax({
			type:"POST",
			url:'/restAdmin/TagAdd',
			data:tag,
			datatype:'json',
		}).done(function(){
			alert('タグを追加しました。');
			window.location.href='/Admin/TagAdd'
		})
	}
}

function removeValidResult() {
	$('.form-control').removeClass('is-invalid');
	$('.invalid-feedback').remove();
}

function reflectValidResult(key,value) {
	$('input[id=' + key + ']').addClass('is-invalid');
	$('input[id=' + key + ']').after('<div class="invalid-feedback">' + value + '</div>');
}