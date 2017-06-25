package rfc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.json.*;

import Connection.PlayerComperator;
import de.fhwgt.quiz.application.Player;
import de.fhwgt.quiz.application.Quiz;

public class LstMessage extends JSONObject {

	public LstMessage(Collection<Player> list) throws JSONException
	{
		put("Type", RFC.TYPE_PLT);
				
		JSONArray playerList = new JSONArray();
		
		List<Player> playerListSorted = new ArrayList<Player>(Quiz.getInstance().getPlayerList()); 
		Collections.sort(playerListSorted, new PlayerComperator());
		
		 
		for(Player p : playerListSorted)
		{
			 
			 JSONObject playerJSON = new JSONObject();
			 playerJSON.put("Name", p.getName());
			 playerJSON.put("Score", p.getScore());
			 
			 playerList.put(playerJSON);
		}
		 
		put("Playerlist", playerList);
		 
	}
}
