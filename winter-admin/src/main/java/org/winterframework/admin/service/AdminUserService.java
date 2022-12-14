package org.winterframework.admin.service;

import com.github.pagehelper.PageInfo;
import org.winterframework.admin.dao.entity.AdminUserEntity;
import org.winterframework.admin.model.dto.ListUserDTO;
import org.winterframework.admin.model.dto.UpdateAdminUserDTO;
import org.winterframework.tk.mybatis.service.base.BaseTkService;

/**
 * @author qinglinl
 * Created on 2022/9/30 2:47 PM
 */
public interface AdminUserService extends BaseTkService<AdminUserEntity, Long> {
	PageInfo<AdminUserEntity> selectByQuery(ListUserDTO req);

	void updateAdminUser(UpdateAdminUserDTO req);
}
