package Connection;

import java.util.Comparator;

import de.fhwgt.quiz.application.Player;

public class PlayerComperator implements Comparator<Player> {
	@Override
	public int compare(Player player1, Player player2) {
		int p1Score = (int)player1.getScore();
		int p2Score = (int)player2.getScore();
			    	
		if(p1Score > p2Score)
			return -1;
		else if(p1Score < p2Score)
			return 1;
		else
			return 0;
	}
}
