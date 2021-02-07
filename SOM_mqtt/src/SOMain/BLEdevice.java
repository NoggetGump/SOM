package SOMain;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@SuppressWarnings("unchecked")
public class BLEdevice {
	
	private static final String deviceName_tag = "deviceName";
	private static final String deviceUUID_tag = "deviceUUID";
	private static final String enableCode_tag = "enableCode";
	private static final String sensorsNames_tag = "sensorsNames";
	private static final String sensorsUUIDs_tag = "sensorsUUIDs";
	private static final String functions_tag = "convertFunctions";
	
	String deviceName;
	String deviceUUID;
	String enableCode;
	ArrayList<String> sensorsNames;
	ArrayList<String> sensorsUUIDs;
	ArrayList <String> sensorsFunctions;
	
	public BLEdevice() {
		
	}
	
	public BLEdevice(String device, String UUID, String enableCode,
			ArrayList<String> sensorsNames, ArrayList<String> sensorsUUIDs,
			ArrayList<String> sensorsFunctions) {
		
		this.deviceName = device;
		this.deviceUUID = UUID;
		this.enableCode = enableCode;
		this.sensorsNames = sensorsNames;
		this.sensorsUUIDs = sensorsUUIDs;
		this.sensorsFunctions = sensorsFunctions;
	}
	
	public String toJsonMessage() {
		
		JSONObject jsonMessage = new JSONObject();
		
		JSONArray sensors = new JSONArray();
		JSONArray sensorsUUIDs = new JSONArray();
		JSONArray sensorsFunctions = new JSONArray();
		
		jsonMessage.put(deviceName_tag, this.deviceName);
		jsonMessage.put(deviceUUID_tag, this.deviceUUID);
		jsonMessage.put(enableCode_tag, this.enableCode);
		
		for (int i = 0; i < this.sensorsNames.size(); i++) {
			sensors.add(this.sensorsNames.get(i));
			sensorsUUIDs.add(this.sensorsUUIDs.get(i));
			sensorsFunctions.add(this.sensorsFunctions.get(i));
		}

		jsonMessage.put(sensorsNames_tag, sensors);
		jsonMessage.put(sensorsUUIDs_tag, sensorsUUIDs);
		jsonMessage.put(functions_tag,sensorsFunctions);
		
		return jsonMessage.toJSONString();
	}
	
	public void extractFromMessage(String jsonMessage) {
		
		JSONObject  jsonObject= new JSONObject();
	    JSONParser jsonParser= new  JSONParser();
	    
	    if ((jsonMessage != null) && !(jsonMessage.isEmpty())) {
	    	
	        try {
	        	
	            jsonObject = (JSONObject) jsonParser.parse(jsonMessage);
	            
	            this.deviceName = (String) jsonObject.get(deviceName_tag);
	            this.deviceUUID = (String) jsonObject.get(deviceUUID_tag);
	            this.enableCode = (String) jsonObject.get(enableCode_tag);
	            
	    		JSONArray sensorsJson = (JSONArray) jsonObject.get(sensorsNames_tag);
	    		JSONArray sensorsUUIDsJson = (JSONArray) jsonObject.get(sensorsUUIDs_tag);
	    		JSONArray functionsJson = (JSONArray) jsonObject.get(functions_tag);

	            if(!sensorsJson.isEmpty()) {
	            	for (int i=0;i<sensorsJson.size();i++){ 
	            		this.sensorsNames.add((String) sensorsJson.get(i));
	            		this.sensorsUUIDs.add((String) sensorsUUIDsJson.get(i));
	            		this.sensorsFunctions.add((String) functionsJson.get(i));
	            	}
	            }
	            
	        } catch (org.json.simple.parser.ParseException e) {
	            e.printStackTrace();
	        }
	    }
	}
}
