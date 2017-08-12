	$(function(){

		//菜单切换(测试)
		bindAdminMenu();
		ChangeNav("common");

	}).keydown(function(event){//快捷键
		if(event.keyCode ==116 ){
			url = $("#main").attr("src");
			main.location.href = url;
			return false;
		}
		if(event.keyCode ==27 ){
			$("#qucikmenu").slideToggle("fast")
		}
	});

	function bindAdminMenu(){
		$("#nav").find("a").click(function(){
			ChangeNav($(this).attr("_for"));
		});

		$("#menu").find("dt").click(function(){
			dt = $(this);
			dd = $(this).next("dd");
			if(dd.css("display")=="none"){
				$("dl").siblings("dl").children("dd").slideUp("fast");
				dd.slideDown("fast");
				dt.css("background-position","left bottom");
			}else{
				dd.slideUp("fast");
				dt.css("background-position","left top");
			}
		});

		$("#menu dd ul li a").click(function(){
			$(this).addClass("thisclass").blur().parents("#menu").find("ul li a").not($(this)).removeClass("thisclass");

		});
	}

	function ChangeNav(nav){//菜单跳转
		//$("#nav").find("a").removeClass("thisclass");
		//$("#nav").find("a[_for='"+nav+"']").addClass("thisclass").blur();
		$("body").attr("class","showmenu");
		//$("#menu").find("div[id^=items]").hide();
		//$("#menu").find("#items_"+nav).show().find("dl dd").show().find("ul li a").removeClass("thisclass");
		//$("#menu").find("#items_"+nav).show().find("dd ul li a").eq(0).addClass("thisclass").blur();
	}



