package org.ububiGroup.IEDManager.model.BIM;

import org.ububiGroup.IEDManager.Annotation.SerializableVar;
import org.ububiGroup.IEDManager.model.generic.BIMData;
import org.ububiGroup.IEDManager.IO.generic.BIMDataFactory;
import lombok.Getter;
import lombok.Setter;
import org.ububiGroup.IEDManager.model.generic.ITypedBIMData;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by mathieu on 6/18/2016.
 */
public class BIMMaterial extends BIMData implements ITypedBIMData
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

    @SerializableVar(Order=8, Name="Description")
    @Getter @Setter protected String description;

    @SerializableVar(Order=9, Name="Manufacturer")
    @Getter @Setter protected String manufacturer;

    @SerializableVar(Order=10, Name="Mark")
    @Getter @Setter protected String mark;

    @SerializableVar(Order=11, Name="Function")
    @Getter @Setter protected String function;

    public BIMMaterial()
    {
        this(-1,-1,"",0,0,0,0);
    }

    public BIMMaterial(long id, long ownerId, String name, double area, double volume, double ratioArea, double ratioVolume)
    {
     this(id, ownerId, name, area, volume, ratioArea, ratioVolume,"","","");
    }

    public BIMMaterial(long id, long ownerId, String name, double area, double volume, double ratioArea,
                       double ratioVolume, String description, String manufacturer, String mark)
    {
        this(id, ownerId, name, area, volume, ratioArea, ratioVolume, description, manufacturer, mark,"");
    }

    public BIMMaterial(long id, long ownerId, String name, double area, double volume, double ratioArea,
                       double ratioVolume, String description, String manufacturer, String mark, String function)
    {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.area = area;
        this.volume = volume;
        this.ratioArea = ratioArea;
        this.ratioVolume = ratioVolume;
        this.description = description;
        this.manufacturer = manufacturer;
        this.mark = mark;
        this.function = function;
    }

    //***************************************
    //             load data
    //***************************************
    public boolean load(String[] data)
    {
        if(data != null && data.length >= 10)
        {
            try
            {
                if(data[0].compareTo("")!=0)this.id = Long.parseLong(data[0],10);
                if(data[1].compareTo("")!=0)this.ownerId = Long.parseLong(data[1],10);
                this.name = data[2];
                this.area = Double.parseDouble(data[3]);
                this.volume = Double.parseDouble(data[4]);
                this.ratioArea = Double.parseDouble(data[5]);
                this.ratioVolume = Double.parseDouble(data[6]);
                this.description = data[7];
                this.manufacturer = data[8];
                this.mark = data[9];
                this.function = data[10];
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
    protected static BIMDataFactory factory = new BIMMaterialFactory();

    public static BIMDataFactory getFactory()
    {
        return factory;
    }

    private static class BIMMaterialFactory extends BIMDataFactory<BIMMaterial>
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

    @Override
    public long getTypeId() {
        return getOwnerId();
    }

    @Override
    public void setTypeId(long id) {
        setOwnerId(id);
    }
}
