package me.rabrg.bencode.type.impl;

import java.util.Map;
import java.util.Map.Entry;

import me.rabrg.bencode.type.BaseType;

public final class DictionaryType extends BaseType<Map<StringType, BaseType<Object>>> {

	public DictionaryType(final Map<StringType, BaseType<Object>> value) {
		super(value);
	}

	@Override
	public String encode() { // TODO: Add sorting code + Finish Work Item in Codeplex TFS.
		final StringBuffer encoded = new StringBuffer().append('d');
		for (final Entry<StringType, BaseType<Object>> entry : getValue().entrySet()) {
			encoded.append(entry.getKey().encode()).append(entry.getValue().encode());
		}
		return encoded.append('e').toString();
	}

}
