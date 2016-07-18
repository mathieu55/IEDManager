package IED.IO.generic;

import IED.model.generic.BIMData;

import java.io.*;
import java.util.List;

/**
 * Created by mathieu on 7/12/2016.
 */
public abstract class IEDWriter<T extends BIMData> extends IEDFile {

    private BufferedWriter outFile;

    public abstract String serialize(T IEDObj);

    private IEDWriter(){}

    public  IEDWriter(BufferedWriter outFile)
    {
        this.outFile = outFile;
    }

    public IEDWriter(String filePath) throws IOException
    {
        this.outFile = openFile(filePath);
    }

    protected BufferedWriter openFile(String FilePath) throws IOException
    {

        /***********
         * Create and open the file
         */
        File IEDFile = new File(FilePath);

        if(!IEDFile.exists())
        {
            IEDFile.createNewFile();
        }

        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(IEDFile,false)));
    }

    protected String serializeItems(Object... param)
    {
        if(param == null || param.length<1)
            return "";

        StringBuilder strBuild = new StringBuilder();
        for(Object i: param)
        {
            strBuild.append(i.toString());
            strBuild.append(FieldSeparator);
        }

        return strBuild.substring(0,strBuild.length()-1);
    }

    public boolean writeObject(T obj) throws IOException
    {
        outFile.write(serialize(obj) + LineSeparator);
        return true;
    }

    public boolean writeObjects(List<T> lstObj)
    {
        boolean returnVal=true;

        for (T i: lstObj) {
            try {
                returnVal &= writeObject(i);
            }
            catch(IOException e)
            {
                returnVal = false;
            }
        }

        return returnVal;
    }

    public void close() throws IOException
    {
        outFile.close();
    }
}
