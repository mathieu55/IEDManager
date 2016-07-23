package IEDManager.IO.BIM;

import IEDManager.IO.generic.IEDWriter;
import IEDManager.model.BIM.BIMObject;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created by mathieu on 7/14/2016.
 */
public class BIMObjectWriter extends IEDWriter<BIMObject>
{
    public BIMObjectWriter(BufferedWriter outFile)
    {
        super(outFile);
    }

    public BIMObjectWriter(String outFile) throws IOException
    {
        super(outFile);
    }

    public String serialize(BIMObject IEDObj)
    {
        if(IEDObj == null)
            return null;

        return serializeItems(IEDObj.getId()/*,IEDObj.getName(),IEDObj.getDepth(),IEDObj.getUnit(),IEDObj.getTypeId()*/);
    }
}
