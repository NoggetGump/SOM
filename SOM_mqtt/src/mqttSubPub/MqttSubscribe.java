package mqttSubPub;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

import SOMain.SOM;

public class MqttSubscribe implements MqttCallback {

	public static void connect(String topic) {
		/* ADICIONAR TÓPICO AQUI */
		int qos = 2;
		String broker = "tcp://localhost:1884";
		String clientId = "SOMlistener";
		MemoryPersistence persistence = new MemoryPersistence();

		try {
			MqttAsyncClient client = new MqttAsyncClient(broker, clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();

			connOpts.setCleanSession(true);
			client.setCallback(new MqttSubscribe());
			System.out.println(clientId + " Connecting to broker: " + broker);
			IMqttToken token = client.connect(connOpts);
			token.waitForCompletion();
			System.out.println(clientId + ": Connected");
			Thread.sleep(1000);
			client.subscribe(topic, qos);
			System.out.println("Subscribed to topic: " + topic);
		} catch (Exception me) {
			if (me instanceof MqttException) {
				System.out.println("reason " + ((MqttException) me).getReasonCode());
			}
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
		}
	}

	public void connectionLost(Throwable arg0) {
		System.out.println("connection lost - trying to reconnect");
		MqttSubscribe.connect(SOM.TOPIC);
	}

	public void deliveryComplete(IMqttDeliveryToken arg0) {
		System.out.println("delivery complete");
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		String msgStr = new String(message.getPayload());
		
		JSONParser parser = new JSONParser();
		
		JSONObject jsonObject = (JSONObject) parser.parse(msgStr);
		
		if(jsonObject.get("action").toString().equalsIgnoreCase("getdriver"))
		{
			JSONObject body = (JSONObject) jsonObject.get("body");
			String deviceName = body.get("name").toString();
			String responseTopic = jsonObject.get("response_topic").toString();
			
			SOM.getSOM().driverRequest(responseTopic, deviceName);
		}
	}
}