<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
  <head>   
    <title>价格鉴定</title>
   	 <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<jsp:include page="/jsp/included/all_js.jsp"></jsp:include>
		<jsp:include page="/jsp/included/all_css.jsp"></jsp:include>
		<script type="text/javascript">
		//得到焦点失去焦点
		function addonfous(){
				//数量
				$('#qnty').focus(function(){
        			if('数量'==$('#qnty').val()){
        				$('#qnty').val('');
        			}
        		});
        		$('#qnty').blur(function(){
        			if(''==$('#qnty').val()){
        				$('#qnty').val('数量');
        			}
        		});
        		if(''==$('#qnty').val()){
       				$('#qnty').val('数量');
       			}
        		//单位
        		$('#unit').focus(function(){
        			if('单位'==$('#unit').val()){
        				$('#unit').val('');
        			}
        		});
        		$('#unit').blur(function(){
        			if(''==$('#unit').val()){
        				$('#unit').val('单位');
        			}
        		});
        		if(''==$('#unit').val()){
       				$('#unit').val('单位');
       			}
        		//品牌名称
        		$('#brndNm').focus(function(){
        			if('品牌名称'==$('#brndNm').val()){
        				$('#brndNm').val('');
        			}
        		});
        		$('#brndNm').blur(function(){
        			if(''==$('#brndNm').val()){
        				$('#brndNm').val('品牌名称');
        			}
        		});
        		if(''==$('#brndNm').val()){
        				$('#brndNm').val('品牌名称');
        		}
        		//型号规格
        		$('#specTp').focus(function(){
        			if('型号规格'==$('#specTp').val()){
        				$('#specTp').val('');
        			}
        		});
        		$('#specTp').blur(function(){
        			if(''==$('#specTp').val()){
        				$('#specTp').val('型号规格');
        			}
        		});
        		if(''==$('#specTp').val()){
        				$('#specTp').val('型号规格');
        			}
        		//物品名称
        		$('#artclNm').focus(function(){
        			if('物品名称'==$('#artclNm').val()){
        				$('#artclNm').val('');
        			}
        		});
        		$('#artclNm').blur(function(){
        			if(''==$('#artclNm').val()){
        				$('#artclNm').val('物品名称');
        			}
        		});
        		if(''==$('#artclNm').val()){
        				$('#artclNm').val('物品名称');
        			}
		}
			//选择物品
			function selectcert(id){
			$.ajax({
	            url: "${pageContext.request.contextPath}/aprisl/selectAprisl.action",
	            cache:false,
	            dataType:"html",
	            //data:{"leftMenuSrno":srnoid},
	            data:{"aprislDto.id":id},
	            success: function(html){
	            	$("#aprisl").html('').html(html);
	            }
	        });
			}
			//确定按钮
			function updateCert(){
			if('数量'==$('#qnty').val()){
        				$('#qnty').val('');
        			}
			if('单位'==$('#unit').val()){
        				$('#unit').val('');
        			}
			if('品牌名称'==$('#brndNm').val()){
        				$('#brndNm').val('');
        			}
			if('型号规格'==$('#specTp').val()){
        				$('#specTp').val('');
        			}
			if('物品名称'==$('#artclNm').val()){
        				$('#artclNm').val('');
        			}
				if (!jQuery("#aprislform").validationEngine('validate')) {return;}
 							$("#kouchufeiyong").remove();
 							var exp_tableData = new Array();
 							var exp_postArr= [];
 							var exp_index = 0;
 							var exp_tableall = $("#exp_Tbl");
 							var totalAmnt = 0;
						 	$(exp_tableall).find("tr").each(function(trindex,tritem){
								var stchk = 0;
								//trindex   行数
								if(trindex != 0 && this.style.display != 'none'){
									$("#exp_Tbl tr:eq("+trindex+")").find("td").each(function(tdindex,tditem){
										//列内容
										exp_tableData[tdindex] = new Array();
										exp_tableData[tdindex] = $(tditem).text();
									  	stchk++;
									});
								}
							
								if(stchk!=0){
									exp_postArr[exp_index]={"name":exp_tableData[1],"remark":exp_tableData[2],"valutnAmnt":exp_tableData[3]};
									if(exp_tableData[3] != null){
										totalAmnt = Number(exp_tableData[3])+ totalAmnt;
									}else{
										totalAmnt = Number(exp_tableData[3]) + 0;
									}
									exp_index++;
								}
							});
							/*计算总价*/
							var qnty = document.getElementById("qnty").value;
							var orgnlPrc = 0;
							var newRate = 100;
							if(undefined != document.getElementById("orgnlPrc")){
							 	orgnlPrc = document.getElementById("orgnlPrc").value;
							}
							/*鉴定价格默认=（市场价格-配件）*数量*成新率，允许修改*/
							if(undefined != document.getElementById("newRate") && document.getElementById("newRate").value!=''){
							 	newRate = document.getElementById("newRate").value;
							}
							var aprislPrcV  = 0;
							if(orgnlPrc>0){
								aprislPrcV  = (orgnlPrc-totalAmnt) * qnty * newRate /100;
							}
							if(undefined != document.getElementById("aprislPrc") && $.trim(document.getElementById("aprislPrc").value)==''){
								document.getElementById("aprislPrc").value = aprislPrcV;
							}
						  var exp_postData = exp_postArr;
						  var expData = JSON.stringify(exp_postData);
						  $("#expData").val(expData);
						  var params=$('#aprislform').serialize();
						  //params = params.replace("%","%25");
						$.ajax({
							type:"POST",
							url:"${pageContext.request.contextPath}/aprisl/updateAprisl.action?"+"&tm="+new Date().getTime(),
					      	data:params,
					        dataType:"json",
						    success:function callback(){
								 window.opener.afterCreateAprisl();
								 closeBut();
							 },error:function(a){alert("保存失败！");}
						}); 
			}
			
			function closeBut() {
				window.close();
			}
			selectcert(<s:property value="aprislDto.id"/>);
			
			//添加参考价格后执行
			function addPrfrmc(params){
				$.ajax({
		            url: "${pageContext.request.contextPath}/aprisl/getPrcList.action",
		            cache:false,
		            dataType:"html",
		            //data:{"leftMenuSrno":srnoid},
		            data: params,
		            success: function(html){
		            	$("#prfrncDiv").html('').html(html);
		            }
		        });
			}
			
			//四舍五入	
			function sswr(){
				$("#aprislPrc").val(Math.round($("#aprislPrc").val()));
			}
			
			//定方法判断
			function checkPrcAprislMtd() {
	        	var checkValue = $("#prcAprislMtd").val();
				if(checkValue == "001"){
					return "请选择鉴定方法";
				}else{
					return ;
				}
	        }
		</script>
  </head>
  <body>
    <form id="aprislform" name="aprislform" action="" method="post">
		<s:if test="aprislDto.tmpCmsId != null ">
			<s:hidden name="aprislDto.tmpCmsId" id="tmpCmsId"/>
		</s:if>
		<s:else>
			<s:hidden name="aprislDto.TCommission.id" id="cmsId"/>
		</s:else>
    <s:hidden name="aprislDto.artclTp" id="artclTp"/>
    <s:hidden name="aprislDto.id" id="id"/>
		<div class="dkuang">
			<div class="buttomk2">
				&nbsp;&nbsp;<input name="button2" type="button"  class="anniu" value="保存" onclick="updateCert();" />
				<input name="button3" type="button" class="anniu" value="取消" onclick="closeBut();" />
			</div>
			<div  class="box2">
			<h4>修改鉴定物品</h4>
			<!-- 鉴定物品 -->
			<div id="aprisl">
			</div>
			</div>
			<!-- 扣除费用 -->
			<jsp:include page="/jsp/aprisl/prc_expenses.jsp" flush="true"></jsp:include>
			
			<!-- 参考价格库  -->
			<div class="box3" id="prfrncDiv">
				<jsp:include page="/jsp/aprisl/prfrnc.jsp" flush="true"></jsp:include>
			</div>
			<!-- 价格鉴定  -->
			<div class="box2" id="prcAprislDiv" style="display: none">
				<jsp:include page="/jsp/aprisl/prc_aprisl.jsp" flush="true"></jsp:include>
			</div>
		</div>
		<s:hidden name="expData" id="expData"/>
	</form>
  </body>
</html>
