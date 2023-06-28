package org.winterframework.admin.service;

import org.winterframework.admin.dao.entity.AdminUserEntity;
import org.winterframework.admin.model.dto.ListUserDTO;
import org.winterframework.admin.model.dto.UpdateAdminUserDTO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/9/30 2:47 PM
 */
public interface AdminUserService {
	PageInfo<AdminUserEntity> selectList(ListUserDTO req);

	void updateAdminUser(UpdateAdminUserDTO req);

	AdminUserEntity getById(Long id);

	int deleteByIds(List<Long> ids);
}
