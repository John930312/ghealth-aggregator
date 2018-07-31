package com.todaysoft.ghealth.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class DownloadFilesUtil
{
    
    public static File zipFiles(File zipfile, List<File> srcfile)
    {
        byte[] buf = new byte[1024];
        try
        {
            // Create the ZIP file
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
            // Compress the files
            for (int i = 0; i < srcfile.size(); i++)
            {
                File file = srcfile.get(i);
                FileInputStream in = new FileInputStream(file);
                // Add ZIP entry to output stream.
                out.putNextEntry(new ZipEntry(file.getName()));
                // Transfer bytes from the file to the ZIP file
                int len;
                while ((len = in.read(buf)) > 0)
                {
                    out.write(buf, 0, len);
                }
                // Complete the entry
                out.closeEntry();
                in.close();
                file.delete();
            }
            // Complete the ZIP file
            out.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return zipfile;
    }
    
    /**
     * 解压Zip文件
     * 
     * @param path
     *            文件目录
     */
    public static void unZip(String path)
    {
        
        int buffer = 2048;
        int count = -1;
        String savepath = "";
        
        File file = null;
        InputStream is = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        
        savepath = path.substring(0, path.lastIndexOf(".")) + File.separator; // 保存解压文件目录
        new File(savepath).mkdir(); // 创建保存目录
        ZipFile zipFile = null;
        try
        {
            zipFile = new ZipFile(path); // 解决中文乱码问题
            Enumeration<?> entries = zipFile.entries();
            
            while (entries.hasMoreElements())
            {
                byte buf[] = new byte[buffer];
                
                ZipEntry entry = (ZipEntry)entries.nextElement();
                
                String filename = entry.getName();
                boolean ismkdir = false;
                if (filename.lastIndexOf("/") != -1)
                { // 检查此文件是否带有文件夹
                    ismkdir = true;
                }
                filename = savepath + filename;
                
                if (entry.isDirectory())
                { // 如果是文件夹先创建
                    file = new File(filename);
                    file.mkdirs();
                    continue;
                }
                file = new File(filename);
                if (!file.exists())
                { // 如果是目录先创建
                    if (ismkdir)
                    {
                        new File(filename.substring(0, filename.lastIndexOf("/"))).mkdirs(); // 目录先创建
                    }
                }
                file.createNewFile(); // 创建文件
                
                is = zipFile.getInputStream(entry);
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos, buffer);
                
                while ((count = is.read(buf)) > -1)
                {
                    bos.write(buf, 0, count);
                }
                bos.flush();
                bos.close();
                fos.close();
                
                is.close();
            }
            
            zipFile.close();
            
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        finally
        {
            try
            {
                if (bos != null)
                {
                    bos.close();
                }
                if (fos != null)
                {
                    fos.close();
                }
                if (is != null)
                {
                    is.close();
                }
                if (zipFile != null)
                {
                    zipFile.close();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public static void download(HttpServletResponse response, String path)
    {
        OutputStream os = null;
        InputStream is = null;
        try
        {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 以流的形式下载文件。
            is = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            os = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            os.write(buffer);
            os.flush();
            
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                if (is != null)
                {
                    is.close();
                }
                if (os != null)
                {
                    os.close();
                }
                
            }
            catch (IOException e)
            {
                
                e.printStackTrace();
            }
        }
    }
}
