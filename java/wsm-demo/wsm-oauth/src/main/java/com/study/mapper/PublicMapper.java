package com.study.mapper;

import com.study.dto.BaseDto;
import com.study.model.OauthClientDetailsModel;
import com.study.model.UserModel;

import java.util.List;

/**
 * 公共接口
 */
public interface PublicMapper {

    UserModel getUserByName(String name);

    List<BaseDto> getRoleByUserId(Long userId);

    OauthClientDetailsModel getOauthClientDetailsByClientId(String clientId);

    String getResourceIdsByClientId(String clientId);
}
