package org.ububiGroup.IEDManager; /**
 * Created by mathieu on 7/21/2016.
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ububiGroup.IEDManager.IO.BIM.TSVExporter;
import org.ububiGroup.IEDManager.IO.BIM.TSVImporter;
import org.ububiGroup.IEDManager.IO.ExportOption;
import org.ububiGroup.IEDManager.IO.generic.IEDImportHandler;
import org.ububiGroup.IEDManager.IO.generic.baseExporter;
import org.ububiGroup.IEDManager.IO.generic.baseImporter;
import org.ububiGroup.IEDManager.model.BIM.BIMMaterial;
import org.ububiGroup.IEDManager.model.BIM.BIMObject;
import org.ububiGroup.IEDManager.model.BIM.BIMObjectType;
import org.ububiGroup.IEDManager.model.generic.BIMData;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public abstract class InterfaceByExt
{

    protected ExportOption exportOption;
    private String filePath;

    protected InterfaceByExt(ExportOption exportOption)
    {
        this.exportOption=exportOption;
    }

    @BeforeEach
    public void createTempFile() throws IOException
    {
        filePath = File.createTempFile("tmp",".zip").getPath();
    }

    @AfterEach
    public void deleteTempFile() throws IOException
    {
        new File(filePath).delete();
    }

    @Test
    public void testModuleInterfaceMaterial() throws IOException
    {
        testModuleInterface(rndUtil.randomMaterials(10,50),null,null);
    }

    @Test
    public void testModuleInterfaceObject() throws IOException
    {
        testModuleInterface(null,rndUtil.randomObjects(10,50),null);
    }

    @Test
    public void testModuleInterfaceObjectType() throws IOException
    {
        testModuleInterface(null,null,rndUtil.randomObjectTypes(10,50));
    }

    @Test
    public void testModuleInterfaceAllBimData() throws IOException
    {
        testModuleInterface(rndUtil.randomMaterials(10,50),rndUtil.randomObjects(10,50),rndUtil.randomObjectTypes(10,50));
    }

    private void testModuleInterface(HashMap<Long,BIMMaterial> lstMaterial,HashMap<Long,BIMObject> lstObject,HashMap<Long,BIMObjectType> lstObjectType) throws IOException
    {
        final HashMap<Long,BIMMaterial> lstImpMaterial = new HashMap<>();
        final HashMap<Long,BIMObject> lstImpObject = new HashMap<>();
        final HashMap<Long,BIMObjectType> lstImpObjectType = new HashMap<>();

        System.out.println("Initial Qty:");
        System.out.println("------------------------------");
        System.out.println("Materials  : " +(lstMaterial!=null?lstMaterial.size():0));
        System.out.println("Objects    : " +(lstObject!=null?lstObject.size():0));
        System.out.println("ObjectType : " +(lstObjectType!=null?lstObjectType.size():0));
        System.out.println("");

        //Create handlers
        IEDImportHandler<BIMMaterial> materialHandler = new IEDImportHandler<BIMMaterial>()
        {
            @Override
            public boolean processData(BIMMaterial bimData)
            {
                lstImpMaterial.put(bimData.getId(),bimData);
                return true;
            }
        };

        IEDImportHandler<BIMObject> objectHandler = new IEDImportHandler<BIMObject>()
        {
            @Override
            public boolean processData(BIMObject bimData)
            {
                lstImpObject.put(bimData.getId(),bimData);
                return true;
            }
        };

        IEDImportHandler<BIMObjectType> objectTypeHandler = new IEDImportHandler<BIMObjectType>()
        {
            @Override
            public boolean processData(BIMObjectType bimData)
            {
                lstImpObjectType.put(bimData.getId(),bimData);
                return true;
            }
        };

        //Write material in designed file
        try(baseExporter exp = exportOption.getExporter())
        {
            exp.init(filePath);
            if(lstMaterial!=null)
                for(HashMap.Entry<Long,BIMMaterial> i:lstMaterial.entrySet())
                    exp.ExportBIMMaterial(i.getValue());

            if(lstObject!=null)
                for(HashMap.Entry<Long,BIMObject> i:lstObject.entrySet())
                    exp.ExportBIMObject(i.getValue());

            if(lstObjectType!=null)
                for(HashMap.Entry<Long,BIMObjectType> i:lstObjectType.entrySet())
                    exp.ExportBIMObjectType(i.getValue());
        }

        //Read with the handler
        try(baseImporter imp = exportOption.getImporter())
        {
            imp.init(filePath);
            imp.ProcessAll(materialHandler, objectHandler, objectTypeHandler);
        }

        //Data validation
        assertBIMDataHashMap("Material Import",lstMaterial,lstImpMaterial);
        assertBIMDataHashMap("Object Import",lstObject,lstImpObject);
        assertBIMDataHashMap("Object type Import",lstObjectType,lstImpObjectType);

        System.out.println("Final Qty:");
        System.out.println("------------------------------");
        System.out.println("Materials  : " +(lstImpMaterial!=null?lstImpMaterial.size():0));
        System.out.println("Objects    : " +(lstImpObject!=null?lstImpObject.size():0));
        System.out.println("ObjectType : " +(lstImpObjectType!=null?lstImpObjectType.size():0));
    }

    private void assertBIMDataHashMap(String desc,HashMap<Long,? extends BIMData> original,HashMap<Long,? extends BIMData> imported)
    {
        assertSame(original!=null?original.size():0,imported.size(),desc+": Quatity of item imported");

        if(original!=null)
        {
            for (HashMap.Entry<Long, ? extends BIMData> i : original.entrySet()) {
                BIMData importedBIMData = imported.getOrDefault(i.getKey(), null);
                assertNotNull(importedBIMData,desc + ": Impossible to find data " + i.getKey());
                assertArrayEquals(i.getValue().export(), importedBIMData.export(),desc + ": Data are note the same");
            }
        }
    }
}