   		jQuery(document).ready(function(){
			    
				//页面中input的class属性通过addClass方式加载，校验通过validate
				$("#excution").addClass("validate[required],custom[onlyChinese] text-input");
				$("#salesman").addClass("validate[required],custom[onlyChinese] text-input");
             	$("#acptng_type").addClass("validate[required] text-input");
             	$("#rmng_prd").addClass("validate[required] text-input");
             	$("#orgnl_prd").addClass("validate[required] text-input");
             	$("#bill_rate").addClass("validate[required] text-input");
             	$("#bill_amnt").addClass("validate[required] text-input");
				$("#orgnl_prd").addClass("validate[required] text-input");
			
                jQuery("#billQuote").validationEngine();
				
				//对手机构名称显示和隐藏
				$("#shrt_radio").click(function testquote(){
						
    				var pointType = $('input:radio[name="shrt_radio"]:checked').val();
					
    				if(pointType =='A'){
    					$("#org_cn").hide();
    				}else{
    					$("#org_cn").show();
    				}
				});
				
				 //报价信息重置
				$("#resetQuote").click(function resetQuote(){
					alert("========报价重置  begin=========");
				$("#excution").attr("value","");
				$("#bill_amnt").attr("value","");
				$("#salesman").attr("value","");
				$("#rmng_prd").attr("value","");
				$("#orgnl_prd").attr("value","");
				$("#bill_rate").attr("value","");
				$("#bill_amnt").attr("value","");
			    
				});
			
				
			    function createQuote(){
    				alert("========报价发送  begin=========");
					
					var billType;
					var dealType;
					var ownrshpType;
					var excution;
					var salesman;
					var txn_dir;
					var pointType;
					var orgFullName;
					var remainPeriod;
					var acptng_type;
					var orgnl_prd;
					var interest;
					var amout;
					var hndvrtype;
					var rcrsrgt;
				    
				    dealType = 'E';//票据介质 P 纸票 E 电票
					ownrshpType = 'B';
				    excution = $("#excution").val();
				    salesman = $("#salesman").val();
					billType = $('input:radio[name="bill_radio"]:checked').val();			
					txn_dir =$('input:radio[name="trans_radio"]:checked').val();
					pointType = $('input:radio[name="shrt_radio"]:checked').val(); 
					acptng_type = $("#acptng_type").val();
					remainPeriod = $("#rmng_prd").val();
					orgnl_prd = $("#orgnl_prd").val();
					interest = $("#bill_rate").val();
					amout = $("#bill_amnt").val();
					hndvrtype =$('input:radio[name="hndvr_radio"]:checked').val();
					rcrsrgt = $('input:radio[name="rcrs_radio"]:checked').val();
					
					$.ajax({
						type:"POST",
						url:"${pageContext.request.contextPath}/billquote/createQuote",
						data:{
							"dealType":dealType,
							"ownrshpType":ownrshpType,
							"billType":billType,
							"txn_dir":txn_dir,
							"pointType":pointType,
							"acptng_type":acptng_type,
							"remainPeriod":remainPeriod,
							"orgnl_prd":orgnl_prd,
							"interest":interest,
							"amout":amout,
							"hndvrtype":hndvrtype,
							"rcrsrgt":rcrsrgt
						   },
					    datatype:"json",
						success:callback
					}); 
				}
								
				
				 function callback(data){
				 		alert(data);
					   if( data == 'sucess'){
					   	alert("报价发送成功！");
					  }
					 }
				
                $("#submitQuote").click(createQuote);
			
		});
		
			
