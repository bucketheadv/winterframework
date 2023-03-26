package admin.dao.service.impl;

import org.springframework.stereotype.Service;
import admin.dao.entity.RoleInfoEntity;
import admin.dao.entity.UserRoleEntity;
import admin.dao.mapper.RoleInfoMapper;
import admin.dao.service.RoleInfoDaoService;
import org.winterframework.tk.mybatis.service.base.impl.BaseTkServiceImpl;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/9/30 3:13 PM
 */
@Service
public class RoleInfoDaoServiceImpl extends BaseTkServiceImpl<RoleInfoMapper, RoleInfoEntity, Long> implements RoleInfoDaoService {
	@Override
	public List<UserRoleEntity> getUserRoleByUserId(Long userId) {
		return baseMapper.getUserRoleByUserId(userId);
	}

	@Override
	public void deleteAdminUserRole(Long adminUserId, List<Long> toDelRoleIds) {
		baseMapper.deleteAdminUserRole(adminUserId, toDelRoleIds);
	}

	@Override
	public void createAdminUserRole(Long adminUserId, List<Long> roleIds) {
		baseMapper.createAdminUserRole(adminUserId, roleIds);
	}
}
