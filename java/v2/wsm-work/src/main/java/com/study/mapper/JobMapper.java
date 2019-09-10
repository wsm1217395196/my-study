package com.study.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.study.model.JobModel;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 职位表 Mapper 接口
 * </p>
 *
 * @author wsm123
 * @since 2019-02-28
 */
public interface JobMapper extends BaseMapper<JobModel> {

    List<JobModel> test(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
