package IEDManager.model.BIM;

import IEDManager.Annotation.SerializableVar;
import IEDManager.model.generic.BIMData;
import IEDManager.IO.generic.BIMFactory;
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

    @SerializableVar(Order=2, Name="Element Id")
    @Getter @Setter protected long elemId;

    @SerializableVar(Order=3, Name="Material Name")
    @Getter @Setter protected String name;

    @SerializableVar(Order=4, Name="Area")
    @Getter @Setter protected double area;

    @SerializableVar(Order=5, Name="Volume")
    @Getter @Setter protected double volume;

    public BIMMaterial(){}

    public BIMMaterial(long id, long elemId, String name, double area, double volume)
    {
        this.id = id;
        this.elemId = elemId;
        this.name = name;
        this.area = area;
        this.volume = volume;
    }

    //***************************************
    //             load data
    //***************************************
    public boolean load(String[] data)
    {
        if(data != null && data.length >= 5)
        {
            try
            {
                this.id = Long.parseLong(data[0],10);
                this.elemId = Long.parseLong(data[1],10);
                this.name = data[2];
                this.area = Double.parseDouble(data[3]);
                this.volume = Double.parseDouble(data[4]);;

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
