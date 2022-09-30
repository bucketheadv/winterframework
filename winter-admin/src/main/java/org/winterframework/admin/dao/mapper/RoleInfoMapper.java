package org.winterframework.admin.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.winterframework.admin.dao.entity.RoleInfoEntity;
import org.winterframework.admin.dao.entity.UserRoleEntity;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/9/30 3:11 PM
 */
@Mapper
public interface RoleInfoMapper extends BaseMapper<RoleInfoEntity> {
	/**
	 * 获取用户角色信息
	 * @param userId 用户id
	 * @return
	 */
	List<UserRoleEntity> getUserRoleByUserId(@Param("userId") Long userId);
}
