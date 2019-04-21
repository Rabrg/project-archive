package com.runescape.cache;

import com.runescape.cache.bzip.BZip2Decompressor;
import com.runescape.net.Buffer;

public class Archive {
	
	public int anInt86;
	public boolean aBoolean87;
	public byte aByteArray88[];
	public int fileCount;
	public int anIntArray90[];
	public int anIntArray91[];
	public int anIntArray92[];
	public int anIntArray93[];
	public boolean aBoolean94;

	public Archive(byte abyte0[]) {
		Buffer buffer = new Buffer(true, abyte0);
		int uncompressed = buffer.method525();
		int compressed = buffer.method525();
		if (compressed != uncompressed) {
			byte abyte1[] = new byte[uncompressed];
			BZip2Decompressor.decompress(abyte1, uncompressed, abyte0, compressed, 6);
			aByteArray88 = abyte1;
			buffer = new Buffer(true, aByteArray88);
			aBoolean94 = true;
		} else {
			aByteArray88 = abyte0;
			aBoolean94 = false;
		}
		fileCount = buffer.method523();
		anIntArray90 = new int[fileCount];
		anIntArray91 = new int[fileCount];
		anIntArray92 = new int[fileCount];
		anIntArray93 = new int[fileCount];
		int offset = buffer.position + fileCount * 10;
		for (int file = 0; file < fileCount; file++) {
			anIntArray90[file] = buffer.method526();
			anIntArray91[file] = buffer.method525();
			anIntArray92[file] = buffer.method525();
			anIntArray93[file] = offset;
			offset += anIntArray92[file];
		}
	}

	public byte[] getFile(String identifier, byte buffer[]) {
		int i = 0;
		identifier = identifier.toUpperCase();
		for (int j = 0; j < identifier.length(); j++) {
			i = (i * 61 + identifier.charAt(j)) - 32;
		}

		for (int k = 0; k < fileCount; k++) {
			if (anIntArray90[k] == i) {
				if (buffer == null) {
					buffer = new byte[anIntArray91[k]];
				}
				if (!aBoolean94) {
					BZip2Decompressor.decompress(buffer, anIntArray91[k], aByteArray88, anIntArray92[k],
							anIntArray93[k]);
				} else {
					for (int l = 0; l < anIntArray91[k]; l++) {
						buffer[l] = aByteArray88[anIntArray93[k] + l];
					}

				}
				return buffer;
			}
		}

		return null;
	}
}
