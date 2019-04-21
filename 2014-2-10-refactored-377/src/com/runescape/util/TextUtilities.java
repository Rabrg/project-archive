package com.runescape.util;

public class TextUtilities {
	
	public static int anInt528 = 236;
	public static int anInt529 = -20714;
	public static int anInt530 = 3;
	public static int anInt531 = -68;
	public static final char aCharArray532[] = { '_', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
			'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9' };
	
	public static long method299(String s) {
		long l = 0L;
		for (int i = 0; i < s.length() && i < 12; i++) {
			char c = s.charAt(i);
			l *= 37L;
			if (c >= 'A' && c <= 'Z') {
				l += (1 + c) - 65;
			} else if (c >= 'a' && c <= 'z') {
				l += (1 + c) - 97;
			} else if (c >= '0' && c <= '9') {
				l += (27 + c) - 48;
			}
		}

		for (; l % 37L == 0L && l != 0L; l /= 37L) {
			;
		}
		return l;
	}

	public static String method300(long l, int i) {
		try {
			if (i < 0 || i > 0) {
				TextUtilities.anInt528 = -187;
			}
			if (l <= 0L || l >= 0x5b5b57f8a98a5dd1L) {
				return "invalid_name";
			}
			if (l % 37L == 0L) {
				return "invalid_name";
			}
			int j = 0;
			char ac[] = new char[12];
			while (l != 0L) {
				long l1 = l;
				l /= 37L;
				ac[11 - j++] = TextUtilities.aCharArray532[(int) (l1 - l * 37L)];
			}
			return new String(ac, 12 - j, j);
		} catch (RuntimeException runtimeexception) {
			SignLink.reporterror("93269, " + l + ", " + i + ", " + runtimeexception.toString());
		}
		throw new RuntimeException();
	}

	public static long method301(int i, String s) {
		s = s.toUpperCase();
		long l = 0L;
		i = 11 / i;
		for (int j = 0; j < s.length(); j++) {
			l = (l * 61L + s.charAt(j)) - 32L;
			l = l + (l >> 56) & 0xffffffffffffffL;
		}

		return l;
	}

	public static String method302(int i, int j) {
		if (j >= 0) {
			throw new NullPointerException();
		} else {
			return (i >> 24 & 0xff) + "." + (i >> 16 & 0xff) + "." + (i >> 8 & 0xff) + "." + (i & 0xff);
		}
	}

	public static String method303(String s, byte byte0) {
		if (byte0 == 7) {
			byte0 = 0;
		} else {
			TextUtilities.anInt531 = 389;
		}
		if (s.length() > 0) {
			char ac[] = s.toCharArray();
			for (int i = 0; i < ac.length; i++) {
				if (ac[i] == '_') {
					ac[i] = ' ';
					if (i + 1 < ac.length && ac[i + 1] >= 'a' && ac[i + 1] <= 'z') {
						ac[i + 1] = (char) ((ac[i + 1] + 65) - 97);
					}
				}
			}

			if (ac[0] >= 'a' && ac[0] <= 'z') {
				ac[0] = (char) ((ac[0] + 65) - 97);
			}
			return new String(ac);
		} else {
			return s;
		}
	}

	public static String method304(int i, String s) {
		StringBuffer stringbuffer = new StringBuffer();
		if (i != 2934) {
			for (int j = 1; j > 0; j++) {
				;
			}
		}
		for (int k = 0; k < s.length(); k++) {
			stringbuffer.append("*");
		}

		return stringbuffer.toString();
	}
}
