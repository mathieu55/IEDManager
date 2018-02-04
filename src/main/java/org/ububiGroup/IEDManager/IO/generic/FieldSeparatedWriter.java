package org.ububiGroup.IEDManager.IO.generic;

import org.ububiGroup.IEDManager.IO.FieldSeparatedOption;
import org.ububiGroup.IEDManager.model.BIM.BIMObject;
import org.ububiGroup.IEDManager.model.generic.BIMData;

import java.io.*;
import java.util.List;

/**
 * Created by mathieu on 7/12/2016.
 */
public class FieldSeparatedWriter<T extends BIMData> extends baseWriter {

    private BufferedWriter outFile;
    private boolean firstLineWrited = false;
    FieldSeparatedOption option;

    private FieldSeparatedWriter(){}

    public FieldSeparatedWriter(BufferedWriter outFile, FieldSeparatedOption option)
    {
        this.outFile = outFile;
        this.option = option;
    }

    public FieldSeparatedWriter(String filePath,FieldSeparatedOption option) throws IOException
    {
        this(openFile(filePath),option);
    }

    protected static BufferedWriter openFile(String FilePath) throws IOException
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

    @Override
    public boolean writeObject(BIMData obj) throws IOException
    {
        if(!firstLineWrited && this.option.isHeaderColumnNeeded())
        {
            outFile.write(String.join(this.option.getFieldSeparator(),
                          this.option.escapeAllFields(obj.getHeaders())) +
                          this.option.getRecordSeparator());
        }
        firstLineWrited=true;

        outFile.write(String.join(this.option.getFieldSeparator(),
                      this.option.escapeAllFields(obj.export())) +
                      this.option.getRecordSeparator());
        return true;
    }

    public boolean writeObjects(List<BIMData> lstObj)
    {
        boolean returnVal=true;

        for (BIMData i: lstObj) {
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
