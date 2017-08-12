function autoComplete($item){
			$($item).autocomplete({
				source: function(request,response){
					$.ajax({
						url:'${pageContext.request.contextPath}/autoComplete/autoCompleteAction.action',
						type:'post',
						data:data,
						dataType:'json',
						success: function(data){
							response(data);
						}
					});
				},
				minLength:1,
				position:{my:'left top',at:'left bottom'},
				delay:600
			});
}