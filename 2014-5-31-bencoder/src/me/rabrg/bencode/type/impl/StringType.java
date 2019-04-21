package me.rabrg.bencode.type.impl;

import me.rabrg.bencode.type.BaseType;

public final class StringType extends BaseType<String> {

	public StringType(final String value) {
		super(value);
	}

	@Override
	public String encode() {
		return new StringBuffer().append(getValue().length()).append(':').append(getValue()).toString();
	}

}
