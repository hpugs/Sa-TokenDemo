package com.hpugs.satoken.web;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSONObject;
import com.hpugs.satoken.model.UserDTO;
import com.hpugs.satoken.model.UserReq;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @Author：xinge
 * @Date：2024/11/12 11:36
 * @Description：headers显示传参token，优先级高于cookie
 */
@RestController
@RequestMapping("/user")
public class UserController {

    public static final HashMap<Long, UserDTO> USERS = new HashMap();

    static {
        USERS.put(1L, new UserDTO(1L, "admin", "123456"));
        USERS.put(2L, new UserDTO(2L, "user", "123456"));
        USERS.put(3L, new UserDTO(3L, "guest", "123456"));
    }

    @SaIgnore
    @RequestMapping("/login")
    public String login(@RequestBody UserReq userReq) {
        String username = userReq.getUsername();
        String password = userReq.getPassword();

        for (Long userId : USERS.keySet()) {
            UserDTO userDTO = USERS.get(userId);
            if (userDTO.getUsername().equals(username) && userDTO.getPassword().equals(password)) {
                StpUtil.login(userId);
                return StpUtil.getTokenValue();
            }
        }
        return "账户密码错误";
    }

    @SaIgnore
    @GetMapping("/logout")
    public String logout() {
        StpUtil.logout();
        return "退出成功";
    }

    @SaIgnore
    @GetMapping("/isLogin")
    public String isLogin() {
        return StpUtil.isLogin() ? "已登录" : "未登录";
    }

    @GetMapping("/getLoginUserInfo")
    public String getLoginUserInfo() {
        Long userId = StpUtil.getLoginIdAsLong();
        UserDTO userDTO = USERS.get(userId);
        return JSONObject.toJSONString(userDTO);
    }

}
