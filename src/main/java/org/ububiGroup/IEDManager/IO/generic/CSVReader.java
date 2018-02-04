package org.ububiGroup.IEDManager.IO.generic;

import org.ububiGroup.IEDManager.IO.FieldSeparatedOption;
import org.ububiGroup.IEDManager.model.generic.BIMData;

import java.io.*;
import java.util.HashMap;

/**
 * Created by mathieu on 7/12/2016.
 */
public class CSVReader<T extends BIMData> implements Closeable{

    private BufferedReader inFile;

    protected BIMDataFactory<T> factory;
    protected boolean firstLineReaded = false;
    FieldSeparatedOption option;
    byte recordSeparatorAnsiCode;

    private CSVReader(){}


    public CSVReader(BufferedReader strReader, BIMDataFactory<T> factory, FieldSeparatedOption option) throws NullPointerException
    {
        if(strReader==null)
            throw new NullPointerException("The IED stream cannot be null.");

        if(factory == null)
            throw new NullPointerException("The factory is required.");

        if(option ==null)
            throw new NullPointerException("The option object is required.");

        this.option=option;
        recordSeparatorAnsiCode = ((byte)(option.getRecordSeparator().charAt(0)));

        this.factory = factory;
        inFile = strReader;
    }

    public CSVReader(String filePath, BIMDataFactory<T> factory, FieldSeparatedOption option) throws NullPointerException, FileNotFoundException
    {
        this(new BufferedReader(new InputStreamReader(new FileInputStream(filePath))),factory,option);
    }

    protected String readRecord() throws IOException
    {
        StringBuilder strBuilder = new StringBuilder();


        int charReaded = inFile.read();

        while(charReaded >= 0 && charReaded != recordSeparatorAnsiCode)
        {
            strBuilder.append((char)charReaded);
            charReaded = inFile.read();
        }

        //Skip header if exist
        if(!firstLineReaded && this.option.isHeaderColumnNeeded())
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
            return rec.split(this.option.getFieldSeparator(),-1);
        }
    }

    public T readObject() throws IOException
    {
        String[] data = this.option.unescapeAllFields(readLineField());
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
