package org.winterframework.admin.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.winterframework.admin.dao.entity.RoleInfoEntity;
import org.winterframework.admin.dao.entity.UserRoleEntity;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/9/30 3:12 PM
 */
public interface RoleInfoDaoService extends IService<RoleInfoEntity> {
	List<UserRoleEntity> getUserRoleByUserId(Long userId);
}
