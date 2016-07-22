package IED.IO.BIM;

import IED.IO.generic.IEDWriter;
import IED.model.BIM.BIMMaterial;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created by mathieu on 7/14/2016.
 */
public class BIMMaterialWriter extends IEDWriter<BIMMaterial>
{
    public BIMMaterialWriter(BufferedWriter outFile)
    {
        super(outFile);
    }

    public BIMMaterialWriter(String outFile) throws IOException
    {
        super(outFile);
    }

    public String serialize(BIMMaterial IEDObj)
    {
        if(IEDObj == null)
            return null;

        return serializeItems(IEDObj.getId(),IEDObj.getName(),IEDObj.getDepth(),IEDObj.getUnit(),IEDObj.getTypeId());
    }
}
