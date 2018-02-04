package org.ububiGroup.IEDManager.IO.generic;

import org.ububiGroup.IEDManager.model.generic.BIMData;

import java.io.Closeable;
import java.io.IOException;

public abstract class baseWriter implements Closeable
{
    public abstract boolean writeObject(BIMData obj) throws IOException;
}
