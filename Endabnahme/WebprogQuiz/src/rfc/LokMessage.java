package rfc;

import org.json.*;

public class LokMessage extends JSONObject {

	public LokMessage(boolean isAdmin, String username)
	{
		try {
			put("Type", RFC.TYPE_LOK);
			put("IsAdmin", isAdmin);
			put("Username", username);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
	}
}
