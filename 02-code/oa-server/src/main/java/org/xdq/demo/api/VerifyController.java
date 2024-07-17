package org.xdq.demo.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xdq.demo.common.verifycode.VerifyCodeUtils;

import java.io.OutputStream;

/**
 * @Author liming
 * @Date 2024/7/15 8:18
 * @Description
 **/
@RestController
@RequestMapping("/res/verifycode")
public class VerifyController {
    @GetMapping("")
    public void outVerifyCodeImage(String u_verify_key, OutputStream out) {
        VerifyCodeUtils.outVerifyCodeImage(u_verify_key, out);
    }
}
