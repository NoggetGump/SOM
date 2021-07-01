package userInterfacePackage.menuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RunScriptListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		String number = e.getActionCommand();
		Integer scriptIndex;
		
		number = number.substring(7);
		scriptIndex = Integer.parseInt(number) - 1;
		try {
			MyMenuBar.scripts.get(scriptIndex).runScript();
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

