package IED.model.BIM;

import IED.model.generic.BIMData;
import IED.model.generic.BIMFactory;

/**
 * Created by mathieu on 6/18/2016.
 */
public class BIMMaterial extends BIMData
{

    private long id;
    private String name;
    private double depth;
    private String unit;
    private long typeId;

    public BIMMaterial()
    {
        this.id = -1;
        this.name = "";
        this.depth = 0;
        this.unit = "";
        this.typeId = -1;
    }

    public BIMMaterial(long id, String name, double depth, String unit,long typeId)
    {
        this.id = id;
        this.name = name;
        this.depth = depth;
        this.unit = unit;
        this.typeId = typeId;
    }

    //***************************************
    //     BIMMaterial Data
    //***************************************
    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getDepth()
    {
        return depth;
    }

    public void setDepth(double depth)
    {
        this.depth = depth;
    }

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public long getTypeId()
    {
        return this.typeId;
    }

    public void setTypeId(long typeId)
    {
        this.typeId = typeId;
    }

    //***************************************
    //     BIMMaterial Factory
    //***************************************
    protected static BIMFactory factory = new BIMMaterialFactory();

    public static BIMFactory getFactory()
    {
        return factory;
    }


    private static class BIMMaterialFactory extends BIMFactory<BIMMaterial>
    {
        public BIMMaterial create()
        {
            return new BIMMaterial();
        }
    }
}
