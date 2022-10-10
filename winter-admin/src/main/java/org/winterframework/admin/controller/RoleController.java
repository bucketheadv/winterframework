package org.winterframework.admin.controller;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.winterframework.admin.model.req.DeleteRoleReqDTO;
import org.winterframework.admin.model.req.QueryRoleReqDTO;
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
	public ApiResponse<?> list(@Valid QueryRoleReqDTO req) {
		return build(roleService.selectByQuery(req));
	}

	@GetMapping("/detail")
	public ApiResponse<?> detail(@RequestParam Long id) {
		return build(roleService.selectByPrimaryKey(id));
	}

	@PostMapping("/update")
	public ApiResponse<?> update(@RequestBody @Valid UpdateRoleReqDTO req) {
		roleService.updateRole(req);
		return build(ErrorCode.OK);
	}

	@PostMapping("/delete")
	public ApiResponse<?> delete(@RequestBody @Valid DeleteRoleReqDTO req) {
		roleService.deleteByIds(req.getIds());
		return build(ErrorCode.OK);
	}
}
