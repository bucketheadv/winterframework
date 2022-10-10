package org.winterframework.admin.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.winterframework.admin.dao.entity.RoleInfoEntity;
import org.winterframework.admin.dao.entity.UserRoleEntity;
import org.winterframework.tk.mybatis.mapper.BaseTkMapper;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/9/30 3:11 PM
 */
@Mapper
public interface RoleInfoMapper extends BaseTkMapper<RoleInfoEntity, Long> {
	/**
	 * 获取用户角色信息
	 * @param userId 用户id
	 * @return
	 */
	List<UserRoleEntity> getUserRoleByUserId(@Param("userId") Long userId);
}
