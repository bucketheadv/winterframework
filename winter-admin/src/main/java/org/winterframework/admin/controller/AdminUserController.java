package org.winterframework.admin.controller;

import com.github.pagehelper.PageInfo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.winterframework.admin.dao.entity.AdminUserEntity;
import org.winterframework.admin.model.dto.DeleteAdminUserDTO;
import org.winterframework.admin.model.dto.ListUserDTO;
import org.winterframework.admin.model.dto.UpdateAdminUserDTO;
import org.winterframework.admin.model.vo.ListUserVO;
import org.winterframework.admin.model.vo.UserDetailVO;
import org.winterframework.admin.service.AdminUserService;
import org.winterframework.core.support.ApiResponse;
import org.winterframework.core.support.enums.ErrorCode;
import org.winterframework.core.tool.BeanTool;
import org.winterframework.rbac.configuration.aop.annotation.RbacPerm;
import org.winterframework.tk.mybatis.tool.PageTool;

/**
 * @author qinglinl
 * Created on 2022/9/30 2:46 PM
 */
@RestController
@AllArgsConstructor
@RequestMapping("/admin_user")
public class AdminUserController extends BaseController {
	private AdminUserService adminUserService;

	@RbacPerm
	@GetMapping("/detail")
	public ApiResponse<UserDetailVO> detail(@RequestParam Long id) {
		AdminUserEntity adminUserEntity = adminUserService.getById(id);
		UserDetailVO res = BeanTool.copyAs(adminUserEntity, UserDetailVO.class);
		return build(res);
	}

	@RbacPerm
	@PostMapping("/update")
	public ApiResponse<Void> update(@RequestBody @Valid UpdateAdminUserDTO req) {
		adminUserService.updateAdminUser(req);
		return build(ErrorCode.OK);
	}

	@RbacPerm
	@GetMapping("/list")
	ApiResponse<PageInfo<ListUserVO>> list(@Valid ListUserDTO req) {
		PageInfo<AdminUserEntity> pageInfo = adminUserService.selectByQuery(req);
		PageInfo<ListUserVO> result = PageTool.convert(pageInfo, ListUserVO.class);
		return build(result);
	}

	@RbacPerm
	@PostMapping("/delete")
	ApiResponse<Void> delete(@RequestBody @Valid DeleteAdminUserDTO req) {
		int num = adminUserService.deleteByIds(req.getIds());
		if (num > 0) {
			return build(ErrorCode.OK);
		}
		return build(ErrorCode.PARAM_ERROR);
	}
}
