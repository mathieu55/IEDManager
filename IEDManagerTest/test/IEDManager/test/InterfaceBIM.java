package IEDManager.test; /**
 * Created by mathieu on 7/21/2016.
 */
import static org.junit.Assert.*;

import IEDManager.IO.BIM.BIMExporter;
import IEDManager.IO.BIM.BIMImporter;
import IEDManager.IO.generic.IEDImportHandler;
import IEDManager.model.BIM.BIMMaterial;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class InterfaceBIM
{

    private File materialFile;
    private File objectFile;
    private File objectTypeFile;

    @Before
    public void createTempFile() throws IOException
    {
        materialFile = File.createTempFile("tmpBM",".ied");
        objectFile = File.createTempFile("tmpBM",".ied");
        objectTypeFile = File.createTempFile("tmpBM",".ied");
    }

    @After
    public void deleteTempFile() throws IOException
    {
        materialFile.delete();
        objectFile.delete();
        objectTypeFile.delete();
    }

    @Test
    public void testModuleInterface()
    {
        BIMMaterial mat1 = new BIMMaterial(1L,"Mat1",1.1,"cm",11L);
        BIMMaterial mat2 = new BIMMaterial(2L,"Mat2",2.2,"cm2",22L);
        BIMMaterial mat3 = new BIMMaterial(3L,"Mat3",3.3,"cm3",33L);

	    final ArrayList<BIMMaterial> lstImp = new ArrayList<BIMMaterial>();

	    //Write material in designed file
        try(BIMExporter exp = new BIMExporter(materialFile, objectFile, objectTypeFile))
        {
            exp.ExportBIMMaterial(mat1);
            exp.ExportBIMMaterial(mat2);
            exp.ExportBIMMaterial(mat3);
        }
	    catch(IOException e)
	    {}

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
        try(BIMImporter imp = new BIMImporter(materialFile, objectFile, objectTypeFile,materialHandler,null,null))
        {
            imp.ProcessAll();
        }
        catch(IOException e)
        {}

	    //Validate the data
        assertSame("Quatity of item imported",3,lstImp.size());

	    BIMMaterial mat4 = lstImp.get(0);;
	    BIMMaterial mat5 = lstImp.get(1);;
	    BIMMaterial mat6 = lstImp.get(2);

        assertArrayEquals("Material 1",
		                  new Object[]{mat1.getId(),mat1.getName(),mat1.getDepth(),mat1.getUnit(),mat1.getTypeId()},
		                  new Object[]{mat4.getId(),mat4.getName(),mat4.getDepth(),mat4.getUnit(),mat4.getTypeId()});

	    assertArrayEquals("Material 2",
			    new Object[]{mat2.getId(),mat2.getName(),mat2.getDepth(),mat2.getUnit(),mat2.getTypeId()},
			    new Object[]{mat5.getId(),mat5.getName(),mat5.getDepth(),mat5.getUnit(),mat5.getTypeId()});

	    assertArrayEquals("Material 3",
			    new Object[]{mat3.getId(),mat3.getName(),mat3.getDepth(),mat3.getUnit(),mat3.getTypeId()},
			    new Object[]{mat6.getId(),mat6.getName(),mat6.getDepth(),mat6.getUnit(),mat6.getTypeId()});
    }

}
