package org.winterframework.admin.dao.service.impl;

import org.springframework.stereotype.Service;
import org.winterframework.admin.dao.entity.AdminUserEntity;
import org.winterframework.admin.dao.mapper.AdminUserMapper;
import org.winterframework.admin.dao.service.UserInfoDaoService;
import org.winterframework.tk.mybatis.service.impl.TkServiceImpl;

/**
 * @author qinglinl
 * Created on 2022/9/30 2:08 PM
 */
@Service
public class UserInfoDaoServiceImpl extends TkServiceImpl<AdminUserMapper, AdminUserEntity, Long> implements UserInfoDaoService {
	@Override
	public AdminUserEntity getByEmail(String email) {
		AdminUserEntity adminUserEntity = new AdminUserEntity();
		adminUserEntity.setEmail(email);
		return baseMapper.selectOne(adminUserEntity);
	}
}
