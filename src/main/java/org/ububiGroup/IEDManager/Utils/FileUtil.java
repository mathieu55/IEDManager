package org.ububiGroup.IEDManager.Utils;

import java.io.File;
import java.io.IOException;

public class FileUtil
{
    public static File getFile(String path) throws IOException
    {
        if(path != null && !path.isEmpty())
            return new File(path);
        else
            return null;
    }

    public static String getExtension(String path)
    {
        String extension = "";

        int i = path.lastIndexOf('.');
        if (i >= 0 && i<path.length()-2) {
            extension = path.substring(i+1);
        }

        return extension.toLowerCase();
    }
}
