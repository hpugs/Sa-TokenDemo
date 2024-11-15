package com.hpugs.satoken;

import cn.dev33.satoken.annotation.SaIgnore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：xinge
 * @Date：2024/11/12 11:36
 * @Description：
 */
@RestController
public class WebController {

    @Value("${spring.profiles.active}")
    private String active;

    @SaIgnore
    @GetMapping("/webStatus")
    public String webStatus() {
        return "success current active：" + active;
    }

}
