package rfc;

import org.json.JSONException;
import org.json.JSONObject;

public class RFC {
	public final static int TYPE_LRQ = 1;
	public final static int TYPE_LOK = 2;
	public final static int TYPE_CRQ = 3;
	public final static int TYPE_CRE = 4;
	public final static int TYPE_CCH = 5;
	public final static int TYPE_PLT = 6;
	public final static int TYPE_STG = 7;
	public final static int TYPE_QRQ = 8;
	public final static int TYPE_QUE = 9;
	public final static int TYPE_QAN = 10;
	public final static int TYPE_QRE = 11;
	public final static int TYPE_GOV = 12;
	public final static int TYPE_ERR = 255;
	
	public static int GetRfcType(JSONObject object)
	{
		try {
			int type = (int) object.getInt("Type");	
			
			switch(type)
			{
				case 1:
					return RFC.TYPE_LRQ;
				case 5:
					return RFC.TYPE_CCH;
				case 7:
					return RFC.TYPE_STG;
				case 8:
					return RFC.TYPE_QRQ;	
				case 10:
					return RFC.TYPE_QAN;	
				default: return 255;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 255;
		}
		
		
	}

	public static String GetRfcTypeName(int type)
	{
		switch(type)
		{
			case 1: return "LRQ";
			case 2: return "LOK";
			case 3: return "CRQ";
			case 4: return "CRE";
			case 5: return "CCH";
			case 6: return "PLT";
			case 7: return "STG";
			case 8: return "QRQ";
			case 9: return "QUE";
			case 10: return "QAN";
			case 11: return "QRE";
			case 12: return "GOV";
			case 255: return "ERR";
		}
		return "unknown";
	}

}
