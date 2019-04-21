package com.github.DarkSeraphim.sql;

public class Reference
{
	private final String table;

	private final String column;

	public Reference(final String table, final String column)
	{
		this.table = table;
		this.column = column;
	}

	public String getTable()
	{
		return this.table;
	}

	public String getColumn()
	{
		return this.column;
	}

}
