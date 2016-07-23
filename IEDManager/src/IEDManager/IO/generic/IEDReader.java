package IEDManager.IO.generic;

import IEDManager.model.generic.BIMData;
import IEDManager.model.generic.BIMFactory;

import java.io.*;
import java.util.HashMap;

/**
 * Created by mathieu on 7/12/2016.
 */
public abstract class IEDReader<T extends BIMData> extends IEDFile implements Closeable{

    private BufferedReader inFile;

    protected BIMFactory<T> factory;

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

    protected String readLine() throws IOException
    {
        StringBuilder strBuilder = new StringBuilder();

        int charReaded = inFile.read();

        while(charReaded >= 0 && charReaded != lineSeparatorAnsiCode)
        {
            strBuilder.append((char)charReaded);

            charReaded = inFile.read();
        }

        return strBuilder.toString();
    }

    protected String[] readLineField() throws IOException
    {
        String line = readLine();
        if(line == null)
            return null;
        else
        {
            return line.split(fieldSeparator);
        }
    }

    public T readObject() throws IOException
    {
        String[] data = readLineField();
        T IEDObj = factory.create();
        if(loadData(IEDObj, data))
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

    public abstract boolean loadData(T IEDObj, String[] data);

}
