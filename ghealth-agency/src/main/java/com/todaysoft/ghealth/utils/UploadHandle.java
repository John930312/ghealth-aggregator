package com.todaysoft.ghealth.utils;

import com.todaysoft.ghealth.model.UploadData;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class UploadHandle
{
    private static int totalRows = 0;// 总行数 
    
    private static int totalCells = 0;// 总列数
    
    public Map<String, UploadData> readTxtByScanner(InputStream in)
    {
        Map<String, UploadData> map = new HashMap<>();
        Scanner scanner = null;
        try
        {
            scanner = new Scanner(in, "UTF-8");
            while (scanner.hasNext())
            {
                String data = scanner.nextLine();
                if (data.indexOf("rs") != -1)
                {
                    UploadData uploadData = new UploadData();
                    int flag = data.indexOf("|");
                    int length = data.length();
                    String risk = data.substring(flag + 1, length);
                    String geneType = data.substring(flag - 2, flag);
                    String locusName = data.substring(data.indexOf("rs"), flag - 2).trim();
                    uploadData.setLocusName(locusName);
                    uploadData.setGeneType(geneType);
                    uploadData.setRisk(risk);
                    map.put(locusName, uploadData);
                }
            }
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (scanner != null)
            {
                scanner.close();
            }
        }
        return map;
    }
    
    public File readExcel(MultipartFile excelData, Map<String, UploadData> map) throws IOException
    {
        Workbook workbook = chooseWorkbook(excelData.getOriginalFilename(), excelData.getInputStream());
        return setData(workbook, map);
    }
    
    private File setData(Workbook wb, Map<String, UploadData> map) throws IOException
    {
        /** 得到第一个shell */
        Sheet sheet = wb.getSheetAt(0);
        
        /** 得到Excel的行数 */
        totalRows = sheet.getPhysicalNumberOfRows();
        
        /** 得到Excel的列数 */
        if (totalRows >= 1 && sheet.getRow(0) != null)
        {
            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }
        
        /** 循环Excel的行 */
        for (int r = 1; r < totalRows; r++)
        {
            Row row = sheet.getRow(r);
            if (row == null)
            {
                continue;
            }
            
            List<String> rowLst = new ArrayList<String>();
            
            //默认这一列为位点名称
            Cell cell = row.getCell(1);
            if (!Objects.isNull(cell))
            {
                String locusName = getCellValue(cell);
                UploadData uploadData = map.get(locusName);
                if (!Objects.isNull(uploadData))
                {
                    Cell geneTypeCell = row.createCell(4);
                    Cell riskCell = row.createCell(5);
                    Optional.ofNullable(geneTypeCell).ifPresent(x -> x.setCellValue(uploadData.getGeneType()));
                    Optional.ofNullable(riskCell).ifPresent(x -> x.setCellValue(uploadData.getRisk()));
                }
            }
            
        }
        String fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + ".xlsx";
        File file = new File(this.getClass().getResource("/").getPath(), fileName);
        wb.write(new FileOutputStream(file));
        return file;
    }
    
    private String getCellValue(Cell cell)
    {
        String cellValue = "";
        
        if (null != cell)
        {
            // 以下是判断数据的类型 
            switch (cell.getCellType())
            {
                case HSSFCell.CELL_TYPE_NUMERIC: // 数字 
                    if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell))
                    {
                        Date theDate = cell.getDateCellValue();
                        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd");
                        cellValue = dff.format(theDate);
                    }
                    else
                    {
                        DecimalFormat df = new DecimalFormat("0");
                        cellValue = df.format(cell.getNumericCellValue());
                    }
                    break;
                case HSSFCell.CELL_TYPE_STRING: // 字符串 
                    cellValue = cell.getStringCellValue();
                    break;
                
                case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean 
                    cellValue = cell.getBooleanCellValue() + "";
                    break;
                
                case HSSFCell.CELL_TYPE_FORMULA: // 公式 
                    cellValue = cell.getCellFormula() + "";
                    break;
                
                case HSSFCell.CELL_TYPE_BLANK: // 空值 
                    cellValue = "";
                    break;
                
                case HSSFCell.CELL_TYPE_ERROR: // 故障 
                    cellValue = "非法字符";
                    break;
                
                default:
                    cellValue = "未知类型";
                    break;
            }
        }
        return cellValue;
    }
    
    private Workbook chooseWorkbook(String filePathOrName, InputStream in) throws IOException
    {
        /** 根据版本选择创建Workbook的方式 */
        Workbook wb = null;
        boolean isExcel2003 = isExcel2003(filePathOrName);
        
        if (isExcel2003)
        {
            wb = new HSSFWorkbook(in);
        }
        else
        {
            wb = new XSSFWorkbook(in);
        }
        
        return wb;
    }
    
    private boolean isExcel2003(String filePath)
    {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }
}
