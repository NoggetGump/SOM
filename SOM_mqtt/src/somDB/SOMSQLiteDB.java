package somDB;

/*import static SOMain.SOMCommandHelper.atr_device_model;
import static SOMain.SOMCommandHelper.atr_device_type;
import static SOMain.SOMCommandHelper.atr_device_vendor;
import static SOMain.SOMCommandHelper.atr_driver;*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONObject;

public class SOMSQLiteDB implements IBaseDB {
	
	public Connection connection() throws SQLException {
		return DriverManager.getConnection("jdbc:sqlite:som.db");
	}
	
	public SOMSQLiteDB()
	{
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try (Connection connection = this.connection();
	            Statement statement = connection.createStatement()) {

            // criando tabelas
            statement.execute("CREATE TABLE IF NOT EXISTS TB_DRIVER ( "
            		+ "NAME TEXT NOT NULL,"
            		+ "VERSION TEXT NOT NULL,"
            		+ "DRIVER BLOB"
            		+ ")");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	

	@Override
	public String InsertOrUpdateDriver(String name, String version, String driver) {
		
		String sql = "INSERT OR REPLACE INTO TB_DRIVER(NAME, VERSION, DRIVER) VALUES(?,?,?)";
		
		try (Connection connection = this.connection();
				PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, name.toLowerCase());
			pstmt.setString(2, version.toLowerCase());
			pstmt.setString(3, driver);
			pstmt.executeUpdate();
			
		}
		catch (SQLException e) {
			return driver_insert_fail + e.getMessage();
		}
		
		return driver_insert_success;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String GetDeviceDriver(String name) {
		String sql = "SELECT DRIVER FROM TB_DRIVER "
				+ "WHERE NAME = ?";
		System.out.println(name + " from DB");
		try (Connection connection = this.connection();
				PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, name.toLowerCase());
			ResultSet resultSet = pstmt.executeQuery();
			if(resultSet.next()) {
				return resultSet.getString("DRIVER");
			}
			
			return null;
		}
		catch (Exception e) {
			return "Error";
		}
	}
}
