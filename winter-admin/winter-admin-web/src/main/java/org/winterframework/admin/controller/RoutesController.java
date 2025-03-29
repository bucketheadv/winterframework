package org.winterframework.admin.controller;

import org.winterframework.admin.model.vo.ListRoutesVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.winterframework.core.support.ApiData;

/**
 * @author qinglinl
 * Created on 2022/10/8 12:03 PM
 */
@RestController
@RequestMapping("/routes")
public class RoutesController extends BaseController {
	@GetMapping
	public ApiData<ListRoutesVO> listRoutes() {
		ListRoutesVO listRoutesVO = new ListRoutesVO();
		return build(listRoutesVO);
	}
}
