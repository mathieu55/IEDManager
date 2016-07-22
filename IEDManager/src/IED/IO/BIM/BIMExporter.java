package IED.IO.BIM;

import IED.model.BIM.*;

import java.io.*;

/**
 * Created by mathieu on 7/19/2016.
 */
public class BIMExporter implements Closeable
{

    protected BIMMaterialWriter bimMaterialW;
    protected BIMObjectWriter bimObjectW;
    protected BIMObjectTypeWriter bimObjectTypeW;

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

        bimMaterialW = new BIMMaterialWriter(getBufferedWriter(bimMaterialF));
        bimObjectW = new BIMObjectWriter(getBufferedWriter(bimObjectF));
        bimObjectTypeW = new BIMObjectTypeWriter(getBufferedWriter(bimObjectTypeF));
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

    private static BufferedWriter getBufferedWriter(File IEDFile) throws FileNotFoundException
    {
       return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(IEDFile,false)));
    }

}
