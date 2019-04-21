package me.rabrg.bencode;

import me.rabrg.bencode.type.impl.IntegerType;
import me.rabrg.bencode.type.impl.StringType;

public final class BencodeEncodeTest {

	public static void main(final String[] args) {
		System.out.println(new StringType("hello world").encode());
		System.out.println(new IntegerType(1234567890).encode());
	}

}
