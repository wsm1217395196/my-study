package com.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.model.RegionModel;
import org.apache.ibatis.annotations.Param;

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

    int testUpdateLock(@Param("id") Long id, @Param("addNum") Integer addNum);
}
