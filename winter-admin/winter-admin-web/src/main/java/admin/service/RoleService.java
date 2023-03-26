package admin.service;

import admin.dao.entity.RoleInfoEntity;
import admin.model.dto.ListRoleDTO;
import admin.model.dto.UpdateRoleDTO;
import admin.model.vo.ListAdminUserRoleVO;
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
