package org.ububiGroup.IEDManager;

import org.ububiGroup.IEDManager.IO.BIM.IED1Exporter;
import org.ububiGroup.IEDManager.IO.BIM.IED1Importer;
import org.ububiGroup.IEDManager.IO.ExportOption;
import org.ububiGroup.IEDManager.IO.generic.baseExporter;
import org.ububiGroup.IEDManager.IO.generic.baseImporter;

public class InterfaceIED1 extends InterfaceByExt
{
    public InterfaceIED1()
    {
        super(new ExportOption("IED1","ied1")
        {
            @Override
            public baseExporter getExporter() { return new IED1Exporter(); }

            @Override
            public baseImporter getImporter() { return new IED1Importer(); }
        });
    }
}
