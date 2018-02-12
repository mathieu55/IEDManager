package org.ububiGroup.IEDManager;

import org.ububiGroup.IEDManager.IO.BIM.IEDExporter;
import org.ububiGroup.IEDManager.IO.BIM.IEDImporter;
import org.ububiGroup.IEDManager.IO.ExportOption;
import org.ububiGroup.IEDManager.IO.generic.baseExporter;
import org.ububiGroup.IEDManager.IO.generic.baseImporter;

public class InterfaceIED extends InterfaceByExt
{
    public InterfaceIED()
    {
        super(new ExportOption("IED","ied")
        {
            @Override
            public baseExporter getExporter() { return new IEDExporter(); }

            @Override
            public baseImporter getImporter() { return new IEDImporter(); }
        });
    }
}
