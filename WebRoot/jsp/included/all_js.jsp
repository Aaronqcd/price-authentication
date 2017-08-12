<%@ page language="java" pageEncoding="utf-8" import="com.pemng.serviceSystem.pojo.TActnInfo,java.util.List"%>
<script type="text/javascript">
var contextPath="${pageContext.request.contextPath}";
//-->
</script>    
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/swfobject.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.8.16.custom.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-fluid16.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/kandytabs.pack.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bs.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/slides.min.jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/validate.js"></script>
	<!--[if IE 6]>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/DD_belatedPNG_0.0.8a.js"></script>
	<script>
	DD_belatedPNG.fix('.png_bg');
	DD_belatedPNG.fix('.pagination li a');
	</script>
	<![endif]-->
   <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validationEngine-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validationEngine.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/my97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.boxy.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/popAjaxErrors.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/tagselect.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ztree.core-3.5.js"></script>
	
	<!-- 页面权限控制 -->
	<script type="text/javascript">
	jQuery(document).ready(function(){
		$("[buttonAction]").each(function(){
			<%
			List<TActnInfo> aclList = (List<TActnInfo>)request.getAttribute("aclList");
			if(aclList != null){
				String js = "";
				int i=0;
				for(TActnInfo acl : aclList){
					if(i > 0){
						js+="||";
					}
					js+=" $(this).attr('buttonAction') == '"+acl.getAction()+"'";
					i++;
				}
				out.print("if(!("+js+")){/*alert($(this).attr('id'));*/");
				//out.print("$(this).attr('disabled','true');");
				out.print("$(this).hide();");
				//out.print("$(this).attr('onclick','alert(\"对不起，您无权限使用该功能。\")');");
				//out.print("$(this).attr('href','javascript:void(0);');");
				out.print("}");
			}
			%>
		});
	});
	</script>