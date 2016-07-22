package IED.model.BIM;

import IED.model.generic.BIMData;
import IED.model.generic.BIMFactory;

/**
 * Created by mathieu on 6/18/2016.
 */
public class BIMObject extends BIMData
{
    private long id;

    //***************************************
    //     BIMOject Data
    //***************************************
    public long getId()
    {
        return id;
    }

    //***************************************
    //     BIMOject Factory
    //***************************************
    protected static BIMFactory factory = new BIMObjectFactory();

    public static BIMFactory getFactory()
    {
        return factory;
    }

    private static class BIMObjectFactory extends BIMFactory<BIMObject>
    {
        public BIMObject create()
        {
            return new BIMObject();
        }
    }
}
