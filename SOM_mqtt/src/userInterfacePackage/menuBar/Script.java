package userInterfacePackage.menuBar;

import java.io.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import actuatorsHandler.Actuator;
import userInterfacePackage.ControlPanel;
import userInterfacePackage.menuBar.Consts;

class CmdPair{
	  String cmd;
	  short nArgs;
	}

public class Script {
	
	private File script;
	private boolean isEmpty = true;;
	private boolean cmd_flag = false;
	private boolean arg_flag = false;
	private boolean value_flag = false;
	private ArrayList<CmdPair> cmds = new ArrayList<CmdPair>();
	private ArrayList<String> args = new ArrayList<String>();
	private ArrayList<String> values = new ArrayList<String>();
	private CmdPair currentCmd = null;
	private final static int FIRST = 0;
	
	public Script(String name) throws IOException{
		File dir = new File(System.getProperty("user.dir") + File.separator +"scripts");
		if(!dir.exists())
			dir.mkdir();
		script = new File(System.getProperty("user.dir") + File.separator + "scripts" + File.separator + name);
		
		if (script.createNewFile())	{
		    System.out.println("File is created!");
		} else {
		    System.out.println("File already exists.");
		}
	}
	
	public void editScript() throws IOException {
		DesktopApi.edit(script);
		//TODO subject to change
		isEmpty = false;
	}
	
	public void runScript() throws IOException {
		FileReader fis = new FileReader(script);
        StringBuilder word = new StringBuilder(new String());
        String query;
        int  character = 0;
        int line = 1;
        
		Actuator actuator = ControlPanel.getControlPanel().getSelectedSmarThing();
		
		if(actuator != null) {
	        while(character != Consts.END && ((character = fis.read()) != -1)) {	        	        	
	        	switch(character) {
	        		case Consts.LINEBREAK:
	            		judgeWord(word.toString());
	        			line++;
	        			word.setLength(0);
	        			break;
	        			
	        		case Consts.SPACE:
	            		judgeWord(word.toString());
	            		word.setLength(0);
	        			break;
	        			
	        		case Consts.TAB:
	            		judgeWord(word.toString());
	            		word.setLength(0);
	        			break;
	        			
	        		case Consts.ARGS_END:
	        			judgeWord(word.toString());
	        			cmd_flag = false;
	        			arg_flag = false;
	        			currentCmd = null;
	        			word.setLength(0);
	        			break;
	        			
	        		case Consts.END:
	        			judgeWord(word.toString());
	        			break;
	        		
	        		case Consts.ERROR:
	        			//TODO popup message ERROR - Script mistake at line @line
	        			System.out.println("Mistake at line: " + line);
	        			break;
	        			
	        		default:
	        			word.append((char)character);
	        			break;
	        	}
	        }
			arg_flag = false;
			cmd_flag = false;
			word.setLength(0);
			currentCmd = null;
			fis.close();
			
			query = generateMactQuery (actuator.toString(), "some label");
			
			actuator.sendCustoMactQuery(query);
		}
		else { 
			JOptionPane.showMessageDialog(ControlPanel.getControlPanel().getMainContainer()
					, "First select an actuator...");
		}
	}

	private void judgeWord(String word) {
		if(!word.isEmpty()) {
			if(word.equals(Consts.CMD))
				cmd_flag = true;
			else if(cmd_flag == true && word.equals(Consts.ARGS)) {
				arg_flag = true;
			}
			else if( cmd_flag == true && arg_flag == false) {
				CmdPair cmd = new CmdPair();
				
				cmd.cmd = word;
				cmd.nArgs = 0;
				cmds.add(cmd);
				currentCmd = cmd;
			}
			else if(cmd_flag == true && arg_flag == true && value_flag == false) {
				args.add(word);
				currentCmd.nArgs++;
				value_flag = true;
			}
			else if(value_flag == true){
				values.add(word);
				value_flag = false;
			}
		}
	}
	
	private String generateMactQuery (String deviceName, String deviceLabel) { 
		String mactQuery = new String();
		
		//TODO USE STRING BUFFER TO DO THIS - IS MORE EFFICIENT -
		// - nog
		mactQuery = "[ {\n"//open Json curly brackets 1
				+ "\t\"MACTQuery\" :\n"
				+ "\t{\n" //open MACTQuery curly brackets 1
				+ "\t\t\"type\" : \"cmd\",\n"
				+ "\t\t\"label\" : \"someLabel\",\n"
				+ "\t\t\"device\" : \"" + deviceName + "\"" + ",\n"
				+ "\t\t\"id\" : " + ControlPanel.getSOM().myUUID + ",\n"
				+ "\t\t\"cmds\" : [\n"; //open brackets to cmds 1
		while(!cmds.isEmpty()) {
			CmdPair cmd = cmds.remove(FIRST);
			
			mactQuery = mactQuery + "\t\t{\n" //opens curly brackets to command 2
					+ "\t\t\t\"cmd\" : \"" + cmd.cmd + "\",\n"
					+ "\t\t\t\"args\" : { "; //open curly brackets to args 3
			while(cmd.nArgs != 0) {
				cmd.nArgs--;
				mactQuery = mactQuery + "\"" + args.remove(FIRST) + "\" : "
						+ values.remove(FIRST);
				if(cmd.nArgs != 0)
					mactQuery = mactQuery + ", ";
			}
			mactQuery = mactQuery + " }\n" //close curly brackets to args 3
					+ "\t\t}"; //close curly brackets to command 2
			if(!cmds.isEmpty())
				mactQuery = mactQuery + ", ";
		}
		
		mactQuery = mactQuery + "]\n" //close brackets to cmds 1
				+ "\t}\n" //close MACTQuery curly brackets 1
				+ "} ]"; //close Json curly brackets 1
		
		return mactQuery;
	}

	public boolean isEmpty() {
		return isEmpty;
	}
}