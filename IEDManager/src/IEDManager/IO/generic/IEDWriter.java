package IEDManager.IO.generic;

import IEDManager.model.generic.BIMData;

import java.io.*;
import java.util.List;

/**
 * Created by mathieu on 7/12/2016.
 */
public class IEDWriter<T extends BIMData> extends IEDFile implements Closeable {

    private BufferedWriter outFile;
    private boolean firstLineWrited = false;

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

    public boolean writeObject(T obj) throws IOException
    {
        if(!firstLineWrited && this.exportOption.isHeaderColumnNeeded())
        {
            outFile.write(String.join(this.exportOption.getFieldSeparator(),
                          this.exportOption.escapeAllFields(obj.getHeaders())) +
                          this.exportOption.getRecordSeparator());
        }
        firstLineWrited=true;

        outFile.write(String.join(this.exportOption.getFieldSeparator(),
                      this.exportOption.escapeAllFields(obj.export())) +
                      this.exportOption.getRecordSeparator());
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
        if(outFile!=null)outFile.close();
        outFile=null;
    }
}
