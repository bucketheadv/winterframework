package org.winterframework.admin.service.impl;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.winterframework.admin.dao.entity.PermissionInfoEntity;
import org.winterframework.admin.dao.entity.RolePermissionEntity;
import org.winterframework.admin.dao.mapper.PermissionInfoMapper;
import org.winterframework.admin.model.req.QueryPermissionReqDTO;
import org.winterframework.admin.model.req.UpdatePermissionReqDTO;
import org.winterframework.admin.model.res.ListRolePermissionResDTO;
import org.winterframework.admin.service.PermissionService;
import org.winterframework.core.tool.BeanTool;
import org.winterframework.core.tool.StringTool;
import org.winterframework.tk.mybatis.service.impl.TkServiceImpl;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author qinglinl
 * Created on 2022/10/8 2:02 PM
 */
@Service
public class PermissionServiceImpl extends TkServiceImpl<PermissionInfoMapper, PermissionInfoEntity, Long> implements PermissionService {
	@Override
	public List<ListRolePermissionResDTO> listRolePermissions(Long roleId) {
		Map<Long, Boolean> permissionPermMap = Maps.newHashMap();
		if (roleId != null) {
			List<RolePermissionEntity> rolePermissions = baseMapper.getPermissionsByRoleId(roleId);
			for (RolePermissionEntity rolePermission : rolePermissions) {
				permissionPermMap.put(rolePermission.getPermissionId(), true);
			}
		}
		List<PermissionInfoEntity> allPermissions = baseMapper.selectAll();
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
			baseMapper.updateByPrimaryKeySelective(permissionInfo);
		} else {
			Date now = new Date();
			permissionInfo.setCreateTime(now);
			permissionInfo.setUpdateTime(now);
			baseMapper.insert(permissionInfo);
		}
	}

	@Override
	public PageInfo<PermissionInfoEntity> selectByQuery(QueryPermissionReqDTO req) {
		Condition condition = new Condition(PermissionInfoEntity.class);
		Example.Criteria criteria = condition.and();
		if (StringTool.isNotBlank(req.getPermissionName())) {
			criteria.andLike("permissionName", req.getPermissionName());
		}
		if (StringTool.isNotBlank(req.getUri())) {
			criteria.andLike("uri", req.getUri());
		}
		condition.orderBy("id").desc();
		return selectByPage(condition, req.getPageNum(), req.getPageSize());
	}
}
