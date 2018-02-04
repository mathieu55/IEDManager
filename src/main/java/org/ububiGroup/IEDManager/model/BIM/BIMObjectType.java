package org.ububiGroup.IEDManager.model.BIM;

import org.ububiGroup.IEDManager.Annotation.SerializableVar;
import org.ububiGroup.IEDManager.model.generic.BIMData;
import org.ububiGroup.IEDManager.IO.generic.BIMDataFactory;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by mathieu on 6/18/2016.
 */
public class BIMObjectType extends BIMData
{
    @SerializableVar(Order=1, Name="Object Type Id")
    @Getter protected long id;

    @SerializableVar(Order=2, Name="Name")
    @Getter @Setter protected String name;

    @SerializableVar(Order=3, Name="Uniformat Code")
    @Getter @Setter protected String uniformatCode;

    @SerializableVar(Order=4, Name="Uniformat Description")
    @Getter @Setter protected String uniformatDesc;

    @SerializableVar(Order=5, Name="Family Name")
    @Getter @Setter protected String familyName;

    //***************************************
    //             constructor
    //***************************************
    public BIMObjectType(){}

    public BIMObjectType(long id, String name, String uniformatCode, String uniformatDesc,String familyName)
    {
        this.id=id;
        this.name=name;
        this.uniformatCode=uniformatCode;
        this.uniformatDesc=uniformatDesc;
        this.familyName=familyName;
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
                this.name = data[1];
                this.uniformatCode = data[2];
                this.uniformatDesc = data[3];
                this.familyName = data[4];

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
    //     BIMOjectType Factory
    //***************************************
    protected static BIMDataFactory factory = new BIMObjectTypeFactory();

    public static BIMDataFactory getFactory()
    {
        return factory;
    }

    private static class BIMObjectTypeFactory extends BIMDataFactory<BIMObjectType>
    {
        public BIMObjectType create()
        {
            return new BIMObjectType();
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
