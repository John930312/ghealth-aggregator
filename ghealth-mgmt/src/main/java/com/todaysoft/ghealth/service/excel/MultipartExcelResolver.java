package com.todaysoft.ghealth.service.excel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.todaysoft.ghealth.service.impl.ErrorCode;
import com.todaysoft.ghealth.support.ServiceException;

public abstract class MultipartExcelResolver<T>
{
    protected static Logger log = LoggerFactory.getLogger(MultipartExcelResolver.class);
    
    private MultipartFile file;
    
    public MultipartExcelResolver(MultipartFile file)
    {
        if (null == file)
        {
            throw new IllegalArgumentException();
        }
        
        this.file = file;
    }
    
    public List<T> resolve()
    {
        try
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
            
            Row row;
            T record;
            List<T> records = new ArrayList<T>();
            
            for (int i = 1; i <= sheet.getLastRowNum(); i++)
            {
                row = sheet.getRow(i);
                
                if (null == row || ignore(row))
                {
                    continue;
                }
                
                record = resolve(row);
                records.add(record);
            }
            
            return records;
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
            throw new ServiceException(ErrorCode.EXCEL_RESOLVE_UNDEFINED_ERROR);
        }
    }
    
    protected abstract T resolve(Row row);
    
    protected boolean ignore(Row row)
    {
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++)
        {
            if (!"".equals(getStringCellValue(row, i)))
            {
                return false;
            }
        }
        
        return true;
    }
    
    protected String getDoubleStringCellValue(Row row, int col)
    {
        String value = null;
        
        Cell cell = row.getCell(col);
        
        if (null != cell)
        {
            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
            {
                double val = cell.getNumericCellValue();
                DecimalFormat format = new DecimalFormat("#.##");
                value = format.format(val);
            }
            else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA)
            {
                try
                {
                    double val = cell.getNumericCellValue();
                    DecimalFormat format = new DecimalFormat("#.##");
                    value = format.format(val);
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
}
