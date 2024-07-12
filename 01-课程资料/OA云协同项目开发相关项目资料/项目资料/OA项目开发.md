# 前言

本次项目将以OA办公系统为背景进行实践，具体要求如下：

1. 以问题为驱动，主动思考，积极动手；
2. 技术上采用前后端分离结构，均使用异步请求进行前后端交互；
3. 后端采用SpringBoot+MyBatis等技术；
4. 前端采用Vue3+Vite4+Vant4等技术。



# 1 项目功能设计（需求分析）

本项目实践的内容是一个OA云协同系统，可以完成请假、批假、销假、任务制定、下达、接收、汇报、验收等。

## 安全管理

1. 登录
2. 用户管理（含角色分配）
3. 角色管理（含权限分配）
4. 密码修改
5. 退出

## 基础信息

1. 部门信息
2. 员工信息

## 业务管理

### 请假

1. 请假申请和销假
2. 请假审批

### 任务

1. 任务制定和下达
2. 任务接收和汇报
3. 任务验收



# 2 项目设计

## 数据库设计

1. 概念模型设计

   用PowerDesigner工具进行设计

2. 通过概念模型生成物理模型

   物理模型就是针对具体数据库的表模型

3. 通过物理模型生成建表的sql脚本

   ```sql
   drop database if exists oadb;
   create database oadb CHARACTER SET utf8 COLLATE utf8_general_ci;
   use oadb;
   
   /*
   ....
   */
   ```

   

# 3 前端项目搭建

### 引言

本前端项目将使用Vue3 + Vite4构建。

Vue3是目前最流行的前端开发框架之一，vite4是Vue3项目的构建工具，提供了编译、打包、开发用前端服务器和热部署等功能。

### 开发工具准备

请自行查找资料安装以下开发工具

- 安装Node.js
- 安装vscode，并为其安装vue3插件：在vscode扩展中，搜索Vue Language Features (Volar)并安装

### 安装cnpm

```javascript
npm install cnpm -g --registry=https://registry.npm.taobao.org
```

### 创建Vue3项目

利用以下命令，通过vite在当前目录构建Vue3项目 oa-front

```bash
cnpm create vite@latest <项目名称> -- --template vue 
```



### 安装依赖

进入项目目录安装依赖

```bash
cnpm i axios vue-router@4 element-plus @element-plus/icons-vue uuid path
```



###  在项目根目录下添加环境变量文件

在根目录下添加文件`.env.development`，内容均如下：

```bash
VITE_API_BASE_URL=/api
VITE_SERVER_URL=http://localhost:9999
VITE_LOCAL_TOKEN_NAME=X-TOKEN
VITE_HEADER_TOKEN_NAME=WDOA-TOKEN
VITE_LOGIN_URL=false
```

### 覆盖文件

使用提供的vite.config.js（项目根目录下）、router（src目录下）、request（src目录下）、App.vue（src目录下）、constants.js（src目录下）、main.js（src目录下）、style.css（src目录下）覆盖项目中对应的目录或文件



# 4 后端项目搭建

## 建立Maven项目，pom.xml如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.xdq</groupId>
    <artifactId>demo</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.1</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>3.0.3</version>
        </dependency>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>com.auth0</groupId>
            <artifactId>java-jwt</artifactId>
            <version>4.3.0</version>
        </dependency>
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper-spring-boot-starter</artifactId>
            <version>2.1.0</version>
        </dependency>
    </dependencies>

</project>
```

##　搭建项目基本结构

1. 建立项目根包org.xdq.demo，将提供的common目录复制到根包下。

2. 在resources下建立配置文件application.yml

   ```yaml
   spring:
     datasource:
       driver-class-name: com.mysql.cj.jdbc.Driver
       url: jdbc:mysql://localhost:3306/oadb
       username: root
       password: root
   
   
   mybatis:
     mapper-locations: classpath:mapper/**/*.xml
   
   
   logging:
     level:
       root: info
       web: trace
       org.xdq.demo: debug
   
   oa:
     token:
       expire-time: 999999 #令牌过期时间，单位：秒
     anonUrls:
       - /security/login
       - /res/**
   
   pagehelper:
     reasonable: true #分页合理化：请求页数不合理时，自动转换未合理页数
     helper-dialect: mysql #指明使用的数据库
   server:
     port: 9999
   ```

3. 建立项目启动类

   ```java
   @SpringBootApplication
   public class App {
   
       public static void main(String[] args) {
           SpringApplication.run(App.class,args);
       }
   }
   ```



4. 在项目根包下建立api、service、dao、vo、dto和model等包
5. 在resource下建立mapper目录



# 登录的开发

## 前端界面的开发

在src下新建views目录，并在views目录下建立子目录security/login，最后在该子目录下建立vue文件index.vue

```vue
<template>
    <div style="position: fixed;top:0;bottom:0;left:0px;right:0;background-color: #EEE;">

        <div style="position: absolute;top:calc( 50% - 350px);left:calc( 50% - 450px);
        box-sizing: border-box; height:700px;width:900px;background-color: white;border-radius: 5px;
        box-shadow: 0 0 5px 1px #DDD;display: flex;flex-direction: column;align-items: center;">

            <h1 style="font-size:80px;color:#777;letter-spacing: 5px;font-family: 隶书; margin-top:120px;">文都OA云协同</h1>

            <el-form style="width:350px;padding-right:15px;" label-width="70px" ref="loginFormRef">
                <el-form-item label="账户">
                    <el-input v-model="data.u_id" />
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="data.u_pwd" type="password" show-password />
                </el-form-item>

                <el-form-item label="验证码" prop="u_verify_code">
                    <el-input v-model="data.u_verify_code" style="width:195px;" />
                    <div style="height:40px;margin-left:5px;" @click="getVerifyCodeUrl">
                        <el-image style="height: 100%;border-radius: 3px;" :src="verify_code_url" fit="scale-down" />
                    </div>

                </el-form-item>

                <el-form-item style="margin-top:30px;">
                    <el-button type="primary" @click="login" style="flex:auto;">登录</el-button>
                </el-form-item>

            </el-form>

        </div>

    </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import req from '@/request'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useRouter } from 'vue-router'
import { v4 as uuidv4 } from 'uuid';//导入uuid生成函数
import { apiBaseUrl, localTokenName } from '@/constants'

const router = useRouter();

const loginFormRef = ref(null);

const verify_code_url = ref('');

const getVerifyCodeUrl = () => {
    data.u_verify_key = uuidv4();
    verify_code_url.value = apiBaseUrl + "/res/verifycode?u_verify_key=" + data.u_verify_key
}

const data = reactive({
    u_id: '',
    u_pwd: '',
    u_verify_code: '',
    u_verify_key: ''
});

getVerifyCodeUrl();


const login = async () => {

    const res = await req.post('/security/login', data);
    ElMessage.success('登录成功！');
    const token = res.data;
    localStorage.setItem(localTokenName, token);

}


</script>

<style scoped></style>
```



建立路由

```json
{
    path:'/security/home',
    name:'SecurityHome',
    component:()=>import("@/view/security/home/index.vue")
}
```

## 后端开发

## 验证码的生成

```java
@RestController
public class VerifyCodeController {

    @GetMapping("/res/verifycode")
    public void outVerifyCodeImage(String u_verify_key, OutputStream out){
        VerifyCodeUtils.outVerifyCodeImage(u_verify_key,out);
    }
}
```

## 登录验证

```java
@RequiredArgsConstructor
@RestController
@RequestMapping("/security/login")
public class LoginController {

    private final LoginService loginService;
    private final TokenUtils tokenUtils;
    private final HttpServletRequest request;

    @PostMapping("")
    public R<?> login(@RequestBody Map<String,String> loginDto){

        if(!VerifyCodeUtils.checkVerifyCode(
                loginDto.get("u_verify_key"),loginDto.get("u_verify_code"))){
            return R.err(R.CODE_ERR_BUSI,"验证码错误！");
        }

        CurrentUser currentUser = loginService.checkLogin(loginDto);
        String userIp = request.getRemoteAddr();
        TokenUser tokenUser = new TokenUser(
            currentUser.getUserId(), currentUser.getUserName(), userIp);

        String token = tokenUtils.loginSign(tokenUser, loginDto.get("u_pwd"));
        return R.OK(token);
    }
}
```

```java
public interface LoginService {
    CurrentUser checkLogin(Map<String, String> loginDto);
}
```

```java
@Service
@Transactional
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final LoginDao loginDao;
    @Override
    public CurrentUser checkLogin(Map<String, String> loginDto) {

        Map<String,String> user = loginDao.findUserByUserIdAndUserPwd(loginDto);
        if(user == null){
            throw new BusinessException("账号或密码错误！");
        }
        CurrentUser currentUser = new CurrentUser(user.get("u_id"), user.get("u_name"));
        return currentUser;
    }
}
```

```java
@Mapper
public interface LoginDao {

    @Select("select u_id,u_name from t_user where u_id = #{u_id} and u_pwd = #{u_pwd}")
    Map<String, String> findUserByUserIdAndUserPwd(Map<String, String> loginDto);
}
```

# 主界面开发

ElementPlus官网Container组件介绍地址：http://element-plus.org/zh-CN/component/container.html
