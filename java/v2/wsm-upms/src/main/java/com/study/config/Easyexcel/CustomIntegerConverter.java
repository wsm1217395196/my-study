package com.study.config.Easyexcel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.study.MyConstant;

/**
 * 读写单元格时调用
 */
public class CustomIntegerConverter implements Converter<Integer> {

    @Override
    public Class supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    /**
     * 这里是读的时候会调用 不用管
     */
    @Override
    public Integer convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        int isEnabled = cellData.getStringValue().equals("否") ? MyConstant.Zero : MyConstant.One;
        return isEnabled;
    }

    /**
     * 这里是写的时候会调用 不用管
     */
    @Override
    public CellData convertToExcelData(Integer integer, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String isEnabled = integer.equals(MyConstant.One) ? "是" : "否";
        return new CellData(isEnabled);
    }
}
