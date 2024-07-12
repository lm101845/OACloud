package org.xdq.demo.common.verifycode;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.xdq.demo.common.ex.SysException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class VerifyCodeUtils {

    //存储验证码的map集合
    private static Map<String , String> verifyCodeMap = new HashMap<>();


    public static void outVerifyCodeImage(String verify_code_key, OutputStream out){

        try {
            VerificationCode verificationCode = new VerificationCode();
            BufferedImage image = verificationCode.getImage();//获取验证码图像
            String verify_code = verificationCode.getText();//获得验证码文字

            verifyCodeMap.put(verify_code_key,verify_code);//将验证码的key和验证码存入Map集合

            ImageIO.write(image,"jpeg",out);//输出验证码图片

            log.debug("将1分钟后清除验证码：{}->{}",verify_code_key,verifyCodeMap.get(verify_code_key));
            new Thread(()->{
                try {
                    log.debug("正等待1分钟后清除验证码：{}->{}",verify_code_key,verifyCodeMap.get(verify_code_key));
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.debug("正在清除过期验证码：{}->{}",verify_code_key,verifyCodeMap.get(verify_code_key));
                verifyCodeMap.remove(verify_code_key);
                log.debug("清除key为{}的过期验证码成功！",verify_code_key);
            }).start();

        } catch (IOException e) {
            throw new SysException("验证码生成失败！",e);
        }

    }


    public static boolean checkVerifyCode(String verify_code_key,String verify_code){
        String factVerifyCode = verifyCodeMap.get(verify_code_key);
        if(StringUtils.hasText(factVerifyCode)){
            return factVerifyCode.equalsIgnoreCase(verify_code);
        }
        return false;
    }
}
