package IEDManager.IO.BIM;

import IEDManager.IO.generic.IEDReader;
import IEDManager.model.BIM.BIMObjectType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;

/**
 * Created by mathieu on 7/12/2016.
 */
public class BIMObjectTypeReader extends IEDReader<BIMObjectType>
{
    public BIMObjectTypeReader(BufferedReader strReader)
    {
        super(strReader, BIMObjectType.getFactory());
    }

    public BIMObjectTypeReader(String filePath) throws FileNotFoundException
    {
        super(filePath, BIMObjectType.getFactory());
    }


    public boolean loadData(BIMObjectType IEDObj,String[] data)
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
