function jumpToPage(redirect,selectedField,restore){ 

	eval(redirect+".location='/Bank/MainController.html?Module=ACCOUNT&Id="+selectedField.options[selectedField.selectedIndex].value+"'");

	if (restore) 
		selObj.selectedIndex=0;
}