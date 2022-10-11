package org.winterframework.admin.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.winterframework.admin.dao.entity.AdminUserEntity;
import org.winterframework.admin.model.dto.LoginDTO;
import org.winterframework.admin.model.vo.LoginVO;
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
	public ApiResponse<LoginVO> login(@RequestBody LoginDTO loginDTO) {
		AdminUserEntity adminUserEntity = authService.loginByEmail(loginDTO.getName(), loginDTO.getPassword());
		String token = JwtsHelper.encrypt(adminUserEntity.getId());
		LoginVO loginVO = new LoginVO();
		LoginVO.User user = new LoginVO.User();
		loginVO.setExpireAt(DateTool.offsetDay(new Date(), 1));
		user.setName(adminUserEntity.getEmail());
		user.setAddress("无锡市");
		user.setAvatar("https://gw.alipayobjects.com/zos/rmsportal/ubnKSIfAJTxIgXOKlciN.png");
		loginVO.setUser(user);
		loginVO.setToken(token);
		ApiResponse<LoginVO> response = build(loginVO);
		response.setMessage("欢迎回来");
		return response;
	}
}
