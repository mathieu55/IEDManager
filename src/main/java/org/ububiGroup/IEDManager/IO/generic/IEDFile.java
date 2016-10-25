package org.ububiGroup.IEDManager.IO.generic;

import org.ububiGroup.IEDManager.IO.ExportOption;
import org.ububiGroup.IEDManager.IO.ExportOptionManager;

/**
 * Created by mathieu on 6/19/2016.
 */
public abstract class IEDFile
{

    protected ExportOption exportOption;
    protected IEDFile()
    {
        exportOption = ExportOptionManager.getExportOptionManager().getExportOptionFromName("IED");
    }

    public void setExportType(ExportOption exportOption)
    {
        this.exportOption=exportOption;
    }

    protected static void setExportOptionFromExt(IEDFile fileObj, String path)
    {
        ExportOption eo = ExportOptionManager.getExportOptionManager().
                          getFirstExportOptionFromExtension(getExtension(path),true);
        fileObj.setExportType(eo);
    }

    public static String getExtension(String path)
    {
        String extension = "";

        int i = path.lastIndexOf('.');
        if (i >= 0 && i<path.length()-2) {
            extension = path.substring(i+1);
        }

        return extension;
    }
}
