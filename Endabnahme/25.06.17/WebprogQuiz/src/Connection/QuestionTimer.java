package Connection;

import java.util.TimerTask;

public class QuestionTimer extends TimerTask {
	
	private boolean timeout = false;
	
	@Override
	public void run() {
		timeout = true;
	}

	public boolean getTimeout(){
		return timeout;
	}
}
