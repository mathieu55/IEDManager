package org.ububiGroup.IEDManager.IO.generic;

import lombok.AccessLevel;
import lombok.Setter;
import org.ububiGroup.IEDManager.IO.BIM.IEDFileType;
import org.ububiGroup.IEDManager.IO.FieldSeparatedOption;
import org.ububiGroup.IEDManager.IO.generic.IEDImportHandler;
import org.ububiGroup.IEDManager.IO.generic.CSVReader;
import org.ububiGroup.IEDManager.Utils.ZipFileUtil;
import org.ububiGroup.IEDManager.model.BIM.BIMMaterial;
import org.ububiGroup.IEDManager.model.BIM.BIMObject;
import org.ububiGroup.IEDManager.model.BIM.BIMObjectType;
import org.ububiGroup.IEDManager.model.BIM.BIMProject;
import org.ububiGroup.IEDManager.model.generic.BIMData;
import lombok.Getter;

import java.io.*;
import java.util.HashMap;


public abstract class baseImporter implements Closeable
{
    @Getter
    private String filepath;

    public void init(String filepath) throws IOException
    {
        this.filepath=filepath;
    }

    public abstract BIMProject ReadBimProject();

    public abstract boolean ProcessAll(IEDImportHandler<BIMMaterial> bimMaterialHandler,
                              IEDImportHandler<BIMObject> bimObjectHandler,
                              IEDImportHandler<BIMObjectType> bimObjectTypeHandler);


    protected static BufferedReader getBufferedReader(String path) throws FileNotFoundException
    {
        return new BufferedReader(new FileReader(path));
    }

    protected static BufferedReader getBufferedReader(File file) throws FileNotFoundException
    {
        return new BufferedReader(new FileReader(file));
    }
}
