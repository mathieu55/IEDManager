package org.ububiGroup.IEDManager.IO.BIM;

import org.ububiGroup.IEDManager.IO.FieldSeparatedOption;
import org.ububiGroup.IEDManager.IO.generic.FieldSeperatedImporter;

import java.io.IOException;

public class IED1Importer extends FieldSeperatedImporter
{

    public IED1Importer()
    {
        super(FieldSeparatedOption.IED1File);
    }
}
