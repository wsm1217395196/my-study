package com.study.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.study.model.RegionModel;

import java.util.List;

/**
 * <p>
 * 域（如一个公司） Mapper 接口
 * </p>
 *
 * @author wsm
 * @since 2019-07-16
 */
public interface RegionMapper extends BaseMapper<RegionModel> {

    int batchAdd(List<RegionModel> models);

}
