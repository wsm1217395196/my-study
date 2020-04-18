package com.study.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.study.config.Easyexcel.CustomSheetWriteHandler;
import com.study.config.Easyexcel.ImportDataListener;
import com.study.config.Easyexcel.RegionDto;
import com.study.model.RegionModel;
import com.study.result.ResultView;
import com.study.service.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * easyexcel控制器
 */
@Api(tags = "easyexcel控制器")
@RestController
@RequestMapping("/easyexcel")
public class EasyexcelController {

    @Autowired
    private RegionService regionService;

    @ApiOperation("导入数据")
    @PostMapping("/importData")
    @Transactional
    public ResultView importData(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), RegionModel.class, new ImportDataListener(regionService)).sheet().doRead();
        return ResultView.success();
    }

    @ApiOperation("生成模板（供导入数据用）")
    @GetMapping("/createTemplate")
    public ResultView createTemplate(HttpServletResponse response) throws IOException {
        String fileName = "region-template";
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = setCommon(fileName, response);

        EasyExcel.write(response.getOutputStream(), RegionDto.class)
                .registerWriteHandler(horizontalCellStyleStrategy)
                .registerWriteHandler(new CustomSheetWriteHandler(regionService,"生成模板"))//自定义策略
                .sheet(fileName).doWrite(null);
        return ResultView.success();
    }

    @ApiOperation("导出数据")
    @GetMapping("/exportData")
    public ResultView exportData(HttpServletResponse response) throws IOException {
        String fileName = "region-data";
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = setCommon(fileName, response);

        EasyExcel.write(response.getOutputStream(), RegionModel.class)
                .registerWriteHandler(horizontalCellStyleStrategy)
                .registerWriteHandler(new CustomSheetWriteHandler(regionService,"导出数据"))//自定义策略
                .sheet(fileName).doWrite(data());//写数据
        return ResultView.success();
    }

    /**
     * 域数据
     *
     * @return
     */
    private List<RegionModel> data() {
        List<RegionModel> models = regionService.list();
        List<RegionModel> topRegions = regionService.getTopRegion();

        //设置父级域名称
        for (RegionModel model : models) {
            for (RegionModel topRegion : topRegions) {
                if (topRegion.getId().equals(model.getParentId())) {
                    model.setParentName(topRegion.getName());
                }
            }
        }

        //设置父级域名称
//        models.stream().forEach(x -> {
//            List<RegionModel> parentNames = topRegions.stream().filter(y -> y.getId().equals(x.getParentId())).collect(Collectors.toList());
//            x.setParentName(parentNames.get(0).getName());
//        });
        return models;
    }


    /**
     * 设置 创建模板、导出数据 通用的代码
     *
     * @param fileName
     * @param response
     * @return
     */
    private HorizontalCellStyleStrategy setCommon(String fileName, HttpServletResponse response) {
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

        //设置response的Header
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        return horizontalCellStyleStrategy;
    }

}
