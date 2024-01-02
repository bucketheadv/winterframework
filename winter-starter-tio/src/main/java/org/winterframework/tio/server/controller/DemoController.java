package org.winterframework.tio.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author qinglin.liu
 * created at 2023/12/29 08:41
 */
@Controller
public class DemoController {

    @RequestMapping("/demo")
    public String demo() {
        return "demo";
    }
}
