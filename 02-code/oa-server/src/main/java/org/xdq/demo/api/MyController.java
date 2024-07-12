package org.xdq.demo.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xdq.demo.common.vo.R;

/**
 * @Author liming
 * @Date 2024/7/12 8:28
 * @Description
 **/
@RestController
public class MyController {
    @GetMapping("/t")
    public R<?> test(){
        return R.OK("这是一个测试");
    }
}
