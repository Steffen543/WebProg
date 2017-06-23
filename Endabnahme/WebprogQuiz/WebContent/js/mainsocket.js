document.addEventListener('DOMContentLoaded', init, false);

var socket;
var readyToSend = false;
var isAdmin = 0;


function init()
{
	var loginButton = window.document.getElementById("loginButton");
	loginButton.addEventListener("click", sendLRQ, false);
	
	var startButton = window.document.getElementById("startGameButton");
	startButton.addEventListener("click", sendSTG, false);
	
	var sendQuestionButton = window.document.getElementById("sendQuestionButton");
	sendQuestionButton.addEventListener("click", sendQAN, false);
	
	var url = "ws://localhost:8080/WebprogQuiz/mainsocket";
	socket = new WebSocket(url);
	
	socket.onopen = socketSendReady;
	socket.onclose = socketClosing;
	socket.onerror = socketError;
	socket.onmessage = socketReceive;
	
	
}



function socketSendReady() { readyToSend = true; }

function socketClosing(event){  }

function socketError(event) { alert("socket error"); }

function socketReceive(message)
{
	var answer = JSON.parse(message.data);
	switch(answer.Type) {
     
	 	case TYPE_LOK:
	 		reiceiveLogin(answer);
	     	break;
	 	case TYPE_PLT:
	 		receiveLST(answer);
	     	break;	
     	case TYPE_CCH:
	     	receiveCCH(answer);
	     	break;
     	case TYPE_GOV:
	     	receiveGOV(answer);
	     	break;	
     	case TYPE_STG:
	     	receiveSTG(answer);
	     	break;
     	case TYPE_QUE:
	     	 receiveQUE(answer);
	         break;
	         
	    case TYPE_QRE:
	     	receiveQRE(answer);
	     	break;
	     case 255:
		    alert(answer.Message);
	     	break;
	     default:
	    	 alert(answer.Type + " unknown");
	    	 break;
	 }
}

function readLoginAnswer(answer)
{
	
}







