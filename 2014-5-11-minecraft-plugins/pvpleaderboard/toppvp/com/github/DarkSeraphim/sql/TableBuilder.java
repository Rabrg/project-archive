package com.github.DarkSeraphim.sql;

import java.util.HashMap;
import java.util.Map;

public class TableBuilder
{
	private final String name;

	private String pkey;

	private final Map<String, PropertyList> columns = new HashMap<String, PropertyList>();

	private final Map<String, Reference> references = new HashMap<String, Reference>();

	public TableBuilder(final String name)
	{
		this.name = name;
	}

	public PropertyList addColumn(final String name, final String type)
	{
		final PropertyList list = new PropertyList(type);
		this.columns.put(name, list);
		return list;
	}

	public void setPrimaryKey(final String field)
	{
		if(this.columns.get(field) == null)
		{
			// This might need some cleaning up, like an actual Logger reference
			System.out.println(String.format("Field '%s' does not exist in table '%s'", field, this.name));
			return;
		}
		this.pkey = field;
	}

	public String getPrimaryKey()
	{
		return this.columns.get(this.pkey) != null ? this.pkey : null;
	}

	public void addReference(final String field, final String table, final String column)
	{
		if(this.columns.get(field) == null)
		{
			// This might need some cleaning up, like an actual Logger reference
			System.out.println(String.format("Field '%s' does not exist in table '%s'", field, this.name));
			return;
		}
		this.references.put(field, new Reference(table, column));
	}

	public Map<String, Reference> getReferences()
	{
		return this.references;
	}

	public Map<String, PropertyList> getColumns()
	{
		return this.columns;
	}

	public String getTableName()
	{
		return this.name;
	}

	public void createTable(final Database db)
	{
		db.createTable(this);
	}

}
