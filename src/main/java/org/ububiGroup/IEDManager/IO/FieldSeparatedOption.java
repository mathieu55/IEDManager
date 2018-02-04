package org.ububiGroup.IEDManager.IO;

import lombok.Getter;

import java.util.HashMap;
import java.util.zip.ZipOutputStream;

/**
 * Created by mathieu on 8/21/2016.
 */
public class FieldSeparatedOption
{
	public static final FieldSeparatedOption TSVFile = new FieldSeparatedOption("csv","\t","\n",true,null);
	public static final FieldSeparatedOption IED1File = new FieldSeparatedOption("ied","\t","\n",true,null);

	@Getter private String fieldSeparator;
	@Getter private String recordSeparator;
	@Getter private String extension;
	@Getter private boolean headerColumnNeeded;
	@Getter private FieldHandler fieldHandler;

	public FieldSeparatedOption(String extension,String fieldSeparator, String recordSeparator, Boolean headerColumnNeeded, FieldHandler fieldHandler)
	{
		this.fieldSeparator=fieldSeparator;
		this.recordSeparator=recordSeparator;
		this.extension=extension.toLowerCase();
		this.headerColumnNeeded = headerColumnNeeded;
		this.fieldHandler=fieldHandler;
	}

	public String[] escapeAllFields(String[] data)
	{
		if(fieldHandler==null || data == null)
			return data;

		String[] escapeData = new String[data.length];
		for(int i=0;i<data.length;i++)
		{
			escapeData[i]=fieldHandler.escapeField(data[i]);
		}
		return escapeData;
	}

	public String[] unescapeAllFields(String[] data)
	{
		if(fieldHandler==null || data == null)
			return data;

		String[] unescapeData = new String[data.length];
		for(int i=0;i<data.length;i++)
		{
			unescapeData[i]=fieldHandler.unescapeField(data[i]);
		}
		return unescapeData;
	}

	public abstract class FieldHandler
	{
		public abstract String escapeField(String field);
		public abstract String unescapeField(String field);
	}
}
