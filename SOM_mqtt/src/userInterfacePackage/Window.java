package	userInterfacePackage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;

import	javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window	extends	JFrame{
	
	@SuppressWarnings("unused")
	private Window window;
	private Set<Integer> pressed = new HashSet<>();
	
	public Window(){
		window = this;
		
		this.requestFocus();
		//WINDOW JFRAME OPTIONS
		this.setTitle("Control Unit");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		this.setSize(370, 435);
		//window.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLUE));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				window.requestFocus();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				pressed.add(e.getKeyCode());
				if(pressed.size() >= 1) {
					ControlPanel cp;
					
					cp = ControlPanel.getControlPanel();
					for(Integer c : pressed) {
						switch(c) {
							case KeyEvent.VK_LEFT:
								if(pressed.contains(KeyEvent.VK_RIGHT))
									break;
								//cp.ctxnetClient.sendCommand("LEFT");
								System.out.println("send LEFT message");
								break;
								
							case KeyEvent.VK_RIGHT:
								if(pressed.contains(KeyEvent.VK_LEFT))
									break;
								//cp.ctxnetClient.sendCommand("RIGHT");
								System.out.println("send RIGHT message");
								break;
								
							case KeyEvent.VK_UP:
								if(pressed.contains(KeyEvent.VK_DOWN))
									break;
								//cp.ctxnetClient.sendCommand("UP");
								System.out.println("send UP message");
								break;
								
							case KeyEvent.VK_DOWN:
								if(pressed.contains(KeyEvent.VK_UP))
									break;
								//cp.ctxnetClient.sendCommand("DOWN");
								System.out.println("send DOWN message");
								break;
								
							case KeyEvent.VK_ENTER:
								
								//cp.ctxnetClient.sendCommand("ENTER");
								System.out.println("R - " + cp.Rtext.getText());
								System.out.println("G - " + cp.Gtext.getText());
								System.out.println("B - " + cp.Btext.getText());
								break;
								
							default:
								break;
						}
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				pressed.remove(key);
				if(pressed.isEmpty() && key != KeyEvent.VK_ENTER) {
					//cp.ctxnetClient.sendCommand("STOP");
					System.out.println("STOP");
				}
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
