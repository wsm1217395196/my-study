package com.study.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.mapper.OauthClientDetailsMapper;
import com.study.model.OauthClientDetailsModel;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wsm
 * @since 2019-07-26
 */
@Service
public class OauthClientDetailsService extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetailsModel> {

    /**
     * 根据client_id查询客户端resource_ids
     *
     * @param clientId
     * @return
     */
    public String getResourceIdsByClientId(String clientId) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("client_id", clientId);
        qw.select("resource_ids resourceIds");
        OauthClientDetailsModel model = this.getOne(qw);
        return model.getResourceIds();
    }
}
