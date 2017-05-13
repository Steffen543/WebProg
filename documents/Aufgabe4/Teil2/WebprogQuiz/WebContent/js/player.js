var playerList = [];

function Player() {
	this.Name;
	this.Points;
}



(function () {
	 
	var loginButton = document.getElementById("loginButton");
	loginButton.addEventListener("click", function(eventArgs) {
		eventArgs.preventDefault();
		
		
		
	
		
		 //var dataForm = "username=" + document.getElementById("input_loginName").value;
		 //var xhr = new XMLHttpRequest();
		
		 /*$.get("Login", function(responseText) {   // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
             //$("#somediv").text(responseText);           // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
			 alert(responseText);
		 });*/
		 /*var username;
		 xhr.onreadystatechange = function() {
		    if (xhr.readyState == 4) {
		        var data = xhr.responseText;
		        username = data;
		        alert(data);
		    }
		 }
		 xhr.open('POST', 'Login', true);
		 xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		 xhr.send(dataForm);*/
	 
		 var button = event.target;
		 var data = new FormData(document.getElementById("loginForm"));
		 request = new XMLHttpRequest();
		 request.onreadystatechange = function() {
			
			 if (request.readyState == 4) {
		        alert(request.responseText);
			 }
		 };
		 request.open("POST", "Login", true);
		 request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		 request.send(data);	 
					 
				
		 
		
		function playerToTop(eventArgs) {
			var selectedCell = eventArgs.target;
			var selectedPlayerIndex;
			
			
			for (i = 0; i < playerList.length; i++) {
				if (playerList[i].Name === selectedCell.parentElement.cells[0].innerHTML) {
					// selectedPlayerIndex is the row index of the player which is clicked
					selectedPlayerIndex = i;
				}
			}
			
			var playerListFirstItem = playerList[0];
			// set the selected item to first index
			playerList[0] = playerList[selectedPlayerIndex];
            playerList[selectedPlayerIndex] = playerListFirstItem;
			
			drawPlayerList();
		}
		
		function drawPlayerList() {
			
			var table = document.getElementById("playerTable");

		
			while (table.rows.length > 0) {
				table.deleteRow(0);
			}
		
			 for (i = 0; i < playerList.length; i++) {
				 var newRow = table.insertRow();
				 newRow.className = "player-row";
				 newRow.addEventListener("click", playerToTop);
				 
				 newRow.insertCell(0).innerHTML = playerList[i].Name;
				 newRow.insertCell(1).innerHTML = playerList[i].Points;
			 }
            
            if(playerList.length > 1) {
                document.getElementById("startGameButton").style.display = "block";        
            }
		
		}
		
		
		
		/*var playername = document.getElementById("input_loginName").value;
		var login = login(playername);
		
		if(login)
		{
			drawPlayerList();
            document.getElementById("input_loginName").value = "";
		}
		*/
	});
})();

