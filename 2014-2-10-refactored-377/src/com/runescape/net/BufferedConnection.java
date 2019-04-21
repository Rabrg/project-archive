package com.runescape.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.runescape.GameShell;

public class BufferedConnection implements Runnable {
	
	public int anInt379;
	public byte aByte380;
	public InputStream anInputStream381;
	public OutputStream anOutputStream382;
	public Socket aSocket383;
	public boolean aBoolean384;
	public GameShell anApplet_Sub1_385;
	public byte aByteArray386[];
	public int anInt387;
	public int anInt388;
	public boolean aBoolean389;
	public boolean aBoolean390;
	
	public BufferedConnection(byte byte0, Socket socket, GameShell applet_sub1) throws IOException {
		aByte380 = 2;
		aBoolean384 = false;
		aBoolean389 = false;
		aBoolean390 = false;
		anApplet_Sub1_385 = applet_sub1;
		aSocket383 = socket;
		if (byte0 == aByte380) {
			byte0 = 0;
		} else {
			anInt379 = -5;
		}
		aSocket383.setSoTimeout(30000);
		aSocket383.setTcpNoDelay(true);
		anInputStream381 = aSocket383.getInputStream();
		anOutputStream382 = aSocket383.getOutputStream();
	}

	public void method224() {
		aBoolean384 = true;
		try {
			if (anInputStream381 != null) {
				anInputStream381.close();
			}
			if (anOutputStream382 != null) {
				anOutputStream382.close();
			}
			if (aSocket383 != null) {
				aSocket383.close();
			}
		} catch (IOException _ex) {
			System.out.println("Error closing stream");
		}
		aBoolean389 = false;
		synchronized (this) {
			notify();
		}
		aByteArray386 = null;
	}

	public int method225() throws IOException {
		if (aBoolean384) {
			return 0;
		} else {
			return anInputStream381.read();
		}
	}

	public int method226() throws IOException {
		if (aBoolean384) {
			return 0;
		} else {
			return anInputStream381.available();
		}
	}

	public void method227(byte abyte0[], int i, int j) throws IOException {
		if (aBoolean384) {
			return;
		}
		int k;
		for (; j > 0; j -= k) {
			k = anInputStream381.read(abyte0, i, j);
			if (k <= 0) {
				throw new IOException("EOF");
			}
			i += k;
		}

	}

	public void method228(int i, int j, int k, byte abyte0[]) throws IOException {
		if (aBoolean384) {
			return;
		}
		if (aBoolean390) {
			aBoolean390 = false;
			throw new IOException("Error in writer thread");
		}
		if (aByteArray386 == null) {
			aByteArray386 = new byte[5000];
		}
		synchronized (this) {
			for (int l = 0; l < j; l++) {
				aByteArray386[anInt388] = abyte0[l + k];
				anInt388 = (anInt388 + 1) % 5000;
				if (anInt388 == (anInt387 + 4900) % 5000) {
					throw new IOException("buffer overflow");
				}
			}

			if (!aBoolean389) {
				aBoolean389 = true;
				anApplet_Sub1_385.method12(this, 3);
			}
			notify();
		}
		if (i == 0) {
			;
		}
	}

	@Override
	public void run() {
		while (aBoolean389) {
			int i;
			int j;
			synchronized (this) {
				if (anInt388 == anInt387) {
					try {
						wait();
					} catch (InterruptedException _ex) {
					}
				}
				if (!aBoolean389) {
					return;
				}
				j = anInt387;
				if (anInt388 >= anInt387) {
					i = anInt388 - anInt387;
				} else {
					i = 5000 - anInt387;
				}
			}
			if (i > 0) {
				try {
					anOutputStream382.write(aByteArray386, j, i);
				} catch (IOException _ex) {
					aBoolean390 = true;
				}
				anInt387 = (anInt387 + i) % 5000;
				try {
					if (anInt388 == anInt387) {
						anOutputStream382.flush();
					}
				} catch (IOException _ex) {
					aBoolean390 = true;
				}
			}
		}
	}

	public void method229(boolean flag) {
		System.out.println("dummy:" + aBoolean384);
		System.out.println("tcycl:" + anInt387);
		System.out.println("tnum:" + anInt388);
		System.out.println("writer:" + aBoolean389);
		if (flag) {
			return;
		}
		System.out.println("ioerror:" + aBoolean390);
		try {
			System.out.println("available:" + method226());
			return;
		} catch (IOException _ex) {
			return;
		}
	}
}
