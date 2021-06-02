package somDB;

/**
 * Fabrica para criar um IBaseDB
 * @author sheriton
 *
 */
public final class DBFactory {
	private static final IBaseDB driverDB = new SOMSQLiteDB();
	public static IBaseDB GetDriverDB()
	{
		return driverDB;
	}
}