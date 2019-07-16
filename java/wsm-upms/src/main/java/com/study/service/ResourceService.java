package com.study.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.study.mapper.ResourceMapper;
import com.study.model.ResourceModel;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资源（如前台某个菜单） 服务实现类
 * </p>
 *
 * @author wsm
 * @since 2019-07-16
 */
@Service
public class ResourceService extends ServiceImpl<ResourceMapper, ResourceModel> {

}
