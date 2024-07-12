package org.xdq.demo.api.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xdq.demo.common.currentuser.CurrentUser;
import org.xdq.demo.common.token.TokenUser;
import org.xdq.demo.common.token.TokenUtils;
import org.xdq.demo.common.verifycode.VerifyCodeUtils;
import org.xdq.demo.common.vo.R;
import org.xdq.demo.service.security.LoginService;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/security/login")
public class LoginController {

    private final LoginService loginService;
    private final HttpServletRequest request;

    private final TokenUtils tokenUtils;

    @PostMapping("")
    public R<?> login(@RequestBody Map<String,String> loginDto){
        boolean ok = VerifyCodeUtils.checkVerifyCode(loginDto.get("u_verify_key"), loginDto.get("u_verify_code"));
        if(!ok){//验证码不正确
            return R.err(R.CODE_ERR_BUSI,"验证码错误！");
        }

        CurrentUser currentUser =  loginService.checkLogin(loginDto);

        String userIp = request.getRemoteAddr();

        TokenUser tokenUser = new TokenUser(currentUser.getUserId(), currentUser.getUserName(), userIp);

        String token = tokenUtils.loginSign(tokenUser, loginDto.get("u_pwd"));

        return R.OK(token);


    }



}
