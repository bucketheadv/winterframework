package org.winterframework.admin.dao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.winterframework.admin.dao.entity.UserInfoEntity;
import org.winterframework.admin.dao.mapper.UserInfoMapper;
import org.winterframework.admin.dao.service.UserInfoDaoService;

/**
 * @author qinglinl
 * Created on 2022/9/30 2:08 PM
 */
@Service
public class UserInfoDaoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfoEntity> implements UserInfoDaoService {
	@Override
	public UserInfoEntity getByEmail(String email) {
		QueryWrapper<UserInfoEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper = queryWrapper.eq("email", email);
		return getOne(queryWrapper);
	}
}
