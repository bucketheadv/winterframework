package org.winterframework.web.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.winterframework.tk.mybatis.mapper.base.BaseTkMapper;
import org.winterframework.web.dao.entity.UserEntity;

@Mapper
public interface UserMapper extends BaseTkMapper<UserEntity, Long> {
}