package org.winterframework.admin.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.winterframework.admin.dao.entity.PermissionInfoEntity;
import org.winterframework.admin.dao.entity.RolePermissionEntity;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/9/30 3:13 PM
 */
public interface PermissionInfoDaoService extends IService<PermissionInfoEntity> {
	List<RolePermissionEntity> getPermissionsByRoleId(Long roleId);
}
