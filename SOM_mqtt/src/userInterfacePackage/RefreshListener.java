package userInterfacePackage;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RefreshListener {
	public MouseAdapter mouseAdapter;
	
	RefreshListener() {
		mouseAdapter = new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				
				ControlPanel.getContextnetClient().refreshDevicesList();
				
			}
			
			public void mouseReleased(MouseEvent e) {
				System.out.println("send STOP message");
			}
		};
	}

}
