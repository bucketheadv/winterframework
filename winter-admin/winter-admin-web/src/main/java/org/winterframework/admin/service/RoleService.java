package org.winterframework.admin.service;

import org.winterframework.admin.dao.entity.RoleInfoEntity;
import org.winterframework.admin.model.dto.ListRoleDTO;
import org.winterframework.admin.model.dto.UpdateRoleDTO;
import org.winterframework.admin.model.vo.ListAdminUserRoleVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/10/8 1:00 PM
 */
public interface RoleService {
	void updateRole(UpdateRoleDTO req);

	PageInfo<RoleInfoEntity> selectByQuery(ListRoleDTO req);

	List<ListAdminUserRoleVO> listAdminUserRoles(Long adminUserId);

	RoleInfoEntity getById(Long id);

	int deleteByIds(List<Long> ids);
}
