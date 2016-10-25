package org.ububiGroup.IEDManager.IO.generic;

import org.ububiGroup.IEDManager.model.generic.BIMData;

import java.io.*;
import java.util.HashMap;

/**
 * Created by mathieu on 7/12/2016.
 */
public class IEDReader<T extends BIMData> extends IEDFile implements Closeable{

    private BufferedReader inFile;

    protected BIMFactory<T> factory;
    protected boolean firstLineReaded = false;

    private IEDReader(){}


    public IEDReader(BufferedReader strReader, BIMFactory<T> factory) throws NullPointerException
    {
        if(strReader==null)
            throw new NullPointerException("The IED stream cannot be null.");

        if(factory == null)
            throw new NullPointerException("The factory is required.");


        this.factory = factory;
        inFile = strReader;
    }

    public IEDReader(String filePath, BIMFactory<T> factory) throws NullPointerException, FileNotFoundException
    {
        if(factory == null)
            throw new NullPointerException("The factory is required.");

        this.factory = factory;
        inFile = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
    }

    protected String readRecord() throws IOException
    {
        StringBuilder strBuilder = new StringBuilder();


        int charReaded = inFile.read();

        while(charReaded >= 0 && charReaded != this.exportOption.getRecordSeparatorAnsiCode())
        {
            strBuilder.append((char)charReaded);
            charReaded = inFile.read();
        }

        //Skip header if exist
        if(!firstLineReaded && this.exportOption.isHeaderColumnNeeded())
        {
            firstLineReaded=true;
            return readRecord();
        }

        firstLineReaded=true;

        return strBuilder.toString();
    }

    protected String[] readLineField() throws IOException
    {
        String rec = readRecord();
        if(rec == null)
            return null;
        else
        {
            return rec.split(this.exportOption.getFieldSeparator());
        }
    }

    public T readObject() throws IOException
    {
        String[] data = this.exportOption.unescapeAllFields(readLineField());
        T IEDObj = factory.create();
        if(IEDObj.load(data))
            return IEDObj;
        else
            return null;
    }

    public HashMap<Long, T> readAllObjects() throws IOException
    {
        HashMap<Long, T> objList = new HashMap<Long, T>();

        T tmpObj = readObject();
        while(tmpObj != null)
        {
            objList.put(tmpObj.getId(),tmpObj);
            tmpObj = readObject();
        }

        return objList;
    }

    public void reset() throws IOException
    {
        inFile.reset();
    }

    public void close() throws IOException
    {
        if(inFile!=null)inFile.close();
        inFile=null;
    }

}
