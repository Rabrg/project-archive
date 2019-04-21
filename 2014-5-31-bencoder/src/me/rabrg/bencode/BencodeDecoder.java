package me.rabrg.bencode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class BencodeDecoder {

	private BencodeDecoder() {
		
	}

	public static Object decode(final StringBuffer bencode) {
		final char initial = bencode.charAt(0);
		switch(initial) {
		case 'i':
			final int integer= Integer.parseInt(bencode.substring(1, bencode.indexOf("e")));
			bencode.delete(0, bencode.indexOf("e") + 1);
			return integer;
		case 'l':
			final List<Object> list = new ArrayList<Object>();
			final StringBuffer listTemp = new StringBuffer(bencode.substring(1));
			while(true) {
				list.add(decode(listTemp));
				if(listTemp.length() <= 0) {
					throw new IllegalArgumentException("It seems that the ending 'e' of the list is missing.");
				} else if(listTemp.charAt(0) == 'e') {
					bencode.deleteCharAt(0);
					break;
				}
			}
			return list;
		case 'd':
			final Map<String, Object> map = new HashMap<String, Object>();
			final StringBuffer mapTemp = new StringBuffer(bencode.substring(1));
			while(true) {
				final String key = (String) decode(mapTemp);
				final Object value = decode(mapTemp);
				map.put(key, value);
				if(mapTemp.length() <= 0) {
					throw new IllegalArgumentException("It seems that the ending 'e' of the dictionary is missing.");
				} else if(mapTemp.charAt(0) == 'e') {
					bencode.deleteCharAt(0);
					break;
				}
			}
			return map;
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			final int stringLength = Integer.parseInt(bencode.substring(0, bencode.indexOf(":")));
			final String string = bencode.substring(bencode.indexOf(":") + 1, stringLength);
			bencode.delete(0, bencode.indexOf(":") + stringLength + 1);
			return string;
		default:
			throw new IllegalArgumentException("Illegal initial character: " + initial);
		}
	}
}
