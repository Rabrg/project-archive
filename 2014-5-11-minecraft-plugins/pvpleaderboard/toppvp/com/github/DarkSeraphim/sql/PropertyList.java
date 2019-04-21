package com.github.DarkSeraphim.sql;

import java.util.ArrayList;

public class PropertyList extends ArrayList<String>
{
	private static final long serialVersionUID = -7736338045107012795L;
	private final String type;

	protected PropertyList(final String type)
	{
		this.type = type;
	}

	public PropertyList addProperty(final String property)
	{
		super.add(property);
		return this;
	}

	@Override
	public boolean add(final String e)
	{
		throw new UnsupportedOperationException("This operation cannot be performed on a PropertyList");
	}

	@Override
	public void add(final int index, final String e)
	{
		throw new UnsupportedOperationException("This operation cannot be performed on a PropertyList");
	}

	public String getProperties()
	{
		final StringBuilder sb = new StringBuilder(this.type);
		for(final String p : this)
			sb.append(" ").append(p);
		return sb.toString();
	}
}