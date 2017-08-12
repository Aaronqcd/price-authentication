<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">

			//添加
			var b = $("#exp_Tbl>tbody>tr").length+1;
			function addExpenses() {
				if(document.getElementById("popname").value==''){
					alert('请添加名称');
					return;
				}
				$("#kouchufeiyong").remove();
				var b = $("#exp_Tbl>tbody>tr").length;
				var trText2 = '<tr>';
				trText2 += '<td align="center">'+b+'</td>';
				trText2 += '<td>' + document.getElementById("popname").value + '</td>';
				trText2 += '<td>' + document.getElementById("popdesic").value + '</td>';
				trText2 += '<td></td>';
				trText2 += '<td align="center"><a id="'
						+ b
						+ '" style="text-decoration: underline;cursor: pointer;" onclick="openDialog(this);">价格</a>&nbsp;&nbsp;&nbsp;<a id="'
						+ b
						+ '" style="text-decoration: underline;cursor: pointer;" onclick="readyupdate(this);">修改</a>&nbsp;&nbsp;&nbsp;<a id="'
						+ b
						+'" style="text-decoration: underline;cursor: pointer;" onclick="delExpenses(this);">删除</a></td>';
				trText2 += '</tr>';
				$("#exp_Tbl tbody").append(trText2);
				b++;
				document.getElementById("popname").value="";
				document.getElementById("popdesic").value="";
			}
			//删除 
			function delExpenses(obj) {
				$(obj).parent().parent().remove();
				var b = $("#exp_Tbl>tbody>tr").length;
				if(b==1){
					document.getElementById("add").style.display='';
					document.getElementById("update").style.display='none';
				}
				var tableall = $("#exp_Tbl");
					$(tableall).find("tr").each(function(trindex,tritem){
					//trindex   行数
					if(trindex != 0 && this.style.display != 'none'){
						$("#exp_Tbl tr:eq("+trindex+")").find("td").each(function(tdindex,tditem){
							//列内容
							if(tdindex==0){
								$(tditem).text(trindex);
							}
						});
					}
				});
				if(b==1){
				var trText='<tr id="kouchufeiyong"><td colspan="4" style="text-align: left;">请添加扣除费用</td></tr>';
					$("#exp_Tbl tbody").append(trText);
				}
			}
			//修改
			function readyupdate(obj) {
				document.getElementById("add").style.display='none';
				document.getElementById("update").style.display='';
				document.getElementById("popname").value = obj.parentElement.parentElement.cells[1].innerHTML;
				document.getElementById("popdesic").value = obj.parentElement.parentElement.cells[2].innerHTML;
				document.getElementById("hid").value = obj.id;
			}
			//确认修改
			function updateRow() {
				id = document.getElementById("hid").value;
				if(id==''){
					alert('请点击修改');
					return;
				};
				var obj = document.getElementById(id);
				obj.parentElement.parentElement.cells[1].innerHTML = document
						.getElementById("popname").value;
				obj.parentElement.parentElement.cells[2].innerHTML = document
						.getElementById("popdesic").value;
				document.getElementById("hid").value="";
				document.getElementById("popname").value="";
				document.getElementById("popdesic").value="";
				document.getElementById("add").style.display='';
				document.getElementById("update").style.display='none';
			}
			//
			function openDialog(obj){
				//var jine = prompt("请输入金额,必须是数字","");
				//obj.parentElement.parentElement.cells[3].innerHTML =jine;
				//jQuery("#expensesForm").validationEngine('hide');
				document.getElementById("hhhid").value=obj.id;
				document.getElementById("jiage").value="";
				$("#expenses_dialog").dialog(
				{
					//hide:true, //点击关闭是隐藏,如果不加这项,关闭弹窗后再点就会出错.
					modal:true,
					draggable:true,
					width:300,
					height:100,
					title:"价格(必须是数字，最多小数点两位)",
					dialogClass:"my-dialog"
				});
				
			}
			function fuzhi(){
				var jiage = document.getElementById("jiage").value;
				var reg = /^\d+(\.\d{1,2})?$/;
				if(!jiage.match(reg)){
					alert("请输入数字");
				}else{
					var id = document.getElementById("hhhid").value;
					document.getElementById(id).parentElement.parentElement.cells[3].innerHTML=jiage;
					closeDialog();
				}
			}
			function closeDialog(){
				$("#expenses_dialog").dialog('close');//关闭弹出窗
			}
</script>
			<div id="expenses_dialog" style="text-align: center;display: none;">
			
			<table>
			<tr><th style="text-align: right;height: 30px">价格：</th>
			<td><input type="text" id="jiage"><input type="hidden" id="hhhid"></td>
			</tr>
			<tr><td colspan="2">
			<input type="button" value="提交" class="anniu" onclick="fuzhi()" id=""/>
			<input type="button" value="取消" class="anniu" onclick="closeDialog();" />
			</td></tr>
			</table>
			</div>


			<div class="box3">
				<h4>扣除费用</h4>
				名称：<input type="text" id="popname" name="nm"  size="20"/>
				描述：<input type="text" id="popdesic" name="remark"  size="100" />
				<input id="add" name="button1" class="anniu2" type="button" value="添加" onclick="addExpenses()" />
				<input id="update"  name="button2" class="anniu2" type="button" style="display: none;" value="修改"
					onclick="updateRow()"  />
				<input type="hidden" id='hid' />
				<table id="exp_Tbl" border="0" cellSpacing="0" cellPadding="0">
					<tbody>
					<tr>
						<th width="5%" style="text-align: center;">序号</th>
						<th width="15%">名称</th>
						<th width="60%">扣除费用描述</th>
						<th width="5%">估价金额</th>
						<th width="15%" style="text-align: center;">操作</th>
					</tr>
					<s:if test="(null == aprislDto.TCmsArtclsAccesorses || aprislDto.TCmsArtclsAccesorses.size()<=0)" >
					<tr id="kouchufeiyong"><td colspan="5" style="text-align: left;">请添加扣除费用</td></tr>
					</s:if>
					<s:iterator value="aprislDto.TCmsArtclsAccesorses" status='st'>
						<tr>
						<td align="center">${st.index+1}</td>
						<td><s:property value="nm" /></td>
						<td><s:property value="remark" /></td>
						<td><s:property value="valutnAmnt" /></td>
						<td align="center">
							<a id="${st.index+1}" style="text-decoration: underline;cursor: pointer;" onclick="openDialog(this);">价格</a>&nbsp;&nbsp;
							<a id="${st.index+1}" style="text-decoration: underline;cursor: pointer;" onclick="readyupdate(this);">修改</a>&nbsp;&nbsp;
							<a id="${st.index+1}" style="text-decoration: underline;cursor: pointer;" onclick="delExpenses(this);">删除</a>
						</td>
						</tr>
					</s:iterator>
					
					</tbody>
				</table>
			</div>
