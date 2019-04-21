package com.runescape.util;

import java.applet.Applet;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

public class SignLink implements Runnable {
	
	public static final int clientversion = 377;
	public static int uid;
	public static int storeid = 32;
	public static RandomAccessFile cache_dat = null;
	public static RandomAccessFile cache_idx[] = new RandomAccessFile[5];
	public static boolean sunjava;
	public static Applet mainapp = null;
	public static boolean active;
	public static int threadliveid;
	public static InetAddress socketip;
	public static int socketreq;
	public static Socket socket = null;
	public static int threadreqpri = 1;
	public static Runnable threadreq = null;
	public static String dnsreq = null;
	public static String dns = null;
	public static String urlreq = null;
	public static DataInputStream urlstream = null;
	public static int savelen;
	public static String savereq = null;
	public static byte savebuf[] = null;
	public static boolean midiplay;
	public static int midipos;
	public static String midi = null;
	public static int midivol;
	public static int midifade;
	public static boolean waveplay;
	public static int wavepos;
	public static String wave = null;
	public static int wavevol;
	public static boolean reporterror = true;
	public static String errorname = "";
	
	public static void startpriv(InetAddress inetaddress) {
		SignLink.threadliveid = (int) (Math.random() * 99999999D);
		if (SignLink.active) {
			try {
				Thread.sleep(500L);
			} catch (Exception _ex) {
			}
			SignLink.active = false;
		}
		SignLink.socketreq = 0;
		SignLink.threadreq = null;
		SignLink.dnsreq = null;
		SignLink.savereq = null;
		SignLink.urlreq = null;
		SignLink.socketip = inetaddress;
		Thread thread = new Thread(new SignLink());
		thread.setDaemon(true);
		thread.start();
		while (!SignLink.active) {
			try {
				Thread.sleep(50L);
			} catch (Exception _ex) {
			}
		}
	}

	@Override
	public void run() {
		SignLink.active = true;
		String s = SignLink.findcachedir();
		SignLink.uid = SignLink.getuid(s);
		try {
			File file = new File(s + "main_file_cache.dat");
			if (file.exists() && file.length() > 0x3200000L) {
				file.delete();
			}
			SignLink.cache_dat = new RandomAccessFile(s + "main_file_cache.dat", "rw");
			for (int j = 0; j < 5; j++) {
				SignLink.cache_idx[j] = new RandomAccessFile(s + "main_file_cache.idx" + j, "rw");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		for (int i = SignLink.threadliveid; SignLink.threadliveid == i;) {
			if (SignLink.socketreq != 0) {
				try {
					SignLink.socket = new Socket(SignLink.socketip, SignLink.socketreq);
				} catch (Exception _ex) {
					SignLink.socket = null;
				}
				SignLink.socketreq = 0;
			} else if (SignLink.threadreq != null) {
				Thread thread = new Thread(SignLink.threadreq);
				thread.setDaemon(true);
				thread.start();
				thread.setPriority(SignLink.threadreqpri);
				SignLink.threadreq = null;
			} else if (SignLink.dnsreq != null) {
				try {
					SignLink.dns = InetAddress.getByName(SignLink.dnsreq).getHostName();
				} catch (Exception _ex) {
					SignLink.dns = "unknown";
				}
				SignLink.dnsreq = null;
			} else if (SignLink.savereq != null) {
				if (SignLink.savebuf != null) {
					try {
						FileOutputStream fileoutputstream = new FileOutputStream(s + SignLink.savereq);
						fileoutputstream.write(SignLink.savebuf, 0, SignLink.savelen);
						fileoutputstream.close();
					} catch (Exception _ex) {
					}
				}
				if (SignLink.waveplay) {
					SignLink.wave = s + SignLink.savereq;
					SignLink.waveplay = false;
				}
				if (SignLink.midiplay) {
					SignLink.midi = s + SignLink.savereq;
					SignLink.midiplay = false;
				}
				SignLink.savereq = null;
			} else if (SignLink.urlreq != null) {
				try {
					SignLink.urlstream = new DataInputStream(
							(new URL(SignLink.mainapp.getCodeBase(), SignLink.urlreq)).openStream());
				} catch (Exception _ex) {
					SignLink.urlstream = null;
				}
				SignLink.urlreq = null;
			}
			try {
				Thread.sleep(50L);
			} catch (Exception _ex) {
			}
		}

	}

	public static String findcachedir() {
		return "./cache/";
	}

	public static int getuid(String s) {
		try {
			File file = new File(s + "uid.dat");
			if (!file.exists() || file.length() < 4L) {
				DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(s + "uid.dat"));
				dataoutputstream.writeInt((int) (Math.random() * 99999999D));
				dataoutputstream.close();
			}
		} catch (Exception _ex) {
		}
		try {
			DataInputStream datainputstream = new DataInputStream(new FileInputStream(s + "uid.dat"));
			int i = datainputstream.readInt();
			datainputstream.close();
			return i + 1;
		} catch (Exception _ex) {
			return 0;
		}
	}

	public static synchronized Socket opensocket(int i) throws IOException {
		for (SignLink.socketreq = i; SignLink.socketreq != 0;) {
			try {
				Thread.sleep(50L);
			} catch (Exception _ex) {
			}
		}

		if (SignLink.socket == null) {
			throw new IOException("could not open socket");
		} else {
			return SignLink.socket;
		}
	}

	public static synchronized DataInputStream openurl(String s) throws IOException {
		for (SignLink.urlreq = s; SignLink.urlreq != null;) {
			try {
				Thread.sleep(50L);
			} catch (Exception _ex) {
			}
		}

		if (SignLink.urlstream == null) {
			throw new IOException("could not open: " + s);
		} else {
			return SignLink.urlstream;
		}
	}

	public static synchronized void dnslookup(String s) {
		SignLink.dns = s;
		SignLink.dnsreq = s;
	}

	public static synchronized void startthread(Runnable runnable, int i) {
		SignLink.threadreqpri = i;
		SignLink.threadreq = runnable;
	}

	public static synchronized boolean wavesave(byte abyte0[], int i) {
		if (i > 0x1e8480) {
			return false;
		}
		if (SignLink.savereq != null) {
			return false;
		} else {
			SignLink.wavepos = (SignLink.wavepos + 1) % 5;
			SignLink.savelen = i;
			SignLink.savebuf = abyte0;
			SignLink.waveplay = true;
			SignLink.savereq = "sound" + SignLink.wavepos + ".wav";
			return true;
		}
	}

	public static synchronized boolean wavereplay() {
		if (SignLink.savereq != null) {
			return false;
		} else {
			SignLink.savebuf = null;
			SignLink.waveplay = true;
			SignLink.savereq = "sound" + SignLink.wavepos + ".wav";
			return true;
		}
	}

	public static synchronized void midisave(byte abyte0[], int i) {
		if (i > 0x1e8480) {
			return;
		}
		if (SignLink.savereq != null) {
			return;
		} else {
			SignLink.midipos = (SignLink.midipos + 1) % 5;
			SignLink.savelen = i;
			SignLink.savebuf = abyte0;
			SignLink.midiplay = true;
			SignLink.savereq = "jingle" + SignLink.midipos + ".mid";
			return;
		}
	}

	public static void reporterror(String s) {
		if (!SignLink.reporterror) {
			return;
		}
		if (!SignLink.active) {
			return;
		}
		System.out.println("Error: " + s);
		try {
			s = s.replace(':', '_');
			s = s.replace('@', '_');
			s = s.replace('&', '_');
			s = s.replace('#', '_');
			DataInputStream datainputstream = SignLink.openurl("reporterror" + 377 + ".cgi?error=" + SignLink.errorname
					+ " " + s);
			datainputstream.readUTF();
			datainputstream.close();
			return;
		} catch (IOException _ex) {
			return;
		}
	}
}
