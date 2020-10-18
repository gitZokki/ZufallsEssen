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
	
	
	public static final String FOODNAME = "Speise";
	public static final String FOODSNAME = FOODNAME + "n";
	public static final String CATEGORYNAME = "Kategorie";
	public static final String CATEGORIESNAME = CATEGORYNAME + "n";
	public static final String INGREDIENTNAME = "Zutat";
	public static final String INGREDIENTSNAME = INGREDIENTNAME + "en";
	public static final String RECIPENAME = "Rezept";
	public static final String RECIPESNAME = RECIPENAME + "e";
	
	private final static String VERSION = "v0.3.0";
	public final static String GUINAME = "Zufällige " + FOODSNAME + " - " + VERSION;
	
	public static Gui GUI = null;
	
	static int PORT = 1044;
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {
			new ServerSocket(PORT);
		} catch (Exception e) {
			System.err.println("Application already running! - set Application on front");
			if(System.getProperty("os.name").equals("Linux")) {
				try {
					Runtime.getRuntime().exec("wmctrl -a " + GUINAME);
				} catch (IOException io) {
					JOptionPane.showMessageDialog(null, "'wmctrl' ist nicht installiert!\nProgramm konnte nicht in den Vordergrund gesetzt werden.", "Error", JOptionPane.ERROR_MESSAGE);
					io.printStackTrace();
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
	 * *GUI*
	 * TODO maybe buttons - recipe and other infos
	 * 
	 * *New feature*
	 * TODO Write everything on .txt and open it 
	 * TODO set Application on front if already open - win? nircmd?
	 * TODO settings -> xml-file location, txt-file location - maybe save or temp
	 */
}