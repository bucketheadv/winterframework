package org.winterframework.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.winterframework.admin.model.dto.ListUserDTO;
import org.winterframework.admin.service.AdminUserService;
import org.winterframework.core.i18n.I18n;

/**
 * @author qinglinl
 * Created on 2022/10/9 11:30 AM
 */
@RestController
@RequestMapping("/test")
public class TestController {
	@Autowired
	private AdminUserService adminUserService;

	@GetMapping("/test")
	public Object test() {
		return I18n.get("error.hello", 123, "456");
	}

	@GetMapping("/haha")
	public Object haha(@RequestParam Integer page, @RequestParam Integer pageSize) {
		ListUserDTO listUserDTO = new ListUserDTO();
		listUserDTO.setPageNum(page);
		listUserDTO.setPageSize(pageSize);
		return adminUserService.selectByQuery(listUserDTO);
	}
}
