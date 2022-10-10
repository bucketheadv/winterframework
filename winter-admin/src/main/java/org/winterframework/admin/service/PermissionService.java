package org.winterframework.admin.service;

import com.github.pagehelper.PageInfo;
import org.winterframework.admin.dao.entity.PermissionInfoEntity;
import org.winterframework.admin.model.req.QueryPermissionReqDTO;
import org.winterframework.admin.model.req.UpdatePermissionReqDTO;
import org.winterframework.admin.model.res.ListRolePermissionResDTO;
import org.winterframework.tk.mybatis.service.TkService;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/10/8 2:01 PM
 */
public interface PermissionService extends TkService<PermissionInfoEntity, Long> {
	/**
	 * 获取角色拥有的权限列表
	 * @param roleId 角色id
	 * @return
	 */
	List<ListRolePermissionResDTO> listRolePermissions(Long roleId);

	void updatePermission(UpdatePermissionReqDTO req);

	PageInfo<PermissionInfoEntity> selectByQuery(QueryPermissionReqDTO req);
}
