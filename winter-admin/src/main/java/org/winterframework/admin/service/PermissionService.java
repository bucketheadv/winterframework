package org.winterframework.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.winterframework.admin.dao.entity.PermissionInfoEntity;
import org.winterframework.admin.model.req.UpdatePermissionReqDTO;
import org.winterframework.admin.model.res.ListRolePermissionResDTO;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/10/8 2:01 PM
 */
public interface PermissionService extends IService<PermissionInfoEntity> {
	List<PermissionInfoEntity> listAll();

	/**
	 * 获取角色拥有的权限列表
	 * @param roleId 角色id
	 * @return
	 */
	List<ListRolePermissionResDTO> listRolePermissions(Long roleId);

	void updatePermission(UpdatePermissionReqDTO req);
}
