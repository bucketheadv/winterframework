package org.winterframework.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.winterframework.admin.model.res.ListRoutesResDTO;
import org.winterframework.core.support.ApiResponse;

/**
 * @author qinglinl
 * Created on 2022/10/8 12:03 PM
 */
@RestController
@RequestMapping("/routes")
public class RoutesController extends BaseController {
	@GetMapping
	public ApiResponse<ListRoutesResDTO> listRoutes() {
		ListRoutesResDTO listRoutesResDTO = new ListRoutesResDTO();
		return build(listRoutesResDTO);
	}
}
