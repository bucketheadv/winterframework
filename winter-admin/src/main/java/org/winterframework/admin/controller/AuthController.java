package org.winterframework.admin.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.winterframework.admin.dao.entity.UserInfoEntity;
import org.winterframework.admin.model.req.LoginReqDTO;
import org.winterframework.admin.model.res.LoginResDTO;
import org.winterframework.admin.service.AuthService;
import org.winterframework.core.support.ApiResponse;
import org.winterframework.core.tool.DateTool;
import org.winterframework.jwt.support.helper.JwtsHelper;

import java.util.Date;

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
	public ApiResponse<LoginResDTO> login(@RequestBody LoginReqDTO loginReqDTO) {
		UserInfoEntity userInfoEntity = authService.loginByEmail(loginReqDTO.getName(), loginReqDTO.getPassword());
		String token = JwtsHelper.encrypt(userInfoEntity.getId());
		LoginResDTO loginResDTO = new LoginResDTO();
		LoginResDTO.User user = new LoginResDTO.User();
		loginResDTO.setExpireAt(DateTool.offsetDay(new Date(), 1));
		user.setName(userInfoEntity.getEmail());
		user.setAddress("无锡市");
		user.setAvatar("https://gw.alipayobjects.com/zos/rmsportal/ubnKSIfAJTxIgXOKlciN.png");
		loginResDTO.setUser(user);
		loginResDTO.setToken(token);
		ApiResponse<LoginResDTO> response = build(loginResDTO);
		response.setMessage("欢迎回来");
		return response;
	}
}
