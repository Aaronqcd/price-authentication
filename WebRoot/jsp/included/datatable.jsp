<%@taglib uri="/struts-tags"  prefix="s"%>
<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

   	
   		<script>
   		
   			//获取参数
   			
   			var dataSource_${param.dataTableId} = '${param.dataSource}';  //访问url 			
   			var initOnload_${param.dataTableId} = ${param.initOnload};    //是否第一次访问table时加载数据
   			
   			var iDisplayLength_${param.dataTableId} = <%=request.getParameter("iDisplayLength")!=null?request.getParameter("iDisplayLength"):10%>; //每页记录数
   			var tableTitle_${param.dataTableId} = ${param.tableTitle}; //tableId 防止同一页面id重复
   			// 是否允许table排序，没有参数默认允许排序，需要屏蔽排序功能，增加参数：<s:param name="isSortable">false</s:param>
   			var sortable_${param.dataTableId} = true;
   			<%if(request.getParameter("isSortable")!=null){%>
   				if(${param.isSortable} == false){
   					sortable_${param.dataTableId} = false;
   				}
   			<%}%>
   			
   			var oTable_${param.dataTableId} = null;
			$(document).ready(function() {
				<%if(request.getParameter("searchButton")!=null){%>		
				$('#${param.searchButton}').click(doSearch_${param.dataTableId});
				<%}%>
				if(initOnload_${param.dataTableId})				
					doSearch_${param.dataTableId}();
			});
			
		function doSearch_${param.dataTableId}(){
		
			<%if(request.getParameter("beforeDoSearch")!=null){%>		
				${param.beforeDoSearch}
			<%}%>
					
			if (oTable_${param.dataTableId} == null) { //仅第一次检索时初始化Datatable  
					$('#${param.dataTableId}').show();         			
	        		oTable_${param.dataTableId} = $('#${param.dataTableId}').dataTable( {  
	            		"bAutoWidth": false,                    //不自动计算列宽度  
	            		"aoColumns": tableTitle_${param.dataTableId},
	            		"fnDrawCallback": fnDrawCallback,
	           			"bProcessing": true,                    //加载数据时显示正在加载信息  
	           			"bSort":sortable_${param.dataTableId},	// 是否允许table排序，没有参数默认允许排序
	            		"bServerSide": true,                    //指定从服务器端获取数据  
	            		"bFilter": false,                       //不使用过滤功能  
	            		"bLengthChange": false,                 //用户不可改变每页显示数量  
	            		"iDisplayLength": iDisplayLength_${param.dataTableId},                    //每页显示10条数据  
	            		"sAjaxSource": dataSource_${param.dataTableId},//获取数据的url  
	            		"fnServerData": retrieveData_${param.dataTableId},           //获取数据的处理函数  
	            		"sPaginationType": "full_numbers",      //翻页界面类型  
	            		"oLanguage": {                          //汉化  
	                		"sLengthMenu": "每页显示 _MENU_ 条记录",  
	                		"sZeroRecords": "没有检索到数据",  
	                		"sInfo": "当前数据为从第 _START_ 到第 _END_ 条数据；总共有 _TOTAL_ 条记录",  
	                		"sInfoEmtpy": "没有数据",  
	                		"sProcessing": "正在加载数据...",  
	                		"oPaginate": {  
	                    		"sFirst": "首页",  
	                    		"sPrevious": "前页",  
	                    		"sNext": "后页",  
	                    		"sLast": "尾页"  
	                		}  
	            		}  
	        	});
	        	
	   	 	} else{
	   	 	
	   	 		//刷新Datatable，会自动激发retrieveData  
    			oTable_${param.dataTableId}.fnDraw();
    		}
    		
    		<%if(request.getParameter("afterDoSearch")!=null){%>		
				${param.afterDoSearch}
			<%}%>
				
		}
		
		function reDraw_${param.dataTableId}(){
			//alert("redraw");
			doSearch_${param.dataTableId}();
			
		}
		
		function adjustColumnSizing_${param.dataTableId}(){
		
			oTable_${param.dataTableId}.fnAdjustColumnSizing();
		
		}
		
		function fnDrawCallback(){
			$("td:contains('审批驳回')").parent().addClass("datatablerowhighlight");
			$("td:contains('全市场')").parent().addClass("datatablerowhighlight");
			
		}
			
		
		function retrieveData_${param.dataTableId} ( sSource, aoData, fnCallback ) {
	        //将查询参数加入参数数组
	        var searchParams = ${param.searchParams};
	        aoData = aoData.concat(searchParams);  
	       
	       
      		var params = JSON.stringify(aoData);
      		//alert(params);
	        $.ajax( {  
	            "type": "post", 
	            "url": sSource,   
	            "dataType": "json",  
	            "data": aoData, //以json格式传递  
	            "success": function(resp) {
	            			
	            			
		    	            $("#${param.dataTableId} thead input[type=checkbox]").removeAttr("checked");	 	            	
		                	fnCallback(resp); //服务器端返回的对象的returnObject部分是要求的格式 
	                	 
	              }
	        });  
	    }	
			  
   
 		</script>
			
		
 			
	  			<div class="clear"></div>
		  		<table style="width: 100%" id="${param.dataTableId}">
					<thead class="table-head">
					</thead>
					<tbody>
					</tbody>
				</table>
			
