package org.winterframework.admin.service.impl;

import org.springframework.stereotype.Service;
import org.winterframework.admin.dao.entity.UserInfoEntity;
import org.winterframework.admin.dao.mapper.UserInfoMapper;
import org.winterframework.admin.service.UserInfoService;
import org.winterframework.tk.mybatis.service.impl.TkServiceImpl;

/**
 * @author qinglinl
 * Created on 2022/9/30 2:48 PM
 */
@Service
public class UserInfoServiceImpl extends TkServiceImpl<UserInfoMapper, UserInfoEntity, Long> implements UserInfoService {
}
