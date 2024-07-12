package org.xdq.demo.api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xdq.demo.common.currentuser.CurrentUser;
import org.xdq.demo.common.vo.R;
import org.xdq.demo.service.security.HomeService;
import org.xdq.demo.vo.MenuVo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/security/home")
public class HomeController {

    private final HomeService homeService;

    //当前登录用户（该对象实际上是当前用户代理，内部所代理的真实当前用户随实际当前用户不同而不同）
    private final CurrentUser currentUser;

    @GetMapping("/menus")
    public R<List<MenuVo>> getMenus(){
        List<MenuVo> mainMenuList = homeService.getMenuList(currentUser);
        return R.OK(mainMenuList);
    }

    @GetMapping("/currentuser")
    public R<CurrentUser> getCurrentUser(){
        return R.OK(currentUser);
    }

    @Value("${oa.user-avatar-dir}")
    private String userAvatarDir;

    @PostMapping("/avatar")
    public R<?> uploadAvatar(MultipartFile avatar) throws IOException {
        File file = new File(userAvatarDir,currentUser.getUserId());
        FileCopyUtils.copy(avatar.getInputStream(),new FileOutputStream(file));
        return R.OK();
    }

    @PutMapping("/pwd")
    public R<?> updatePassword(@RequestBody Map<String,String> pwdDto){

        pwdDto.put("userId",currentUser.getUserId());
        homeService.updatePassword(pwdDto);
        return R.OK();

    }
}
