package org.winterframework.admin.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.winterframework.admin.dao.entity.RoleInfoEntity;
import org.winterframework.admin.dao.entity.UserRoleEntity;
import org.winterframework.admin.dao.mapper.RoleInfoMapper;
import org.winterframework.admin.dao.service.RoleInfoDaoService;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/9/30 3:13 PM
 */
@Service
public class RoleInfoDaoServiceImpl extends ServiceImpl<RoleInfoMapper, RoleInfoEntity> implements RoleInfoDaoService {
	@Override
	public List<UserRoleEntity> getUserRoleByUserId(Long userId) {
		return baseMapper.getUserRoleByUserId(userId);
	}
}
