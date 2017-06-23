function sendStart()
{
	
}

/**
 * sends a catalog change to the server
 * @param catalogName
 */
function sendCatalogChange(name)
{
	
	var cchJson = {
		Type : TYPE_CCH,
		CatalogName : name
	};
	
	if(readyToSend == true)
	{
		socket.send(JSON.stringify(cchJson));
	}
}

/**
 * sends a start game message to the server
 */
function sendSTG()
{
	if(isAdmin)
	{
		var startJson = {
			Type : TYPE_STG
		};
		
		if(readyToSend == true)
		{
			socket.send(JSON.stringify(startJson));
		}
	}
}


/**
 * sends a login request to the server
 * @param username
 */
function sendLRQ(name)
{
	var loginName = window.document.getElementById("input_loginName").value;
	var loginJson = {
		Type : TYPE_LRQ,
		Name : loginName
	};
	
	if(readyToSend == true)
	{
		socket.send(JSON.stringify(loginJson));
	}
}

function sendQRQ()
{
	var qrqJson = {
		Type : TYPE_QRQ
		};
	
	if(readyToSend == true)
	{
		socket.send(JSON.stringify(qrqJson));
	}
}

function sendQAN()
{
	var radioButtons = document.getElementsByName('sq');
	var selectedAnswer = 0;
	
	for (var i = 0, length = radioButtons.length; i < length; i++) {
	    if (radioButtons[i].checked) {
	        selectedAnswer = radioButtons[i].dataset.an;
	        break;
	    }
	}
	
	var qanJson = {
			Type : TYPE_QAN,
			Answer : selectedAnswer
			};

	if(readyToSend == true)
	{
		socket.send(JSON.stringify(qanJson));
	}
	
}

function receiveSTG(jsonObject)
{
	document.getElementById("gamePanel").style.display = "block";
	document.getElementById("startGamePanel").style.display = "none";   
	
	sendQRQ();
}

function receiveQUE(jsonObject)
{
	if(jsonObject.Question == null)
	{
		/* player is finish with his questions */
		document.getElementById("gamePanel").innerHTML = "Warten auf andere Spieler..";
		return;
	}
	
	
	
	document.getElementById("questionHeader").innerHTML = jsonObject.Question;
	
	/* reset the colors of the radio buttons */
	var radioButtons = document.getElementsByName('sq');
	for (var i = 0, length = radioButtons.length; i < length; i++) {
		radioButtons[i].parentElement.parentElement.style.backgroundColor  = "White";
    	radioButtons[i].parentElement.parentElement.style.color  = "#7c8081";
	
	}
	
	
	
	var i = 1;
	jsonObject.Answers.forEach(function(a) {
		
		document.getElementById("a" + i + "text").innerHTML = a;
		i++;
	});
	
}

function receiveQRE(jsonObject)
{
	var rightAnswer = jsonObject.RightAnswer;
	var radioButtons = document.getElementsByName('sq');
	for (var i = 0, length = radioButtons.length; i < length; i++) {
	    if (i == rightAnswer) {
	    	radioButtons[i].parentElement.parentElement.style.backgroundColor  = "Green";
	    	radioButtons[i].parentElement.parentElement.style.color  = "White";
	    }
	    if(i != rightAnswer && radioButtons[i].checked)
    	{
	    	radioButtons[i].parentElement.parentElement.style.backgroundColor  = "Red";
	    	radioButtons[i].parentElement.parentElement.style.color  = "White";
    	}
	}
	
	
	if(jsonObject.Timeout == true)
	{
		document.getElementById("timeoutPanel").style.display = "block";
	}

	var sendQuestionButton = window.document.getElementById("sendQuestionButton");
	sendQuestionButton.disabled = true;
	
	var timer = setInterval(function() {
		sendQRQ();
		sendQuestionButton.disabled = false;
		document.getElementById("timeoutPanel").style.display = "none";
		clearInterval(timer);		
	}, 3000);
	
	
}

function receiveCCH(jsonObject)
{
	var selectedCatalog = jsonObject.CatalogName;
	var catalogSelectLinks = document.getElementsByClassName("catalog-select-element");
	for (var i = 0; i < catalogSelectLinks.length; i++) {
		catalogSelectLinks[i].classList.remove("selected");
		catalogSelectLinks[i].classList.add("unselected");
	}
	document.getElementById(selectedCatalog).classList.remove("unselected");
	document.getElementById(selectedCatalog).classList.add("selected");
}

function reiceiveLogin(jsonObject)
{
	if(jsonObject.IsAdmin == true){
		alert("Sie sind als Spielleiter (" + jsonObject.Username + ") eingeloggt");
		document.getElementById("startGamePanel").style.display = "block";   
		isAdmin = true;
		
		/* add event listener to the category buttons */
		var catalogSelectLinks = document.getElementsByClassName("catalog-select-element");
		for (var i = 0; i < catalogSelectLinks.length; i++) {
		    catalogSelectLinks[i].addEventListener('click', function() {
		    	sendCatalogChange(this.id);
		    }, false);
		}
	}
	else {
		alert("Sie sind als " + jsonObject.Username + " eingeloggt"); 
	    
	}
	
	document.getElementById("loginPanel").style.display = "none";
	//document.getElementById("catalogPlaceHolder").style.display = "block";	
}

function receiveGOV(jsonObject)
{
	document.getElementById("gamePanel").innerHTML = jsonObject.Message;

}

function receiveLST(jsonObject)
{
	
	
	var table = document.getElementById("playerTable");
	/* clear old rows */
	var oldTableRows = document.getElementsByClassName("player-row");
	while (oldTableRows[0]) {
		oldTableRows[0].parentNode.removeChild(oldTableRows[0]);
	}

	jsonObject.Playerlist.forEach(function(p) {
		
		 var newRow = table.insertRow();
		 newRow.className = "player-row";
		 
		 newRow.insertCell(0).innerHTML = p.Name;
		 newRow.insertCell(1).innerHTML = p.Score;
	});
}