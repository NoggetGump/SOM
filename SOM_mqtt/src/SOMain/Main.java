package SOMain;

import java.util.logging.Level;
import java.util.logging.Logger;

import mqttSubPub.MqttPub;
import userInterfacePackage.ControlPanel;

public class Main {
	public static void main(String[] args) {
		Logger.getLogger("").setLevel(Level.OFF);
		/**
		 * creating SOM and running it
		 */
		SOM som = SOM.getSOM();
		ControlPanel CP = ControlPanel.getControlPanel();
		MqttPub.publish("driver", "some shit for testing");
	}
}
