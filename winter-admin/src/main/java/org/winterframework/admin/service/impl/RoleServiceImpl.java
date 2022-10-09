package org.winterframework.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.winterframework.admin.dao.entity.RoleInfoEntity;
import org.winterframework.admin.dao.entity.RolePermissionEntity;
import org.winterframework.admin.dao.mapper.RoleInfoMapper;
import org.winterframework.admin.dao.service.PermissionInfoDaoService;
import org.winterframework.admin.model.req.UpdateRoleReqDTO;
import org.winterframework.admin.service.RoleService;
import org.winterframework.core.tool.BeanTool;
import org.winterframework.core.tool.CollectionTool;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qinglinl
 * Created on 2022/10/8 1:01 PM
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleInfoMapper, RoleInfoEntity> implements RoleService {
	@Resource
	private PermissionInfoDaoService permissionInfoDaoService;
	@Override
	public List<RoleInfoEntity> listAll() {
		QueryWrapper<RoleInfoEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper = queryWrapper.orderByDesc("id");
		return list(queryWrapper);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateRole(UpdateRoleReqDTO req) {
		RoleInfoEntity roleInfoEntity = BeanTool.copyAs(req, RoleInfoEntity.class);
		assert roleInfoEntity != null;
		if (req.getId() == null) {
			Date now = new Date();
			roleInfoEntity.setCreateTime(now);
			roleInfoEntity.setUpdateTime(now);
			save(roleInfoEntity);
			if (CollectionTool.isNotEmpty(req.getPermissionIds())) {
				permissionInfoDaoService.createRolePermissions(roleInfoEntity.getId(), req.getPermissionIds());
			}
		} else {
			updateById(roleInfoEntity);
			// 已经拥有的权限
			List<RolePermissionEntity> rolePermissions = permissionInfoDaoService.getPermissionsByRoleId(roleInfoEntity.getId());
			List<Long> rolePermissionIds = rolePermissions.stream().map(RolePermissionEntity::getPermissionId).collect(Collectors.toList());

			// 需要删除的权限
			List<Long> toDelPermissionIds = Lists.newArrayList(rolePermissionIds);
			toDelPermissionIds.removeAll(req.getPermissionIds());
			if (CollectionTool.isNotEmpty(toDelPermissionIds)) {
				permissionInfoDaoService.deleteRolePermission(roleInfoEntity.getId(), req.getPermissionIds());
			}

			// 需要新增的权限
			List<Long> toAddPermissionIds = Lists.newArrayList(req.getPermissionIds());
			toAddPermissionIds.removeAll(rolePermissionIds);
			if (CollectionTool.isNotEmpty(toAddPermissionIds)) {
				permissionInfoDaoService.createRolePermissions(roleInfoEntity.getId(), toAddPermissionIds);
			}
		}
	}
}
