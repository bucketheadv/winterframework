package org.winterframework.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.winterframework.admin.dao.entity.RoleInfoEntity;
import org.winterframework.admin.model.req.UpdateRoleReqDTO;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/10/8 1:00 PM
 */
public interface RoleService extends IService<RoleInfoEntity> {
	List<RoleInfoEntity> listAll();

	void updateRole(UpdateRoleReqDTO req);
}
