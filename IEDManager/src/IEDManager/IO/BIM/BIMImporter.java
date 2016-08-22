package IEDManager.IO.BIM;

import IEDManager.IO.ExportOption;
import IEDManager.IO.generic.IEDFile;
import IEDManager.IO.generic.IEDImportHandler;
import IEDManager.IO.generic.IEDReader;
import IEDManager.model.BIM.BIMMaterial;
import IEDManager.model.BIM.BIMObject;
import IEDManager.model.BIM.BIMObjectType;
import IEDManager.model.generic.BIMData;

import java.io.*;

/**
 * Created by mathieu on 7/20/2016.
 */
public class BIMImporter extends IEDFile implements Closeable
{
    protected IEDReader<BIMMaterial> bimMaterialR;
    protected IEDReader<BIMObject> bimObjectR;
    protected IEDReader<BIMObjectType> bimObjectTypeR;

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
        this(new File(bimMaterialFilePath),
             new File(bimObjectFilePath),
             new File(bimObjectTypeFilePath),
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
        bimMaterialR=new IEDReader<BIMMaterial>(getBufferedReader(bimMaterialFile), BIMMaterial.getFactory());
        bimObjectR=new IEDReader<BIMObject>(getBufferedReader(bimObjectFile), BIMObject.getFactory());
        bimObjectTypeR=new IEDReader<BIMObjectType>(getBufferedReader(bimObjectTypeFile),BIMObjectType.getFactory());

        this.bimMaterialHandler = bimMaterialHandler;
        this.bimObjectHandler = bimObjectHandler;
        this.bimObjectTypeHandler = bimObjectTypeHandler;

        setExportOptionFromExt(bimMaterialR,bimMaterialFile.getPath());
        setExportOptionFromExt(bimObjectR,bimObjectFile.getPath());
        setExportOptionFromExt(bimObjectTypeR,bimObjectTypeFile.getPath());
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

    public void setExportType(ExportOption exportOption)
    {
        super.setExportType(exportOption);
        this.bimMaterialR.setExportType(exportOption);
        this.bimObjectTypeR.setExportType(exportOption);
        this.bimObjectR.setExportType(exportOption);
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
