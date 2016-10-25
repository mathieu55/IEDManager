package org.ububiGroup.IEDManager.IO.generic;

import org.ububiGroup.IEDManager.model.generic.BIMData;

/**
 * Created by mathieu on 7/20/2016.
 */
public abstract class IEDImportHandler<T extends BIMData> {
    public abstract boolean processData(T bimData);
}
