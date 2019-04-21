package me.rabrg.bencode.type.impl;

import me.rabrg.bencode.type.BaseType;

public final class IntegerType extends BaseType<Integer> {

	public IntegerType(final Integer value) {
		super(value);
	}

	@Override
	public String encode() {
		return new StringBuffer().append('i').append(getValue()).append('e').toString();
	}

}
