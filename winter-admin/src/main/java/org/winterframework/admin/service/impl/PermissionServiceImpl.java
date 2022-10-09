package org.winterframework.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.winterframework.admin.dao.entity.PermissionInfoEntity;
import org.winterframework.admin.dao.entity.RolePermissionEntity;
import org.winterframework.admin.dao.mapper.PermissionInfoMapper;
import org.winterframework.admin.model.req.UpdatePermissionReqDTO;
import org.winterframework.admin.model.res.ListRolePermissionResDTO;
import org.winterframework.admin.service.PermissionService;
import org.winterframework.core.tool.BeanTool;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author qinglinl
 * Created on 2022/10/8 2:02 PM
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionInfoMapper, PermissionInfoEntity> implements PermissionService {
	@Override
	public List<PermissionInfoEntity> listAll() {
		QueryWrapper<PermissionInfoEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper = queryWrapper.orderByDesc("id");
		return list(queryWrapper);
	}

	@Override
	public List<ListRolePermissionResDTO> listRolePermissions(Long roleId) {
		Map<Long, Boolean> permissionPermMap = Maps.newHashMap();
		if (roleId != null) {
			List<RolePermissionEntity> rolePermissions = baseMapper.getPermissionsByRoleId(roleId);
			for (RolePermissionEntity rolePermission : rolePermissions) {
				permissionPermMap.put(rolePermission.getPermissionId(), true);
			}
		}
		List<PermissionInfoEntity> allPermissions = listAll();
		List<ListRolePermissionResDTO> result = Lists.newArrayList();
		for (PermissionInfoEntity permissionInfo : allPermissions) {
			ListRolePermissionResDTO listRolePermissionResDTO = new ListRolePermissionResDTO();
			listRolePermissionResDTO.setPermissionId(permissionInfo.getId());
			listRolePermissionResDTO.setPermissionName(permissionInfo.getPermissionName());
			listRolePermissionResDTO.setUri(permissionInfo.getUri());
			listRolePermissionResDTO.setHasPerm(permissionPermMap.getOrDefault(permissionInfo.getId(), false));
			result.add(listRolePermissionResDTO);
		}
		return result;
	}

	@Override
	public void updatePermission(UpdatePermissionReqDTO req) {
		PermissionInfoEntity permissionInfo = BeanTool.copyAs(req, PermissionInfoEntity.class);
		assert permissionInfo != null;
		if (req.getId() != null) {
			updateById(permissionInfo);
		} else {
			Date now = new Date();
			permissionInfo.setCreateTime(now);
			permissionInfo.setUpdateTime(now);
			save(permissionInfo);
		}
	}
}
