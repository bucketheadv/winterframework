package admin.dao.service.impl;

import org.springframework.stereotype.Service;
import admin.dao.entity.AdminUserEntity;
import admin.dao.mapper.AdminUserMapper;
import admin.dao.service.AdminUserInfoDaoService;
import org.winterframework.tk.mybatis.service.base.impl.BaseTkServiceImpl;

/**
 * @author qinglinl
 * Created on 2022/9/30 2:08 PM
 */
@Service
public class AdminUserInfoDaoServiceImpl extends BaseTkServiceImpl<AdminUserMapper, AdminUserEntity, Long> implements AdminUserInfoDaoService {
	@Override
	public AdminUserEntity getByEmail(String email) {
		AdminUserEntity adminUserEntity = new AdminUserEntity();
		adminUserEntity.setEmail(email);
		return baseMapper.selectOne(adminUserEntity);
	}
}
