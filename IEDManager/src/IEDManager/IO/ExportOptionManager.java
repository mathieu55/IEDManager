package IEDManager.IO;

import lombok.Getter;

import java.util.*;

/**
 * Created by mathieu on 8/21/2016.
 */
public final class ExportOptionManager
{
	private static ExportOptionManager expOptManager;
	public static ExportOptionManager getExportOptionManager()
	{
		if(expOptManager==null)
			expOptManager = new ExportOptionManager();
		return expOptManager;
	}

	private HashMap<String,ExportOption> lstExportOption =new HashMap<String,ExportOption>();
	@Getter private ExportOption defaultExportOption;

	private ExportOptionManager()
	{
		registerDefaultExportOption();
	}

	public ExportOption[] getExportOptionList()
	{
		ExportOption[] lst = new ExportOption[lstExportOption.values().size()];

		return (ExportOption[]) lstExportOption.values().toArray(lst);
	}

	public ExportOption getExportOptionFromName(String name)
	{
		return lstExportOption.get(name);
	}

	public boolean registerExportOption(ExportOption eo)
	{
		if(getExportOptionFromName(eo.getName())==null)
		{
			lstExportOption.put(eo.getName(),eo);
			return true;
		}
		else
		{
			return false;
		}
	}

	public ExportOption[] getAllExportOptionFromExtension(String extension)
	{
		return getAllExportOptionFromExtension(extension,false);
	}

	public ExportOption[] getAllExportOptionFromExtension(String extension, Boolean defaultWhenNotFound)
	{
		String extensionSearched = extension.toLowerCase();
		List<ExportOption> lst = new ArrayList<ExportOption>();

		for(ExportOption eo : this.lstExportOption.values())
		{
			if (eo.getExtension().compareTo(extensionSearched) == 0)
				lst.add(eo);
		}

		if(!defaultWhenNotFound || lst.size()>0)
		{
			ExportOption[] tabEO = new ExportOption[lst.size()];
			return lst.toArray(tabEO);
		}
		else
		{
			return new ExportOption[]{defaultExportOption};
		}
	}

	public ExportOption getFirstExportOptionFromExtension(String extension, Boolean defaultWhenNotFound)
	{
		String extensionSearched = extension.toLowerCase();
		List<ExportOption> lst = new ArrayList<ExportOption>();

		for(ExportOption eo : this.lstExportOption.values())
		{
			if (eo.getExtension().compareTo(extensionSearched) == 0)
				return eo;
		}

		if(defaultWhenNotFound)
			return defaultExportOption;
		else
			return null;
	}

	public ExportOption getFirstExportOptionFromExtension(String extension)
	{
		return getFirstExportOptionFromExtension(extension, false);
	}

	private void registerDefaultExportOption()
	{
		defaultExportOption= new ExportOption("IED",""+((char)30),""+((char)29),"ied",false,false)
		{
			@Override
			public String escapeField(String data){ return data; }

			@Override
			public String unescapeField(String data){ return data; }
		};
		registerExportOption(defaultExportOption);

		registerExportOption(new ExportOption("TSV","\t","\n","csv",true,false)
		{
			@Override
			public String escapeField(String data){ return data; }

			@Override
			public String unescapeField(String data){ return data; }
		});
	}


}
