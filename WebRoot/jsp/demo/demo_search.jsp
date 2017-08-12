<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<jsp:include page="/jsp/included/all_css.jsp"></jsp:include>
<jsp:include page="/jsp/included/all_js.jsp"></jsp:include>
</head>

<body>
<!--  头部   -->
<div class="logo"><img src="${pageContext.request.contextPath}/images/logo.jpg"></div>
<div class="nav"></div>
<div class="title1"><span class="sy">首页</span><span class="jt">短信平台</span></div>
<!--  查询区域   -->
<div class="kuang">
<!-- 查询条件收缩 -->
<div class="box1" id="searchShrinkDiv">
<table>
  <tr>
    <th>姓名：</th>
    <td><input type="text" name="textfield" class="input1" >    </td>
    <th>用户名：</th>
    <td><input type="text" name="textfield2" class="input1" ></td>
    <th>状态：</th>
    <td>
      <select name="select" class="input1">
      </select>   </td>
    <td><div class="buttomk">
      <input name="Submit" type="submit" class="buttom" value="查询">
      <input name="Submit" type="submit" class="buttomlv" value="新建">
    </div>
      <div class="ss"><a href="#" onclick="showObj('searchUnfoldDiv');hideObj('searchShrinkDiv')"><img src="${pageContext.request.contextPath}/images/ss1.png" width="21" height="21" border="0"></a><a href="#" onclick="showObj('searchUnfoldDiv');hideObj('searchShrinkDiv')">展开</a></div></td>
    </tr>

</table>
</div>
<!-- 查询条件展开 -->
<div class="box1" id="searchUnfoldDiv" style="display:none">
<table>
  <tr>
    <th>姓名：</th>
    <td><input type="text" name="textfield" class="input1" >    </td>
    <th>用户名：</th>
    <td><input type="text" name="textfield2" class="input1" ></td>
    <th>状态：</th>
    <td>
      <select name="select" class="input1">
      </select>   </td>
    <th>&nbsp;</th>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <th>电子邮件：</th>
    <td><input type="text" name="textfield3" class="input1" ></td>
    <th>固定电话：</th>
    <td><input type="text" name="textfield22" class="input1" ></td>
    <th>电话分机：</th>
    <td><input type="text" name="textfield23" class="input1" ></td>
    <th>手机号码：</th>
    <td><input type="text" name="textfield232" class="input1" ></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
     <th>家庭电话:</th>
     <td><input type="text" name="textfield32" class="input1" ></td>
    <th>备用电话：</th>
    <td><input type="text" name="textfield33" class="input1" ></td>
    <th>职位编码：</th>
    <td><input type="text" name="textfield34" class="input1" ></td>
    <td colspan="3"><div class="ss"><a href="#" onclick="showObj('searchShrinkDiv');hideObj('searchUnfoldDiv')"><img src="${pageContext.request.contextPath}/images/ss.png" border="0"></a>
    <a href="#" onclick="showObj('searchShrinkDiv');hideObj('searchUnfoldDiv')">收缩</a></div>
    <div class="buttomk">
      <input name="Submit" type="submit" class="buttom" value="查询">
      <input name="Submit" type="submit" class="buttomlv" value="新建"></div>
      </td>
    </tr>
</table>
</div> 
 <div class="clear"></div>
</div>
<div class="kuang"><div class="title2">查询结果</div>
<div class="box2">
<table border="0" cellpadding="0" cellspacing="0">
  <tr>
  <th>姓名</th>
  <th>用户名</th>   
  <th>状态</th>
  <th>电子邮件</th>
  <th>固定电话</th>
  <th>电话分机</th>    
  <th>手机号码</th>
   </tr>
  <tr class="td1">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
<tr class="td1">

    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
<tr class="td1">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr class="td1">
    <td colspan="7"><div class="fany">展示
    <select name="select2">
      <option>10</option>
      </select>
    条 共24条 </div>
	<div class="fany2"><a href="#"><img src="${pageContext.request.contextPath}/images/png/jiant1.png" border="0"></a><a href="#"><img src="${pageContext.request.contextPath}/images/png/jiant2.png" border="0"></a><a href="#"><img src="${pageContext.request.contextPath}/images/png/jiant3.png" border="0"></a><a href="#"><img src="${pageContext.request.contextPath}/images/png/jiant4.png" border="0"></a></div>
	</td>
    </tr>
</table>
</div>

</div>
<div class="kuang1"><div class="title3">详细信息</div>
<div class="box3">
  <div class="buttomk1">
    <input name="Submit2" type="submit" class="anniu" value="保存">
    <input name="Submit2" type="submit" class="anniu" value="新建">
	<input name="Submit2" type="submit" class="anniu" value="关闭">

  </div>
  <table>
    <tr>
      <th>姓名：</th>
      <td><input type="text" name="textfield4" class="input1" >      </td>
      <th>用户名：</th>
      <td><input type="text" name="textfield24" class="input1" ></td>
      <th>密码：</th>
      <td><input type="text" name="textfield2332" class="input1" ></td>
      <th>状态：</th>
      <td><select name="select3" class="input1">
      </select></td>
    </tr>
    <tr>
      <th>电子邮件：</th>
      <td><input type="text" name="textfield35" class="input1" ></td>
      <th>固定电话：</th>
      <td><input type="text" name="textfield222" class="input1" ></td>
      <th>电话分机：</th>
      <td><input type="text" name="textfield233" class="input1" ></td>
      <th>&nbsp;</th>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <th>手机号码：</th>
      <td><input type="text" name="textfield322" class="input1" ></td>
      <th>家庭电话:</th>
      <td><input type="text" name="textfield332" class="input1" ></td>
      <th>备用电话：</th>
      <td><input type="text" name="textfield342" class="input1" ></td>
      <td>职位编码：</td>
      <td><input type="text" name="textfield3422" class="input1" ></td>
    </tr>
    <tr>
      <th>备注：</th>
      <td colspan="7"><textarea name="textarea" class="textarea1"></textarea></td>
      </tr>
  </table>
</div>

</div>
</body>
</html>
