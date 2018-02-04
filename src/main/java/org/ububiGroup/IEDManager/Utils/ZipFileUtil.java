package org.ububiGroup.IEDManager.Utils;

import org.ububiGroup.IEDManager.IO.BIM.IEDFileType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public final class ZipFileUtil
{
    public static boolean create(HashMap<String,String> files, String zipFilePath) throws IOException
    {
        try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(FileUtil.getFile(zipFilePath),false)))
        {
            for (Map.Entry<String, String> entry:files.entrySet())
            {
                zout.putNextEntry(new ZipEntry(entry.getKey()));
                byte[] bytes = new byte[1024];
                int length;

                FileInputStream fis = new FileInputStream(new File(entry.getValue()));
                while((length = fis.read(bytes)) >= 0)
                {
                    zout.write(bytes, 0, length);
                }
                zout.closeEntry();
            }
            zout.close();
        }

        return true;
    }

    public static ArrayList<String> listFile(String zipFilePath) throws IOException
    {
        ArrayList<String> filesList = new ArrayList<>();
        try(ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFilePath)))
        {
            ZipEntry entry = zin.getNextEntry();
            while(entry!=null)
            {
                filesList.add(entry.getName());
                entry = zin.getNextEntry();
            }
        }

        return filesList;
    }

    public static HashMap<String,File> extractFiles(String filePath) throws IOException
    {
        HashMap<String,File> extractedFiles = new HashMap<>();

        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(filePath));
        ZipEntry zipEntry = zis.getNextEntry();
        while(zipEntry != null){
            String fileName = zipEntry.getName();

            //TODO Change it
            File newFile = File.createTempFile("tmp", fileName);
            extractedFiles.put(fileName,newFile);

            FileOutputStream fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();

        return extractedFiles;
    }

    public static HashMap<IEDFileType,File> exctractIEDFiles(String filePath) throws IOException
    {
        HashMap<IEDFileType,File> extractedIEDFiles = new HashMap<>();

        HashMap<String,File> extractedFiles = extractFiles(filePath);

        for(Map.Entry<String,File> entry:extractedFiles.entrySet())
        {
            if(entry.getKey().startsWith(IEDFileType.IEDMaterial.toString()+"."))
            {
                extractedIEDFiles.put(IEDFileType.IEDMaterial,entry.getValue());
            }
            else if(entry.getKey().startsWith(IEDFileType.IEDObjectType.toString()+"."))
            {
                extractedIEDFiles.put(IEDFileType.IEDObjectType,entry.getValue());
            }
            else if(entry.getKey().startsWith(IEDFileType.IEDObject.toString()+"."))
            {
                extractedIEDFiles.put(IEDFileType.IEDObject,entry.getValue());
            }
            else
                entry.getValue().deleteOnExit();
        }

        return extractedIEDFiles;
    }

    private ZipFileUtil(){};
}
