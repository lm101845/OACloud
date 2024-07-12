package org.xdq.demo.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.xdq.demo.common.ex.BusinessException;
import org.xdq.demo.common.ex.SysException;
import org.xdq.demo.common.ex.UnauthencationException;
import org.xdq.demo.common.vo.R;



import java.util.List;
import java.util.Set;

//@RestControllerAdvice声明本类对象是受spring管理的bean，并且是一个针对RestController的通知（切面功能）
//即本类将拦截所有注解为@RestController的控制器方法发生异常的情形
@RestControllerAdvice
@Slf4j
public class ApiAdvice {




    //针对发生BusinessException的Controller方法的处理,方法的返回结果将代替Controller方法的返回结果
    @ExceptionHandler(BusinessException.class)
    public R<?> handleBusinessException(BusinessException e){
        return R.err(R.CODE_ERR_BUSI,e.getMessage());
    }


    @ExceptionHandler(UnauthencationException.class)
    public R<?> handleUnauthencationException(UnauthencationException e){
        return R.err(R.CODE_ERR_AUTHENTICATION,e.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public R<?> handlesException(Exception e){
        log.error("系统错误！",e);
        return R.err(R.CODE_ERR_SYS,"系统错误！");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){


        List<ObjectError> allErrors = e.getAllErrors();
        StringBuilder builder = new StringBuilder();
        for(ObjectError err : allErrors){
            builder.append(err.getDefaultMessage()).append("；");
        }

        if(builder.length() > 0 )builder.deleteCharAt(builder.length()-1).append("。");

        return R.err(R.CODE_ERR_BUSI,builder.toString());


    }



    @ExceptionHandler(BindException.class)
    public R<?> handleBindException(BindException e){
        List<ObjectError> allErrors = e.getAllErrors();
        StringBuilder builder = new StringBuilder();
        for(ObjectError err : allErrors){
            builder.append(err.getDefaultMessage()).append("；");
        }

        if(builder.length() > 0 )builder.deleteCharAt(builder.length()-1).append("。");

        return R.err(R.CODE_ERR_BUSI,builder.toString());
    }

    @ExceptionHandler(TypeMismatchException.class)
    public R<?> handleTypeMismatchException(TypeMismatchException e){
        log.warn("参数错误！",e);
        return R.err(R.CODE_ERR_BUSI,"需要类型："+e.getRequiredType().getName()+"，而值为："+e.getValue());
    }

    @ExceptionHandler(SysException.class)
    public R<?> handleSysException(SysException e){
        log.error("系统错误！",e);
        return R.err(R.CODE_ERR_SYS,"系统错误！");
    }

}
