package org.winterframework.admin.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.winterframework.admin.dao.entity.AdminUserEntity;
import org.winterframework.tk.mybatis.mapper.BaseTkMapper;

/**
 * @author qinglinl
 * Created on 2022/9/30 2:06 PM
 */
@Mapper
public interface AdminUserMapper extends BaseTkMapper<AdminUserEntity, Long> {
}
