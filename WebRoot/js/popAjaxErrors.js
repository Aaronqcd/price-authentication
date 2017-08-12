					function messageError(message){
					var errorDiv = "<div id='errorMessageDiv'>"+message+"</div>";
					
					
					
					$(errorDiv).dialog(
						{
							modal:true,
							title:"提示",
							draggable:false
							
						}
					);
				}
					function popAjaxErrors(result){
										if (result.message == "error") {
											var message = "<ul>";
											//添加actionErrors
											$.each(result.actionErrors, function(i, n){
											
												message += "<li>" + n + "</li>";
												
											});
											//添加fieldErrors
											$.each(result.fieldErrors, function(key, value){
											
												message += "<li>" + key + ":" + value + "</li>";
												
											});
											
											message += "</ul>";
											
											messageError(message);
										}			
				
				}
				
				function checkSession(result, contextPath, dataType){
					if(!_checkNoLogin(result,dataType)){
						popLoginDiv(contextPath);
						return false;
					}
					else if(!_checkNoRight(result,dataType)){
						$("#workpanel").load(contextPath+"/jsp/common/login_not_right_div.jsp");
						
					}
						
					return true;
					
				}
				
				function _checkNoLogin(result,dataType){
					if (dataType && dataType == "html") {
						if (result == "sessionLost") {
							return false;
						}
					}else {
						if (!dataType || dataType == "json") {
							if (result.message && result.message == "sessionLost") {								
								return false;
							}
						}
					}
					
					return true;
					
						
				}
				
				function _checkNoRight(result,dataType){
					if (dataType && dataType == "html") {
						if (result == "noRight") {
							return false;
						}
					}else {
						if (!dataType || dataType == "json") {
							if (result.message && result.message == "noRight") {
								return false;
							}
						}
					}
					
					return true;
					
						
				}
				
				
				function popLoginDiv(contextPath){
					var url = contextPath+"/login/toLogin.action";
					//window.open(url,"sessionOut");
					window.location.href = url;
				}
				
				function clearIntervalIfSessionLost(result,dataType,intervalIdArr){
					if(!_checkNoLogin(result,dataType)){
						$.each(intervalIdArr,function(i,n){
							window.clearInterval(n);
						
							
						});
						return false;	
					
					}
					
					return true;
					
				}
				
				
