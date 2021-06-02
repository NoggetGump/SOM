package userInterfacePackage;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

import actuatorsHandler.Actuator;
import userInterfacePackage.menuBar.MyMenuBar;

import javax.swing.JLabel;

//3C:71:BF:9D:1E:EF

public class ControlPanel{
	private static ControlPanel CP = null;
	
	private Window window = new Window();
	private Container mainPanel = window.getContentPane();
	private JComboBox<Actuator> smarThingsList = new JComboBox<Actuator>();
	private JComboBox<String> mHubsList = new JComboBox<String>();
	
	private ArrayList<MhubThings> associatedThings = new ArrayList<MhubThings>();
	
	private MyMenuBar menuBar = new MyMenuBar();
	
	JTextField Rtext;
	JTextField Gtext;
	JTextField Btext;
	
	class MhubThings{
		  String mhub;
		  ArrayList<Actuator> actuators;
		  
		  MhubThings() {
			  mhub = null;
			  actuators = new ArrayList<Actuator>();
		  }
		}

	/**
	 * Singleton, facade for UI purposes; generates a ControlPanel object, if it does not exist,
	 * and return the instance of it;
	 * Creates a Window component and adds its elements to construct 
	 * the GUI controller which controls the selected smart thing;
	 * 
	 * @param ControllerClient
	 * @return ControlPanel
	 * @throws IOException 
	 */
	public static ControlPanel getControlPanel() {		
		if(CP == null)
			CP = new ControlPanel();

		return CP;
	}
	
	/**
	 * Singleton; generates, if does not exist, and get instance of
	 * ContextNET ControllerClient Object stored in Control Panel;
	 *  
	 * @return
	 */
	
	public static ControllerClient getContextnetClient() {
		if(cc == null)
			cc = new ControllerClient();
			
		return cc;
	}
	
	public Window getMainFrame() { 
		return window;
	}
	
	public Container getMainContainer() {
		return mainPanel;
	}
	
	public void add2Lists(Actuator act, String hub) {
		for(MhubThings mht : associatedThings) {
			if(mht.mhub.equals(hub)) {
				mht.actuators.add(act);
				refreshLists(hub);
				return;
			}
		}
		
		MhubThings mhubThings = new MhubThings();
		
		mhubThings.mhub = hub;
		mhubThings.actuators.add(act);
		associatedThings.add(mhubThings);
		
		mHubsList.addItem(hub);
		
		refreshLists(hub);
	}

	public Actuator getSelectedSmarThing() {
		return (Actuator) smarThingsList.getSelectedItem();
	}
	
	public String getSelectedMhub() {
		return (String) mHubsList.getSelectedItem();
	}
	
	private void refreshLists(String newMhubSelected) {
		for(MhubThings mht : associatedThings) {
			if(mht.mhub.equals(newMhubSelected)) {
				smarThingsList.removeAllItems();
				
				for(Actuator act : mht.actuators) {
					smarThingsList.addItem(act);
				}
				
				return;
			}
		}
	}
	
	private JButton addAButton(String title, Container container, int width, int height, float alignment) {
		JButton button = new JButton(title);
		Dimension dimension = new Dimension(width, height);
		
		button.setMinimumSize(dimension);
		button.setPreferredSize(dimension);
		button.setMaximumSize(dimension);
		button.setAlignmentX(alignment);
		container.add(button);
		
		return button;
	}
	
	private JTextField addIntTextField(String text, Container container, float alignment, String label, Color color) {
		JTextField textField = new JTextField("", 25);
		JLabel jLabel = new JLabel(label);
		PlainDocument doc;
		
		jLabel.setForeground(color);
		textField.setMaximumSize(new Dimension(50, (int)textField.getPreferredSize().getHeight()));
		textField.setAlignmentX(Component.CENTER_ALIGNMENT);
		container.add(jLabel);
		container.add(textField);
		
		doc = (PlainDocument) textField.getDocument();
	    doc.setDocumentFilter(new MyIntFilter());
		
		return textField;
	}

	private ControlPanel() {
		
		mHubsList.addItemListener (new ItemListener () {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					refreshLists(getSelectedMhub());
				}
				
			}
		});
		
		//COMPONENTS THAT ARE PART OF THE WINDOW
		JPanel smarThings = new JPanel();
		JPanel mobileHubs = new JPanel();
		JPanel buttonPad = new JPanel();
		JPanel leftAndRightButtons = new JPanel();
		JPanel RGBFields = new JPanel();
		
		RefreshListener refreshListener = new RefreshListener();
		
		PadListener upButtonListener = new PadListener('f');
		PadListener leftButtonListener = new PadListener('l');
		PadListener rightButtonListener = new PadListener('r');
		PadListener downButtonListener = new PadListener('b');
		
		Dimension minSize = new Dimension(10, 10);
		Dimension prefSize = new Dimension(10, 10);
		Dimension maxSize = new Dimension(10, 10);
		
		JButton button;
		
		window.setJMenuBar(menuBar);
		
		//MAIN CONTAINER SET TO BOXLAYOUT (VERTICAL)
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		mainPanel.setBackground(Color.lightGray);
		mainPanel.add(new Box.Filler(minSize, prefSize, maxSize));
		
		//MOBLIE HUBS COMBO BOX
		mobileHubs.setLayout(new BoxLayout(mobileHubs, BoxLayout.LINE_AXIS));
		mobileHubs.setBackground(Color.lightGray);
		mobileHubs.add(new JLabel("Choose M Hub:"));
		minSize = new Dimension(130, 30);
		mHubsList.setMinimumSize(minSize);
		mHubsList.setMaximumSize(minSize);
		mobileHubs.add(mHubsList);
		mainPanel.add(mobileHubs);
		
		minSize = new Dimension(10, 10); prefSize = new Dimension(10, 10); maxSize = new Dimension(10, 10);  //NEW DIMENSIONS TO FILL
		mainPanel.add(new Box.Filler(minSize, prefSize, maxSize));//SPACE FILLING
		
		//SMART STUFF COMBO BOX
		smarThings.setLayout(new BoxLayout(smarThings, BoxLayout.LINE_AXIS));
		smarThings.setBackground(Color.lightGray);
		smarThings.add(new JLabel("Choose Device:"));
		minSize = new Dimension(130, 30);
		smarThingsList.setMinimumSize(minSize);
		smarThingsList.setMaximumSize(minSize);
		smarThings.add(smarThingsList);
		mainPanel.add(smarThings);
		
		minSize = new Dimension(10, 10); prefSize = new Dimension(10, 10); maxSize = new Dimension(10, 10);  //NEW DIMENSIONS TO FILL
		mainPanel.add(new Box.Filler(minSize, prefSize, maxSize));//SPACE FILLING

		//ADD REFRESH LISTS BUTTON
		button = addAButton("Refresh (Request SOM)", mainPanel, 200, 20, Component.CENTER_ALIGNMENT);
		button.addMouseListener(refreshListener.mouseAdapter);
		
		minSize = new Dimension(10, 10); prefSize = new Dimension(10, 10); maxSize = new Dimension(10, 10);  //NEW DIMENSIONS TO FILL
		mainPanel.add(new Box.Filler(minSize, prefSize, maxSize));//SPACE FILLING
		
		//BUTTON PAD
		buttonPad.setLayout(new BoxLayout(buttonPad, BoxLayout.PAGE_AXIS));
		//UP_BUTTON
		minSize = new Dimension(10, 10); prefSize = new Dimension(10, 10); maxSize = new Dimension(10, 10);  //NEW DIMENSIONS TO FILL
		buttonPad.add(new Box.Filler(minSize, prefSize, maxSize));//SPACE FILLING
		button = addAButton("▲", buttonPad, 50, 50, Component.CENTER_ALIGNMENT);
		button.addMouseListener(upButtonListener.mouseAdapter);
		//LEFT & RIGHT BUTTONS
		leftAndRightButtons.setLayout(new BoxLayout(leftAndRightButtons, BoxLayout.LINE_AXIS));
		minSize = new Dimension(10, 10); prefSize = new Dimension(10, 10); maxSize = new Dimension(10, 10);  //NEW DIMENSIONS TO FILL
		leftAndRightButtons.add(new Box.Filler(minSize, prefSize, maxSize));//SPACE FILLING
		button = addAButton("◀", leftAndRightButtons, 50, 50, Component.CENTER_ALIGNMENT);
		button.addMouseListener(leftButtonListener.mouseAdapter);
		minSize = new Dimension(50,50); prefSize = new Dimension(50,50); maxSize = new Dimension(50,50); //NEW DIMENSIONS TO FILL
		leftAndRightButtons.add(new Box.Filler(minSize, prefSize, maxSize)); //SPACE FILLING
		button = addAButton("▶", leftAndRightButtons, 50, 50, Component.CENTER_ALIGNMENT);
		button.setActionCommand(Constants.RIGHT_BUTTON);
		button.addMouseListener(rightButtonListener.mouseAdapter);
		minSize = new Dimension(10, 10); prefSize = new Dimension(5, 5); maxSize = new Dimension(10, 10);  //NEW DIMENSIONS TO FILL
		leftAndRightButtons.add(new Box.Filler(minSize, prefSize, maxSize));//SPACE FILLING
		buttonPad.add(leftAndRightButtons);
		//DOWN BUTTON
		button = addAButton("▼", buttonPad, 50, 50, Component.CENTER_ALIGNMENT);
		button.setActionCommand(Constants.DOWN_BUTTON);
		button.addMouseListener(downButtonListener.mouseAdapter);
		minSize = new Dimension(10, 10); prefSize = new Dimension(10, 10); maxSize = new Dimension(10, 10);  //NEW DIMENSIONS TO FILL
		buttonPad.add(new Box.Filler(minSize, prefSize, maxSize));//SPACE FILLING
		buttonPad.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.darkGray));
		mainPanel.add(buttonPad);
		
		minSize = new Dimension(10, 10); prefSize = new Dimension(10, 10); maxSize = new Dimension(10, 10);  //NEW DIMENSIONS TO FILL
		mainPanel.add(new Box.Filler(minSize, prefSize, maxSize));//SPACE FILLING
	
		//RGB FIELD TEXTS
		RGBFields.setLayout(new BoxLayout(RGBFields, BoxLayout.LINE_AXIS));
		RGBFields.setBackground(Color.lightGray);
		Rtext = addIntTextField("Red", RGBFields, Component.CENTER_ALIGNMENT, "Red:", Color.RED);
		minSize = new Dimension(20, 20); prefSize = new Dimension(20, 20); maxSize = new Dimension(20, 20); //NEW DIMENSIONS TO FILL
		RGBFields.add(new Box.Filler(minSize, prefSize, maxSize)); //SPACE FILLING
		Gtext = addIntTextField("Green", RGBFields, Component.CENTER_ALIGNMENT, "Green:", new Color(0,200,0));
		minSize = new Dimension(20, 20); prefSize = new Dimension(20, 20); maxSize = new Dimension(20, 20); //NEW DIMENSIONS TO FILL
		RGBFields.add(new Box.Filler(minSize, prefSize, maxSize)); //SPACE FILLING
		Btext = addIntTextField("Blue", RGBFields, Component.CENTER_ALIGNMENT, "Blue:", Color.BLUE);
		mainPanel.add(RGBFields);
		
		//RGB SEND BUTTON
		minSize = new Dimension(20, 20); prefSize = new Dimension(20, 20); maxSize = new Dimension(20, 20); //NEW DIMENSIONS TO FILL
		mainPanel.add(new Box.Filler(minSize, prefSize, maxSize)); //SPACE FILLING
		
		button = addAButton("Send RGB", mainPanel, 120, 25, Component.CENTER_ALIGNMENT);
		button.addActionListener(new SendRgbListener());
		
		window.revalidate();
		window.repaint();
	}
}