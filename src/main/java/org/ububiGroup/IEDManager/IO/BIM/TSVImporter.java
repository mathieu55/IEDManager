package org.ububiGroup.IEDManager.IO.BIM;

import org.ububiGroup.IEDManager.IO.FieldSeparatedOption;
import org.ububiGroup.IEDManager.IO.generic.FieldSeperatedImporter;

import java.io.IOException;

public class TSVImporter extends FieldSeperatedImporter
{

    public TSVImporter()
    {
        super(FieldSeparatedOption.TSVFile);
    }
}
