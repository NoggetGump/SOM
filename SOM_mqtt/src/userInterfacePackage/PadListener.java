package userInterfacePackage;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import actuatorsHandler.Actuator;

public class PadListener {
	public MouseAdapter mouseAdapter;
	
	PadListener(char heading) {
		mouseAdapter = new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				Actuator actuator = null;
				actuator = (Actuator) ControlPanel.getControlPanel().getSelectedSmarThing();
				
				if(actuator != null)
					actuator.move(heading);
				
				else { 
					JOptionPane.showMessageDialog(ControlPanel.getControlPanel().getMainContainer()
							, "First select an actuator...");
				}
			}
			
			public void mouseReleased(MouseEvent e) {
				System.out.println("send STOP message");
			}
		};
	}
}
