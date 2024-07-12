package org.xdq.demo.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class OaWebMvcConfig implements WebMvcConfigurer {

    @Value("${oa.user-avatar-dir}")
    private String userAvatarDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/user/avatars/**")
                .addResourceLocations("file:"+userAvatarDir+"/");
    }
}
