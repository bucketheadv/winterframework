package org.winterframework.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.winterframework.core.support.ApiResponse;

/**
 * @author qinglin.liu
 * created at 2024/3/7 19:11
 */
@RestController
public class UserController {
    @GetMapping("/test")
    public ApiResponse<?> test() {
        return ApiResponse.builder()
                .data("OK")
                .build();
    }
}
