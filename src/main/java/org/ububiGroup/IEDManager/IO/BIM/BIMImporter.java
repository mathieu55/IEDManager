package org.ububiGroup.IEDManager.IO.BIM;

import org.ububiGroup.IEDManager.IO.ExportOption;
import org.ububiGroup.IEDManager.IO.generic.IEDFile;
import org.ububiGroup.IEDManager.IO.generic.IEDImportHandler;
import org.ububiGroup.IEDManager.IO.generic.IEDReader;
import org.ububiGroup.IEDManager.model.BIM.BIMMaterial;
import org.ububiGroup.IEDManager.model.BIM.BIMObject;
import org.ububiGroup.IEDManager.model.BIM.BIMObjectType;
import org.ububiGroup.IEDManager.model.generic.BIMData;
import lombok.Getter;

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

    protected readOrder[] readers;

    @Getter protected IEDFileType[] sequence;

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

        readers = new readOrder[]
                {
                        new readOrder(null,null),
                        new readOrder(bimMaterialR,bimMaterialHandler),
                        new readOrder(bimObjectR,bimObjectHandler),
                        new readOrder(bimObjectTypeR,bimObjectTypeHandler)
                };

        setSequence(null);
    }

    public void setSequence(IEDFileType[] sequence)
    {
        if(sequence==null)
            this.sequence = new IEDFileType[]
                            {
                                IEDFileType.IEDMaterial,
                                IEDFileType.IEDObject,
                                IEDFileType.IEDObjectType
                            };
        else
            this.sequence = sequence;
    }

    public boolean ProcessAll()
    {
        boolean success=true;

        for (IEDFileType fileType:sequence)
        {
            readOrder nextReader = readers[fileType.ordinal()];
            success &= processBIMData(nextReader.reader,nextReader.handler);
        }

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

    private class readOrder
    {
        public IEDImportHandler handler;
        public IEDReader reader;

        public readOrder(IEDReader reader,IEDImportHandler handler)
        {
            this.handler = handler;
            this.reader = reader;
        }
    }
}
