package admin.dao.service.impl;

import org.springframework.stereotype.Service;
import admin.dao.entity.PermissionInfoEntity;
import admin.dao.entity.RolePermissionEntity;
import admin.dao.mapper.PermissionInfoMapper;
import admin.dao.service.PermissionInfoDaoService;
import org.winterframework.tk.mybatis.service.base.impl.BaseTkServiceImpl;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/9/30 3:14 PM
 */
@Service
public class PermissionInfoDaoServiceImpl extends BaseTkServiceImpl<PermissionInfoMapper, PermissionInfoEntity, Long> implements PermissionInfoDaoService {
	@Override
	public List<RolePermissionEntity> getPermissionsByRoleId(Long roleId) {
		return baseMapper.getPermissionsByRoleId(roleId);
	}

	@Override
	public void deleteRolePermission(Long roleId, List<Long> toDelPermissionIds) {
		baseMapper.deleteRolePermission(roleId, toDelPermissionIds);
	}

	@Override
	public void createRolePermissions(Long roleId, List<Long> permissionIds) {
		baseMapper.createRolePermissions(roleId, permissionIds);
	}
}
