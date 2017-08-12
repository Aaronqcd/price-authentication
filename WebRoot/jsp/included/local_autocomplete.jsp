
 	
 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.6.2.min.js"></script>
 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.8.16.custom.min.js"></script>
 	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/smoothness/jquery-ui-1.8.16.custom.css" media="screen" />
	<script type="text/javascript">
            
          
		  $(document).ready(function(){
          		//$("#loading").hide();
				
			
	          	var autocomplete = $( "#${param.id}" ).autocomplete({
					source: ${param.items},
					minLength: 2
					
				});
				
				autocomplete.bind( "input.autocomplete", function(){
		             $(this).trigger('keydown.autocomplete');
				});
								
          	}
          )
            
        </script>
		<input type="text" id="${param.id}" value="">	
		

