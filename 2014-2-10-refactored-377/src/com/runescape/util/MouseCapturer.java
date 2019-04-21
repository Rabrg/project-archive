package com.runescape.util;

import com.runescape.Game;
import com.runescape.GameShell;

public class MouseCapturer implements Runnable {
	
	public boolean aBoolean131;
	public int anIntArray132[];
	public Object anObject133;
	public Game aClient134;
	public int anInt135;
	public int anInt136;
	public int anIntArray137[];
	
	public MouseCapturer(Game client1, byte byte0) {
		aBoolean131 = true;
		anIntArray132 = new int[500];
		anObject133 = new Object();
		anInt135 = 8;
		anIntArray137 = new int[500];
		if (byte0 != -116) {
			anInt135 = 294;
		}
		aClient134 = client1;
	}

	@Override
	public void run() {
		while (aBoolean131) {
			synchronized (anObject133) {
				if (anInt136 < 500) {
					anIntArray137[anInt136] = ((GameShell) (aClient134)).anInt22;
					anIntArray132[anInt136] = ((GameShell) (aClient134)).anInt23;
					anInt136++;
				}
			}
			try {
				Thread.sleep(50L);
			} catch (Exception _ex) {
			}
		}
	}
}
