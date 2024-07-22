package org.winterframework.groovy.controller

import groovy.util.logging.Slf4j
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author qinglin.liu
 * created at 2024/7/21 23:18
 */
@Slf4j
@RestController("/api")
class ApiController {
    @RequestMapping("/getUser")
    Object getUser() {
        int a = 1L
        return a
    }
}
