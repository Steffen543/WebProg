package rfc;
import java.util.Collection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QueMessage extends JSONObject {

	public QueMessage(String question, Collection<String> answers) throws JSONException
	{
		put("Type", RFC.TYPE_QUE);
		put("Question", question);
		
		
		
		JSONArray answerArray = new JSONArray();
		
		if(answers != null)
		{
			for(String a : answers)
			{
				answerArray.put(a);
			}
		}
		
		put("Answers", answerArray);
		
	}
	
}
