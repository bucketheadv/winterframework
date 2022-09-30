package org.winterframework.admin.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.winterframework.admin.dao.entity.PermissionInfoEntity;
import org.winterframework.admin.dao.entity.RolePermissionEntity;
import org.winterframework.admin.dao.mapper.PermissionInfoMapper;
import org.winterframework.admin.dao.service.PermissionInfoDaoService;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/9/30 3:14 PM
 */
@Service
public class PermissionInfoDaoServiceImpl extends ServiceImpl<PermissionInfoMapper, PermissionInfoEntity> implements PermissionInfoDaoService {
	@Override
	public List<RolePermissionEntity> getPermissionsByRoleId(Long roleId) {
		return baseMapper.getPermissionsByRoleId(roleId);
	}
}
