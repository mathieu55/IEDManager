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
public class BIMObject extends BIMData implements ITypedBIMData
{
    @SerializableVar(Order=1, Name="Object Id")
    @Getter protected long id;

    @SerializableVar(Order=2, Name="Object Name")
    @Getter @Setter protected String name;

    @SerializableVar(Order=3, Name="Type Id")
    @Getter @Setter protected long typeId;

    @SerializableVar(Order=4, Name="Object Type")
    @Getter @Setter protected String objectType;

    @SerializableVar(Order=5, Name="Category")
    @Getter @Setter protected String category;

    @SerializableVar(Order=6, Name="Uniformat Code")
    @Getter @Setter protected String uniformatCode;

    @SerializableVar(Order=7, Name="Area")
    @Getter @Setter protected double area;

    @SerializableVar(Order=8, Name="Volume")
    @Getter @Setter protected double volume;

    //***************************************
    //             constructor
    //***************************************
    public BIMObject()
    {
        this(-1,"",-1,"","","",0,0);
    }

    public BIMObject(long id,String name, long typeId, String objectType, String category,
                     String uniformatCode, double area, double volume)
    {
        this.id=id;
        this.name=name;
        this.typeId=typeId;
        this.objectType=objectType;
        this.category=category;
        this.uniformatCode=uniformatCode;
        this.area=area;
        this.volume=volume;
    }

    //***************************************
    //             load data
    //***************************************
    public boolean load(String[] data)
    {
        if(data != null && data.length >= 8)
        {
            try
            {
                if(data[0].compareTo("")!=0)this.id = Long.parseLong(data[0],10);
                this.name = data[1];
                if(data[2].compareTo("")!=0)this.typeId = Long.parseLong(data[2],10);
                this.objectType = data[3];
                this.category = data[4];
                this.uniformatCode = data[5];
                this.area = Double.parseDouble(data[6]);
                this.volume = Double.parseDouble(data[7]);;

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
    //     BIMOject Factory
    //***************************************
    protected static BIMDataFactory factory = new BIMObjectFactory();

    public static BIMDataFactory getFactory()
    {
        return factory;
    }

    private static class BIMObjectFactory extends BIMDataFactory<BIMObject>
    {
        public BIMObject create()
        {
            return new BIMObject();
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
