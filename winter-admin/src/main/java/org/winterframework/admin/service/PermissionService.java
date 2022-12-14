package org.winterframework.admin.service;

import com.github.pagehelper.PageInfo;
import org.winterframework.admin.dao.entity.PermissionInfoEntity;
import org.winterframework.admin.model.dto.ListPermissionDTO;
import org.winterframework.admin.model.dto.UpdatePermissionDTO;
import org.winterframework.admin.model.vo.ListRolePermissionVO;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/10/8 2:01 PM
 */
public interface PermissionService {
	/**
	 * 获取角色拥有的权限列表
	 * @param roleId 角色id
	 * @return
	 */
	List<ListRolePermissionVO> listRolePermissions(Long roleId);

	void updatePermission(UpdatePermissionDTO req);

	PageInfo<PermissionInfoEntity> selectByQuery(ListPermissionDTO req);

	PermissionInfoEntity getById(Long id);

	int deleteByIds(List<Long> ids);
}
