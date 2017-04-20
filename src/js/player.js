var playerList = [];

function Player() {
	this.Name;
	this.Points;
}



(function () {
	 
	var loginButton = document.getElementById("loginButton");
	loginButton.addEventListener("click", function(eventArgs) {
		eventArgs.preventDefault();
		
		function playerNameAlreadyExists(playerName) {
			for(i = 0; i < playerList.length; i++)
			{
				if(playerList[i].Name == playerName)
					return true;
			}
			return false;
		}
		
		function login(playerName) {
			if (playerList.length >= 4) {
				alert("Keine Plätze mehr verfügbar");
				return false;
			}
			if (!playerName) {
				alert("Bitte geben Sie einen gültigen Namen ein");
				return false;
			}
			if (playerNameAlreadyExists(playerName)) {
				alert("Name leider schon vergeben");
				return false;
			}
			// all tests are okey, create player now
			var newPlayer = new Player();
			newPlayer.Name = playerName;
			newPlayer.Points = 0;
			playerList.push(newPlayer);
			return true;
        }
		
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
		
		
		
		var playername = document.getElementById("input_loginName").value;
		var login = login(playername);
		
		if(login)
		{
			drawPlayerList();
		}
	});
}());

