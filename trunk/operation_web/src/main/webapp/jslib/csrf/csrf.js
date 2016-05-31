$(function () {
	var token = $('meta[property=csrf]').attr('csrf-token');
	var paramName = $('meta[property=csrf]').attr('csrf-param');
	var headerName = $('meta[property=csrf]').attr('csrf-header');
	// 遍历form 给post方式 添加csrfToken
	$('form').each(function(index, el) {
		var method = $(el).attr('method');
		if(method.toLowerCase() == 'post'){
			var enctype = $(el).attr('enctype');
			// form-data 方式token拼接在action后
			if(enctype == 'multipart/form-data'){
				var oldAction = $(el).attr('action');
				$(el).attr('action', oldAction + "?" + paramName + "=" + token);
			} else {
				$(el).append("<input type='hidden' id='_csrf' name='" +paramName+"' value='"+token+"'>");
			}
		}
	});
	// ajax 加上token
	$(document).ajaxSend(function(event, xhr, settings) {
		xhr.setRequestHeader(headerName, token);
	});
})