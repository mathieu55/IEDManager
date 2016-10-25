package org.ububiGroup.IEDManager.model.BIM;

import org.ububiGroup.IEDManager.Annotation.SerializableVar;
import org.ububiGroup.IEDManager.model.generic.BIMData;
import org.ububiGroup.IEDManager.IO.generic.BIMFactory;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by mathieu on 6/18/2016.
 */
public class BIMMaterial extends BIMData
{
    @SerializableVar(Order=1, Name="Material Id")
    @Getter protected long id;

    @SerializableVar(Order=2, Name="Owner Id")
    @Getter @Setter protected long ownerId;

    @SerializableVar(Order=3, Name="Material Name")
    @Getter @Setter protected String name;

    @SerializableVar(Order=4, Name="Area")
    @Getter @Setter protected double area;

    @SerializableVar(Order=5, Name="Volume")
    @Getter @Setter protected double volume;

    @SerializableVar(Order=6, Name="Ratio Area")
    @Getter @Setter protected double ratioArea;

    @SerializableVar(Order=7, Name="Ratio Volume")
    @Getter @Setter protected double ratioVolume;

    public BIMMaterial(){}

    public BIMMaterial(long id, long ownerId, String name, double area, double volume, double ratioArea, double ratioVolume)
    {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.area = area;
        this.volume = volume;
        this.ratioArea = ratioArea;
        this.ratioVolume = ratioVolume;
    }

    //***************************************
    //             load data
    //***************************************
    public boolean load(String[] data)
    {
        if(data != null && data.length >= 7)
        {
            try
            {
                this.id = Long.parseLong(data[0],10);
                this.ownerId = Long.parseLong(data[1],10);
                this.name = data[2];
                this.area = Double.parseDouble(data[3]);
                this.volume = Double.parseDouble(data[4]);
                this.ratioArea = Double.parseDouble(data[5]);
                this.ratioVolume = Double.parseDouble(data[6]);

                return true;
            }
            catch(Exception e)
            {
                return false;
            }
        }
        return false;
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

    private static Map<Integer, Field> serializableMap;
    public Map<Integer,Field> getMap()
    {
        if(serializableMap == null)
            serializableMap = BIMData.loadMap(this);
        return serializableMap;
    }
}
