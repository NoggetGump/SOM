package SOMain;

import java.util.concurrent.TimeUnit;

import mqttSubPub.MqttPub;
import mqttSubPub.MqttSubscribe;

public class SOM {
	
	/**
	 * Função principal de inicialização do SmartObjects Manager
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		MqttSubscribe.start();
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MqttPub.publish(TestingDevices.testDevice.toJsonMessage());
	}
	
}