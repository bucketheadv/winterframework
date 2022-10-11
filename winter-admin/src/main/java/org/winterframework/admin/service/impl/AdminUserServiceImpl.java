package org.winterframework.admin.service.impl;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.winterframework.admin.dao.entity.AdminUserEntity;
import org.winterframework.admin.dao.entity.UserRoleEntity;
import org.winterframework.admin.dao.mapper.AdminUserMapper;
import org.winterframework.admin.dao.service.RoleInfoDaoService;
import org.winterframework.admin.enums.BizErrorCode;
import org.winterframework.admin.model.dto.ListUserDTO;
import org.winterframework.admin.model.dto.UpdateAdminUserDTO;
import org.winterframework.admin.service.AdminUserService;
import org.winterframework.core.exception.ServiceException;
import org.winterframework.core.tool.BeanTool;
import org.winterframework.core.tool.CollectionTool;
import org.winterframework.core.tool.StringTool;
import org.winterframework.tk.mybatis.service.impl.TkServiceImpl;
import tk.mybatis.mapper.entity.Condition;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qinglinl
 * Created on 2022/9/30 2:48 PM
 */
@Service
public class AdminUserServiceImpl extends TkServiceImpl<AdminUserMapper, AdminUserEntity, Long> implements AdminUserService {
	@Autowired
	private RoleInfoDaoService roleInfoDaoService;
	@Override
	public PageInfo<AdminUserEntity> selectByQuery(ListUserDTO req) {
		Condition condition = new Condition(AdminUserEntity.class);
		condition.orderBy("id").desc();
		return selectByPage(condition, req.getPageNum(), req.getPageSize());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateAdminUser(UpdateAdminUserDTO req) {
		AdminUserEntity entity = BeanTool.copyAs(req, AdminUserEntity.class);
		Date now = new Date();
		if (entity.getId() == null) {
			if (StringTool.isBlank(entity.getPassword())) {
				throw new ServiceException(BizErrorCode.PASSWORD_CANNOT_BE_BLANK);
			}
			entity.setCreateTime(now);
			entity.setUpdateTime(now);
			baseMapper.insertSelective(entity);
		} else {
			if (StringTool.isBlank(entity.getPassword())) {
				entity.setPassword(null);
			}
			baseMapper.updateByPrimaryKeySelective(entity);

			// 已经拥有的角色
			List<UserRoleEntity> userRoles = roleInfoDaoService.getUserRoleByUserId(req.getId());
			List<Long> userRoleIds = userRoles.stream().map(UserRoleEntity::getRoleId).collect(Collectors.toList());

			// 需要删除的角色
			List<Long> toDelRoleIds = Lists.newArrayList(userRoleIds);
			toDelRoleIds.removeAll(req.getRoleIds());
			if (CollectionTool.isNotEmpty(toDelRoleIds)) {
				roleInfoDaoService.deleteAdminUserRole(entity.getId(), toDelRoleIds);
			}
			// 需要新增的角色
			List<Long> toAddRoleIds = Lists.newArrayList(req.getRoleIds());
			toAddRoleIds.removeAll(userRoleIds);
			if (CollectionTool.isNotEmpty(toAddRoleIds)) {
				roleInfoDaoService.createAdminUserRole(entity.getId(), toAddRoleIds);
			}
		}
	}
}
