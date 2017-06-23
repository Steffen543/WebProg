package rfc;

import org.json.JSONException;
import org.json.JSONObject;

public class QreMessage extends JSONObject{

	public QreMessage(int rightanswer, boolean timeout) throws JSONException
	{
		put("Type", RFC.TYPE_QRE);
		put("RightAnswer", rightanswer);
		put("Timeout", timeout);
	}
}
