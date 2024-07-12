package org.xdq.demo.common.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import org.xdq.demo.common.ex.UnauthencationException;

import java.util.Date;

@RequiredArgsConstructor
@Component
public class TokenUtils {


    private final TokenDao tokenDao;

    private static final String CLAIM_NAME_USERID = "CLAIM_NAME_USERID";
    private static final String CLAIM_NAME_USERNAME = "CLAIM_NAME_USERNAME";
    private static final String CLAIM_NAME_USERIP = "CLAIM_NAME_USERIP";

    @Value("${oa.token.expire-time:100}")
    private int expiredTime;//令牌过期时间


    private String sign(TokenUser tokenUser,String secret){

        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withClaim(CLAIM_NAME_USERID,tokenUser.getUserId())
                .withClaim(CLAIM_NAME_USERNAME,tokenUser.getUserName())
                .withClaim(CLAIM_NAME_USERIP,tokenUser.getUserIP())
                .withExpiresAt(new Date(System.currentTimeMillis()+expiredTime*1000L))
                .sign(algorithm);

    }

    public String loginSign(TokenUser tokenUser,String userPwd){
        return sign(tokenUser,userPwd);
    }

    public TokenUser getTokenUser(String clientToken) {

        if(!StringUtils.hasText(clientToken)){
            throw new UnauthencationException("令牌为空！请登录。");
        }

        //解码令牌
        DecodedJWT decodedJWT = null;
        try {
            decodedJWT = JWT.decode(clientToken);
        } catch (JWTDecodeException e) {
            throw new UnauthencationException("令牌错误！请登录。");
        }

        String userId = decodedJWT.getClaim(CLAIM_NAME_USERID).asString();
        String userName = decodedJWT.getClaim(CLAIM_NAME_USERNAME).asString();
        String userIP = decodedJWT.getClaim(CLAIM_NAME_USERIP).asString();

        if(!StringUtils.hasText(userId) || !StringUtils.hasText(userName) || !StringUtils.hasText(userIP)){
            throw new UnauthencationException("令牌信息不足！请登录。");
        }

        return new TokenUser(userId,userName,userIP);


    }

    public TokenUser verify(String clientToken,String userIP){

        TokenUser tokenUser = getTokenUser(clientToken);

        //获取用户密码
        String secret = tokenDao.findUserPassword(tokenUser.getUserId());
        if(!StringUtils.hasText(secret))
            throw new UnauthencationException("未获取令牌密钥！");

        //创建验证器
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret))
                                        .withClaim(CLAIM_NAME_USERIP,userIP)
                                        .build();
        //验证令牌
        try {
            jwtVerifier.verify(clientToken);
        } catch (TokenExpiredException e) {
            throw new UnauthencationException("登录过期！请重新登录。");
        } catch (Exception e){
            throw new UnauthencationException("令牌非法！请登录。");
        }


        return tokenUser;


    }

}
