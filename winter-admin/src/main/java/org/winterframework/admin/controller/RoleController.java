package org.winterframework.admin.controller;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.winterframework.admin.model.req.DeleteRoleReqDTO;
import org.winterframework.admin.model.req.UpdateRoleReqDTO;
import org.winterframework.admin.service.RoleService;
import org.winterframework.core.support.ApiResponse;
import org.winterframework.core.support.enums.ErrorCode;

/**
 * @author qinglinl
 * Created on 2022/10/8 1:03 PM
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {
	@Resource
	private RoleService roleService;

	@GetMapping("/list")
	public ApiResponse<?> list() {
		return build(roleService.listAll());
	}

	@GetMapping("/detail")
	public ApiResponse<?> detail(@RequestParam Long id) {
		return build(roleService.getById(id));
	}

	@PostMapping("/update")
	public ApiResponse<?> update(@RequestBody @Valid UpdateRoleReqDTO req) {
		roleService.updateRole(req);
		return build(ErrorCode.OK);
	}

	@PostMapping("/delete")
	public ApiResponse<?> delete(@RequestBody @Valid DeleteRoleReqDTO req) {
		roleService.removeBatchByIds(req.getIds());
		return build(ErrorCode.OK);
	}
}
