package org.winterframework.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.winterframework.web.dao.mapper.UserMapper;
import org.winterframework.web.model.UserVO;

@Slf4j
@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;

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
}
