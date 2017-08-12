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
<!--  查询区域   -->
<div class="kuang"><div class="title3">详细信息</div>
<div class="box3-tc">
  <div class="buttomk1">
    <input name="Submit2" type="submit" class="anniu" value="保存">
    <input name="Submit22" type="submit" class="anniu" value="详细">
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
      <td colspan="7">
        <textarea name="textarea" class="textarea1"></textarea>
     </td>
      </tr>
  </table>
</div>
<div class="title3">设置权限</div>
<div class="box_fx"> 
 <div class="buttomk1">
    <input name="Submit2" type="submit" class="anniu" value="保存">
	<input name="Submit2" type="submit" class="anniu" value="关闭">
  </div>
<table  border="0" cellpadding="0" cellspacing="0"  id="table4" >
    <tr>
      <td style="width:250px">
    	
		<p>	<select name="left" multiple="true" size="6" id="select_left"></select>

		</td>
		
		<td style=" text-align:center; width:100px" >
		<p><input type="button" value="&lt;&lt;" name="B3" onclick="moveselect('right','left',1)" class="fangniu" >
		<p><input type="button" value="&lt;" name="B5" onclick="" id="options_left" class="fangniu">
		<p><input type="button" value="&gt;" name="B6" onclick="" id="options_right" class="fangniu">
		<p><input type="button" value="&gt;&gt;" name="B4" onclick="moveselect('left','right',1)" class="fangniu" >
		</td>
	
		<td style=" width:250px">
			 	 <p> <select  name="right" multiple="true" size="6" id="select_right" ></select></p></td>
    </tr>

  </table>
 
  </div>


</div>
</body>
</html>
