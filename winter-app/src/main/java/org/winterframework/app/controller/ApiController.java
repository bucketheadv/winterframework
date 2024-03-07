package org.winterframework.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.winterframework.core.support.ApiResponse;

/**
 * @author qinglin.liu
 * created at 2024/3/7 19:16
 */
@RestController
public class ApiController {
    @GetMapping("/api")
    public ApiResponse<?> api() {
        return ApiResponse.builder().data("haha").build();
    }
}
