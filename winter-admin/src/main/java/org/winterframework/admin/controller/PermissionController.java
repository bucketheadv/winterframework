package org.winterframework.admin.controller;

import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.winterframework.admin.dao.entity.PermissionInfoEntity;
import org.winterframework.admin.model.dto.DeletePermissionDTO;
import org.winterframework.admin.model.dto.ListPermissionDTO;
import org.winterframework.admin.model.dto.UpdatePermissionDTO;
import org.winterframework.admin.model.vo.ListRolePermissionVO;
import org.winterframework.admin.service.PermissionService;
import org.winterframework.core.support.ApiResponse;
import org.winterframework.core.support.enums.ErrorCode;
import org.winterframework.rbac.configuration.aop.annotation.RbacPerm;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/10/8 2:00 PM
 */
@RestController
@RequestMapping("/permission")
public class PermissionController extends BaseController {
	@Resource
	private PermissionService permissionService;
	@RbacPerm
	@GetMapping("/list")
	public ApiResponse<PageInfo<PermissionInfoEntity>> list(@Valid ListPermissionDTO req) {
		return build(permissionService.selectByQuery(req));
	}

	@RbacPerm
	@GetMapping("/detail")
	public ApiResponse<PermissionInfoEntity> detail(@RequestParam Long id) {
		return build(permissionService.selectByPrimaryKey(id));
	}

	@RbacPerm
	@PostMapping("/update")
	public ApiResponse<Void> update(@RequestBody @Valid UpdatePermissionDTO req) {
		permissionService.updatePermission(req);
		return build(ErrorCode.OK);
	}

	@RbacPerm
	@PostMapping("/delete")
	public ApiResponse<Void> delete(@RequestBody @Valid DeletePermissionDTO req) {
		permissionService.deleteByIds(req.getIds());
		return build(ErrorCode.OK);
	}

	@GetMapping("/listRolePermissions")
	public ApiResponse<List<ListRolePermissionVO>> listRolePermissions(@RequestParam(value = "roleId", required = false) Long roleId) {
		return build(permissionService.listRolePermissions(roleId));
	}
}
