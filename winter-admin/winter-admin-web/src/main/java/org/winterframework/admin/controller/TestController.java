package org.winterframework.admin.controller;

import org.winterframework.admin.model.dto.ListUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.winterframework.admin.service.AdminUserService;
import org.winterframework.core.i18n.I18n;
import org.winterframework.dubbo.facade.HelloFacade;

/**
 * @author qinglinl
 * Created on 2022/10/9 11:30 AM
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
	@Autowired
	private AdminUserService adminUserService;
	@DubboReference
	private HelloFacade helloFacade;

	@GetMapping("/test")
	public Object test() {
		log.info(helloFacade.sayHello("123"));
		return I18n.get("error.email_or_password_invalid", 123, "456");
	}

	@GetMapping("/haha")
	public Object haha(@RequestParam Integer page, @RequestParam Integer pageSize) {
		ListUserDTO listUserDTO = new ListUserDTO();
		listUserDTO.setPageNum(page);
		listUserDTO.setPageSize(pageSize);
		return adminUserService.selectList(listUserDTO);
	}
}
