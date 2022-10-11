package org.winterframework.admin.service.impl;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.winterframework.admin.dao.entity.RoleInfoEntity;
import org.winterframework.admin.dao.entity.RolePermissionEntity;
import org.winterframework.admin.dao.entity.UserRoleEntity;
import org.winterframework.admin.dao.mapper.RoleInfoMapper;
import org.winterframework.admin.dao.service.PermissionInfoDaoService;
import org.winterframework.admin.model.dto.ListRoleDTO;
import org.winterframework.admin.model.dto.UpdateRoleDTO;
import org.winterframework.admin.model.vo.ListAdminUserRoleVO;
import org.winterframework.admin.service.RoleService;
import org.winterframework.core.tool.BeanTool;
import org.winterframework.core.tool.CollectionTool;
import org.winterframework.core.tool.StringTool;
import org.winterframework.tk.mybatis.service.impl.TkServiceImpl;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author qinglinl
 * Created on 2022/10/8 1:01 PM
 */
@Service
public class RoleServiceImpl extends TkServiceImpl<RoleInfoMapper, RoleInfoEntity, Long> implements RoleService {
	@Resource
	private PermissionInfoDaoService permissionInfoDaoService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateRole(UpdateRoleDTO req) {
		RoleInfoEntity roleInfoEntity = BeanTool.copyAs(req, RoleInfoEntity.class);
		if (req.getId() == null) {
			Date now = new Date();
			roleInfoEntity.setCreateTime(now);
			roleInfoEntity.setUpdateTime(now);
			baseMapper.insert(roleInfoEntity);
			if (CollectionTool.isNotEmpty(req.getPermissionIds())) {
				permissionInfoDaoService.createRolePermissions(roleInfoEntity.getId(), req.getPermissionIds());
			}
		} else {
			baseMapper.updateByPrimaryKeySelective(roleInfoEntity);
			// 已经拥有的权限
			List<RolePermissionEntity> rolePermissions = permissionInfoDaoService.getPermissionsByRoleId(roleInfoEntity.getId());
			List<Long> rolePermissionIds = rolePermissions.stream().map(RolePermissionEntity::getPermissionId).collect(Collectors.toList());

			// 需要删除的权限
			List<Long> toDelPermissionIds = Lists.newArrayList(rolePermissionIds);
			toDelPermissionIds.removeAll(req.getPermissionIds());
			if (CollectionTool.isNotEmpty(toDelPermissionIds)) {
				permissionInfoDaoService.deleteRolePermission(roleInfoEntity.getId(), toDelPermissionIds);
			}

			// 需要新增的权限
			List<Long> toAddPermissionIds = Lists.newArrayList(req.getPermissionIds());
			toAddPermissionIds.removeAll(rolePermissionIds);
			if (CollectionTool.isNotEmpty(toAddPermissionIds)) {
				permissionInfoDaoService.createRolePermissions(roleInfoEntity.getId(), toAddPermissionIds);
			}
		}
	}

	@Override
	public PageInfo<RoleInfoEntity> selectByQuery(ListRoleDTO req) {
		Condition condition = new Condition(RoleInfoEntity.class);
		Example.Criteria criteria = condition.and();
		if (StringTool.isNotBlank(req.getRoleName())) {
			criteria.andLike("roleName", req.getRoleName());
		}
		if (req.getIsSuperAdmin() != null) {
			criteria.andEqualTo("isSuperAdmin", req.getIsSuperAdmin());
		}
		condition.orderBy("id").desc();
		return selectByPage(condition, req.getPageNum(), req.getPageSize());
	}

	@Override
	public List<ListAdminUserRoleVO> listAdminUserRoles(Long adminUserId) {
		Map<Long, Boolean> rolePermMap = Maps.newHashMap();
		if (adminUserId != null) {
			List<UserRoleEntity> userRoleEntities = baseMapper.getUserRoleByUserId(adminUserId);
			for (UserRoleEntity userRoleEntity : userRoleEntities) {
				rolePermMap.put(userRoleEntity.getRoleId(), true);
			}
		}
		List<RoleInfoEntity> allRoles = baseMapper.selectAll();
		List<ListAdminUserRoleVO> result = Lists.newArrayList();
		for (RoleInfoEntity roleInfo : allRoles) {
			ListAdminUserRoleVO listAdminUserRoleVO = new ListAdminUserRoleVO();
			listAdminUserRoleVO.setRoleId(roleInfo.getId());
			listAdminUserRoleVO.setRoleName(roleInfo.getRoleName());
			listAdminUserRoleVO.setHasRole(rolePermMap.getOrDefault(roleInfo.getId(), false));
			result.add(listAdminUserRoleVO);
		}
		return result;
	}
}
