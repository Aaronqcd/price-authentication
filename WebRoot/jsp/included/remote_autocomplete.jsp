

<script type="text/javascript">
            
          
		  $(document).ready(function(){
          		//$("#loading").hide();
				
				           		
          		var cache = {},
				lastXhr;
	          	var autocomplete = $( "#${param.id}" ).autocomplete({
					source: function( request, response ) {
						var term = request.term;
						if ( term in cache ) {
							response( cache[ term ] );
							return;
						}
		
						lastXhr = $.getJSON( "${param.actionUrl}", request, function( data, status, xhr ) {
							cache[ term ] = data;
							if ( xhr === lastXhr ) {
								response( data);
							}
						});
					},
					minLength: 1
					
				});
				
				autocomplete.bind( "input.autocomplete", function(){
		             $(this).trigger('keydown.autocomplete');
				});
				
				
				<%if(request.getParameter("callbackOnSelect")!=null){%>		
					autocomplete.bind("autocompleteselect",${param.callbackOnSelect});	
				<%}%>
				
								
          	}
          )
            
        </script>
<input type="text" id="${param.id}" value="${param.val}"
	name="${param.nm}"
	<%if(request.getParameter("readonly")!=null&&request.getParameter("readonly").equals("readonly")){%>
	readonly="readonly" <%} %>
	<%if(request.getParameter("iClass")!=null){%>
	class="<%=request.getParameter("iClass")%>" <%} %>>


