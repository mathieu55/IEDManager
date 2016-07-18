package IED.model;

import IED.model.generic.BIMData;
import IED.model.generic.BIMFactory;

/**
 * Created by mathieu on 6/18/2016.
 */
public class BIMObjectType implements BIMData
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