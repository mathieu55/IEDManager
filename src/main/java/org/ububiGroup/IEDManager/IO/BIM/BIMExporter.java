package org.ububiGroup.IEDManager.IO.BIM;

import org.ububiGroup.IEDManager.IO.ExportOption;
import org.ububiGroup.IEDManager.IO.generic.IEDFile;
import org.ububiGroup.IEDManager.IO.generic.IEDWriter;
import org.ububiGroup.IEDManager.model.BIM.BIMMaterial;
import org.ububiGroup.IEDManager.model.BIM.BIMObject;
import org.ububiGroup.IEDManager.model.BIM.BIMObjectType;

import java.io.*;


/**
 * Created by mathieu on 7/19/2016.
 */
public class BIMExporter extends IEDFile implements Closeable
{
    protected IEDWriter<BIMMaterial> bimMaterialW;
    protected IEDWriter<BIMObject>  bimObjectW;
    protected IEDWriter<BIMObjectType>  bimObjectTypeW;

    protected File bimMaterialF;
    protected File bimObjectF;
    protected File bimObjectTypeF;

    public BIMExporter() throws IOException
    {
        this(File.createTempFile("tmpBM", ".ied"), File.createTempFile("tmpBO", ".ied"), File.createTempFile("tmpBOT", ".ied"));
    }

    public BIMExporter(File bimMaterialFile,File bimObjectFile, File bimObjectTypeFile) throws IOException
    {
        bimMaterialF = bimMaterialFile;
        bimObjectF = bimObjectFile;
        bimObjectTypeF = bimObjectTypeFile;

        bimMaterialW = new IEDWriter<BIMMaterial>(getBufferedWriter(bimMaterialF));
        bimObjectW = new IEDWriter<BIMObject>(getBufferedWriter(bimObjectF));
        bimObjectTypeW = new IEDWriter<BIMObjectType>(getBufferedWriter(bimObjectTypeF));

        setExportOptionFromExt(bimMaterialW,bimMaterialFile.getPath());
        setExportOptionFromExt(bimObjectW,bimObjectF.getPath());
        setExportOptionFromExt(bimObjectTypeW,bimObjectTypeF.getPath());
    }

    public BIMExporter(String bimMaterialFilePath,String bimObjectFilePath, String bimObjectTypeFilePath) throws IOException
    {
        this(new File(bimMaterialFilePath),new File(bimObjectFilePath),new File(bimObjectTypeFilePath));
    }

    protected static File getFile(String path) throws IOException
    {
        if(path != null && path.isEmpty())
            return new File(path);
        else
            return null;
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

    public boolean sendFiles(boolean compress)
    {
        //TODO sendFile();
        return true;
    }

    public String getBimMaterialFilePath()
    {
        return bimMaterialF.getPath();
    }

    public String getBimObjectFilePath()
    {
        return bimObjectF.getPath();
    }

    public String getBimObjectTypeFilePath()
    {
        return bimObjectTypeF.getPath();
    }

    public void close() throws IOException
    {
        if(bimMaterialW!=null)bimMaterialW.close();
        if(bimObjectW!=null)bimObjectW.close();
        if(bimObjectTypeW!=null)bimObjectTypeW.close();

        bimMaterialW=null;
        bimObjectW=null;
        bimObjectTypeW=null;
    }

    public void setExportType(ExportOption exportOption)
    {
        super.setExportType(exportOption);
        this.bimMaterialW.setExportType(exportOption);
        this.bimObjectTypeW.setExportType(exportOption);
        this.bimObjectW.setExportType(exportOption);
    }

    private static BufferedWriter getBufferedWriter(File IEDFile) throws FileNotFoundException
    {
       return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(IEDFile,false)));
    }

}
