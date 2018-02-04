package org.ububiGroup.IEDManager.IO.BIM;

import org.ububiGroup.IEDManager.IO.FieldSeparatedOption;
import org.ububiGroup.IEDManager.IO.generic.FieldSeparatedExporter;

import java.io.IOException;

public class IED1Exporter extends FieldSeparatedExporter
{
    public IED1Exporter()
    {
        super(FieldSeparatedOption.IED1File);
    }
}
