package org.ububiGroup.IEDManager.IO.generic;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.ububiGroup.IEDManager.model.BIM.BIMMaterial;
import org.ububiGroup.IEDManager.model.BIM.BIMObject;
import org.ububiGroup.IEDManager.model.BIM.BIMObjectType;

import java.io.*;

public abstract class baseExporter implements Closeable
{
    @Getter
    private String filePath;

    public void init(String filename)  throws IOException
    {
        this.filePath=filename;
    }
    public abstract boolean ExportBIMMaterial(BIMMaterial bimMaterial);
    public abstract boolean ExportBIMObject(BIMObject bimObject);
    public abstract boolean ExportBIMObjectType(BIMObjectType bimObjectType);
    public abstract void close() throws IOException;

    public static BufferedWriter getBufferedWriter(File IEDFile) throws FileNotFoundException
    {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(IEDFile,false)));
    }
}
