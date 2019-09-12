package com.study.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.study.MyConstant;
import com.study.model.RegionModel;
import com.study.service.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * easyexcel控制器
 */
@Api(description = "easyexcel控制器")
@RestController
@RequestMapping("/easyexcel")
public class EasyexcelController {

    @Autowired
    private RegionService regionService;

    @ApiOperation("生成模板")
    @GetMapping("/createTemplate")
    public void createTemplate(HttpServletResponse response) throws IOException {
        String fileName = "域模板（easyexcel）";

        //设置头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillForegroundColor(IndexedColors.RED.index);
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontName("楷体");
        headWriteFont.setBold(true);
        headWriteFont.setFontHeightInPoints((short) 14);
        headWriteCellStyle.setWriteFont(headWriteFont);

        //设置内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontName("微软雅黑");
        contentWriteFont.setFontHeightInPoints((short) 12);

        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

        response.setContentType("application/force-download");//设置强制下载
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");//设置文件名
        response.setCharacterEncoding("utf-8");

        EasyExcel.write(response.getOutputStream(), RegionModel.class)
                .registerWriteHandler(horizontalCellStyleStrategy)
                .registerWriteHandler(new CustomSheetWriteHandler())
                .sheet(fileName).doWrite(null);
    }


    /**
     * 自定义拦截器
     */
    @Component
    class CustomSheetWriteHandler implements SheetWriteHandler {

//        private RegionService regionService;

//        public CustomSheetWriteHandler(@Autowired RegionService regionService) {
//            this.regionService = regionService;
//        }

        @Override
        public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

        }

        @Override
        public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

            //查父级域
//            EntityWrapper ew = new EntityWrapper();
//            ew.setSqlSelect("name");
//            ew.eq("parent_id", MyConstant.Zero);
//            List<RegionModel> regionModels = regionService.selectList(ew);
//            String[] regionNames = new String[regionModels.size()];
//            for (int i = 0; i < regionModels.size(); i++) {
//                regionNames[i] = regionModels.get(i).getName();
//            }

            Sheet sheet = writeSheetHolder.getSheet();
            DataValidationHelper helper = sheet.getDataValidationHelper();

            //设置父级域数据验证
//            CellRangeAddressList cellRangeAddressList1 = new CellRangeAddressList(1, 65535, 2, 2);
//            DataValidationConstraint constraint1 = helper.createExplicitListConstraint(regionNames);
//            DataValidation dataValidation1 = helper.createValidation(constraint1, cellRangeAddressList1);
//            dataValidation1.setSuppressDropDownArrow(false);
//            dataValidation1.setShowErrorBox(true);
//            sheet.addValidationData(dataValidation1);

            //设置是否数据验证
            CellRangeAddressList cellRangeAddressList2 = new CellRangeAddressList(1, 65535, 3, 3);
            DataValidationConstraint constraint2 = helper.createExplicitListConstraint(new String[]{"是", "否"});
            DataValidation dataValidation2 = helper.createValidation(constraint2, cellRangeAddressList2);
            dataValidation2.setSuppressDropDownArrow(false);
            dataValidation2.setShowErrorBox(true);
            sheet.addValidationData(dataValidation2);

        }
    }
}
