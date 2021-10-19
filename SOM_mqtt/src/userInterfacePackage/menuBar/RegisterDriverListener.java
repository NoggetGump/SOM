package userInterfacePackage.menuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import userInterfacePackage.ControlPanel;

public class RegisterDriverListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		ControlPanel cp = ControlPanel.getControlPanel();
		JFileChooser fc = new JFileChooser();
		
		fc.setCurrentDirectory(null);
		fc.setDialogTitle("Carregar driver...");
		
		if(fc.showOpenDialog(cp.getMainContainer()) == JFileChooser.APPROVE_OPTION) {
			File driverFile = fc.getSelectedFile();
			byte[] data = new byte[(int) driverFile.length()];
			FileInputStream fis = null;
			String driver = null;
			
			try {
				fis = new FileInputStream(driverFile);
			} catch (FileNotFoundException e2) {
				e2.printStackTrace();
				System.out.println(e2.toString());
				return;
			}
			
			try {
				fis.read(data);
				fis.close();
			} catch (IOException e1) {
				// TODO - Error, couldn't read from the file!
				e1.printStackTrace();
				System.out.println(e1.toString());
				return;
			}
			
			try {
				driver = new String(data, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
				System.out.println(e1.toString());
				try {
					driver = new String(data, "ASCII");
				} catch (UnsupportedEncodingException e2) {
					//TODO - Error, encoding not supported!
					e2.printStackTrace();
					System.out.println(e1.toString());
					return;
				}
			}
			
			System.out.println(driver);
			
			// ASK FOR OTHER FIELDS OF OPERATION			
			JTextField name = new JTextField(5);
			JTextField version = new JTextField(5);
			int result;
			
			JPanel myPanel = new JPanel();
			myPanel.add(Box.createVerticalStrut(15)); // a spacer
			myPanel.add(new JLabel("Name:"));
			myPanel.add(name);
			myPanel.add(Box.createVerticalStrut(15)); // a spacer
			myPanel.add(new JLabel("Version:"));
			myPanel.add(version);
			myPanel.add(Box.createVerticalStrut(15)); // a spacer
			
			
			result = JOptionPane.showConfirmDialog(null, myPanel,
					"Please Enter All Data ", JOptionPane.OK_CANCEL_OPTION);
			
			String nameString = name.getText();
			String versionString = version.getText();
			
			if (result == JOptionPane.OK_OPTION) {
				if(driver != null
					&& !nameString.equals("")
					&& !versionString.equals("")) {
					
						ControlPanel.getSOM().
						sendDriver2SOM(nameString, versionString, driver);
						
						return;
				}
				else {
					JOptionPane.showMessageDialog(cp.getMainContainer()
							, "All fields are mandatory and driver must be valid!");
				}
			}
		}
		JOptionPane.showMessageDialog(cp.getMainContainer()
				, "User canceled driver upload operation");
		return;
	}
}
