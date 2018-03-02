package org.ububiGroup.IEDManager.IO.generic;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.ububiGroup.IEDManager.IO.BIM.IEDFileType;
import org.ububiGroup.IEDManager.IO.FieldSeparatedOption;
import org.ububiGroup.IEDManager.Utils.ZipFileUtil;
import org.ububiGroup.IEDManager.model.BIM.BIMMaterial;
import org.ububiGroup.IEDManager.model.BIM.BIMObject;
import org.ububiGroup.IEDManager.model.BIM.BIMObjectType;
import org.ububiGroup.IEDManager.model.BIM.BIMProject;
import org.ububiGroup.IEDManager.model.generic.BIMData;

import java.io.*;
import java.util.HashMap;

public abstract class FieldSeperatedImporter extends baseImporter
{
    protected BIMProject project=null;

    protected File bimMaterialFile;
    protected File bimObjectFile;
    protected File bimObjectTypeFile;
    protected File bimProjectFile;

    protected CSVReader<BIMMaterial> bimMaterialR;
    protected CSVReader<BIMObject> bimObjectR;
    protected CSVReader<BIMObjectType> bimObjectTypeR;

    protected IEDImportHandler<BIMMaterial> bimMaterialHandler;
    protected IEDImportHandler<BIMObject> bimObjectHandler;
    protected IEDImportHandler<BIMObjectType> bimObjectTypeHandler;

    protected readOrder[] readers;
    private boolean closed=false;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private IEDFileType[] sequence=new IEDFileType[]{ IEDFileType.IEDMaterial,
            IEDFileType.IEDObject,
            IEDFileType.IEDObjectType };

    protected FieldSeparatedOption option;

    protected FieldSeperatedImporter(FieldSeparatedOption option)
    {
        this.option=option;
    }

    public void init(String filepath) throws IOException
    {
        super.init(filepath);
        HashMap<IEDFileType,File> exctractedFiles = ZipFileUtil.exctractIEDFiles(filepath);

        bimMaterialFile = exctractedFiles.getOrDefault(IEDFileType.IEDMaterial,null);
        bimObjectFile = exctractedFiles.getOrDefault(IEDFileType.IEDObject,null);
        bimObjectTypeFile = exctractedFiles.getOrDefault(IEDFileType.IEDObjectType,null);
        bimProjectFile = exctractedFiles.getOrDefault(IEDFileType.IEDProject,null);

        bimMaterialR=new CSVReader<>(getBufferedReader(bimMaterialFile), BIMMaterial.getFactory(),option);
        bimObjectR=new CSVReader<>(getBufferedReader(bimObjectFile), BIMObject.getFactory(),option);
        bimObjectTypeR=new CSVReader<>(getBufferedReader(bimObjectTypeFile),BIMObjectType.getFactory(),option);
    }

    public BIMProject ReadBimProject()
    {
        if(bimProjectFile==null)
            return null;

        if(this.project==null)
        {
            try (CSVReader<BIMProject> bimProjectR = new CSVReader<>(getBufferedReader(bimProjectFile), BIMProject.getFactory(),option))
            {
                this.project=bimProjectR.readObject();
            }
            catch (IOException e){}
        }

        return this.project;
    }

    public boolean ProcessAll(IEDImportHandler<BIMMaterial> bimMaterialHandler,
                              IEDImportHandler<BIMObject> bimObjectHandler,
                              IEDImportHandler<BIMObjectType> bimObjectTypeHandler)
    {

        this.bimMaterialHandler = bimMaterialHandler;
        this.bimObjectHandler = bimObjectHandler;
        this.bimObjectTypeHandler = bimObjectTypeHandler;

        readers = new readOrder[]
                {
                        new readOrder(null,null),
                        new readOrder(bimMaterialR,bimMaterialHandler),
                        new readOrder(bimObjectR,bimObjectHandler),
                        new readOrder(bimObjectTypeR,bimObjectTypeHandler)
                };

        boolean success=true;

        for (IEDFileType fileType:sequence)
        {
            readOrder nextReader = readers[fileType.ordinal()];
            success &= processBIMData(nextReader.reader,nextReader.handler);
        }

        return success;
    }

    protected boolean processBIMData(CSVReader reader, IEDImportHandler handler)
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
        if(closed)
            return;

        closed=true;

        if(bimMaterialR!=null)
            bimMaterialR.close();

        if(bimObjectR!=null)
            bimObjectR.close();

        if(bimObjectTypeR!=null)
            bimObjectTypeR.close();

        bimMaterialR=null;
        bimObjectR=null;
        bimObjectTypeR=null;

        if(bimProjectFile!=null)bimProjectFile.deleteOnExit();
        bimMaterialFile.deleteOnExit();
        bimObjectFile.deleteOnExit();
        bimObjectTypeFile.deleteOnExit();
    }

    protected class readOrder
    {
        public IEDImportHandler handler;
        public CSVReader reader;

        public readOrder(CSVReader reader, IEDImportHandler handler)
        {
            this.handler = handler;
            this.reader = reader;
        }
    }
}
