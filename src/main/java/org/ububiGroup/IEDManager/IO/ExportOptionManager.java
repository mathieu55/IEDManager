package org.ububiGroup.IEDManager.IO;

import lombok.Getter;
import org.ububiGroup.IEDManager.IO.BIM.*;
import org.ububiGroup.IEDManager.IO.generic.baseExporter;
import org.ububiGroup.IEDManager.IO.generic.baseImporter;

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

	private HashMap<String,ExportOption> lstExportOption =new HashMap<>();
	@Getter private ExportOption defaultOption;

	private ExportOptionManager()
	{
		ExportOption tmp=new ExportOption("CSV","ZIP")
		{
			 @Override
			 public baseExporter getExporter() { return new TSVExporter(); }

			 @Override
			 public baseImporter getImporter() { return new TSVImporter(); }
		 };
		this.lstExportOption.put(tmp.name,tmp);

		tmp=new ExportOption("IED legacy","ZIP")
		{
			@Override
			public baseExporter getExporter() { return new IED1Exporter(); }

			@Override
			public baseImporter getImporter() { return new IED1Importer(); }
		};
		this.lstExportOption.put(tmp.name,tmp);

		tmp=new ExportOption("IED legacy","IED")
		{
			@Override
			public baseExporter getExporter() { return new IEDExporter(); }

			@Override
			public baseImporter getImporter() { return new IEDImporter(); }
		};
		this.lstExportOption.put(tmp.name,tmp);

		defaultOption=tmp;
	}

	public ExportOption[] getExportOptionList()
	{
		ExportOption[] lst = new ExportOption[lstExportOption.values().size()];

		return (ExportOption[]) lstExportOption.values().toArray(lst);
	}

	public abstract class ExportOption
	{
		@Getter private String name;
		@Getter private String extension;
		protected ExportOption(String name, String extension)
		{
			this.name=name;
			this.extension=extension;
		}

		public abstract baseExporter getExporter();
		public abstract baseImporter getImporter();
	}
}
