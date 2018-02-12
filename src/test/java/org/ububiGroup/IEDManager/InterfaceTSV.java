package org.ububiGroup.IEDManager;

import org.ububiGroup.IEDManager.IO.BIM.TSVExporter;
import org.ububiGroup.IEDManager.IO.BIM.TSVImporter;
import org.ububiGroup.IEDManager.IO.ExportOption;
import org.ububiGroup.IEDManager.IO.generic.baseExporter;
import org.ububiGroup.IEDManager.IO.generic.baseImporter;

public class InterfaceTSV extends InterfaceByExt
{
    public InterfaceTSV()
    {
        super(new ExportOption("TSV","csv")
        {
            @Override
            public baseExporter getExporter() { return new TSVExporter(); }

            @Override
            public baseImporter getImporter() { return new TSVImporter(); }
        });
    }
}
