<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">			
			//添加
			var b = $("#de_Tbl>tbody>tr").length+1;
			function addRow() {
				if(document.getElementById("popname").value==''){
					alert('请添加名称');
					return;
				}
				$("#kouchufeiyong").remove();
				var b = $("#de_Tbl>tbody>tr").length;
				var trText2 = '<tr>';
				trText2 += '<td align="center">'+b+'</td>';
				trText2 += '<td>' + document.getElementById("popname").value + '</td>';
				trText2 += '<td>' + document.getElementById("popdesic").value + '</td>';
				trText2 += '<td align="center"><a id="'
						+ b
						+ '" style="text-decoration: underline;cursor: pointer;" onclick="readyupdate(this);">修改</a>&nbsp;&nbsp;<a id="'
						+ b
						+'" style="text-decoration: underline;cursor: pointer;" onclick="delRow(this);">删除</a></td>';
				trText2 += '</tr>';
				$("#de_Tbl tbody").append(trText2);
				b++;
				document.getElementById("popname").value="";
				document.getElementById("popdesic").value="";
			}
			//删除 
			function delRow(obj) {
				$(obj).parent().parent().remove();
				var b = $("#de_Tbl>tbody>tr").length;
				if(b==1){
					document.getElementById("add").style.display='';
					document.getElementById("update").style.display='none';
				}
				var tableall = $("#de_Tbl");
					$(tableall).find("tr").each(function(trindex,tritem){
					//trindex   行数
					if(trindex != 0 && this.style.display != 'none'){
						$("#de_Tbl tr:eq("+trindex+")").find("td").each(function(tdindex,tditem){
							//列内容
							if(tdindex==0){
								$(tditem).text(trindex);
							}
						});
					}
				});
				if(b==1){
				var trText='<tr id="kouchufeiyong"><td colspan="4" style="text-align: left;">请添加扣除费用</td></tr>';
					$("#de_Tbl tbody").append(trText);
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
</script>
			<div class="box3">
				<h4>扣除费用</h4>
				名称：<input type="text" id="popname" name="nm"  size="20"/>
				描述：<input type="text" id="popdesic" name="remark"  size="100" />
				<input id="add" name="button1" class="anniu2" type="button" value="添加" onclick="addRow()" />
				<input id="update"  name="button2" class="anniu2" type="button" style="display: none;" value="修改"
					onclick="updateRow()"  />
				<input type="hidden" id='hid' />
				<table id="de_Tbl" border="0" cellSpacing="0" cellPadding="0">
					<tbody>
					<tr>
						<th width="5%" style="text-align: center;">序号</th>
						<th width="15%">名称</th>
						<th width="65%">扣除费用描述</th>
						<th width="15%" style="text-align: center;">操作</th>
					</tr>
					<tr id="kouchufeiyong" ><td colspan="4" style="text-align: left;" >请添加扣除费用</td></tr>
					</tbody>
				</table>
			</div>
