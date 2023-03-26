package admin.service;

import admin.dao.entity.PermissionInfoEntity;
import admin.model.dto.ListPermissionDTO;
import admin.model.dto.UpdatePermissionDTO;
import admin.model.vo.ListRolePermissionVO;
import com.github.pagehelper.PageInfo;

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
