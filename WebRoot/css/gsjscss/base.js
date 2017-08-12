Z.ns("base");

// 全局设置
base.g = {
	jzTypeHS : {
		"1" : "刑事",
		"2" : "民事",
		"3" : "行政",
		"4" : "价格认证",
		"5" : "价格评估"
	},
	wbHS : {
		"1" : "机器设备",
		"2" : "建筑物、土地",
		"3" : "首饰(珠|宝|工)",
		"4" : "手机",
		"5" : "机动车",
		"6" : "机动车(灭失)",
		"7" : "电子产品",
		"8" : "首饰(黄金|铂金)",
		"0" : "其它"
	},
	wb2HS : {
		"1" : "自行车",
		"2" : "手机",
		"3" : "日用百货",
		"4" : "工地建材",
		"5" : "电子产品",
		"6" : "机动车",
		"7" : "钱包",
		"8" : "首饰(珠|宝|工)",
		"9" : "市政设施",
		"10" : "假冒产品",
		"0" : "其他"
	},
	wtsStateHS : {
		// -1拒绝受理 0暂存 -2中止 -3终止 1已提交(待受理) 2受理中 3完成
		"-1" : "拒绝受理",
		"-2" : "中止",
		"-3" : "终止",
		"-4" : "补充材料",
		"0" : "暂存",
		"1" : "已提交(待受理)",
		"2" : "受理中",
		"3" : "完成"
	},
	nextAppStepHS : {
		"-2" : "中止",
		"-3" : "终止",
		"-4" : "补充材料",
		"-1" : "拒绝受理",
		"0" : "待受理",
		"1" : "受理",
		"2" : "提交复核",
		"3" : "科长审批",
		"4" : "提交送达",
		"5" : "提交完成"
	},
	// 不矛受理字典
	wtsRefuseHS : {
		"otherId" : "8", // 自定义原因的id
		"1" : "1、不符合价格鉴定分级管理规定要求",
		"2" : "2、法律法规明确规定不以价格数额作为办案依据，不需要进行价格鉴定",
		"3" : "3、委托书（函件）或相关材料未加盖公章",
		"4" : "4、委托书（函件）内容不符合要求",
		"5" : "5、相关材料不齐全，或经书面通知补充材料后，仍然达不到受理要求",
		"6" : "6、未提供有效的质量、技术等检测、鉴定报告",
		"7" : "7、价格鉴定标的灭失或其状态与基准日状态相比发生较大变化，委托（提出）方不能确定其在价格鉴定基准日状况",
		"8" : "8、其他："
	},
	// 中止受理字典
	wtsSuspendHS : {
		"otherId" : "4", // 自定义原因的id
		"1" : "1、委托（提出）方书面提出中止",
		"2" : "2、价格鉴证机构和委托（提出）方协商中止",
		"3" : "3、委托（提出）方不能按规定或约定时间提供相关材料",
		"4" : "4、其他："
	},
	// 终止受理字典
	wtsStopHS : {
		"otherId" : "4", // 自定义原因的id
		"1" : "1、委托（提出）方书面提出撤销或终止",
		"2" : "2、受理后发现材料不完整、不充分，导致不能作出结论，且委托（提出）方不能补充提供符合要求的材料",
		"3" : "3、因不可抗力致使工作无法继续进行",
		"4" : "4、其他："
	},
	step : {
		"-2" : "<font color='red'>中止</font>",
		"-3" : "<font color='red'>终止</font>",
		"-1" : "<font color='red'>拒绝受理</font>",
		"-4" : "<font color='orange'>补充材料</font>",
		"0" : "待受理",
		"1" : "受理",
		"2" : "复核",
		"3" : "审批",
		"4" : "送达",
		"5" : "<font color='green'>完成</font>"
	}
};

base.g.dpn = "京海价（鉴）字[{year}]第{seqno}号"; // 文号模板

/*
 * 示例 1,"3","31","FlowStepUserId","流程活动处理人：3科长审批",2
 * 2,"4","35","FlowStepUserId","流程活动处理人：4送达人",2
 * 3,"kzsp_min_money","10000","G","全局组：科长审批最小金额",1
 */
base.cfg = function(group, k) {
	var s = base.ajax("jgjzBaseAction.queryCfg", {
		"group" : group,
		"k" : k
	});
	// alert(s);
	var rows = Ext.decode(s);
	if (Z.isEmpty(k)) {
		return rows;
	} else {
		return rows.length > 0 ? rows[0].v : "";
	}
};

base.getWpTypeName = function(type1, type2, name) {
	var s;
	if (type1 == "0") {
		s = base.g.wb2HS[type2];
	} else {
		s = base.g.wbHS[type1];
	}
	// if (name && (s == "其它" || s == "其他")) {
	// s = name;
	// }
	return s;
};

// Ajax快捷方式，是否是异步取决于是否声明了第3个参数(即回调函数cb)
/*
 * 1.同步示例： var str=base.ajax("jgjzDemoAction.findById",{id:1}); alert(str);
 * 
 * 2.异步示例：
 * base.ajax("jgjzDemoAction.findById&id=1",null,function(str){alert(str)});
 */
base.ajax = function(action, p, cb) {
	var sync = !Z.isFunction(cb);
	var s;
	Z.Ajax.request({
		url : '2.ajax?action=' + action,
		params : p,
		sync : sync,
		callback : function(options, success, response) {
			s = response.responseText;
			if (!sync)
				cb(s, success, options);
		}
	});
	if (sync)
		return s;
};

// 绑定下拉框
/*
 * 示例1： base.bindSelect("country", "jgjzDemoCountryAction.findAll");//初始化国籍下拉框
 * 
 * 示例2： base.bindSelect("country",
 * "jgjzDemoCountryAction.findAll",{textField:"name",valueField:"id"});//初始化国籍下拉框
 * 
 * 示例2： var myArray=[ {name:"中国",id:1}, {name:"日本",id:2}, {name:"韩国",id:3} ];
 * var cfg={"data":myArray}; base.bindSelect("country", null, cfg);//初始化国籍下拉框
 */
base.bindSelect = function(el, action, cfg) {

	cfg = cfg ? cfg : {};
	if (!Z.isDefined(cfg.empty))
		cfg.empty = "请选择...";
	if (!Z.isDefined(cfg.clear))
		cfg.clear = true;
	if (!Z.isDefined(cfg.textField))
		cfg.textField = "name";
	if (!Z.isDefined(cfg.valueField))
		cfg.valueField = "id";
	var bind = function(arr) {
		Z.html.Select.bindData(el, arr, cfg.textField, cfg.valueField, cfg.empty, cfg.clear);
		if (!Z.isEmpty(cfg.value)) {
			try {
				$E(el).value = cfg.value;
			} catch (e) {
				alert("设置下拉框selectedIndex时出错:" + e);
			}
		}
	};

	if (Z.isArray(cfg.data)) {
		bind(cfg.data);
	} else {
		Z.Ajax
				.request({
					url : '2.ajax?action=' + action,
					sync : true,
					callback : function(options, success, response) {
						if (success) {
							var obj = null;
							try {
								obj = Ext.decode(response.responseText);
							} catch (e) {
								alert("Ext.decode(response.responseText)出错,response.responseText=\r\n"
										+ response.responseText);
								return;
							}
							if (obj) {
								if (Z.isArray(obj))
									bind(obj);
								else if (Z.isNumber(obj.totalCount) && Z.isArray(obj.data))
									bind(obj.data);
								else
									alert("绑定下拉框失败：不可识别的obj对象");
							}
						} else {
							alert("绑定下拉框失败");
						}
					}
				});
	}
};

// 缓存用户信息
base.cacheUserInfo = function(loginType, account, id, truename, companyId, companyName) {
	Z.setCookie("jgjz_login_id", id);
	Z.setCookie("jgjz_login_type", loginType);
	Z.setCookie("jgjz_login_account", account);
	Z.setCookie("jgjz_login_truename", truename);
	Z.setCookie("jgjz_login_companyid", companyId);
	Z.setCookie("jgjz_login_companyname", companyName);
};
// 清除用户信息缓存
base.uncacheUserInfo = function() {
	Z.delCookie("jgjz_login_id");
	Z.delCookie("jgjz_login_truename");
	Z.delCookie("jgjz_login_companyid");
	Z.delCookie("jgjz_login_companyname");
};
// 取得当前用户登录信息
base.getUser = function() {
	return {
		"id" : Z.getCookie("jgjz_login_id"),
		"type" : Z.getCookie("jgjz_login_type"),
		"account" : Z.getCookie("jgjz_login_account"),
		"truename" : Z.getCookie("jgjz_login_truename"),
		"companyId" : Z.getCookie("jgjz_login_companyid"),
		"companyName" : Z.getCookie("jgjz_login_companyname")
	};
};

// 支持 用户、账号、单位 按键取值
base._hsU = new Z.HashTable();
base._hsA = new Z.HashTable();
base._hsC = new Z.HashTable();
base._hsQuery = function(action, hs) {
	if (hs.count() == 0) {
		var s = base.ajax(action, null);
		// alert(s);
		var list = Ext.decode(s);
		hs.clear();
		for ( var i = 0; i < list.length; i++) {
			var o = list[i];
			hs.add(String(o.id), o);
		}
	}
};
base.getU = function(id, clear) {
	if (!id || clear) {
		base._hsU.clear();
		if (!id)
			return null;
	}
	base._hsQuery("jgjzBaseAction.getU", base._hsU);
	return base._hsU.items(id + "");
};
base.getA = function(id, clear) {
	if (!id || clear) {
		base._hsA.clear();
		if (!id)
			return null;
	}
	base._hsQuery("jgjzBaseAction.getA", base._hsA);
	return base._hsA.items(String(id));
};
base.getC = function(id, clear) {
	if (!id || clear) {
		base._hsC.clear();
		if (!id)
			return null;
	}
	base._hsQuery("jgjzBaseAction.getC", base._hsC);
	return base._hsC.items(String(id));
};

base.selectUser = function(fnGet, fnSet, multiSelect, cb, dom) {
	var ids = fnGet();
	var names = "";
	var sm = null;
	var cs = [];
	if (multiSelect) {
		sm = new Ext.grid.CheckboxSelectionModel({
			singleSelect : false
		});
		cs.push(sm);
	} else {
		sm = new Ext.grid.RowSelectionModel({
			singleSelect : true
		});
	}
	cs.push({
		header : "编号",
		width : 60,
		sortable : false,
		dataIndex : 'id'
	});
	cs.push({
		header : "姓名",
		sortable : false,
		dataIndex : 'truename',
		id : "truename"
	});
	var grid = new Ext.grid.GridPanel({
		store : new Ext.data.JsonStore({
			url : '2.ajax?action=jgjzBaseAction.selectUser',
			root : 'data',
			autoLoad : true,
			fields : [ 'id', 'truename' ],
			loadexception : function() {
				alert("jgjzBaseAction.selectUser出错!")
			},
			baseParams : {},
			listeners : {
				"load" : function() {
					if (ids) {
						var fn = function(r, id) {
							return ids.indexOf(String(r.get('id'))) != -1;
						};
						var recordArr = grid.getStore().queryBy(fn).getRange();
						grid.getSelectionModel().selectRecords(recordArr, true);
					}
				}
			}
		}),
		columns : cs,
		sm : sm,
		border : false,
		autoExpandColumn : "truename"
	});

	var win1 = new Ext.Window({
		title : '选取用户-对话框',
		width : 200,
		height : 420,
		closable : true,
		resizable : true,
		minimizable : false,
		maximizable : true,
		stripeRows : true,
		layout : 'fit',
		items : [ grid ],
		buttonAlign : 'center',
		buttons : [ {
			text : '确定',
			handler : function() {
				var smObj = grid.getSelectionModel();
				var idArr = [], nameArr = [];

				smObj.each(function(r) {
					var id = r.get("id");
					var name = r.get("truename");
					idArr.push(id);
					nameArr.push(name);
				});
				ids = idArr.join(",");
				names = nameArr.join(",");
				// alert(ids + "=" + names);
				fnSet(ids, names);
				win1.close();
			}
		}, {
			text : '取消',
			handler : function() {
				win1.close();
			}
		} ]
	});
	win1.show(dom);
};

// 判断某个书签是否存在
base.existBookMark = function(name) {
	var ctrl = document.getElementById("SOAOfficeCtrl");
	if (!ctrl)
		return false;
	var doc = ctrl.Document;
	for ( var i = 1; i <= doc.BookMarks.Count; i++) {
		if (doc.BookMarks(i).Name == name) {
			return true;
		}
	}
	return false;
};
// 测试打印书签
base.alertBookMarks = function() {
	var ctrl = document.getElementById("SOAOfficeCtrl");
	if (!ctrl) {
		alert("未找到组件SOAOfficeCtrl");
		return;
	}
	;
	var doc = ctrl.Document;
	var arr = [];
	for ( var i = 1; i <= doc.BookMarks.Count; i++) {
		arr.push(doc.BookMarks(i).Name);
	}
	alert(arr.join(","));
};

// 设置书签值
base.setBookMark = function(name, value, no_name_alert) {
	if (!base.existBookMark(name)) {
		if (!no_name_alert) {
			alert("名为[" + name + "]的书签不存在,请检查!");
		}
		return;
	}
	try {
		var bkObj = document.getElementById("SOAOfficeCtrl").Document.BookMarks(name);
		var range = bkObj.Range;
		range.Text = value;
		document.getElementById("SOAOfficeCtrl").Document.Bookmarks.Add(name, range);
	} catch (err) {
		alert("设置书签值出错！name=" + name);
	}
};

// 数字转小写
base.lowerNumber = function(x) {
	// 如果不是数字，返回x
	if (isNaN(x))
		return x;
	// 如果是小数，返回x
	if (String(x).indexOf(".") != -1)
		return x;

	x = String(parseInt(x));
	var k = {
		"0" : "零",
		"1" : "一",
		"2" : "二",
		"3" : "三",
		"4" : "四",
		"5" : "五",
		"6" : "六",
		"7" : "七",
		"8" : "八",
		"9" : "九",
		"10" : "十"
	};
	var s = k[x];
	if (Z.isEmpty(s) && x.length == 2) {
		var a = x.charAt(0);
		var b = x.charAt(1);
		s = k[a] + "十" + (b == "0" ? "" : k[b]);
	}
	if (Z.isEmpty(s) && x.length == 3) {
		var c1 = x.charAt(0);
		var c2 = x.charAt(1);
		var c3 = x.charAt(2);
		s = k[c1] + "百" + (c2 == "0" ? (c3 == "0" ? "" : "零") : k[c2] + "十") + (c3 == "0" ? "" : k[c3]);
	}
	if (Z.isEmpty(s) && x.length == 4) {

		var c1 = x.charAt(0);

		if (parseInt(x) % 1000 == 0) {
			s = k[c1] + "千";
		} else {
			s = k[c1] + "千";
			var c2 = x.charAt(1);
			var c3 = x.charAt(2);
			var c4 = x.charAt(3);

			if (c2 == "0")
				s += "零";
			else
				s += k[c2] + "百";
			if (c3 != "0")
				s += (c3 == '1' ? '十' : k[c3] + "十");
			else{
				if(c2 != "0" && c4 !=0)
					s += "零";
			}
			if (c4 != 0)
				s += k[c4];

		}
	}
	return Z.isEmpty(s) ? x : s;
};

// 数字转小写日期
base.lowerNumberDate = function(date) {
	var arr = date.toDate().fmt("yyyy-M-d").split("-");
	var y = arr[0];
	var M = arr[1];
	var d = arr[2];
	var fn = function(x) {
		var k = {
			"0" : "０",
			"1" : "一",
			"2" : "二",
			"3" : "三",
			"4" : "四",
			"5" : "五",
			"6" : "六",
			"7" : "七",
			"8" : "八",
			"9" : "九",
			"10" : "十",
			"11" : "十一",
			"12" : "十二",
			"13" : "十三",
			"14" : "十四",
			"15" : "十五",
			"16" : "十六",
			"17" : "十七",
			"18" : "十八",
			"19" : "十九",
			"20" : "二十",
			"21" : "二十一",
			"22" : "二十二",
			"23" : "二十三",
			"24" : "二十四",
			"25" : "二十五",
			"26" : "二十六",
			"27" : "二十七",
			"28" : "二十八",
			"29" : "二十九",
			"30" : "三十",
			"31" : "三十一"
		};
		var v = k[x];
		return Z.isEmpty(v) ? x : v;
	};
	var yyyy = "";
	for ( var i = 0; i < y.length; i++) {
		yyyy += fn(y.charAt(i));
	}
	return yyyy + "年" + fn(M) + "月" + fn(d) + "日";
};

// 格式历史日期(如：今天、昨天、前天、x天前)
base.beforeDay = function(date, x) {
	if (!Z.isDefined(x)) {
		alert("base.beforeDay方法调用时,缺少参数x");
		return null
	}
	if (Z.isEmpty(date))
		return date;
	date = date.toDate().fmt("yyyy-MM-dd").toDate();
	var today = new Date().fmt("yyyy-MM-dd").toDate();

	// 在未来 或 晚于x之前
	if (date > today || date.add(Date.DAY, x) < today) {
		return date.getYear() == today.getYear() ? date.fmt("M月d日") : date.fmt("yyyy-M-d");
	} else {
		if (date.getTime() == today.getTime())
			return "今日";
		else {
			var day = (today - date) / (1000 * 3600 * 24);
			switch (day) {
			case 1:
				return "昨天";
				break;
			case 2:
				return "前天";
				break;
			default:
				return day + "天前";
				break;
			}
		}
	}
};

// banner区用户登录信息
Ext.onReady(function() {
	var span = $E("welcome");
	if (span) {
		var u = base.getUser();
		span.innerHTML = (u.type == 2 ? '价格鉴证中心' : u.companyName) + "（" + u.truename + "）";
	}
});

// 检查当前登录用户是不是可以显示委托书调度项,目前只有id为31的用户可以显示此项
Ext.onReady(function() {
	var currLoginId = base.getUser().id;
	if ($E("dispatcher")) {
		Z.disp("dispatcher", currLoginId == 31);
	}
})
