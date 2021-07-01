package SOMain;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

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
		MqttSubscribe.connect();
		
/*		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			MqttPub.publish("whatever string for testing purposes ");
		}*/
	}
	
	public void driverRequest(String name, String version)  {
		String message = new String("requested name version\n");
		String driver = driverDB.GetDeviceDriver(name, version);
		
		message = message + driver;
		
		MqttPub.publish(message);
	}

	public void newDriverArrived(String driver, String name, String version)  {
		
		driverDB.InsertOrUpdateDriver(driver, name, version);
		
	}

	public void refreshDevicesList() {
		// TODO Auto-generated method stub
		
	}

	public void sendCommand(String jsonString, String selectedMhub) {
		// TODO Auto-generated method stub
		
	}

	public void sendDevice2SOM(String text, String text2, String text3, String text4) {
		// TODO Auto-generated method stub
		
	}

	public void sendDriver2SOM(String name, String version, String driver) {
		driverDB.InsertOrUpdateDriver(name, version, driver);
	}
}
