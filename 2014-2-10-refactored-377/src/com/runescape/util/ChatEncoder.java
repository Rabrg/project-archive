package com.runescape.util;

import com.runescape.net.Buffer;

public class ChatEncoder {
	
	public static int anInt587 = 20411;
	public static char aCharArray588[] = new char[100];
	public static Buffer aClass50_Sub1_Sub2_589 = new Buffer(true, new byte[100]);
	public static char aCharArray590[] = { ' ', 'e', 't', 'a', 'o', 'i', 'h', 'n', 's', 'r', 'd', 'l', 'u', 'm', 'w',
			'c', 'y', 'f', 'g', 'p', 'b', 'v', 'k', 'x', 'j', 'q', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', ' ', '!', '?', '.', ',', ':', ';', '(', ')', '-', '&', '*', '\\', '\'', '@', '#', '+', '=', '\243',
			'$', '%', '"', '[', ']' };
	
	public static String method320(int i, Buffer class50_sub1_sub2, int j) {
		int k = 0;
		int l = -1;
		for (int i1 = 0; i1 < j; i1++) {
			int j1 = class50_sub1_sub2.method521();
			int k1 = j1 >> 4 & 0xf;
			if (l == -1) {
				if (k1 < 13) {
					ChatEncoder.aCharArray588[k++] = ChatEncoder.aCharArray590[k1];
				} else {
					l = k1;
				}
			} else {
				ChatEncoder.aCharArray588[k++] = ChatEncoder.aCharArray590[((l << 4) + k1) - 195];
				l = -1;
			}
			k1 = j1 & 0xf;
			if (l == -1) {
				if (k1 < 13) {
					ChatEncoder.aCharArray588[k++] = ChatEncoder.aCharArray590[k1];
				} else {
					l = k1;
				}
			} else {
				ChatEncoder.aCharArray588[k++] = ChatEncoder.aCharArray590[((l << 4) + k1) - 195];
				l = -1;
			}
		}

		boolean flag = true;
		for (int l1 = 0; l1 < k; l1++) {
			char c = ChatEncoder.aCharArray588[l1];
			if (flag && c >= 'a' && c <= 'z') {
				ChatEncoder.aCharArray588[l1] += '\uFFE0';
				flag = false;
			}
			if (c == '.' || c == '!' || c == '?') {
				flag = true;
			}
		}

		if (i != 0) {
			ChatEncoder.anInt587 = -409;
		}
		return new String(ChatEncoder.aCharArray588, 0, k);
	}

	public static void method321(String s, int i, Buffer class50_sub1_sub2) {
		if (s.length() > 80) {
			s = s.substring(0, 80);
		}
		s = s.toLowerCase();
		if (i <= 0) {
			for (int j = 1; j > 0; j++) {
				;
			}
		}
		int k = -1;
		for (int l = 0; l < s.length(); l++) {
			char c = s.charAt(l);
			int i1 = 0;
			for (int j1 = 0; j1 < ChatEncoder.aCharArray590.length; j1++) {
				if (c != ChatEncoder.aCharArray590[j1]) {
					continue;
				}
				i1 = j1;
				break;
			}

			if (i1 > 12) {
				i1 += 195;
			}
			if (k == -1) {
				if (i1 < 13) {
					k = i1;
				} else {
					class50_sub1_sub2.method511(i1);
				}
			} else if (i1 < 13) {
				class50_sub1_sub2.method511((k << 4) + i1);
				k = -1;
			} else {
				class50_sub1_sub2.method511((k << 4) + (i1 >> 4));
				k = i1 & 0xf;
			}
		}

		if (k != -1) {
			class50_sub1_sub2.method511(k << 4);
		}
	}

	public static String method322(byte byte0, String s) {
		ChatEncoder.aClass50_Sub1_Sub2_589.position = 0;
		ChatEncoder.method321(s, 569, ChatEncoder.aClass50_Sub1_Sub2_589);
		int i = ChatEncoder.aClass50_Sub1_Sub2_589.position;
		if (byte0 != 0) {
			for (int j = 1; j > 0; j++) {
				;
			}
		}
		ChatEncoder.aClass50_Sub1_Sub2_589.position = 0;
		String s1 = ChatEncoder.method320(0, ChatEncoder.aClass50_Sub1_Sub2_589, i);
		return s1;
	}
}
