package de.zokki;

import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.JOptionPane;

import de.zokki.FileManager.XMLWriter;
import de.zokki.Gui.Gui;

/**
 * @author zokki
 * @version 0.3.0
 */
public class Main {
	public static boolean admin = false;
	
	private final static String VERSION = "v0.3.0";
	public final static String GUINAME = "Zufälliges Essen - " + VERSION;
	
	public static Gui GUI = null;
	
	static int PORT = 1044;
	
	public static void main(String[] args) {
		
		try {
			new ServerSocket(PORT);
		} catch (Exception e) {
			System.err.println("Application already running! - set Application on front");
			if(System.getProperty("os.name").equals("Linux")) {
				try {
					Runtime.getRuntime().exec("wmctrl -a " + GUINAME);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "'wmctrl' ist nicht installiert", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			System.exit(-1);
		}
		
		if(args.length != 0)
			admin = true;
		new XMLWriter();
		GUI = new Gui(GUINAME);
	}
	
	/**
	 * FIXMEs and TODOs
	 * 
	 * FIXME deactivate and activate with XMLWriter
	 * FIXME delete and edit categories etc in other foods etc
	 * TODO maybe buttons - recipe and other infos
	 * TODO Write everything on .txt and open it 
	 * TODO set Application on front if already open - win? nircmd?
	 */
}