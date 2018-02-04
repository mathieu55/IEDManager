package org.ububiGroup.IEDManager.IO.BIM;

import org.ububiGroup.IEDManager.IO.FieldSeparatedOption;
import org.ububiGroup.IEDManager.IO.generic.FieldSeparatedExporter;

import java.io.IOException;

public class TSVExporter extends FieldSeparatedExporter
{
    public TSVExporter()
    {
        super(FieldSeparatedOption.TSVFile);
    }
}
