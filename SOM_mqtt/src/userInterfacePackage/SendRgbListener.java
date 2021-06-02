package userInterfacePackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import actuatorsHandler.Actuator;
import userInterfacePackage.ControlPanel;

public class SendRgbListener implements ActionListener{
	
	SendRgbListener () {}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Actuator actuator;
		String R;
		String G;
		String B;
		ControlPanel cp;
		
		cp = ControlPanel.getControlPanel();
		
		actuator = (Actuator) cp.getSelectedSmarThing();
		R = cp.Rtext.getText();
		G = cp.Gtext.getText();
		B = cp.Btext.getText();
		
		cp.getMainFrame().requestFocus();
		
		if(actuator != null)
			actuator.sendRGB(R, G, B);
		else
			System.out.println("Register and select an actuator to send a command.");
			
	}
}