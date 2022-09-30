package org.winterframework.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.winterframework.admin.dao.entity.UserInfoEntity;
import org.winterframework.admin.dao.mapper.UserInfoMapper;
import org.winterframework.admin.service.UserInfoService;

/**
 * @author qinglinl
 * Created on 2022/9/30 2:48 PM
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfoEntity> implements UserInfoService {
}
