package org.winterframework.admin.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.winterframework.admin.dao.entity.UserInfoEntity;
import org.winterframework.admin.model.req.LoginReqDTO;
import org.winterframework.admin.service.AuthService;
import org.winterframework.core.support.ApiResponse;
import org.winterframework.jwt.support.helper.JwtsHelper;

/**
 * @author qinglinl
 * Created on 2022/9/30 1:48 PM
 */
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController extends BaseController {
	private AuthService authService;

	@PostMapping("/login")
	public ApiResponse<?> login(@RequestBody LoginReqDTO loginReqDTO) {
		UserInfoEntity userInfoEntity = authService.loginByEmail(loginReqDTO.getEmail(), loginReqDTO.getPassword());
		String token = JwtsHelper.encrypt(userInfoEntity.getId());
		return build(token);
	}
}
