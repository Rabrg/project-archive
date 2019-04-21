package com.runescape;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import com.runescape.cache.media.ImageRGB;
import com.runescape.media.ProducingGraphicsBuffer;

@SuppressWarnings("serial")
public class GameShell extends Applet implements Runnable, MouseListener, MouseMotionListener, KeyListener,
		FocusListener, WindowListener {
	
	public int anInt1;
	public boolean aBoolean2;
	public boolean aBoolean3;
	public int anInt4;
	public int anInt5;
	public int anInt6;
	public int anInt7;
	public int anInt8;
	public long aLongArray9[];
	public int anInt10;
	public boolean aBoolean11;
	public int anInt12;
	public int anInt13;
	public Graphics aGraphics14;
	public ProducingGraphicsBuffer aClass18_15;
	public ImageRGB aClass50_Sub1_Sub1_Sub1Array16[];
	public GameFrame aFrame_Sub1_17;
	public boolean aBoolean18;
	public boolean aBoolean19;
	public int anInt20;
	public int anInt21;
	public int anInt22;
	public int anInt23;
	public int anInt24;
	public int anInt25;
	public int anInt26;
	public long aLong27;
	public int anInt28;
	public int anInt29;
	public int anInt30;
	public long aLong31;
	public int anIntArray32[];
	public int anIntArray33[];
	public int anInt34;
	public int anInt35;
	public static int anInt36;
	
	public void method1(int i, int j, int k) {
		anInt12 = k;
		anInt13 = j;
		for (aFrame_Sub1_17 = new GameFrame(anInt12, anInt13, this); i >= 0;) {
			return;
		}

		aGraphics14 = method11(-756).getGraphics();
		aClass18_15 = new ProducingGraphicsBuffer(anInt13, (byte) -12, method11(-756), anInt12);
		method12(this, 1);
	}

	public void method2(int i, int j, int k) {
		anInt12 = i;
		if (k != 2) {
			return;
		} else {
			anInt13 = j;
			aGraphics14 = method11(-756).getGraphics();
			aClass18_15 = new ProducingGraphicsBuffer(anInt13, (byte) -12, method11(-756), anInt12);
			method12(this, 1);
			return;
		}
	}

	@Override
	public void run() {
		method11(-756).addMouseListener(this);
		method11(-756).addMouseMotionListener(this);
		method11(-756).addKeyListener(this);
		method11(-756).addFocusListener(this);
		if (aFrame_Sub1_17 != null) {
			aFrame_Sub1_17.addWindowListener(this);
		}
		method13(0, true, "Loading...");
		method6();
		int i = 0;
		int j = 256;
		int k = 1;
		int i1 = 0;
		int j1 = 0;
		for (int k1 = 0; k1 < 10; k1++) {
			aLongArray9[k1] = System.currentTimeMillis();
		}

		System.currentTimeMillis();
		while (anInt6 >= 0) {
			if (anInt6 > 0) {
				anInt6--;
				if (anInt6 == 0) {
					method3(aBoolean2);
					return;
				}
			}
			int i2 = j;
			int j2 = k;
			j = 300;
			k = 1;
			long l1 = System.currentTimeMillis();
			if (aLongArray9[i] == 0L) {
				j = i2;
				k = j2;
			} else if (l1 > aLongArray9[i]) {
				j = (int) (2560 * anInt7 / (l1 - aLongArray9[i]));
			}
			if (j < 25) {
				j = 25;
			}
			if (j > 256) {
				j = 256;
				k = (int) (anInt7 - (l1 - aLongArray9[i]) / 10L);
			}
			if (k > anInt7) {
				k = anInt7;
			}
			aLongArray9[i] = l1;
			i = (i + 1) % 10;
			if (k > 1) {
				for (int k2 = 0; k2 < 10; k2++) {
					if (aLongArray9[k2] != 0L) {
						aLongArray9[k2] += k;
					}
				}

			}
			if (k < anInt8) {
				k = anInt8;
			}
			try {
				Thread.sleep(k);
			} catch (InterruptedException _ex) {
				j1++;
			}
			for (; i1 < 256; i1 += j) {
				anInt28 = anInt24;
				anInt29 = anInt25;
				anInt30 = anInt26;
				aLong31 = aLong27;
				anInt24 = 0;
				method7((byte) -111);
				anInt34 = anInt35;
			}

			i1 &= 0xff;
			if (anInt7 > 0) {
				anInt10 = (1000 * j) / (anInt7 * 256);
			}
			method9(818);
			if (aBoolean11) {
				System.out.println("ntime:" + l1);
				for (int l2 = 0; l2 < 10; l2++) {
					int i3 = ((i - l2 - 1) + 20) % 10;
					System.out.println("otim" + i3 + ":" + aLongArray9[i3]);
				}

				System.out.println("fps:" + anInt10 + " ratio:" + j + " count:" + i1);
				System.out.println("del:" + k + " deltime:" + anInt7 + " mindel:" + anInt8);
				System.out.println("intex:" + j1 + " opos:" + i);
				aBoolean11 = false;
				j1 = 0;
			}
		}
		if (anInt6 == -1) {
			method3(aBoolean2);
		}
	}

	public void method3(boolean flag) {
		anInt6 = -2;
		method8(277);
		if (flag) {
			return;
		}
		if (aFrame_Sub1_17 != null) {
			try {
				Thread.sleep(1000L);
			} catch (Exception _ex) {
			}
			try {
				System.exit(0);
				return;
			} catch (Throwable _ex) {
			}
		}
	}

	public void method4(byte byte0, int i) {
		if (byte0 != 103) {
			anInt1 = 388;
		}
		anInt7 = 1000 / i;
	}

	@Override
	public void start() {
		if (anInt6 >= 0) {
			anInt6 = 0;
		}
	}

	@Override
	public void stop() {
		if (anInt6 >= 0) {
			anInt6 = 4000 / anInt7;
		}
	}

	@Override
	public void destroy() {
		anInt6 = -1;
		try {
			Thread.sleep(10000L);
		} catch (Exception _ex) {
		}
		if (anInt6 == -1) {
			method3(aBoolean2);
		}
	}

	@Override
	public void update(Graphics g) {
		if (aGraphics14 == null) {
			aGraphics14 = g;
		}
		aBoolean18 = true;
		method10((byte) -99);
	}

	@Override
	public void paint(Graphics g) {
		if (aGraphics14 == null) {
			aGraphics14 = g;
		}
		aBoolean18 = true;
		method10((byte) -99);
	}

	@Override
	public void mousePressed(MouseEvent mouseevent) {
		int i = mouseevent.getX();
		int j = mouseevent.getY();
		if (aFrame_Sub1_17 != null) {
			i -= 4;
			j -= 22;
		}
		anInt20 = 0;
		anInt25 = i;
		anInt26 = j;
		aLong27 = System.currentTimeMillis();
		if (mouseevent.isMetaDown()) {
			anInt24 = 2;
			anInt21 = 2;
			return;
		} else {
			anInt24 = 1;
			anInt21 = 1;
			return;
		}
	}

	@Override
	public void mouseReleased(MouseEvent mouseevent) {
		anInt20 = 0;
		anInt21 = 0;
	}

	@Override
	public void mouseClicked(MouseEvent mouseevent) {
	}

	@Override
	public void mouseEntered(MouseEvent mouseevent) {
	}

	@Override
	public void mouseExited(MouseEvent mouseevent) {
		anInt20 = 0;
		anInt22 = -1;
		anInt23 = -1;
	}

	@Override
	public void mouseDragged(MouseEvent mouseevent) {
		int i = mouseevent.getX();
		int j = mouseevent.getY();
		if (aFrame_Sub1_17 != null) {
			i -= 4;
			j -= 22;
		}
		anInt20 = 0;
		anInt22 = i;
		anInt23 = j;
	}

	@Override
	public void mouseMoved(MouseEvent mouseevent) {
		int i = mouseevent.getX();
		int j = mouseevent.getY();
		if (aFrame_Sub1_17 != null) {
			i -= 4;
			j -= 22;
		}
		anInt20 = 0;
		anInt22 = i;
		anInt23 = j;
	}

	@Override
	public void keyPressed(KeyEvent keyevent) {
		anInt20 = 0;
		int i = keyevent.getKeyCode();
		int j = keyevent.getKeyChar();
		if (j < 30) {
			j = 0;
		}
		if (i == 37) {
			j = 1;
		}
		if (i == 39) {
			j = 2;
		}
		if (i == 38) {
			j = 3;
		}
		if (i == 40) {
			j = 4;
		}
		if (i == 17) {
			j = 5;
		}
		if (i == 8) {
			j = 8;
		}
		if (i == 127) {
			j = 8;
		}
		if (i == 9) {
			j = 9;
		}
		if (i == 10) {
			j = 10;
		}
		if (i >= 112 && i <= 123) {
			j = (1008 + i) - 112;
		}
		if (i == 36) {
			j = 1000;
		}
		if (i == 35) {
			j = 1001;
		}
		if (i == 33) {
			j = 1002;
		}
		if (i == 34) {
			j = 1003;
		}
		if (j > 0 && j < 128) {
			anIntArray32[j] = 1;
		}
		if (j > 4) {
			anIntArray33[anInt35] = j;
			anInt35 = anInt35 + 1 & 0x7f;
		}
	}

	@Override
	public void keyReleased(KeyEvent keyevent) {
		anInt20 = 0;
		int i = keyevent.getKeyCode();
		char c = keyevent.getKeyChar();
		if (c < '\036') {
			c = '\0';
		}
		if (i == 37) {
			c = '\001';
		}
		if (i == 39) {
			c = '\002';
		}
		if (i == 38) {
			c = '\003';
		}
		if (i == 40) {
			c = '\004';
		}
		if (i == 17) {
			c = '\005';
		}
		if (i == 8) {
			c = '\b';
		}
		if (i == 127) {
			c = '\b';
		}
		if (i == 9) {
			c = '\t';
		}
		if (i == 10) {
			c = '\n';
		}
		if (c > 0 && c < '\200') {
			anIntArray32[c] = 0;
		}
	}

	@Override
	public void keyTyped(KeyEvent keyevent) {
	}

	public int method5(int i) {
		while (i >= 0) {
			anInt5 = -9;
		}
		int j = -1;
		if (anInt35 != anInt34) {
			j = anIntArray33[anInt34];
			anInt34 = anInt34 + 1 & 0x7f;
		}
		return j;
	}

	@Override
	public void focusGained(FocusEvent focusevent) {
		aBoolean19 = true;
		aBoolean18 = true;
		method10((byte) -99);
	}

	@Override
	public void focusLost(FocusEvent focusevent) {
		aBoolean19 = false;
		for (int i = 0; i < 128; i++) {
			anIntArray32[i] = 0;
		}

	}

	@Override
	public void windowActivated(WindowEvent windowevent) {
	}

	@Override
	public void windowClosed(WindowEvent windowevent) {
	}

	@Override
	public void windowClosing(WindowEvent windowevent) {
		destroy();
	}

	@Override
	public void windowDeactivated(WindowEvent windowevent) {
	}

	@Override
	public void windowDeiconified(WindowEvent windowevent) {
	}

	@Override
	public void windowIconified(WindowEvent windowevent) {
	}

	@Override
	public void windowOpened(WindowEvent windowevent) {
	}

	public void method6() {
	}

	public void method7(byte byte0) {
		if (byte0 != -111) {
			anInt5 = -400;
		}
	}

	public void method8(int i) {
		i = 41 / i;
	}

	public void method9(int i) {
		if (i > 0) {
			;
		}
	}

	public void method10(byte byte0) {
		if (byte0 == -99) {
			;
		}
	}

	public Component method11(int i) {
		if (i >= 0) {
			anInt4 = 329;
		}
		if (aFrame_Sub1_17 != null) {
			return aFrame_Sub1_17;
		} else {
			return this;
		}
	}

	public void method12(Runnable runnable, int i) {
		Thread thread = new Thread(runnable);
		thread.start();
		thread.setPriority(i);
	}

	public void method13(int i, boolean flag, String s) {
		while (aGraphics14 == null) {
			aGraphics14 = method11(-756).getGraphics();
			try {
				method11(-756).repaint();
			} catch (Exception _ex) {
			}
			try {
				Thread.sleep(1000L);
			} catch (Exception _ex) {
			}
		}
		Font font = new Font("Helvetica", 1, 13);
		FontMetrics fontmetrics = method11(-756).getFontMetrics(font);
		if (!flag) {
			anInt1 = -145;
		}
		Font font1 = new Font("Helvetica", 0, 13);
		method11(-756).getFontMetrics(font1);
		if (aBoolean18) {
			aGraphics14.setColor(Color.black);
			aGraphics14.fillRect(0, 0, anInt12, anInt13);
			aBoolean18 = false;
		}
		Color color = new Color(140, 17, 17);
		int j = anInt13 / 2 - 18;
		aGraphics14.setColor(color);
		aGraphics14.drawRect(anInt12 / 2 - 152, j, 304, 34);
		aGraphics14.fillRect(anInt12 / 2 - 150, j + 2, i * 3, 30);
		aGraphics14.setColor(Color.black);
		aGraphics14.fillRect((anInt12 / 2 - 150) + i * 3, j + 2, 300 - i * 3, 30);
		aGraphics14.setFont(font);
		aGraphics14.setColor(Color.white);
		aGraphics14.drawString(s, (anInt12 - fontmetrics.stringWidth(s)) / 2, j + 22);
	}

	public GameShell() {
		anInt1 = 8;
		aBoolean2 = false;
		aBoolean3 = false;
		anInt4 = 3;
		anInt7 = 20;
		anInt8 = 1;
		aLongArray9 = new long[10];
		aBoolean11 = false;
		aClass50_Sub1_Sub1_Sub1Array16 = new ImageRGB[6];
		aBoolean18 = true;
		aBoolean19 = true;
		anIntArray32 = new int[128];
		anIntArray33 = new int[128];
	}
}
