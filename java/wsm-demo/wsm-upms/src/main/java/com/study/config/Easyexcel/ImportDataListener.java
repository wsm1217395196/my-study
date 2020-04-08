package com.study.config.Easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.exception.MyRuntimeException;
import com.study.model.RegionModel;
import com.study.service.RegionService;
import com.study.utils.CreateUtil;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 导入数据读监听器
 */
public class ImportDataListener extends AnalysisEventListener<RegionModel> {

    private RegionService regionService;

    private List<RegionModel> models = new ArrayList<>();

    List<RegionModel> topRegions;

    public ImportDataListener(RegionService regionService) {
        this.regionService = regionService;
    }

    /**
     * 每读一行数据调用一次
     *
     * @param regionModel
     * @param analysisContext
     */
    @Override
    public void invoke(RegionModel regionModel, AnalysisContext analysisContext) {

        //查父级域
        if (topRegions == null) {
            topRegions = regionService.getTopRegion();
        }

        //设置id,编码，创建时间
        regionModel.setId(CreateUtil.id());
        if (StringUtils.isEmpty(regionModel.getCode())) {
            regionModel.setCode(CreateUtil.validateCode(6));
        }
        regionModel.setCreateTime(LocalDateTime.now());

        //设置父级id
        if (!StringUtils.isEmpty(regionModel.getParentName())) {
            List<Long> parentIds = topRegions.stream().filter(x -> x.getName().equals(regionModel.getParentName())).map(RegionModel::getId).collect(Collectors.toList());
            if (parentIds.size() != 1) {
                throw new MyRuntimeException("父级域 [" + regionModel.getParentName() + "] 不存在！");
            }
            regionModel.setParentId(parentIds.get(0));
        }

        models.add(regionModel);
    }

    /**
     * 读完数据调用
     *
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        try {
            //插入数据
            regionService.saveBatch(models);
        } catch (Exception e) {
            if (e.getMessage().contains("Duplicate entry")) {

                //查全部域
                QueryWrapper qw = new QueryWrapper();
                qw.select("name,code");
                List<RegionModel> list = regionService.list(qw);
                list.addAll(models);

                //查找重复名称
                List<String> names = list.stream()
                        .collect(Collectors.groupingBy(x -> x.getName().toLowerCase(), Collectors.counting()))
                        .entrySet().stream().filter(y -> y.getValue() > 1).map(z -> z.getKey())
                        .collect(Collectors.toList());
                if (names.size() > 0) {
                    throw new MyRuntimeException("名称 " + names.toString() + " 重复！");
                }

                //查找重复编码
                List<String> codes = list.stream()
                        .collect(Collectors.groupingBy(x -> x.getCode().toLowerCase(), Collectors.counting()))
                        .entrySet().stream().filter(y -> y.getValue() > 1).map(z -> z.getKey())
                        .collect(Collectors.toList());
                if (codes.size() > 0) {
                    throw new MyRuntimeException("编码 " + codes.toString() + " 重复！");
                }

            }
            throw new RuntimeException(e);
        }
    }
}