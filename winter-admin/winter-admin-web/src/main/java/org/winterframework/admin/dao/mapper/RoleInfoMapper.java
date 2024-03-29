package org.winterframework.admin.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.winterframework.admin.dao.entity.RoleInfoEntity;
import org.winterframework.admin.dao.entity.UserRoleEntity;
import org.winterframework.tk.mybatis.mapper.base.BaseTkMapper;
import tk.mybatis.mapper.common.base.select.SelectAllMapper;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/9/30 3:11 PM
 */
@Mapper
public interface RoleInfoMapper extends BaseTkMapper<RoleInfoEntity, Long>, SelectAllMapper<RoleInfoEntity> {
	/**
	 * 获取用户角色信息
	 * @param userId 用户id
	 * @return
	 */
	List<UserRoleEntity> getUserRoleByUserId(@Param("userId") Long userId);

	void deleteAdminUserRole(@Param("adminUserId") Long adminUserId, @Param("toDelRoleIds") List<Long> toDelRoleIds);

	void createAdminUserRole(@Param("adminUserId") Long adminUserId, @Param("roleIds") List<Long> roleIds);
}
