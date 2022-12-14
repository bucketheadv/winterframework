package org.winterframework.admin.service.impl;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.winterframework.admin.dao.entity.PermissionInfoEntity;
import org.winterframework.admin.dao.entity.RolePermissionEntity;
import org.winterframework.admin.dao.service.PermissionInfoDaoService;
import org.winterframework.admin.model.dto.ListPermissionDTO;
import org.winterframework.admin.model.dto.UpdatePermissionDTO;
import org.winterframework.admin.model.vo.ListRolePermissionVO;
import org.winterframework.admin.service.PermissionService;
import org.winterframework.core.tool.BeanTool;
import org.winterframework.core.tool.StringTool;
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
public class PermissionServiceImpl implements PermissionService {
	@Resource
	private PermissionInfoDaoService permissionInfoDaoService;
	@Override
	public List<ListRolePermissionVO> listRolePermissions(Long roleId) {
		Map<Long, Boolean> permissionPermMap = Maps.newHashMap();
		if (roleId != null) {
			List<RolePermissionEntity> rolePermissions = permissionInfoDaoService.getPermissionsByRoleId(roleId);
			for (RolePermissionEntity rolePermission : rolePermissions) {
				permissionPermMap.put(rolePermission.getPermissionId(), true);
			}
		}
		List<PermissionInfoEntity> allPermissions = permissionInfoDaoService.selectAll();
		List<ListRolePermissionVO> result = Lists.newArrayList();
		for (PermissionInfoEntity permissionInfo : allPermissions) {
			ListRolePermissionVO listRolePermissionVO = new ListRolePermissionVO();
			listRolePermissionVO.setPermissionId(permissionInfo.getId());
			listRolePermissionVO.setPermissionName(permissionInfo.getPermissionName());
			listRolePermissionVO.setUri(permissionInfo.getUri());
			listRolePermissionVO.setHasPerm(permissionPermMap.getOrDefault(permissionInfo.getId(), false));
			result.add(listRolePermissionVO);
		}
		return result;
	}

	@Override
	public void updatePermission(UpdatePermissionDTO req) {
		PermissionInfoEntity permissionInfo = BeanTool.copyAs(req, PermissionInfoEntity.class);
		if (req.getId() != null) {
			permissionInfoDaoService.updateByPrimaryKeySelective(permissionInfo);
		} else {
			Date now = new Date();
			permissionInfo.setCreateTime(now);
			permissionInfo.setUpdateTime(now);
			permissionInfoDaoService.insertSelective(permissionInfo);
		}
	}

	@Override
	public PageInfo<PermissionInfoEntity> selectByQuery(ListPermissionDTO req) {
		Condition condition = new Condition(PermissionInfoEntity.class);
		Example.Criteria criteria = condition.and();
		if (StringTool.isNotBlank(req.getPermissionName())) {
			criteria.andLike("permissionName", req.getPermissionName());
		}
		if (StringTool.isNotBlank(req.getUri())) {
			criteria.andLike("uri", req.getUri());
		}
		condition.orderBy("id").desc();
		return permissionInfoDaoService.selectByPage(condition, req.getPageNum(), req.getPageSize());
	}

	@Override
	public PermissionInfoEntity getById(Long id) {
		return permissionInfoDaoService.selectByPrimaryKey(id);
	}

	@Override
	public int deleteByIds(List<Long> ids) {
		return permissionInfoDaoService.deleteByIds(ids);
	}
}
