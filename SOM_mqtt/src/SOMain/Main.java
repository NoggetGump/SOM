package SOMain;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
	public static void main(String[] args) {
		Logger.getLogger("").setLevel(Level.OFF);
		/**
		 * creating SOM and running it
		 */
		SOM som = SOM.getSOM();
	}
}
