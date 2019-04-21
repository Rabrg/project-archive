package me.rabrg.bencode.type.impl;

import java.util.List;

import me.rabrg.bencode.type.BaseType;

@SuppressWarnings("rawtypes")
public final class ListType extends BaseType<List<BaseType>> {

	public ListType(final List<BaseType> value) {
		super(value);
	}

	@Override
	public String encode() {
		final StringBuffer encoded = new StringBuffer().append('l');
		for(final BaseType type : getValue()) {
			encoded.append(type.encode());
		}
		return encoded.append('e').toString();
	}

}
