package org.ububiGroup.IEDManager; /**
 * Created by mathieu on 7/21/2016.
 */
import static org.junit.Assert.*;

import org.ububiGroup.IEDManager.IO.BIM.IED1Exporter;
import org.ububiGroup.IEDManager.IO.BIM.IED1Importer;
import org.ububiGroup.IEDManager.IO.generic.baseExporter;
import org.ububiGroup.IEDManager.IO.generic.baseImporter;
import org.ububiGroup.IEDManager.IO.generic.IEDImportHandler;
import org.ububiGroup.IEDManager.model.BIM.BIMMaterial;
import org.ububiGroup.IEDManager.model.BIM.BIMObject;
import org.ububiGroup.IEDManager.model.BIM.BIMObjectType;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class InterfaceBIMDefaultExt
{

    private String filePath;

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
        BIMMaterial mat1 = new BIMMaterial(1l,11l,"Mat1",1.1,11.11,0.1,0.11);
        BIMMaterial mat2 = new BIMMaterial(2l,22l,"Mat2",2.2,22.22,0.2,0.22);
        BIMMaterial mat3 = new BIMMaterial(3l,33l,"Mat3",3.3,33.33,0.3,0.33);

        final ArrayList<BIMMaterial> lstImp = new ArrayList<BIMMaterial>();

        //Write material in designed file

        try(baseExporter exp = new IED1Exporter())
        {
            exp.init(filePath);
            exp.ExportBIMMaterial(mat1);
            exp.ExportBIMMaterial(mat2);
            exp.ExportBIMMaterial(mat3);
        }

        //Create a handler
        IEDImportHandler<BIMMaterial> materialHandler = new IEDImportHandler<BIMMaterial>()
        {
            @Override
            public boolean processData(BIMMaterial bimData)
            {
                lstImp.add(bimData);
                return true;
            }
        };

        //Read with the handler
        try(baseImporter imp = new IED1Importer())
        {
            imp.init(filePath);
            imp.ProcessAll(materialHandler,null,null);
        }

        //Validate the data
        assertSame("Quatity of item imported",3,lstImp.size());

        BIMMaterial mat4 = lstImp.get(0);;
        BIMMaterial mat5 = lstImp.get(1);;
        BIMMaterial mat6 = lstImp.get(2);

        assertArrayEquals("Material 1", mat1.export(), mat4.export());
        assertArrayEquals("Material 2", mat2.export(), mat5.export());
        assertArrayEquals("Material 3", mat3.export(), mat6.export());
    }

    @Test
    public void testModuleInterfaceObject() throws IOException
    {
        BIMObject obj1 = new BIMObject(1l,"Object 1",11l,"Type1","Categ1","Code1",1.1,11.11);
        BIMObject obj2 = new BIMObject(2l,"Object 2",22l,"Type2","Categ2","Code2",2.2,22.22);
        BIMObject obj3 = new BIMObject(3l,"Object 3",33l,"Type3","Categ3","Code3",3.3,33.33);

        final ArrayList<BIMObject> lstImp = new ArrayList<BIMObject>();

        //Write Object in designed file
        try(baseExporter exp = new IED1Exporter())
        {
            exp.init(filePath);
            exp.ExportBIMObject(obj1);
            exp.ExportBIMObject(obj2);
            exp.ExportBIMObject(obj3);
        }

        //Create a handler
        IEDImportHandler<BIMObject> ObjectHandler = new IEDImportHandler<BIMObject>()
        {
            @Override
            public boolean processData(BIMObject bimData)
            {
                lstImp.add(bimData);
                return true;
            }
        };

        //Read with the handler
        try(baseImporter imp = new IED1Importer())
        {
            imp.init(filePath);
            imp.ProcessAll(null,ObjectHandler,null);
        }

        //Validate the data
        assertSame("Quatity of item imported",3,lstImp.size());

        BIMObject obj4 = lstImp.get(0);;
        BIMObject obj5 = lstImp.get(1);;
        BIMObject obj6 = lstImp.get(2);

        assertArrayEquals("Object 1", obj1.export(), obj4.export());
        assertArrayEquals("Object 2", obj2.export(), obj5.export());
        assertArrayEquals("Object 3", obj3.export(), obj6.export());
    }

    @Test
    public void testModuleInterfaceObjectType() throws IOException
    {
        BIMObjectType ObjType1 = new BIMObjectType(1l,"Object Type1","UCode1","UCodeDesc1","Family1");
        BIMObjectType ObjType2 = new BIMObjectType(2l,"Object Type2","UCode2","UCodeDesc2","Family2");
        BIMObjectType ObjType3 = new BIMObjectType(3l,"Object Type3","UCode3","UCodeDesc3","Family3");

        final ArrayList<BIMObjectType> lstImp = new ArrayList<BIMObjectType>();

        //Write material in designed file
        try(baseExporter exp = new IED1Exporter())
        {
            exp.init(filePath);
            exp.ExportBIMObjectType(ObjType1);
            exp.ExportBIMObjectType(ObjType2);
            exp.ExportBIMObjectType(ObjType3);
        }

        //Create a handler
        IEDImportHandler<BIMObjectType> objectTypeHandler = new IEDImportHandler<BIMObjectType>()
        {
            @Override
            public boolean processData(BIMObjectType bimData)
            {
                lstImp.add(bimData);
                return true;
            }
        };

        //Read with the handler
        try(baseImporter imp = new IED1Importer())
        {
            imp.init(filePath);
            imp.ProcessAll(null,null,objectTypeHandler);
        }

        //Validate the data
        assertSame("Quatity of item imported",3,lstImp.size());

        BIMObjectType objType4 = lstImp.get(0);;
        BIMObjectType objType5 = lstImp.get(1);;
        BIMObjectType objType6 = lstImp.get(2);

        assertArrayEquals("Object Type 1", ObjType1.export(), objType4.export());
        assertArrayEquals("Object Type 2", ObjType2.export(), objType5.export());
        assertArrayEquals("Object Type 3", ObjType3.export(), objType6.export());
    }

}
