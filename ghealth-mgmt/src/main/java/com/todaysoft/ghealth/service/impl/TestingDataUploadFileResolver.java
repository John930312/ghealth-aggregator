package com.todaysoft.ghealth.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.todaysoft.ghealth.support.ServiceException;

public class TestingDataUploadFileResolver
{
    private MultipartFile file;
    
    private List<String> orderCodes = new ArrayList<String>();
    
    private List<String> locusNames = new ArrayList<String>();
    
    private Map<String, String> genetypes = new HashMap<String, String>();
    
    public TestingDataUploadFileResolver(MultipartFile file)
    {
        if (null == file)
        {
            throw new IllegalArgumentException();
        }
        
        this.file = file;
    }
    
    public void resolve()
        throws IOException
    {
        Sheet sheet = getSheet();
        int lastRowNum = sheet.getLastRowNum();
        
        if (0 == lastRowNum)
        {
            throw new ServiceException(ErrorCode.EXCEL_RESOLVE_SHEET_EMPTY);
        }
        
        Row rowAsOrderCodes = sheet.getRow(0);
        
        if (null == rowAsOrderCodes)
        {
            throw new IllegalStateException();
        }
        
        for (int i = 1; i < rowAsOrderCodes.getLastCellNum(); i++)
        {
            orderCodes.add(getStringCellValue(rowAsOrderCodes, i));
        }
        
        // 移除空标题列
        List<String> ocs = new ArrayList<String>(orderCodes);
        
        for (int i = ocs.size() - 1; i > 0; i--)
        {
            if (StringUtils.isEmpty(ocs.get(i)))
            {
                orderCodes.remove(i);
            }
        }
        
        ocs.clear();
        
        Row row;
        
        for (int i = 1; i <= lastRowNum; i++)
        {
            row = sheet.getRow(i);
            
            if (ignore(row))
            {
                continue;
            }
            
            locusNames.add(getStringCellValue(row, 0));
            
            for (int j = 1; j < row.getLastCellNum() && j <= orderCodes.size(); j++)
            {
                genetypes.put((i - 1) + "-" + (j - 1), getStringCellValue(row, j));
            }
        }
    }
    
    private Sheet getSheet()
        throws IOException
    {
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf(".") + 1);
        
        Workbook workbook;
        
        if ("xlsx".equalsIgnoreCase(suffix))
        {
            workbook = new XSSFWorkbook(file.getInputStream());
        }
        else if ("xls".equalsIgnoreCase(suffix))
        {
            workbook = new HSSFWorkbook(file.getInputStream());
        }
        else
        {
            throw new ServiceException(ErrorCode.EXCEL_RESOLVE_SUFFIX_UNSUPPORTED);
        }
        
        Sheet sheet = workbook.getSheetAt(0);
        
        if (null == sheet)
        {
            throw new ServiceException(ErrorCode.EXCEL_RESOLVE_SHEET_EMPTY);
        }
        
        return sheet;
    }
    
    protected boolean ignore(Row row)
    {
        if (null == row)
        {
            return true;
        }
        
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++)
        {
            if (!"".equals(getStringCellValue(row, i)))
            {
                return false;
            }
        }
        
        return true;
    }
    
    protected String getStringCellValue(Row row, int col)
    {
        String value = null;
        
        Cell cell = row.getCell(col);
        
        if (null != cell)
        {
            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
            {
                value = String.valueOf((long)cell.getNumericCellValue()).trim();
            }
            else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA)
            {
                try
                {
                    return String.valueOf((long)cell.getNumericCellValue()).trim();
                }
                catch (IllegalStateException e)
                {
                    try
                    {
                        return String.valueOf(cell.getRichStringCellValue());
                    }
                    catch (Exception ex)
                    {
                        return "";
                    }
                }
            }
            else
            {
                value = cell.getStringCellValue().trim();
            }
        }
        
        if (StringUtils.isEmpty(value))
        {
            return "";
        }
        
        return format(value);
    }
    
    private String format(String s)
    {
        return s.replaceAll(" ", "").replaceAll("　", "").replaceAll("，", ",");
    }
    
    public List<String> getOrderCodes()
    {
        return orderCodes;
    }
    
    public List<String> getLocusNames()
    {
        return locusNames;
    }
    
    public Map<String, String> getGenetypes()
    {
        return genetypes;
    }
}
