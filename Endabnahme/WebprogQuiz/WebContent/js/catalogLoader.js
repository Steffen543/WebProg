document.addEventListener('DOMContentLoaded', initCatalogs, false);

function getCatalogs(xml)
{
	
	var catalogList;
	var table = "";
	var xmlDoc = xml.responseXML;
	var x = xmlDoc.getElementsByTagName("catalog");
	
	for(i = 0; i < x.length; i++)
	{
		
		table += "<a href='#' id='" + x[i].getElementsByTagName("name")[0].textContent + "' class='catalog-select-element unselected'>" 
			+ x[i].getElementsByTagName("name")[0].textContent + "</a>";
		
	}
	table += "";

	document.getElementById("catalogPlaceHolder").innerHTML = table;
}

function initCatalogs()
{
	
	var xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.addEventListener('load', function(event) {
		
		if(xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200)
		{
			getCatalogs(this);
		}
	});

	xmlHttpRequest.open("GET", "CatalogServlet", true);
	xmlHttpRequest.setRequestHeader('Content-Type', 'text/xml');	
	xmlHttpRequest.send();
}

