var localPlayerList = [];

function Player() {
	this.Name;
	this.Points;
}

function playerToTop(eventArgs) {
	var selectedCell = eventArgs.target;
	var selectedPlayerIndex;
	
	
	for (i = 0; i < localPlayerList.length; i++) {
		if (localPlayerList[i].Name === selectedCell.parentElement.cells[0].innerHTML) {
			// selectedPlayerIndex is the row index of the player which is clicked
			selectedPlayerIndex = i;
		}
	}
	
	var playerListFirstItem = localPlayerList[0];
	// set the selected item to first index
	localPlayerList[0] = localPlayerList[selectedPlayerIndex];
	localPlayerList[selectedPlayerIndex] = playerListFirstItem;
	
	drawPlayerList();
}

function drawPlayerList() {
	
	var table = document.getElementById("playerTable");

	var oldTableRows = document.getElementsByClassName("player-row");
	while (oldTableRows[0]) {
		oldTableRows[0].parentNode.removeChild(oldTableRows[0]);
	}

	 for (i = 0; i < localPlayerList.length; i++) {
		 var newRow = table.insertRow();
		 newRow.className = "player-row";
		 newRow.addEventListener("click", playerToTop);
		 
		 newRow.insertCell(0).innerHTML = localPlayerList[i].Name;
		 newRow.insertCell(1).innerHTML = localPlayerList[i].Points;
	 }
    
    if(playerList.length > 1) {
        document.getElementById("startGameButton").style.display = "block";        
    }

}

