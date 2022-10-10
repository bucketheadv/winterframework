package org.winterframework.admin.dao.service;

import org.winterframework.admin.dao.entity.RoleInfoEntity;
import org.winterframework.admin.dao.entity.UserRoleEntity;
import org.winterframework.tk.mybatis.service.TkService;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/9/30 3:12 PM
 */
public interface RoleInfoDaoService extends TkService<RoleInfoEntity, Long> {
	List<UserRoleEntity> getUserRoleByUserId(Long userId);
}
