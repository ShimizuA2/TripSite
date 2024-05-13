'use strict';

jQuery(function($){
	
	$('#btnUpdate').click(function(event){
		userUpdate();
	});
	
	$('#btnDelete').click(function(event){
		userDelete();
	});
	
});

function userUpdate() {
	
	removeValidResult();
	
	var user = $('#UserAddForm').serialize();
	
	$.ajax({
		type:"POST",
		url:'/restAdmin/UserUpdate',
		data:user,
		datatype:'json',
	}).done(function(data){
		if(data.result === 90) {
			$.each(data.errors,function(key,value){
				reflectValidResult(key,value);
			});
		}else if(data.result === 0){
			alert('ユーザー情報を更新しました。');
			window.location.href='/Admin/UserAll'
		}
	})
}

function removeValidResult() {
	$('.form-control').removeClass('is-invalid');
	$('.invalid-feedback').remove();
}

function reflectValidResult(key,value) {
	$('input[id=' + key + ']').addClass('is-invalid');
	$('input[id=' + key + ']').after('<div class="invalid-feedback">' + value + '</div>');
}

function userDelete(){
	
	var user = $('#UserAddForm').serialize();
	
	$.ajax({
		type:"POST",
		url:'/restAdmin/UserDelete',
		data:user,
		datatype:'json',
	}).done(function(){
		alert('ユーザーを削除しました。');
		window.location.href='/Admin/UserAll'
	})
	
}