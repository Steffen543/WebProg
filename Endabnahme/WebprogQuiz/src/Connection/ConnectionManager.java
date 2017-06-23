package Connection;

import java.util.ArrayList;

import javax.websocket.Session;

public class ConnectionManager {
	public static final ArrayList<Session> socketList = new ArrayList<Session>();
	
	public static synchronized String outputAllSessions() 
	{ 
		return socketList.toString(); 
	}
	
	public static synchronized Session GetSession(int i) 
	{ 
		return socketList.get(i); 
	}
	
	public static synchronized int SessionCount() 
	{
		return socketList.size(); 
	}
	
	public static synchronized void AddSession(Session session) 
	{ 
		socketList.add(session); 
	}
	
	public static synchronized void RemoveSession(Session session) 
	{ 
		socketList.remove(session); 
	}
	
}
