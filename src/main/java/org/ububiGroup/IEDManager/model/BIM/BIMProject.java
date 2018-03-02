package org.ububiGroup.IEDManager.model.BIM;

import lombok.Getter;
import org.ububiGroup.IEDManager.Annotation.SerializableVar;
import org.ububiGroup.IEDManager.IO.generic.BIMDataFactory;
import org.ububiGroup.IEDManager.model.generic.BIMData;
import org.ububiGroup.IEDManager.Utils.Convert;

import java.lang.reflect.Field;
import java.util.Map;

public class BIMProject extends BIMData
{
    @Getter private long id=-1; //The real ID is the UUID

    @SerializableVar(Order=1, Name="Project UUID")
    @Getter private String UUID ="";

    @SerializableVar(Order=2, Name="Building lifetime")
    @Getter private Double lifeTime = null;

    @SerializableVar(Order=3, Name="Number of storey")
    @Getter private Integer numberOfStorey = null;

    @SerializableVar(Order=4, Name="Building function")
    @Getter private String buildingFunction="";

    @SerializableVar(Order=5, Name="latitude")
    @Getter private Double latitude = null;

    @SerializableVar(Order=6, Name="longitude")
    @Getter private Double longitude = null;

    @SerializableVar(Order=7, Name="address")
    @Getter private String address = "";

    @SerializableVar(Order=8, Name="Structure type")
    @Getter private String structureType = "";

    public BIMProject(){}

    public BIMProject(String UUID,Double lifeTime,Integer numberOfStorey, String buildingFunction, Double latitude,
                      Double longitude, String address,String structureType)
    {
        this.UUID = UUID;
        this.lifeTime=lifeTime;
        this.numberOfStorey = numberOfStorey;
        this.buildingFunction = buildingFunction;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.structureType = structureType;
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
                UUID = data[0];
                lifeTime = Convert.toDouble(data[1]);
                numberOfStorey=Convert.toInteger(data[2]);
                buildingFunction=data[3];
                latitude=Convert.toDouble(data[4]);
                longitude=Convert.toDouble(data[5]);
                address=data[6];
                structureType=data[7];

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
    //     BIMProject Factory
    //***************************************

    protected static BIMDataFactory factory = new BIMProject.BIMProjectFactory();

    public static BIMDataFactory getFactory()
    {
        return factory;
    }

    private static class BIMProjectFactory extends BIMDataFactory<BIMProject>
    {
        public BIMProject create()
        {
            return new BIMProject();
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
