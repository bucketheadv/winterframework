package org.winterframework.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.dromara.dynamictp.core.executor.DtpExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.winterframework.core.support.ApiResponse;
import org.winterframework.web.config.BlacklistProperties;
import org.winterframework.web.dao.mapper.UserMapper;
import org.winterframework.web.model.UserVO;

@Slf4j
@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BlacklistProperties blacklistProperties;
    @Autowired
    @Qualifier("dtpExecutor1")
    private DtpExecutor dtpExecutor;

    @RequestMapping("/getBlacklist")
    public Object getBlacklist() {
        return blacklistProperties.getUsers();
    }

    @RequestMapping("/user")
    public Object getUser() {
        UserVO userVO = new UserVO();
        userVO.setId(1L);
        userVO.setUsername("TestAbc");
        userVO.setMobile("13012345678");
        userVO.setAddress("上海市长宁区临虹路123号");
        userVO.setIdCard("510102199001232209");
        log.info("user : {}", userVO);
        return userVO;
    }

    @RequestMapping("/testThrow")
    public Object testThrow(@RequestParam(name = "flag", defaultValue = "true") Boolean flag) {
        if (flag) {
            throw new RuntimeException("Test");
        }
        return new ApiResponse<>();
    }
}
