package org.winterframework.admin.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.winterframework.admin.dao.entity.UserInfoEntity;
import org.winterframework.admin.model.res.UserDetailResDTO;
import org.winterframework.admin.service.UserInfoService;
import org.winterframework.core.support.ApiResponse;
import org.winterframework.core.tool.BeanTool;
import org.winterframework.rbac.configuration.aop.annotation.RbacPerm;

/**
 * @author qinglinl
 * Created on 2022/9/30 2:46 PM
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController extends BaseController {
	private UserInfoService userInfoService;

	@RbacPerm
	@GetMapping("/detail")
	public ApiResponse<?> detail(@RequestParam Long id) {
		UserInfoEntity userInfoEntity = userInfoService.selectByPrimaryKey(id);
		UserDetailResDTO res = BeanTool.copyAs(userInfoEntity, UserDetailResDTO.class);
		return build(res);
	}
}
