package me.rabrg.bencode;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public final class BencodeDecodeTest {

	public static void main(final String[] args) {
		try(final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("test.torrent")))) {
			BencodeDecoder.decode(new StringBuffer(reader.readLine()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
