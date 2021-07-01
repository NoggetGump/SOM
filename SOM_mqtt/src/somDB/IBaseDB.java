package somDB;

import java.util.List;


/**
 * Interface que define os métodos para manipular um banco de dados de drivers e dispositivos do SOM
 * @author sheriton
 */
public interface IBaseDB {
	
	public final String driver_insert_success = "The driver has been successfully inserted";
	public final String driver_insert_fail = "Failed to insert the driver with error: ";
	
	public final String device_insert_success = "The device has been successfully inserted";
	public final String device_insert_fail = "Failed to insert the device with error: ";
	public final String device_delete_success = "The device has been successfully deleted";
	public final String device_delete_fail = "Failed to delete the driver with error: ";
	
	public final String connected_device_insert_success = "The connection of device to m-hub was successfully registered.";
	public final String connected_device_fail = "Failed to register the connection of device to m-hub with error: ";
	public final String disconnected_device_success = "The device was successfully removed from the connection table";
	public final String disconnected_device_failed = "Failed to remove the device from the connection table with error: ";
	
	/**
	 * Insere ou atualiza um novo driver no banco de dados
	 * 
	 * @param driver - driver do dispositivo em String
	 * @param deviceType - tipo do dipositivo (p.ex. Robot)
	 * @param deviceModel - modelo do dipositivo (p.ex. BB-8)
	 * @param deviceVendor - fabricante do dispositivo (p.ex. Sphero)
	 * @return string indicando sucesso ou o erro
	 */
	String InsertOrUpdateDriver(String name, String version, String driver);

	/**
	 * Obtem um driver compativel ao dispositivo
	 * @param macAddress
	 * @return objeto json { driver: "driver" }
	 */
	String GetDeviceDriver(String name, String version);
}
