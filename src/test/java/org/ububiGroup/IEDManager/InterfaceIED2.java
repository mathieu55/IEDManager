package org.ububiGroup.IEDManager;

import org.ububiGroup.IEDManager.IO.BIM.IED1Exporter;
import org.ububiGroup.IEDManager.IO.BIM.IED1Importer;
import org.ububiGroup.IEDManager.IO.BIM.IED2Exporter;
import org.ububiGroup.IEDManager.IO.BIM.IED2Importer;
import org.ububiGroup.IEDManager.IO.ExportOption;
import org.ububiGroup.IEDManager.IO.generic.baseExporter;
import org.ububiGroup.IEDManager.IO.generic.baseImporter;

public class InterfaceIED2 extends InterfaceByExt
{
    public InterfaceIED2()
    {
        super(new ExportOption("IED2","ied2")
        {
            @Override
            public baseExporter getExporter() { return new IED2Exporter(); }

            @Override
            public baseImporter getImporter() { return new IED2Importer(); }
        });
    }
}
