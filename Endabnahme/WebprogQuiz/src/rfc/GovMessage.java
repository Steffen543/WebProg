package rfc;

import org.json.JSONException;
import org.json.JSONObject;

public class GovMessage extends JSONObject {

	public GovMessage(String message) throws JSONException
	{
		put("Type", RFC.TYPE_GOV);
		put("Message", message);
	}
}
