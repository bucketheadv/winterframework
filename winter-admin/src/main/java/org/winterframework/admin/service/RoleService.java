package org.winterframework.admin.service;

import com.github.pagehelper.PageInfo;
import org.winterframework.admin.dao.entity.RoleInfoEntity;
import org.winterframework.admin.model.req.QueryRoleReqDTO;
import org.winterframework.admin.model.req.UpdateRoleReqDTO;
import org.winterframework.tk.mybatis.service.TkService;

/**
 * @author qinglinl
 * Created on 2022/10/8 1:00 PM
 */
public interface RoleService extends TkService<RoleInfoEntity, Long> {
	void updateRole(UpdateRoleReqDTO req);

	PageInfo<RoleInfoEntity> selectByQuery(QueryRoleReqDTO req);
}
