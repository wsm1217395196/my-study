package com.study.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.study.mapper.ProjectMapper;
import com.study.model.ProjectModel;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 项目（如公司里某个项目） 服务实现类
 * </p>
 *
 * @author wsm
 * @since 2019-07-16
 */
@Service
public class ProjectService extends ServiceImpl<ProjectMapper, ProjectModel> {

}
