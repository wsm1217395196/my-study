package com.study.config.Easyexcel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.study.utils.DateUtil;

import java.util.Date;

/**
 * 读写单元格时调用
 */
public class CustomDateConverter implements Converter<Date> {


    @Override
    public Class supportJavaTypeKey() {
        return null;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return null;
    }

    /**
     * 这里是读的时候会调用 不用管
     */
    @Override
    public Date convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return null;
    }

    /**
     * 这里是写的时候会调用 不用管
     */
    @Override
    public CellData convertToExcelData(Date date, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if (date != null) {
            String formatDate = DateUtil.formatDate(date);
            return new CellData(formatDate);
        }
        return null;
    }
}
