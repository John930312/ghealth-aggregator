package com.todaysoft.ghealth.service.excel;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.web.multipart.MultipartFile;

public class MenuResolver extends MultipartExcelResolver<MenuResolveRecord>
{
    public MenuResolver(MultipartFile file)
    {
        super(file);
    }
    
    @Override
    protected MenuResolveRecord resolve(Row row)
    {
        MenuResolveRecord record = new MenuResolveRecord();
        record.setFirstLevelName(getStringCellValue(row, 0));
        record.setSecondLevelName(getStringCellValue(row, 1));
        record.setThirdLevelName(getStringCellValue(row, 2));
        record.setIcon(getStringCellValue(row, 3));
        record.setUri(getStringCellValue(row, 4));
        record.setEnabled(!"否".equals(getStringCellValue(row, 5)));
        return record;
    }
}
