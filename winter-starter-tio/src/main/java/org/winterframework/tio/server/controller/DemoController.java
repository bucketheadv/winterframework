package org.winterframework.tio.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author qinglin.liu
 * created at 2023/12/29 08:41
 */
@Slf4j
@Controller
public class DemoController {

    @RequestMapping("/demo")
    public String demo() {
        log.info("Hello World!");
        return "demo";
    }

    @ResponseBody
    @RequestMapping("/test")
    public String test() {
        return "OK";
    }
}
