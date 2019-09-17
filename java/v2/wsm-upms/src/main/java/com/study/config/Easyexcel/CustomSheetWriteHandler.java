package com.study.config.Easyexcel;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.study.model.RegionModel;
import com.study.service.RegionService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.util.List;

/**
 * 自定义策略
 */
public class CustomSheetWriteHandler implements SheetWriteHandler {

    private RegionService regionService;

    private String mode;

    public CustomSheetWriteHandler(RegionService regionService, String mode) {
        this.regionService = regionService;
        this.mode = mode;
    }

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

        //查父级域
        List<RegionModel> models = regionService.getTopRegion();
        int length = models.size();
        String[] regionNames = new String[length];
        for (int i = 0; i < length; i++) {
            regionNames[i] = models.get(i).getName();
        }

        Workbook workbook = writeWorkbookHolder.getWorkbook();
        Sheet sheet = writeSheetHolder.getSheet();
        DataValidationHelper helper = sheet.getDataValidationHelper();

        //设置父级域数据验证
        CellRangeAddressList cellRangeAddressList1 = new CellRangeAddressList(1, 65535, 2, 2);
        DataValidationConstraint constraint1 = helper.createExplicitListConstraint(regionNames);
        DataValidation dataValidation1 = helper.createValidation(constraint1, cellRangeAddressList1);
        dataValidation1.setShowErrorBox(true);
        sheet.addValidationData(dataValidation1);

        //设置是否数据验证
        CellRangeAddressList cellRangeAddressList2 = new CellRangeAddressList(1, 65535, 3, 3);
        if (mode.equals("导出数据")) {
            cellRangeAddressList2 = new CellRangeAddressList(1, 65535, 7, 7);
        }

        DataValidationConstraint constraint2 = helper.createExplicitListConstraint(new String[]{"是", "否"});
        DataValidation dataValidation2 = helper.createValidation(constraint2, cellRangeAddressList2);
        dataValidation2.setShowErrorBox(true);
        sheet.addValidationData(dataValidation2);

        //设置文本格式
        CellStyle textStyle = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        textStyle.setDataFormat(format.getFormat("@"));
        sheet.setDefaultColumnStyle(0, textStyle);
        sheet.setDefaultColumnStyle(1, textStyle);
        sheet.setDefaultColumnStyle(2, textStyle);
        sheet.setDefaultColumnStyle(3, textStyle);
        sheet.setDefaultColumnStyle(4, textStyle);
        if (mode.equals("导出数据")) {
            sheet.setDefaultColumnStyle(5, textStyle);
            sheet.setDefaultColumnStyle(6, textStyle);
            sheet.setDefaultColumnStyle(7, textStyle);
            sheet.setDefaultColumnStyle(8, textStyle);
        }
    }
}
