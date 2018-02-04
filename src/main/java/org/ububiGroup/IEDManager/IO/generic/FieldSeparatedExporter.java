package org.ububiGroup.IEDManager.IO.generic;

import org.ububiGroup.IEDManager.IO.BIM.IEDFileType;
import org.ububiGroup.IEDManager.IO.FieldSeparatedOption;
import org.ububiGroup.IEDManager.Utils.ZipFileUtil;
import org.ububiGroup.IEDManager.model.BIM.BIMMaterial;
import org.ububiGroup.IEDManager.model.BIM.BIMObject;
import org.ububiGroup.IEDManager.model.BIM.BIMObjectType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public class FieldSeparatedExporter extends baseExporter
{
    protected FieldSeparatedWriter<BIMMaterial> bimMaterialW;
    protected FieldSeparatedWriter<BIMObject> bimObjectW;
    protected FieldSeparatedWriter<BIMObjectType> bimObjectTypeW;

    protected File bimMaterialF;
    protected File bimObjectF;
    protected File bimObjectTypeF;

    protected String bimMaterialFilePath;
    protected String bimObjectFilePath;
    protected String bimObjectTypeFilePath;

    protected FieldSeparatedOption option;

    private boolean closed=false;

    protected FieldSeparatedExporter(FieldSeparatedOption option)
    {
        this.option=option;
    }

    @Override
    public void init(String fileName) throws IOException
    {
        super.init(fileName);
        this.option=option;

        bimMaterialF=File.createTempFile("tmpBM", ".tmp");
        bimObjectF=File.createTempFile("tmpBO", ".tmp");
        bimObjectTypeF=File.createTempFile("tmpBOT", ".tmp");

        bimMaterialFilePath = bimMaterialF.getPath();
        bimObjectFilePath = bimObjectF.getPath();
        bimObjectTypeFilePath = bimObjectTypeF.getPath();

        bimMaterialW = new FieldSeparatedWriter(getBufferedWriter(bimMaterialF),this.option);
        bimObjectW = new FieldSeparatedWriter(getBufferedWriter(bimObjectF),this.option);
        bimObjectTypeW = new FieldSeparatedWriter(getBufferedWriter(bimObjectTypeF),this.option);
    }

    public boolean ExportBIMMaterial(BIMMaterial bimMaterial)
    {
        try {
            return bimMaterialW.writeObject(bimMaterial);
        }
        catch(IOException e) {
            return false;
        }
    }

    public boolean ExportBIMObject(BIMObject bimObject)
    {
        try {
            return bimObjectW.writeObject(bimObject);
        }
        catch(IOException e) {
            return false;
        }
    }

    public boolean ExportBIMObjectType(BIMObjectType bimObjectType)
    {
        try {
            return bimObjectTypeW.writeObject(bimObjectType);
        }
        catch(IOException e) {
            return false;
        }
    }

    public boolean compress() throws IOException
    {
        HashMap<String,String> fileMap=new HashMap<>();

        fileMap.put(IEDFileType.IEDMaterial.toString()+"."+option.getExtension(),this.bimMaterialFilePath);
        fileMap.put(IEDFileType.IEDObject.toString()+"."+option.getExtension(),this.bimObjectFilePath);
        fileMap.put(IEDFileType.IEDObjectType.toString()+"."+option.getExtension(),this.bimObjectTypeFilePath);

        return ZipFileUtil.create(fileMap,getFilePath());
    }

    public void close() throws IOException
    {
        if(closed)
            return;

        closed=true;
        if(bimMaterialW!=null)bimMaterialW.close();
        if(bimObjectW!=null)bimObjectW.close();
        if(bimObjectTypeW!=null)bimObjectTypeW.close();

        bimMaterialW=null;
        bimObjectW=null;
        bimObjectTypeW=null;
        compress();

        bimMaterialF.deleteOnExit();
        bimObjectF.deleteOnExit();
        bimObjectTypeF.deleteOnExit();
    }

}
