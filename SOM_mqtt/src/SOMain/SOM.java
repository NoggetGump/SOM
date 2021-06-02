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
	 * Função principal de inicialização do SmartObjects Manager
	 * 
	 * @param args
	 * 
	 */
	
	private static SOM som;
	
	private IBaseDB driverDB;
	
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
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void driverRequest(String name, String version)  {
		String message = new String("requested \n");
		String driver = driverDB.GetDeviceDriver(name, version);
		
		message = message + driver;
		
		MqttPub.publish(message);
	}

	public void newDriverArrived(String driver, String name, String version)  {
		
		driverDB.InsertOrUpdateDriver(driver, name, version);
		
	}
}
