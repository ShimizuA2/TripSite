'use strict';

jQuery(function($){
	
	$('#btnAdd').click(function(event){
		add();
	});
});

function add() {
	
	removeValidResult();
	
	var user = $('#UserAddForm').serialize();
	
	$.ajax({
		type:"POST",
		url:'/restAdmin/CustomerAdd',
		data:user,
		datatype:'json',
	}).done(function(data){
		
		if(data.result === 90) {
			$.each(data.errors,function(key,value){
				reflectValidResult(key,value);
			});
		}else if(data.result === 0){
			alert('社員登録が完了しました。');
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