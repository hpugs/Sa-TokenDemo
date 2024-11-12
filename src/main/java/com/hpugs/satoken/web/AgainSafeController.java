package com.hpugs.satoken.web;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckSafe;
import cn.dev33.satoken.stp.StpUtil;
import com.hpugs.satoken.model.UserReq;
import org.springframework.web.bind.annotation.*;

/**
 * @Author：xinge
 * @Date：2024/11/12 14:47
 * @Description：二级认证
 */
@RestController
@RequestMapping("/againSafe")
public class AgainSafeController {

    public static final String AGAIN_SAFE_DELETE_USER = "DELETE_USER";

    /**
     * 二次认证有效期：60秒
     */
    private static final Long AGAIN_SAFE_DELETE_USER_VALIDITY = 60L;

    @SaCheckSafe(AGAIN_SAFE_DELETE_USER)
    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam Long userId) {
        UserController.USERS.remove(userId);
        return "success";
    }

    @SaCheckLogin
    @PostMapping("/deleteUserSafe")
    public String deleteUserSafe(@RequestBody UserReq userReq) {
        if (userReq.getUsername().equals("admin") && userReq.getPassword().equals("123456")) {
            StpUtil.openSafe(AGAIN_SAFE_DELETE_USER, AGAIN_SAFE_DELETE_USER_VALIDITY);
            return "success";
        }

        return "账号或密码错误";
    }

    @SaCheckLogin
    @GetMapping("/getDeleteUserSafeValidity")
    public String getDeleteUserSafeValidity() {
        if(!StpUtil.isSafe(AGAIN_SAFE_DELETE_USER)){
            return "请先进行二次认证";
        }
        return String.valueOf(StpUtil.getSafeTime(AGAIN_SAFE_DELETE_USER));
    }

}
