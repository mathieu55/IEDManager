package org.ububiGroup.IEDManager.IO.generic;

import org.ububiGroup.IEDManager.model.generic.BIMData;

/**
 * Created by mathieu on 7/12/2016.
 */
public abstract class BIMFactory<E extends BIMData>
{
    public abstract E create();
}