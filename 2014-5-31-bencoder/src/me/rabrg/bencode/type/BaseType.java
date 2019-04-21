package me.rabrg.bencode.type;

public abstract class BaseType<T extends Object> {

	private final T t;

	public BaseType(final T t) {
		this.t = t;
	}

	public abstract String encode();

	public final T getValue() {
		return t;
	}

	@Override
	public String toString() {
		return t.toString();
	}
}
