package org.winterframework.admin.controller;

import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.winterframework.admin.dao.entity.RoleInfoEntity;
import org.winterframework.admin.model.dto.DeleteRoleDTO;
import org.winterframework.admin.model.dto.ListRoleDTO;
import org.winterframework.admin.model.dto.UpdateRoleDTO;
import org.winterframework.admin.model.vo.ListAdminUserRoleVO;
import org.winterframework.admin.service.RoleService;
import org.winterframework.core.support.ApiResponse;
import org.winterframework.core.support.enums.ErrorCode;
import org.winterframework.rbac.configuration.aop.annotation.RbacPerm;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/10/8 1:03 PM
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {
	@Resource
	private RoleService roleService;

	@RbacPerm
	@GetMapping("/list")
	public ApiResponse<PageInfo<RoleInfoEntity>> list(@Valid ListRoleDTO req) {
		return build(roleService.selectByQuery(req));
	}

	@RbacPerm
	@GetMapping("/detail")
	public ApiResponse<RoleInfoEntity> detail(@RequestParam Long id) {
		return build(roleService.selectByPrimaryKey(id));
	}

	@RbacPerm
	@PostMapping("/update")
	public ApiResponse<Void> update(@RequestBody @Valid UpdateRoleDTO req) {
		roleService.updateRole(req);
		return build(ErrorCode.OK);
	}

	@RbacPerm
	@PostMapping("/delete")
	public ApiResponse<Void> delete(@RequestBody @Valid DeleteRoleDTO req) {
		int num = roleService.deleteByIds(req.getIds());
		if (num > 0) {
			return build(ErrorCode.OK);
		}
		return build(ErrorCode.PARAM_ERROR);
	}

	@GetMapping("/listAdminUserRoles")
	public ApiResponse<List<ListAdminUserRoleVO>> listAdminUserRoles(@RequestParam(value = "adminUserId", required = false) Long adminUserId) {
		return build(roleService.listAdminUserRoles(adminUserId));
	}
}
