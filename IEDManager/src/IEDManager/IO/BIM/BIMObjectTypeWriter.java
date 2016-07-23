package IEDManager.IO.BIM;

import IEDManager.IO.generic.IEDWriter;
import IEDManager.model.BIM.BIMObjectType;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created by mathieu on 7/14/2016.
 */
public class BIMObjectTypeWriter extends IEDWriter<BIMObjectType>
{
    public BIMObjectTypeWriter(BufferedWriter outFile)
    {
        super(outFile);
    }

    public BIMObjectTypeWriter(String outFile) throws IOException
    {
        super(outFile);
    }

    public String serialize(BIMObjectType IEDObj)
    {
        if(IEDObj == null)
            return null;

        return serializeItems(IEDObj.getId()/*,IEDObj.getName(),IEDObj.getDepth(),IEDObj.getUnit(),IEDObj.getTypeId()*/);
    }
}
