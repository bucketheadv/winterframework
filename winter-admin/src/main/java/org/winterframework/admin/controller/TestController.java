package org.winterframework.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.winterframework.core.i18n.I18n;

/**
 * @author qinglinl
 * Created on 2022/10/9 11:30 AM
 */
@RestController
@RequestMapping("/test")
public class TestController {
	@GetMapping("/test")
	public Object test() {
		return I18n.get("error.hello", 123, "456");
	}
}
