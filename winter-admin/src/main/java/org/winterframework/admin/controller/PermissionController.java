package org.winterframework.admin.controller;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.winterframework.admin.model.req.DeletePermissionReqDTO;
import org.winterframework.admin.model.req.QueryPermissionReqDTO;
import org.winterframework.admin.model.req.UpdatePermissionReqDTO;
import org.winterframework.admin.model.res.ListRolePermissionResDTO;
import org.winterframework.admin.service.PermissionService;
import org.winterframework.core.support.ApiResponse;
import org.winterframework.core.support.enums.ErrorCode;

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
	@GetMapping("/list")
	public ApiResponse<?> list(@Valid QueryPermissionReqDTO req) {
		return build(permissionService.selectByQuery(req));
	}

	@GetMapping("/detail")
	public ApiResponse<?> detail(@RequestParam Long id) {
		return build(permissionService.selectByPrimaryKey(id));
	}

	@PostMapping("/update")
	public ApiResponse<?> update(@RequestBody @Valid UpdatePermissionReqDTO req) {
		permissionService.updatePermission(req);
		return build(ErrorCode.OK);
	}

	@PostMapping("/delete")
	public ApiResponse<?> delete(@RequestBody @Valid DeletePermissionReqDTO req) {
		permissionService.deleteByIds(req.getIds());
		return build(ErrorCode.OK);
	}

	@GetMapping("/listRolePermissions")
	public ApiResponse<List<ListRolePermissionResDTO>> listRolePermissions(@RequestParam(value = "roleId", required = false) Long roleId) {
		return build(permissionService.listRolePermissions(roleId));
	}
}
