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
		ExportOption tmp=new ExportOption("CSV","zip")
		{
			 @Override
			 public baseExporter getExporter() { return new TSVExporter(); }

			 @Override
			 public baseImporter getImporter() { return new TSVImporter(); }
		 };
		this.lstExportOption.put(tmp.getName(),tmp);

		tmp=new ExportOption("IED v1","ied1")
		{
			@Override
			public baseExporter getExporter() { return new IED1Exporter(); }

			@Override
			public baseImporter getImporter() { return new IED1Importer(); }
		};
		this.lstExportOption.put(tmp.getName(),tmp);

		tmp=new ExportOption("IED v2","ied2")
		{
			@Override
			public baseExporter getExporter() { return new IED2Exporter(); }

			@Override
			public baseImporter getImporter() { return new IED2Importer(); }
		};
		this.lstExportOption.put(tmp.getName(),tmp);

		tmp=new ExportOption("IED","ied")
		{
			@Override
			public baseExporter getExporter() { return new IEDExporter(); }

			@Override
			public baseImporter getImporter() { return new IEDImporter(); }
		};
		this.lstExportOption.put(tmp.getName(),tmp);

		defaultOption=tmp;
	}

	public ExportOption[] getExportOptionList()
	{
		ExportOption[] lst = new ExportOption[lstExportOption.values().size()];

		return (ExportOption[]) lstExportOption.values().toArray(lst);
	}

}
