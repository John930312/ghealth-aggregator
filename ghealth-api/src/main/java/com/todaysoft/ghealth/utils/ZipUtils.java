package com.todaysoft.ghealth.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class ZipUtils
{
    public static List<File> unzip(File file, String dest, String prefix)
        throws IOException
    {
        if (null == file || !file.exists())
        {
            throw new IllegalStateException();
        }
        
        ZipEntry entry;
        String entryName;
        InputStream entryInputStream;
        ZipFile zip = new ZipFile(file);
        
        try
        {
            File destDirectory = new File(dest);
            
            if (!destDirectory.exists())
            {
                destDirectory.mkdirs();
            }
            
            Enumeration<? extends ZipEntry> entries = zip.entries();
            List<File> files = new ArrayList<File>();
            
            while (entries.hasMoreElements())
            {
                entry = entries.nextElement();
                entryName = entry.getName();
                
                if (StringUtils.isEmpty(prefix))
                {
                    file = new File(dest, entryName);
                }
                else
                {
                    file = new File(dest, prefix + "-" + entryName);
                }
                
                entryInputStream = zip.getInputStream(entry);
                FileUtils.copyInputStreamToFile(entryInputStream, file);
                files.add(file);
            }
            
            return files;
        }
        finally
        {
            zip.close();
        }
    }
}
