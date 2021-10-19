package SOMain;

import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;

import mqttSubPub.MqttPub;
import mqttSubPub.MqttSubscribe;

import somDB.DBFactory;
import somDB.IBaseDB;

public class SOM {
	
	/**
	 * Arquivo principal de inicialização do Smart Objects Manager
	 * Facade cujas funções são chamadas pela UI
	 * 
	 * @param args
	 * 
	 */
	
	private static SOM som;
	
	private IBaseDB driverDB;

	public String myUUID;
	
	public static String TOPIC = "default";
	
	public static synchronized SOM getSOM() {
		
		if(som == null)
			som = new SOM();
		
		return som;
	}
	
	private SOM() {
		
		/** driver DB */
		driverDB = DBFactory.GetDriverDB();
		
		/**
		 * MQTT
		 * Se inscrevendo nos tópicos de interesse via MQTT.
		 */
		MqttSubscribe.connect(TOPIC);
		
	
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void driverRequest(String topic, String name)  {
		JSONObject drResponse = new JSONObject();
		JSONObject responseBody = new JSONObject();
		
		drResponse.put("action", "save");
		responseBody.put("wpan", "1");
		responseBody.put("name", name);
		responseBody.put("content", driverDB.GetDeviceDriver(name.toLowerCase()));
		drResponse.put("body", responseBody.toJSONString());
		
		MqttPub.publish(topic, drResponse.toString());
	}

	public void newDriverArrived(String driver, String name, String version)  {
		
		driverDB.InsertOrUpdateDriver(driver, name, version);
		
	}

	public void refreshDevicesList() {
		
	}

	public void sendCommand(String jsonString, String selectedMhub) {
		
	}

	public void sendDevice2SOM(String text, String text2, String text3, String text4) {
		
	}

	public void sendDriver2SOM(String name, String version, String driver) {
		driverDB.InsertOrUpdateDriver(name, version, driver);
	}
}
