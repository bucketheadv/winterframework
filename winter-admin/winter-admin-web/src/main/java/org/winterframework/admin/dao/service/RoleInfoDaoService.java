package org.winterframework.admin.dao.service;

import org.winterframework.admin.dao.entity.RoleInfoEntity;
import org.winterframework.admin.dao.entity.UserRoleEntity;
import org.winterframework.tk.mybatis.service.base.BaseTkService;
import tk.mybatis.mapper.common.base.select.SelectAllMapper;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/9/30 3:12 PM
 */
public interface RoleInfoDaoService extends BaseTkService<RoleInfoEntity, Long>, SelectAllMapper<RoleInfoEntity> {
	List<UserRoleEntity> getUserRoleByUserId(Long userId);

	void deleteAdminUserRole(Long adminUserId, List<Long> toDelRoleIds);

	void createAdminUserRole(Long adminUserId, List<Long> roleIds);
}
