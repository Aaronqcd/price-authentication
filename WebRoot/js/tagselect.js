function selectTag(showContent,selfObj,num){
	for(var i=0;i<num;i++)
	{
		if(i==showContent)
		{
			document.getElementById("tab"+i).className="tabsJdq";	
		}
		else
			{
				if(null != document.getElementById("tab"+i)) {
					document.getElementById("tab"+i).className="";
				}
			}
	}
  
}

function selectChildTag(showContent,selfObj,num){
	for(var i=0;i<num;i++)
	{
		if(i==showContent)
		{
			document.getElementById("tagChildContent"+i).style.display = "";
			document.getElementById("tabChild"+i).className="xtabsJdq";	
		}
		else
			{
				document.getElementById("tagChildContent"+i).style.display = "none";
				if(null != document.getElementById("tabChild"+i)) {
					document.getElementById("tabChild"+i).className="";	
				}
			}
			
	}

  
}

