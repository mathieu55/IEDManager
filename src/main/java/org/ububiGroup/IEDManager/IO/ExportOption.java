package org.ububiGroup.IEDManager.IO;

import lombok.Getter;

/**
 * Created by mathieu on 8/21/2016.
 */
public abstract class ExportOption
{
	@Getter private String name;
	@Getter private String fieldSeparator;
	@Getter private String recordSeparator;
	@Getter private byte recordSeparatorAnsiCode;
	@Getter private String extension;
	@Getter private boolean headerColumnNeeded;

	protected boolean escapeNeeded=false;

	protected ExportOption(String name, String fieldSeparator, String recordSeparator, String extension,
	                       Boolean headerColumnNeeded, boolean escapeNeeded)
	{
		this.name=name;
		this.fieldSeparator=fieldSeparator;
		this.recordSeparator=recordSeparator;
		this.extension=extension.toLowerCase();
		this.headerColumnNeeded = headerColumnNeeded;
		this.escapeNeeded=escapeNeeded;
		recordSeparatorAnsiCode = (byte)recordSeparator.getBytes()[0];
	}

	public String[] escapeAllFields(String[] data)
	{
		if(!escapeNeeded || data == null)
			return data;

		String[] escapeData = new String[data.length];
		for(int i=0;i<data.length;i++)
		{
			escapeData[i]=escapeField(data[i]);
		}
		return escapeData;
	}

	public String[] unescapeAllFields(String[] data)
	{
		if(!escapeNeeded || data == null)
			return data;

		String[] unescapeData = new String[data.length];
		for(int i=0;i<data.length;i++)
		{
			unescapeData[i]=unescapeField(data[i]);
		}
		return unescapeData;
	}

	public abstract String escapeField(String data);
	public abstract String unescapeField(String data);

}
