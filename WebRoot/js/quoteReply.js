/**********应答页面元素***********/
//报价机构
var qt_org_cn_full_nm;
//业务员
var rply_usr_nm;
//交易方向
var txn_dir;
//票据类型
var bill_type;
//对手方
var rply_org_cn_full_nm;
//修改记录
var titl;
//承兑行类型
var acptng_type;
//剩余期限
var rmng_prd;
//原始期限
var orgnl_prd;
//回购期限
var repo_prd; 
//赎回开放期
var rdm_bgn_date;
//赎回利率
var ancmnt_no;
//利率
var bill_rate;
//金额
var bill_amnt;
//清算 or 交接方式
var clrng_way;
//不附追索权
var rcrs_rgt;
//备注
var res_bak;



	/**************报价应答 begin**************/
	function to_quoteReply(quoteNo,quoteId){
 	 
	  $.ajax({
			url : '${pageContext.request.contextPath}/quoteReply/getReplyDetail',
			type : 'post',
			dataType : 'json',
			data : {"quoteNo":quoteNo,"quoteId":quoteId},
			success : callback
		});
	}
	
	        callback = function(data){
				alert(data);
			}
	
	/**************报价应答 end**************/
	
	/**************应答发送 begin**************/
	function QuoteSend(d_type){
	 
	      //买断式
		 if (dealType == 'B') {
		 	qt_org_cn_full_nm = $("#").val();
			
			rply_usr_nm = $("#").val();
			
			txn_dir = $("#txn_dir").val();
			
			bill_type = $("#bill_type").val();
			
			rply_org_cn_full_nm = $("#rply_org").val();
			
			titl = $("#titl").val();
			
			acptng_type = $("#acptng_type").val();
			
			rmng_prd = $("#rmng_prd").val();
			
			orgnl_prd = $("#orgnl_prd").val();
			
			bill_rate = $("#bill_rate").val();
			
			bill_amnt = $("#bill_amnt").val();
			
			clrng_way = $("#hndvr_type").val();
			
			rcrs_rgt = $("#rcrs_rgt").val();
			
			
			$.ajax({
						type:"POST",
						url:"quoteReply/createReply.action",
						timeout:"60000",
						data:{
							"qt_org_cn_full_nm":qt_org_cn_full_nm,
							"rply_usr_nm":rply_usr_nm,
							"txn_dir":txn_dir,
							"bill_type":bill_type,
							"rply_org_cn_full_nm":rply_org_cn_full_nm,
							"titl":titl,
							"acptng_type":acptng_type,
							"rmng_prd":rmng_prd,
							"orgnl_prd":orgnl_prd,
							"bill_rate":bill_rate,
							"bill_amnt":bill_amnt,
							"clrng_way":clrng_way,
							"rcrs_rgt":rcrs_rgt
						    },
					    datatype:"json",
						success:callback = function callback(data){
						
						alert(data);
						if( data == null){
							
							return;
								}
							},
						error:callerror=function callerror(){ },
					})
	
				 }else if(dealType == 'R'){//回购式
		 	
		 		}
		}
		/**************应答发送 end**************/
		
		/**************应答终止 begin**************/
		function QuoteStop(){
		
		}
		/**************应答终止 end**************/