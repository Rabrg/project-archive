import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class JsonDecoder {

	private final byte[] buffer;
	private int pos = 0;

	public JsonDecoder(final InputStream inputStream) throws IOException {
		buffer = new byte[inputStream.available()];
		inputStream.read(buffer);
	}

	public JsonDecoder(final String string) {
		buffer = string.getBytes();
	}

	public JsonDecoder(final byte[] data) {
		buffer = data;
	}

	private Object decodeObject() {
		if (buffer[pos] == '{') {
			return decodeMap();
		} else if (buffer[pos] == '"') {
			return decodeString();
		} else if (buffer[pos] == 't' || buffer[pos] == 'f') {
			return decodeBoolean();
		} else if (buffer[pos] >= '0' && buffer[pos] <= '9') {
			return decodeNumber();
		} else if (buffer[pos] == '[') {
			System.out.println(pos);
			return decodeList();
		}
		return null;
	}

	public Map<String, Object> decodeMap() {
		final Map<String, Object> map = new HashMap<String, Object>();
		while (buffer[pos++] != '}') {
			if (buffer[pos] == ',') {
				continue;
			}
			final String key = decodeString();
			pos++;
			final Object object = decodeObject();
			map.put(key, object);
		}
		return map;
	}

	private String decodeString() {
		int start = ++pos;
		while (buffer[++pos] != '"') {
			
		}
		return new String(buffer, start, pos++ - start);
	}

	private boolean decodeBoolean() {
		if (buffer[pos] == 't') {
			pos += 4;
			return true;
		} else {
			pos += 5;
			return false;
		}
	}

	private Number decodeNumber() {
		int start = pos;
		boolean integer = true;
		while (buffer[pos++] != ',') {
			if (pos == buffer.length) {
				break;
			}
			if (buffer[pos] == '.') {
				integer = false;
			}
		}
		pos--;
		final String value = new String(buffer, start, pos - start);
		Number number;
		if (integer) {
			try {
				number = Integer.parseInt(value);
			} catch (final NumberFormatException e) {
				number = Long.parseLong(value);
			}
		} else {
			try {
				number = Float.parseFloat(value);
			} catch (final NumberFormatException e) {
				number = Double.parseDouble(value);
			}
		}
		return number;
	}

	private List<Object> decodeList() {
		final List<Object> list = new ArrayList<Object>();
		while (buffer[pos++] != ']') {
			list.add(decodeObject());
		}
		return list;
	}
}