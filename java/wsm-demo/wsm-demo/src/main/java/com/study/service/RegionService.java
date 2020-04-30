package com.study.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.mapper.RegionMapper;
import com.study.model.RegionModel;
import com.study.utils.CreateUtil;
import com.study.utils.MyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 域（如一个公司） 服务实现类
 * </p>
 *
 * @author wsm
 * @since 2019-07-16
 */
@CacheConfig(cacheNames = "regions")
@Service
public class RegionService extends ServiceImpl<RegionMapper, RegionModel> {

    @Autowired
    private RegionMapper regionMapper;

    @DS("slave")
    public List<RegionModel> getAll() {
        List<RegionModel> models = regionMapper.selectList(null);
        return models;
    }

    @DS("slave")
    @Cacheable(key = "#id")
    public RegionModel getById(Long id) {
        RegionModel model = regionMapper.selectById(id);
        return model;
    }

    @DS("master")
    @CachePut(key = "#model.id")
    public RegionModel add(RegionModel model) {
        LocalDateTime date = LocalDateTime.now();
        model.setId(CreateUtil.id());
        model.setName(CreateUtil.validateCode(6));
        model.setCode(CreateUtil.validateCode(6));
        model.setCreateTime(date);
        model.setUpdateTime(date);
        regionMapper.insert(model);
        return model;
    }

    @DS("master")
    @CachePut(key = "#model.id")
    public RegionModel update(RegionModel model) {
        LocalDateTime date = LocalDateTime.now();
        model.setUpdateTime(date);
        regionMapper.updateById(model);
        RegionModel regionModel = getById(model.getId());
        BeanUtils.copyProperties(model, regionModel, MyUtils.getNullPropertyNames(model, true, false));
        return regionModel;
    }

    @DS("master")
    @CacheEvict(key = "#id")
    public Integer deleteById(Long id) {
        int i = regionMapper.deleteById(id);
        return i;
    }

    @DS("master")
    public void deleteByIds(@RequestParam Long[] ids) {
        this.removeByIds(Arrays.asList(ids));
    }
}
