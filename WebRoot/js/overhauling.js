//上传附件
function uploadBtn(){
	$.ajaxFileUpload({
		url: "upLoadFile.action",
		fileElementId: "fileId",
		dataType: "json",
		data: {"params.receiptId": $("#receiptId").val()},
		success: function(data) {
			if(data != null) {
				$("#upTr").show();
				$("#uploadTab").append( 
					'<tr>'+    
					'<td>'+data.uploadTime+'</td>'+
					'<td>'+data.userName+'</td>'+
					'<td>'+data.dept+'</td>'+
					'<td>'+data.desc+'</td>'+
					'<td>'+data.fileFileName+'</td>'+
					'<td><a href="javascript:void(0);">查看</a><a href="#" onclick="deleteFile(this,\''+data.fileDir+'\')">删除</a></td>'+
					'</tr>'
				); 
			}
		}
	});
}

function deleteFile(obj,fileName){
	if(confirm("确定删除该图片?")) {
		var delTrObj = $(obj).parent().parent();
		$.ajax({
			url:'deleteFile.action',
			cache: false,
			async: false,
			data: {"fileName": fileName},
			success:function(data){
				//data == "success"
			}
		});  
		delTrObj.remove();
		if($("#uploadTab tr").length<2){
			$("#upTr").hide();
		}
	}
}