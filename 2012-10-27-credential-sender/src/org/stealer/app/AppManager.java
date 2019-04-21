package org.stealer.app;

import java.util.ArrayList;

import org.stealer.app.impl.Chrome;

public class AppManager {

	private ArrayList<App> apps;
	
	public AppManager() {
		apps = new ArrayList<App>();
	}
	
	public void init() {
		add(new Chrome());
	}

	private void add(App app) {
		apps.add(app);
		System.out.println("Added: " + app.getClass().getName());
	}

	public ArrayList<App> getApps() {
		return apps;
	}
}
