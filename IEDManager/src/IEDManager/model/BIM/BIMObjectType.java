package IEDManager.model.BIM;

import IEDManager.model.generic.BIMData;
import IEDManager.model.generic.BIMFactory;

/**
 * Created by mathieu on 6/18/2016.
 */
public class BIMObjectType extends BIMData
{

    //***************************************
    //     BIMOjectType Data
    //***************************************
    public long getId()
    {
        return -1;
    }

    //***************************************
    //     BIMOjectType Factory
    //***************************************
    protected static BIMFactory factory = new BIMObjectTypeFactory();

    public static BIMFactory getFactory()
    {
        return factory;
    }

    private static class BIMObjectTypeFactory extends BIMFactory<BIMObjectType>
    {
        public BIMObjectType create()
        {
            return new BIMObjectType();
        }
    }
}
