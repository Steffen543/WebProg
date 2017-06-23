package Connection;

import org.json.*;

import javax.websocket.server.ServerEndpoint;

import de.fhwgt.quiz.application.Player;
import de.fhwgt.quiz.application.Question;
import de.fhwgt.quiz.application.Quiz;
import de.fhwgt.quiz.error.QuizError;
import rfc.GovMessage;
import rfc.LokMessage;
import rfc.LstMessage;
import rfc.QreMessage;
import rfc.QueMessage;
import rfc.RFC;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.io.IOException;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;

@ServerEndpoint("/mainsocket")
public class MainSocket {

	private ConcurrentMap<Session, Player> sessionPlayerLink = new ConcurrentHashMap<Session, Player>();
	private ConcurrentMap<Session, QuestionTimer> sessionTimerLink = new ConcurrentHashMap<Session, QuestionTimer>();
	
	
	@OnOpen
	public void open(Session session, EndpointConfig conf)
	{
		System.out.println("open socket " + session.getId());
		ConnectionManager.AddSession(session);
	}
	
	@OnError 
	public void onError(Throwable t) throws IOException{
		
	}
	
	@OnClose
	public void close(Session session, CloseReason reason) 
	{
		try {
			
			System.out.println("closing socket " + session.getId());
			ConnectionManager.RemoveSession(session);
			
			Player removePlayer = sessionPlayerLink.get(session);
			if(removePlayer != null)
			{
				if(removePlayer.isSuperuser())
				{
					GovMessage gov = new GovMessage("Spielleiter hat das Spiel verlassen");
					 for(Session s : ConnectionManager.socketList)
					 {
						 sendMessage(s, gov.toString(), true);
					 }
					 
				}
					
				
				QuizError error = new QuizError();
				Quiz.getInstance().removePlayer(removePlayer, error);
				sessionPlayerLink.remove(session);
				if(error.isSet())
				{
					System.out.println("error: " + error.getDescription());
				}
				
			}
			session.close();
			sendLST();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@OnMessage
	public void OnMessage(Session session, String msg) throws IOException{
		
		try{
			JSONObject obj = new JSONObject(msg);
			
			int type = RFC.GetRfcType(obj);
			System.out.println("received " + RFC.GetRfcTypeName(type) + " from s[" + session.getId() + "]");
			
			switch(type)
			{
				case RFC.TYPE_LRQ:
					handleLRQ(obj, session);
					break;
				case RFC.TYPE_CCH:
					handleCCH(obj, session);
					break;
				case RFC.TYPE_STG:
					handleSTG(obj, session);
					break;
				case RFC.TYPE_QRQ:
					handleQRQ(obj, session);
					break;
				case RFC.TYPE_QAN:
					handleQAN(obj, session);
					break;
			}
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			session.close();
		}
	}
	
	
	

	private String checkUsername(String name)
	{
		String infoText = "";
		if (name == null) {
			infoText = "Kein Username gefunden";
		}
		else if(name.length() < 3) {
			infoText = "Name muss mindestens 3 Zeichen lang sein";
			
		}
		else if (name.equals("")) {
			infoText = "Username darf nicht leer sein";
			
		}
		return infoText;
	}
	
	private void handleQAN(JSONObject qrqJson, Session session)
	{
		try
		{
			Player player = sessionPlayerLink.get(session);
			if(player == null)
				return;
			
			if(player.isDone()){
				return;
			}
			
			QuizError error = new QuizError();
			int rightAnswer = (int) Quiz.getInstance().answerQuestion(player, qrqJson.getInt("Answer"), error);
			
			if(error.isSet())
			{
				System.out.println("handleQAN error: " + error.getDescription());
				return;
			}
			
			boolean timeout = (sessionTimerLink.get(session)).getTimeout();
			QreMessage qre = new QreMessage(rightAnswer, timeout);
			sendMessage(session, qre.toString(), true);
			sendLST();
			
		}
		catch(Exception ex)
		{
			System.out.println("handleQAN error: " + ex.getMessage());
		}
		
	}
	
	private void handleQRQ(JSONObject qrqJson, Session session) throws JSONException, IOException
	{
		QuestionTimer timer = new QuestionTimer() ;

		Player player = sessionPlayerLink.get(session);
		if(player == null)
			return;
		
		QuizError error = new QuizError();
		Question currentQuestion = Quiz.getInstance().requestQuestion(player, timer, error);
		
		if(error.isSet())
		{
			System.out.println("handleQRQ error: " + error.getDescription());
			return;
		}
		
		if(sessionTimerLink.containsKey(session))
			sessionTimerLink.replace(session, timer);
		else
			sessionTimerLink.putIfAbsent(session, timer);
		
		
		if(currentQuestion != null)
		{
			QueMessage que = new QueMessage(currentQuestion.getQuestion(), currentQuestion.getAnswerList());
			sendMessage(session, que.toString(), true);
		}
		else
		{
			/* player is finish */
			boolean gameIsOver = Quiz.getInstance().setDone(player);
			
			if(gameIsOver)
			{
				System.out.println("game is over");
				
				List<Player> players = new ArrayList<Player>(Quiz.getInstance().getPlayerList()); 
				Collections.sort(players, new PlayerComperator());
				Player winner = players.get(0);
				
				System.out.println(players.get(0).getName() + " won the game");
				
				GovMessage gov = new GovMessage(winner.getName() + " hat das Spiel gewonnen");
				/* send game over message to all clients */
				Collection<Session> sessions = ConnectionManager.socketList;
				for(Session s : sessions)
				{
					sendMessage(s, gov.toString(), true);
				}
			}
			else
			{
				QueMessage que = new QueMessage(null, null);
				sendMessage(session, que.toString(), true);
			}
		}
		
		
	}
	
	private void handleSTG(JSONObject stgJson, Session session) throws IOException, JSONException
	{
		if(Quiz.getInstance().getCurrentCatalog() == null)
		{
			sendErrorMessage(session, "Es ist kein Katalog ausgewählt", true, false);
			return;
		}
			

		Player player = sessionPlayerLink.get(session);
		if(player == null) return;
		
		QuizError error = new QuizError();
		Quiz.getInstance().startGame(player, error);
		
		if(error.isSet())
		{
			sendErrorMessage(session, error.getDescription(), true, false);
			return;
		}
		
		
		Collection<Session> sessions = ConnectionManager.socketList;
		for(Session s : sessions)
		{
			sendMessage(s, stgJson.toString(), true);
		}
	}
	
	private void handleCCH(JSONObject cchJson, Session session)
	{
		
		try {
			if(sessionPlayerLink.get(session) == null || !sessionPlayerLink.get(session).isSuperuser())
			{
				sendErrorMessage(session, "Keine Berechtigung zum starten", true, false);
				return;
			}

			
			Player player = sessionPlayerLink.get(session);
			QuizError error = new QuizError();
			Quiz.getInstance().changeCatalog(player, cchJson.getString("CatalogName"), error);
			if(error.isSet())
			{
				System.out.println("handleCCH error: " + error.getDescription());
			}
			else
			{
				Collection<Session> sessions = ConnectionManager.socketList;
				for(Session s : sessions)
				{
					sendMessage(s, cchJson.toString(), true);
				}
			}
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
			
		
	}

	private void handleLRQ(JSONObject loginJSON, Session session) throws JSONException, IOException
	{
		Quiz quiz = Quiz.getInstance();
		QuizError error = new QuizError();
		
		String name = loginJSON.getString("Name");
		String check = checkUsername(name);
		if(check != "")
		{
			sendErrorMessage(session, check, true, false);
			return;
		}
		Player newPlayer = quiz.createPlayer(name, error);
		if(error.isSet())
		{
			sendErrorMessage(session, error.getDescription(), true, false);
			return;
		}
		
		/* connect the player & session to the list */
		sessionPlayerLink.putIfAbsent(session, newPlayer);
		
		JSONObject lok = new LokMessage(newPlayer.isSuperuser(), newPlayer.getName());
		
		sendMessage(session, lok.toString(), true);
		
		sendLST();
		
	}
	
	
	 private void sendErrorMessage(Session session, String msg, boolean last, boolean fatal) throws IOException, JSONException{
		 
		 JSONObject errorAnswer = new JSONObject();
		 errorAnswer.put("Type", RFC.TYPE_ERR);
		 errorAnswer.put("Fatal", fatal);
		 errorAnswer.put("Message", msg);


		 sendMessage(session, errorAnswer.toString(), last);
	 }
	 
	 private void sendMessage(Session session, String msg, boolean last) throws IOException{
		 if(session.isOpen()) {
			 session.getBasicRemote().sendText(msg, last);
			 System.out.println("sended to s[" + session.getId() + "]: " + msg);
		 }
	 }
	
	 private void sendLST() throws JSONException, IOException
	 {
		 LstMessage lst = new LstMessage(Quiz.getInstance().getPlayerList());
		 Collection<Session> sessions = ConnectionManager.socketList;
		 for(Session s : sessions)
		 {
			 sendMessage(s, lst.toString(), true);
		 }
		 
	 }
}
