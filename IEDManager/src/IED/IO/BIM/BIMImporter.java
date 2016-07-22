package IED.IO.BIM;

import IED.IO.generic.IEDImportHandler;
import IED.IO.generic.IEDReader;
import IED.model.BIM.BIMMaterial;
import IED.model.BIM.BIMObject;
import IED.model.BIM.BIMObjectType;
import IED.model.generic.BIMData;

import java.io.*;

/**
 * Created by mathieu on 7/20/2016.
 */
public class BIMImporter implements Closeable
{
    protected BIMMaterialReader bimMaterialR;
    protected BIMObjectReader bimObjectR;
    protected BIMObjectTypeReader bimObjectTypeR;

    protected IEDImportHandler<BIMMaterial> bimMaterialHandler;
    protected IEDImportHandler<BIMObject> bimObjectHandler;
    protected IEDImportHandler<BIMObjectType> bimObjectTypeHandler;

    private BIMImporter(){}

    public BIMImporter(String bimMaterialFilePath,
                       String bimObjectFilePath,
                       String bimObjectTypeFilePath,
                       IEDImportHandler<BIMMaterial> bimMaterialHandler,
                       IEDImportHandler<BIMObject> bimObjectHandler,
                       IEDImportHandler<BIMObjectType> bimObjectTypeHandler) throws FileNotFoundException
    {
        this(getBufferedReader(bimMaterialFilePath),
             getBufferedReader(bimObjectFilePath),
             getBufferedReader(bimObjectTypeFilePath),
             bimMaterialHandler,
             bimObjectHandler,
             bimObjectTypeHandler);
    }

    public BIMImporter(File bimMaterialFile,
                       File bimObjectFile,
                       File bimObjectTypeFile,
                       IEDImportHandler<BIMMaterial> bimMaterialHandler,
                       IEDImportHandler<BIMObject> bimObjectHandler,
                       IEDImportHandler<BIMObjectType> bimObjectTypeHandler) throws FileNotFoundException
    {
        this(getBufferedReader(bimMaterialFile),
                getBufferedReader(bimObjectFile),
                getBufferedReader(bimObjectTypeFile),
                bimMaterialHandler,
                bimObjectHandler,
                bimObjectTypeHandler);
    }


    public BIMImporter(BufferedReader bimMaterialReader,
                       BufferedReader bimObjectReader,
                       BufferedReader bimObjectTypeReader,
                       IEDImportHandler<BIMMaterial> bimMaterialHandler,
                       IEDImportHandler<BIMObject> bimObjectHandler,
                       IEDImportHandler<BIMObjectType> bimObjectTypeHandler)
    {
        bimMaterialR=new BIMMaterialReader(bimMaterialReader);
        bimObjectR=new BIMObjectReader(bimObjectReader);
        bimObjectTypeR=new BIMObjectTypeReader(bimObjectTypeReader);

        this.bimMaterialHandler = bimMaterialHandler;
        this.bimObjectHandler = bimObjectHandler;
        this.bimObjectTypeHandler = bimObjectTypeHandler;
    }

    public boolean ProcessAll()
    {
        boolean success=true;

        success &= processBIMData(bimMaterialR,bimMaterialHandler);
        success &= processBIMData(bimObjectR,bimObjectHandler);
        success &= processBIMData(bimObjectTypeR,bimObjectTypeHandler);

        return success;
    }

    protected boolean processBIMData(IEDReader reader,IEDImportHandler handler)
    {
        boolean success=true;

        if(handler !=null)
        {
            try {
                BIMData data = reader.readObject();
                while(data!=null)
                {
                    success &= handler.processData(data);
                    data = reader.readObject();
                }
            }
            catch (IOException e)
            {
                return false;
            }
        }

        return success;
    }

    public void close() throws IOException
    {
        if(bimMaterialR!=null)bimMaterialR.close();
        if(bimObjectR!=null)bimObjectR.close();
        if(bimObjectTypeR!=null)bimObjectTypeR.close();

        bimMaterialR=null;
        bimObjectR=null;
        bimObjectTypeR=null;
    }

    private static BufferedReader getBufferedReader(String path) throws FileNotFoundException
    {
        return new BufferedReader(new FileReader(path));
    }

    private static BufferedReader getBufferedReader(File file) throws FileNotFoundException
    {
        return new BufferedReader(new FileReader(file));
    }
}
