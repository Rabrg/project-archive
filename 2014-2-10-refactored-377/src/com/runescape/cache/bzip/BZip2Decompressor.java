package com.runescape.cache.bzip;

public class BZip2Decompressor {
	
	public static BZip2Context aClass1_586 = new BZip2Context();
	
	public static int decompress(byte abyte0[], int i, byte abyte1[], int j, int k) {
		synchronized (BZip2Decompressor.aClass1_586) {
			BZip2Decompressor.aClass1_586.aByteArray47 = abyte1;
			BZip2Decompressor.aClass1_586.anInt48 = k;
			BZip2Decompressor.aClass1_586.aByteArray52 = abyte0;
			BZip2Decompressor.aClass1_586.anInt53 = 0;
			BZip2Decompressor.aClass1_586.anInt49 = j;
			BZip2Decompressor.aClass1_586.anInt54 = i;
			BZip2Decompressor.aClass1_586.anInt61 = 0;
			BZip2Decompressor.aClass1_586.anInt60 = 0;
			BZip2Decompressor.aClass1_586.anInt50 = 0;
			BZip2Decompressor.aClass1_586.anInt51 = 0;
			BZip2Decompressor.aClass1_586.anInt55 = 0;
			BZip2Decompressor.aClass1_586.anInt56 = 0;
			BZip2Decompressor.aClass1_586.anInt63 = 0;
			BZip2Decompressor.method314(BZip2Decompressor.aClass1_586);
			i -= BZip2Decompressor.aClass1_586.anInt54;
			int l = i;
			return l;
		}
	}

	public static void method313(BZip2Context class1) {
		byte byte4 = class1.aByte57;
		int i = class1.anInt58;
		int j = class1.anInt68;
		int k = class1.anInt66;
		int ai[] = BZip2Context.anIntArray71;
		int l = class1.anInt65;
		byte abyte0[] = class1.aByteArray52;
		int i1 = class1.anInt53;
		int j1 = class1.anInt54;
		int k1 = j1;
		int l1 = class1.anInt85 + 1;
		label0: do {
			if (i > 0) {
				do {
					if (j1 == 0) {
						break label0;
					}
					if (i == 1) {
						break;
					}
					abyte0[i1] = byte4;
					i--;
					i1++;
					j1--;
				} while (true);
				if (j1 == 0) {
					i = 1;
					break;
				}
				abyte0[i1] = byte4;
				i1++;
				j1--;
			}
			boolean flag = true;
			while (flag) {
				flag = false;
				if (j == l1) {
					i = 0;
					break label0;
				}
				byte4 = (byte) k;
				l = ai[l];
				byte byte0 = (byte) (l & 0xff);
				l >>= 8;
				j++;
				if (byte0 != k) {
					k = byte0;
					if (j1 == 0) {
						i = 1;
					} else {
						abyte0[i1] = byte4;
						i1++;
						j1--;
						flag = true;
						continue;
					}
					break label0;
				}
				if (j != l1) {
					continue;
				}
				if (j1 == 0) {
					i = 1;
					break label0;
				}
				abyte0[i1] = byte4;
				i1++;
				j1--;
				flag = true;
			}
			i = 2;
			l = ai[l];
			byte byte1 = (byte) (l & 0xff);
			l >>= 8;
			if (++j != l1) {
				if (byte1 != k) {
					k = byte1;
				} else {
					i = 3;
					l = ai[l];
					byte byte2 = (byte) (l & 0xff);
					l >>= 8;
					if (++j != l1) {
						if (byte2 != k) {
							k = byte2;
						} else {
							l = ai[l];
							byte byte3 = (byte) (l & 0xff);
							l >>= 8;
							j++;
							i = (byte3 & 0xff) + 4;
							l = ai[l];
							k = (byte) (l & 0xff);
							l >>= 8;
							j++;
						}
					}
				}
			}
		} while (true);
		int i2 = class1.anInt55;
		class1.anInt55 += k1 - j1;
		if (class1.anInt55 < i2) {
			class1.anInt56++;
		}
		class1.aByte57 = byte4;
		class1.anInt58 = i;
		class1.anInt68 = j;
		class1.anInt66 = k;
		BZip2Context.anIntArray71 = ai;
		class1.anInt65 = l;
		class1.aByteArray52 = abyte0;
		class1.anInt53 = i1;
		class1.anInt54 = j1;
	}

	public static void method314(BZip2Context class1) {
		int k8 = 0;
		int ai[] = null;
		int ai1[] = null;
		int ai2[] = null;
		class1.anInt62 = 1;
		if (BZip2Context.anIntArray71 == null) {
			BZip2Context.anIntArray71 = new int[class1.anInt62 * 0x186a0];
		}
		boolean flag19 = true;
		while (flag19) {
			byte byte0 = BZip2Decompressor.method315(class1);
			if (byte0 == 23) {
				return;
			}
			byte0 = BZip2Decompressor.method315(class1);
			byte0 = BZip2Decompressor.method315(class1);
			byte0 = BZip2Decompressor.method315(class1);
			byte0 = BZip2Decompressor.method315(class1);
			byte0 = BZip2Decompressor.method315(class1);
			class1.anInt63++;
			byte0 = BZip2Decompressor.method315(class1);
			byte0 = BZip2Decompressor.method315(class1);
			byte0 = BZip2Decompressor.method315(class1);
			byte0 = BZip2Decompressor.method315(class1);
			byte0 = BZip2Decompressor.method316(class1);
			if (byte0 != 0) {
				class1.aBoolean59 = true;
			} else {
				class1.aBoolean59 = false;
			}
			if (class1.aBoolean59) {
				System.out.println("PANIC! RANDOMISED BLOCK!");
			}
			class1.anInt64 = 0;
			byte0 = BZip2Decompressor.method315(class1);
			class1.anInt64 = class1.anInt64 << 8 | byte0 & 0xff;
			byte0 = BZip2Decompressor.method315(class1);
			class1.anInt64 = class1.anInt64 << 8 | byte0 & 0xff;
			byte0 = BZip2Decompressor.method315(class1);
			class1.anInt64 = class1.anInt64 << 8 | byte0 & 0xff;
			for (int j = 0; j < 16; j++) {
				byte byte1 = BZip2Decompressor.method316(class1);
				if (byte1 == 1) {
					class1.aBooleanArray74[j] = true;
				} else {
					class1.aBooleanArray74[j] = false;
				}
			}

			for (int k = 0; k < 256; k++) {
				class1.aBooleanArray73[k] = false;
			}

			for (int l = 0; l < 16; l++) {
				if (class1.aBooleanArray74[l]) {
					for (int i3 = 0; i3 < 16; i3++) {
						byte byte2 = BZip2Decompressor.method316(class1);
						if (byte2 == 1) {
							class1.aBooleanArray73[l * 16 + i3] = true;
						}
					}

				}
			}

			BZip2Decompressor.method318(class1);
			int i4 = class1.anInt72 + 2;
			int j4 = BZip2Decompressor.method317(3, class1);
			int k4 = BZip2Decompressor.method317(15, class1);
			for (int i1 = 0; i1 < k4; i1++) {
				int j3 = 0;
				do {
					byte byte3 = BZip2Decompressor.method316(class1);
					if (byte3 == 0) {
						break;
					}
					j3++;
				} while (true);
				class1.aByteArray79[i1] = (byte) j3;
			}

			byte abyte0[] = new byte[6];
			for (byte byte16 = 0; byte16 < j4; byte16++) {
				abyte0[byte16] = byte16;
			}

			for (int j1 = 0; j1 < k4; j1++) {
				byte byte17 = class1.aByteArray79[j1];
				byte byte15 = abyte0[byte17];
				for (; byte17 > 0; byte17--) {
					abyte0[byte17] = abyte0[byte17 - 1];
				}

				abyte0[0] = byte15;
				class1.aByteArray78[j1] = byte15;
			}

			for (int k3 = 0; k3 < j4; k3++) {
				int l6 = BZip2Decompressor.method317(5, class1);
				for (int k1 = 0; k1 < i4; k1++) {
					do {
						byte byte4 = BZip2Decompressor.method316(class1);
						if (byte4 == 0) {
							break;
						}
						byte4 = BZip2Decompressor.method316(class1);
						if (byte4 == 0) {
							l6++;
						} else {
							l6--;
						}
					} while (true);
					class1.aByteArrayArray80[k3][k1] = (byte) l6;
				}

			}

			for (int l3 = 0; l3 < j4; l3++) {
				byte byte8 = 32;
				int i = 0;
				for (int l1 = 0; l1 < i4; l1++) {
					if (class1.aByteArrayArray80[l3][l1] > i) {
						i = class1.aByteArrayArray80[l3][l1];
					}
					if (class1.aByteArrayArray80[l3][l1] < byte8) {
						byte8 = class1.aByteArrayArray80[l3][l1];
					}
				}

				BZip2Decompressor.method319(class1.anIntArrayArray81[l3], class1.anIntArrayArray82[l3],
						class1.anIntArrayArray83[l3], class1.aByteArrayArray80[l3], byte8, i, i4);
				class1.anIntArray84[l3] = byte8;
			}

			int l4 = class1.anInt72 + 1;
			int i5 = -1;
			int j5 = 0;
			for (int i2 = 0; i2 <= 255; i2++) {
				class1.anIntArray67[i2] = 0;
			}

			int j9 = 4095;
			for (int l8 = 15; l8 >= 0; l8--) {
				for (int i9 = 15; i9 >= 0; i9--) {
					class1.aByteArray76[j9] = (byte) (l8 * 16 + i9);
					j9--;
				}

				class1.anIntArray77[l8] = j9 + 1;
			}

			int i6 = 0;
			if (j5 == 0) {
				i5++;
				j5 = 50;
				byte byte12 = class1.aByteArray78[i5];
				k8 = class1.anIntArray84[byte12];
				ai = class1.anIntArrayArray81[byte12];
				ai2 = class1.anIntArrayArray83[byte12];
				ai1 = class1.anIntArrayArray82[byte12];
			}
			j5--;
			int i7 = k8;
			int l7;
			byte byte9;
			for (l7 = BZip2Decompressor.method317(i7, class1); l7 > ai[i7]; l7 = l7 << 1 | byte9) {
				i7++;
				byte9 = BZip2Decompressor.method316(class1);
			}

			for (int k5 = ai2[l7 - ai1[i7]]; k5 != l4;) {
				if (k5 == 0 || k5 == 1) {
					int j6 = -1;
					int k6 = 1;
					do {
						if (k5 == 0) {
							j6 += k6;
						} else if (k5 == 1) {
							j6 += 2 * k6;
						}
						k6 *= 2;
						if (j5 == 0) {
							i5++;
							j5 = 50;
							byte byte13 = class1.aByteArray78[i5];
							k8 = class1.anIntArray84[byte13];
							ai = class1.anIntArrayArray81[byte13];
							ai2 = class1.anIntArrayArray83[byte13];
							ai1 = class1.anIntArrayArray82[byte13];
						}
						j5--;
						int j7 = k8;
						int i8;
						byte byte10;
						for (i8 = BZip2Decompressor.method317(j7, class1); i8 > ai[j7]; i8 = i8 << 1 | byte10) {
							j7++;
							byte10 = BZip2Decompressor.method316(class1);
						}

						k5 = ai2[i8 - ai1[j7]];
					} while (k5 == 0 || k5 == 1);
					j6++;
					byte byte5 = class1.aByteArray75[class1.aByteArray76[class1.anIntArray77[0]] & 0xff];
					class1.anIntArray67[byte5 & 0xff] += j6;
					for (; j6 > 0; j6--) {
						BZip2Context.anIntArray71[i6] = byte5 & 0xff;
						i6++;
					}

				} else {
					int j11 = k5 - 1;
					byte byte6;
					if (j11 < 16) {
						int j10 = class1.anIntArray77[0];
						byte6 = class1.aByteArray76[j10 + j11];
						for (; j11 > 3; j11 -= 4) {
							int k11 = j10 + j11;
							class1.aByteArray76[k11] = class1.aByteArray76[k11 - 1];
							class1.aByteArray76[k11 - 1] = class1.aByteArray76[k11 - 2];
							class1.aByteArray76[k11 - 2] = class1.aByteArray76[k11 - 3];
							class1.aByteArray76[k11 - 3] = class1.aByteArray76[k11 - 4];
						}

						for (; j11 > 0; j11--) {
							class1.aByteArray76[j10 + j11] = class1.aByteArray76[(j10 + j11) - 1];
						}

						class1.aByteArray76[j10] = byte6;
					} else {
						int l10 = j11 / 16;
						int i11 = j11 % 16;
						int k10 = class1.anIntArray77[l10] + i11;
						byte6 = class1.aByteArray76[k10];
						for (; k10 > class1.anIntArray77[l10]; k10--) {
							class1.aByteArray76[k10] = class1.aByteArray76[k10 - 1];
						}

						class1.anIntArray77[l10]++;
						for (; l10 > 0; l10--) {
							class1.anIntArray77[l10]--;
							class1.aByteArray76[class1.anIntArray77[l10]] = class1.aByteArray76[(class1.anIntArray77[l10 - 1] + 16) - 1];
						}

						class1.anIntArray77[0]--;
						class1.aByteArray76[class1.anIntArray77[0]] = byte6;
						if (class1.anIntArray77[0] == 0) {
							int i10 = 4095;
							for (int k9 = 15; k9 >= 0; k9--) {
								for (int l9 = 15; l9 >= 0; l9--) {
									class1.aByteArray76[i10] = class1.aByteArray76[class1.anIntArray77[k9] + l9];
									i10--;
								}

								class1.anIntArray77[k9] = i10 + 1;
							}

						}
					}
					class1.anIntArray67[class1.aByteArray75[byte6 & 0xff] & 0xff]++;
					BZip2Context.anIntArray71[i6] = class1.aByteArray75[byte6 & 0xff] & 0xff;
					i6++;
					if (j5 == 0) {
						i5++;
						j5 = 50;
						byte byte14 = class1.aByteArray78[i5];
						k8 = class1.anIntArray84[byte14];
						ai = class1.anIntArrayArray81[byte14];
						ai2 = class1.anIntArrayArray83[byte14];
						ai1 = class1.anIntArrayArray82[byte14];
					}
					j5--;
					int k7 = k8;
					int j8;
					byte byte11;
					for (j8 = BZip2Decompressor.method317(k7, class1); j8 > ai[k7]; j8 = j8 << 1 | byte11) {
						k7++;
						byte11 = BZip2Decompressor.method316(class1);
					}

					k5 = ai2[j8 - ai1[k7]];
				}
			}

			class1.anInt58 = 0;
			class1.aByte57 = 0;
			class1.anIntArray69[0] = 0;
			for (int j2 = 1; j2 <= 256; j2++) {
				class1.anIntArray69[j2] = class1.anIntArray67[j2 - 1];
			}

			for (int k2 = 1; k2 <= 256; k2++) {
				class1.anIntArray69[k2] += class1.anIntArray69[k2 - 1];
			}

			for (int l2 = 0; l2 < i6; l2++) {
				byte byte7 = (byte) (BZip2Context.anIntArray71[l2] & 0xff);
				BZip2Context.anIntArray71[class1.anIntArray69[byte7 & 0xff]] |= l2 << 8;
				class1.anIntArray69[byte7 & 0xff]++;
			}

			class1.anInt65 = BZip2Context.anIntArray71[class1.anInt64] >> 8;
			class1.anInt68 = 0;
			class1.anInt65 = BZip2Context.anIntArray71[class1.anInt65];
			class1.anInt66 = (byte) (class1.anInt65 & 0xff);
			class1.anInt65 >>= 8;
			class1.anInt68++;
			class1.anInt85 = i6;
			BZip2Decompressor.method313(class1);
			if (class1.anInt68 == class1.anInt85 + 1 && class1.anInt58 == 0) {
				flag19 = true;
			} else {
				flag19 = false;
			}
		}
	}

	public static byte method315(BZip2Context class1) {
		return (byte) BZip2Decompressor.method317(8, class1);
	}

	public static byte method316(BZip2Context class1) {
		return (byte) BZip2Decompressor.method317(1, class1);
	}

	public static int method317(int i, BZip2Context class1) {
		int j;
		do {
			if (class1.anInt61 >= i) {
				int k = class1.anInt60 >> class1.anInt61 - i & (1 << i) - 1;
				class1.anInt61 -= i;
				j = k;
				break;
			}
			class1.anInt60 = class1.anInt60 << 8 | class1.aByteArray47[class1.anInt48] & 0xff;
			class1.anInt61 += 8;
			class1.anInt48++;
			class1.anInt49--;
			class1.anInt50++;
			if (class1.anInt50 == 0) {
				class1.anInt51++;
			}
		} while (true);
		return j;
	}

	public static void method318(BZip2Context class1) {
		class1.anInt72 = 0;
		for (int i = 0; i < 256; i++) {
			if (class1.aBooleanArray73[i]) {
				class1.aByteArray75[class1.anInt72] = (byte) i;
				class1.anInt72++;
			}
		}

	}

	public static void method319(int ai[], int ai1[], int ai2[], byte abyte0[], int i, int j, int k) {
		int l = 0;
		for (int i1 = i; i1 <= j; i1++) {
			for (int l2 = 0; l2 < k; l2++) {
				if (abyte0[l2] == i1) {
					ai2[l] = l2;
					l++;
				}
			}

		}

		for (int j1 = 0; j1 < 23; j1++) {
			ai1[j1] = 0;
		}

		for (int k1 = 0; k1 < k; k1++) {
			ai1[abyte0[k1] + 1]++;
		}

		for (int l1 = 1; l1 < 23; l1++) {
			ai1[l1] += ai1[l1 - 1];
		}

		for (int i2 = 0; i2 < 23; i2++) {
			ai[i2] = 0;
		}

		int i3 = 0;
		for (int j2 = i; j2 <= j; j2++) {
			i3 += ai1[j2 + 1] - ai1[j2];
			ai[j2] = i3 - 1;
			i3 <<= 1;
		}

		for (int k2 = i + 1; k2 <= j; k2++) {
			ai1[k2] = (ai[k2 - 1] + 1 << 1) - ai1[k2];
		}

	}
}
