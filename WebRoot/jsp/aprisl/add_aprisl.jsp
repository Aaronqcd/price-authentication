<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>鉴定物品</title>
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
			function selectcert(obj){
			//window.location.href="${pageContext.request.contextPath}/aprisl/selectAprisl.action?aprislDto.artclTp="+obj.value;
			$.ajax({
	            url: "${pageContext.request.contextPath}/aprisl/selectAprisl.action",
	            cache:false,
	            dataType:"html",
	            //data:{"leftMenuSrno":srnoid},
	            data:{"aprislDto.artclTp":obj.value},
	            success: function(html){
	            	$("#aprisl").html('').html(html);
	            }
	        });
			}
			//确定按钮
			function addCert(){
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
						var tableData = new Array();
						var postArr= [];
						var index = 0;
						var tableall = $("#de_Tbl");
				 	$(tableall).find("tr").each(function(trindex,tritem){
						var stchk = 0;
						//trindex   行数
						if(trindex != 0 && this.style.display != 'none'){
							$("#de_Tbl tr:eq("+trindex+")").find("td").each(function(tdindex,tditem){
								//列内容
								tableData[tdindex] = new Array();
							  	tableData[tdindex] = $(tditem).text();
							  	stchk++;
							});
						}
						if(stchk!=0){
							postArr[index]={"name":tableData[1],"remark":tableData[2]};
							index++;
						}
					}
				  );
				  
				  var params=$('#aprislform').serialize();
				  var postData = postArr;
				  var jsonData = JSON.stringify(postData);
				$.ajax({
					type:"POST",
					url:"${pageContext.request.contextPath}/aprisl/addAprisl.action?"+params+"&tm="+new Date().getTime(),
					data:{"reData":jsonData},
			        dataType:"json",
				    success:function callback(){
						 window.opener.afterCreateAprisl();
						 closeBut();
					 }
				}); 
			}
			
			//取消
			function closeBut() {
				window.close();
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
		
		<div class="dkuang">
			<div class="buttomk2">
				&nbsp;&nbsp;<input name="button2" type="button"" class="anniu" value="确定" onclick="addCert();" />
				<input name="button3" type="button" class="anniu" value="取消" onclick="closeBut();"/>
			</div>
			<div  class="box2">
			<h4>添加鉴定物品</h4>
				<table ><tr><th style="width: 150px;">鉴定物品：</th>
				<td colspan="3"><s:select cssClass="input1" id="artclTp" name="aprislDto.artclTp" onchange="selectcert(this)"
				list="#{'001':'手机','002':'钱包','003':'自行车','004':'电子产品','005':'机动车','006':'机动车(灭失)',
				'007':'首饰（黄金|铂金）','008':'珠|宝|玉|工艺','009':'日用百货','010':'工地建材','011':'市政设施',
				'012':'假冒产品','013':'机器设备','014':'建筑物、土地','015':'其它'}" 
				></s:select></td></tr>
				</table>
				<div id="aprisl">
				<jsp:include page="/jsp/aprisl/aprisl_mobile.jsp" flush="true"></jsp:include>			
				</div>
			</div>
			<!-- 扣除费用 -->
			<jsp:include page="/jsp/aprisl/expenses.jsp" flush="true"></jsp:include>
		</div>
	</form>
	</body>
</html>
