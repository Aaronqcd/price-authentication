
/**
 * @author zhoujunyang
 * @date 2012-4-19
 * @Description 监管用户，机构用户的验证
 *
 */
$(document).ready(function() {
	
		$("#registFormId").SetValidateSettings({});
		
		$("#orgCnFullNm").SetValidateSettings({
			FormValidate: {
				Empty: {
					Value: true,
					Message: "机构全称不能为空"
				},
				
				Length: {
					Value:[0,30],
					Message: "机构全称在30个字符之内"
				}
			},
			Message: {
				Text: {
					Show: "",//缺省
					Success: "",
					Error: ""
				},
				MessageSpaceHolderID: "TipEducation"
			}
			
		});
		
		
		$("#orgCnShrtNm").SetValidateSettings({
			FormValidate: {
				Empty: {
					Value: true,
					Message: "机构简称不能为空"
				},
				
				Length: {
					Value:[0,10],
					Message: "机构简称在10个字符之内"
				}
			},
			Message: {
				Text: {
					Show: "",//缺省
					Success: "",
					Error: ""
				},
				MessageSpaceHolderID: "TipEducation"
			}
			
		});
		
		$("#legalRprsntvNm").SetValidateSettings({
			FormValidate: {
				Empty: {
					Value: true,
					Message: "机构负责人不能为空"
				},
				
				Length: {
					Value:[0,20],
					Message: "机构负责人在20个字符之内"
				}
			},
			Message: {
				Text: {
					Show: "",//缺省
					Success: "",
					Error: ""
				},
				MessageSpaceHolderID: "TipEducation"
			}
			
		});
		
		$("#largePayNo").SetValidateSettings({
			
			FormValidate: {
				Empty: {
					Value: true,
					Message: "大额支付系统号不能为空"
				}
			},
			Message: {
				Text: {
					Show: "",//缺省
					Success: "",
					Error: ""
				},
				MessageSpaceHolderID: "TipEducation"
			}
			
		});
		
		
		$("#deptNm").SetValidateSettings({
			FormValidate: {
				Empty: {
					Value: true,
					Message: "部门名称不能为空"
				},
				
				Length: {
					Value:[0,10],
					Message: "部门名称在10个字符之内"
				}
			},
			Message: {
				Text: {
					Show: "",//缺省
					Success: "",
					Error: ""
				},
				MessageSpaceHolderID: "TipEducation"
			}
			
		});
		
		$("#orgAddr").SetValidateSettings({
			FormValidate: {
				Empty: {
					Value: true,
					Message: "机构地址不能为空"
				},
				
				Length: {
					Value:[0,30],
					Message: "机构地址在30个字符之内"
				}
			},
			Message: {
				Text: {
					Show: "",//缺省
					Success: "",
					Error: ""
				},
				MessageSpaceHolderID: "TipEducation"
			}
			
		});
		
		$("#zipCd").SetValidateSettings({
			FormValidate: {
				Empty: {
					Value: true,
					Message: "邮编不能为空"
				},
				
				IsPostCode: {
					Message: "邮编格式不正确"
				}
			},
			Message: {
				Text: {
					Show: "",//缺省
					Success: "",
					Error: ""
				},
				MessageSpaceHolderID: "TipEducation"
			}
			
		});
		
		
		
		$("#deptFax").SetValidateSettings({
			FormValidate: {
				Empty: {
					Value: true,
					Message: "传真不能为空"
				},
				
				IsFax: {
					Message: "传真格式不正确"
				}
			},
			Message: {
				Text: {
					Show: "",//缺省
					Success: "",
					Error: ""
				},
				MessageSpaceHolderID: "TipEducation"
			}
			
		});
		
		
		$("#rspnsblUsrNm").SetValidateSettings({
			FormValidate: {
				Empty: {
					Value: true,
					Message: "姓名不能为空"
				},
				
				Length: {
					Value:[0,20],
					Message: "姓名在20个字符之内"
				}
			},
			Message: {
				Text: {
					Show: "",//缺省
					Success: "",
					Error: ""
				},
				MessageSpaceHolderID: "TipEducation"
			}
			
		});
		
		
		$("#rspnsblTel").SetValidateSettings({
			FormValidate: {
				Empty: {
					Value: true,
					Message: "联系电话不能为空"
				},
				
				IsFax: {
					Message: "联系电话格式不正确"
				}
			},
			Message: {
				Text: {
					Show: "",//缺省
					Success: "",
					Error: ""
				},
				MessageSpaceHolderID: "TipEducation"
			}
			
		});
		
		$("#rspnsblMblTel").SetValidateSettings({
			FormValidate: {
				Empty: {
					Value: true,
					Message: "移动电话不能为空"
				},
				
				IsMobile: {
					Message: "移动电话格式不正确"
				}
			},
			Message: {
				Text: {
					Show: "",//缺省
					Success: "",
					Error: ""
				},
				MessageSpaceHolderID: "TipEducation"
			}
			
		});
		
		
		$("#rspnsblEml").SetValidateSettings({
			FormValidate: {
				Empty: {
					Value: true,
					Message: "电子邮件不能为空"
				},
				IsEmail: {
					Message: "电子邮件格式不正确"
				}
			},
			Message: {
				Text: {
					Show: "",//缺省
					Success: "",
					Error: ""
				},
				MessageSpaceHolderID: "TipEducation"
			}
			
		});
		
});