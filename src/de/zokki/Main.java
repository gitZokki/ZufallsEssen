package de.zokki;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;

import javax.swing.JOptionPane;

import de.zokki.ConsoleColors.ConsoleColors;
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

	private static osName os;

	public static Gui GUI = null;

	static int PORT = 1044;

	enum osName {
		Linux, Windows, other
	}

	public static void main(String[] args) {
		System.getProperties().forEach((o, p) -> {
			System.out.println(o + ": " + p);
		});
		setOsName();
		if(checkAlreadyRunning()) {
			System.err.println("Application already running! - set Application on front");
			setOnFocus();
			System.exit(-1);
		} else {
			System.out.println(ConsoleColors.GREEN_BACKGROUND + GUINAME + " started successfully!" + ConsoleColors.RESET);
		}

		if(args.length != 0)
			admin = true;
		new XMLWriter();
		GUI = new Gui(GUINAME);
	}

	private static void setOsName() {
		String name = System.getProperty("os.name");
		if(name.contains("Windows")) {
			os = osName.Windows;
		} else if(name.contains("Linux")) {
			os = osName.Linux;
		} else {
			os = osName.other;
		}
	}

	private static boolean checkAlreadyRunning() {
		switch(os) {
			case Windows:
				return checkOnWindows();
			case Linux:
				return checkOnLinux();
			default:
				return checkOnOther();
		}
	}

	private static boolean checkOnWindows() {
		try {
			Process process = new ProcessBuilder().command("powershell", "Get-Process | Where-Object { $_.MainWindowTitle -like '" + GUINAME + "' }").start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while(reader.readLine() != null) {
				return true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private static boolean checkOnLinux() {
		try {
			Process process = new ProcessBuilder().command("dash", "ps aux | grep -i \"" + GUINAME + "\"").start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while(reader.readLine() != null) {
				return true;
			}
		} catch(Exception e) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("resource")
	private static boolean checkOnOther() {
		try {
			new ServerSocket(PORT);
			return false;
		} catch(Exception e) {
			return true;
		}
	}
	
	private static void setOnFocus() {
		switch(os) {
			case Windows:
				setOnFocusWindows();
				break;
			case Linux:
				setOnFocusLinux();
				break;
			default:
				break;
		}
	}
	
	private static void setOnFocusWindows() {
		//FIXME tempfile? or so idn
		try {
			/*
			 * Maybe as script in tempfile and execute this and del it after this
			 */
			/*
			File file = new File(System.getProperty("temp") + System.getProperty("file.separator") + "temp.bat");
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			writer.write("every comment in new line or so idn");
			writer.flush();
			writer.close();
			Runtime.getRuntime().exec("powershell start " + file);
			*/
			/*ProcessBuilder builder = new ProcessBuilder();
			String[] command = new String[]{
					"cd c:\\",
					"$notepad = Start-Process notepad -WindowStyle Minimized -PassThru", "Start-Sleep -Seconds 2", //Find-Process 
					"$sig = '[DllImport(\"user32.dll\")] public static extern bool ShowWindowAsync(IntPtr hWnd, int nCmdShow); [DllImport(\"user32.dll\")] public static extern int SetForegroundWindow(IntPtr hwnd);' ","Start-Sleep -Seconds 2",
					"$type = Add-Type -MemberDefinition $sig -Name WindowAPI -PassThru","Start-Sleep -Seconds 2",
					"$hwnd = $notepad.MainWindowHandle ","Start-Sleep -Seconds 2",
					"$hwnd",
					"$type::ShowWindowAsync($hwnd, 3)", "Start-Sleep -Seconds 2",
					"$type::SetForegroundWindow($hwnd)"};
			Process process;
			String s = "";
			for(String string : command) {
				s += string + " ; ";
			}
			System.out.println(s);
			process = Runtime.getRuntime().exec("powershell start powershell {" + s + "}");
			//process = builder.command("powershell" , "start powershell {(New-Object -ComObject WScript.Shell).AppActivate((get-process javaw).MainWindowTitle)}").start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String line = "";
			while((line = reader.readLine()) != null) {
				System.out.println(line);
			}*/
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static void setOnFocusLinux() {
		try {
			Runtime.getRuntime().exec("wmctrl -a " + GUINAME);
		} catch(IOException io) {
			JOptionPane.showMessageDialog(null,
					"'wmctrl' ist nicht installiert!\nProgramm konnte nicht in den Vordergrund gesetzt werden.", "Error",
					JOptionPane.ERROR_MESSAGE);
			io.printStackTrace();
		}
	}
	
	/**
	 * FIXMEs and TODOs
	 * 
	 * *XMLWriter* 
	 * TODO existend ingredients dont show in addRecipe 
	 * TODO set in editIngredients the category and unit
	 * 
	 * *GUI* 
	 * TODO maybe buttons - recipe and other infos
	 * 
	 * *New feature* 
	 * TODO Write everything on .txt and open it 
	 * TODO set Application on front if already open - win? nircmd? 
	 * TODO settings -> xml-file location, txt-file location - maybe save or temp
	 * 
	 * *Websides for branch*
	 * https://community.idera.com/database-tools/powershell/powertips/b/tips/posts/bringing-window-in-the-foreground
	 */
}