package SOMain;

import java.util.ArrayList;
import java.util.List;

public class TestingDevices {
	
	static String deviceName = "CC2650SensorTag";
	static String deviceUUID = "f000aa01-0451-4000-b000-000000000000";
	static String enableCode = "1";
	static ArrayList<String> sensorsNames = new ArrayList<>( 
            List.of("temperature",
                    "accelerometer"));
	static ArrayList<String> sensorsUUIDs = new ArrayList<>( 
            List.of("f000aa01-0451-4000-b000-000000000000",
            		"f000aa21-0451-4000-b000-000000000000"));
	static ArrayList <String> sensorsFunctions = new ArrayList<>( 
            List.of("TODO1",
            		"TODO2"));
	
	public static BLEdevice testDevice = new BLEdevice(deviceName, deviceUUID, enableCode,
			sensorsNames, sensorsUUIDs, sensorsFunctions);

}