package mqttSubPub;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import SOMain.SOM;

public class MqttSubscribe implements MqttCallback {

	public static void connect(/* TODO PASSAR O TÓPICO POR AQUI */) {
		/* ADICIONAR TÓPICO AQUI */
		String topic = "driver";
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
		MqttSubscribe.connect();
	}

	public void deliveryComplete(IMqttDeliveryToken arg0) {
		System.out.println("delivery complete");
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		String msgStr = new String(message.getPayload());
		String[] msgWords = msgStr.split(" ");

		System.out.println("topic: " + topic);
		System.out.println("message: " + msgStr);

		if (msgWords[0].contentEquals("request")) {
			SOM som = SOM.getSOM();
			som.driverRequest(msgWords[1], msgWords[2]);
		}

	}

}