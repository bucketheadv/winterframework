package org.winterframework.admin.dao.service.impl;

import org.springframework.stereotype.Service;
import org.winterframework.admin.dao.entity.UserInfoEntity;
import org.winterframework.admin.dao.mapper.UserInfoMapper;
import org.winterframework.admin.dao.service.UserInfoDaoService;
import org.winterframework.tk.mybatis.service.impl.TkServiceImpl;

/**
 * @author qinglinl
 * Created on 2022/9/30 2:08 PM
 */
@Service
public class UserInfoDaoServiceImpl extends TkServiceImpl<UserInfoMapper, UserInfoEntity, Long> implements UserInfoDaoService {
	@Override
	public UserInfoEntity getByEmail(String email) {
		UserInfoEntity userInfoEntity = new UserInfoEntity();
		userInfoEntity.setEmail(email);
		return baseMapper.selectOne(userInfoEntity);
	}
}
