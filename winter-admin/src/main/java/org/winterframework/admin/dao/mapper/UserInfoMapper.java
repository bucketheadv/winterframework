package org.winterframework.admin.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.winterframework.admin.dao.entity.UserInfoEntity;

/**
 * @author qinglinl
 * Created on 2022/9/30 2:06 PM
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfoEntity> {
}
