package IEDManager.IO.BIM;

import IEDManager.IO.generic.IEDReader;
import IEDManager.model.BIM.BIMMaterial;

import java.io.BufferedReader;
import java.io.FileNotFoundException;

/**
 * Created by mathieu on 7/12/2016.
 */
public class BIMMaterialReader extends IEDReader<BIMMaterial>
{
    public BIMMaterialReader(BufferedReader strReader)
    {
        super(strReader, BIMMaterial.getFactory());
    }

    public BIMMaterialReader(String filePath) throws FileNotFoundException
    {
        super(filePath, BIMMaterial.getFactory());
    }


    public boolean loadData(BIMMaterial IEDObj,String[] data)
    {
        if(IEDObj != null && data != null && data.length >= 5)
        {
            try
            {
                IEDObj.setId(Long.parseLong(data[0],10));
                IEDObj.setName(data[1]);
                IEDObj.setDepth(Double.parseDouble(data[2]));
                IEDObj.setUnit(data[3]);
                IEDObj.setTypeId(Long.parseLong(data[4],10));
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
