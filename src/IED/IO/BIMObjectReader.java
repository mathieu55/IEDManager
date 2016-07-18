package IED.IO;

import IED.IO.generic.IEDReader;
import IED.model.BIMObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;

/**
 * Created by mathieu on 7/12/2016.
 */
public class BIMObjectReader extends IEDReader<BIMObject>
{
    public BIMObjectReader(BufferedReader strReader)
    {
        super(strReader, BIMObject.getFactory());
    }

    public BIMObjectReader(String filePath) throws FileNotFoundException
    {
        super(filePath, BIMObject.getFactory());
    }


    public boolean loadData(BIMObject IEDObj,String[] data)
    {
        if(IEDObj != null && data != null && data.length >= 5)
        {
            try
            {
                /*IEDObj.setId(Long.parseLong(data[0],10));
                IEDObj.setName(data[1]);
                IEDObj.setDepth(Double.parseDouble(data[2]));
                IEDObj.setUnit(data[3]);
                IEDObj.setTypeId(Long.parseLong(data[4],10));*/
                return true;
            }
            catch(Exception e)
            {
                return false;
            }

        }

        return false;
    }

}
