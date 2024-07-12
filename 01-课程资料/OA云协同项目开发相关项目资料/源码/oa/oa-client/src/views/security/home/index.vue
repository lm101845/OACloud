<template>

    <!-- 最外层容器 -->
    <el-container style="position: fixed;left:0;right:0;top:0;bottom:0;">


        <!-- 侧边栏 -->
        <el-aside width="200px" style="background-color: #337ecc;padding:0 5px 0 3px;">
            <!-- 头像 -->
            <div style="display: flex;justify-content: center;margin-top:20px">
                <el-avatar :src="avatarUrl" :size="150"></el-avatar>
            </div>

            <el-menu active-text-color="yellow" background-color="#226dbb" text-color="white" 
                style="margin-top:30px;border:none;"
                @select="goUrl">
                <el-sub-menu v-for="main in mainMenuList" :key="main.menuId" :index="main.menuId + ''">
                    <template #title>
                        <span>{{ main.menuName }}</span>
                    </template>
                    <el-menu-item v-for="sub in main.children" :key="sub.menuId" :index="sub.menuUrl">
                        {{ sub.menuName }}
                    </el-menu-item>

                </el-sub-menu>
            </el-menu>

        </el-aside>
        <el-container style="position: absolute;left:200px;right:0;top:0;bottom:0;">
            <!-- 右侧容器 -->

            <!-- 头部 -->
            <el-header height="50px" style="padding:0 5px;background-color: #79bbff;position: relative;">

                <div style="font-family: 隶书;font-size:24px;font-weight: bold;color: white;line-height: 50px;">
                    文都OA云协同信息系统
                </div>

                <div style="position: absolute;right: 5px;bottom:5px;">
                    <!-- 下拉菜单 -->
                    <el-dropdown @command="handleCommand">
                        <span class="currentuser">
                            当前用户：【{{currentUser.userId}}】{{ currentUser.userName }}
                            <el-icon class="el-icon--right">
                                <arrow-down />
                            </el-icon>
                        </span>

                        <template #dropdown>
                            <el-dropdown-item command="avatar">修改头像</el-dropdown-item>
                            <el-dropdown-item command="pwd">修改密码</el-dropdown-item>
                            <el-dropdown-item command="exit">退出系统</el-dropdown-item>
                        </template>
                    </el-dropdown>
                </div>


            </el-header>
            <!-- 主体部分 -->
            <el-main style="padding:0 3px;position: relative;">

                <el-breadcrumb :separator-icon="ArrowRight" style="height:26px;line-height: 26px;">
                    <el-breadcrumb-item to="/welcome">
                        首页
                    </el-breadcrumb-item>
                    <el-breadcrumb-item v-for="item in route.meta.position" :key="item">
                        {{ item }}
                    </el-breadcrumb-item>

                </el-breadcrumb>

                <div style="position: absolute;left:3px;right:3px;top:26px;bottom: 0;padding-top:10px;box-sizing: border-box;">
                    <router-view></router-view>
                </div>

            </el-main>
            <!-- 底部 -->
            <el-footer height="30px" style="padding:0 5px;background-color: #79bbff;
                        color:white;font-size: 13px;line-height: 30px;text-align: center;">

                技术支持：&copy;XXXXX科技有限公司
            </el-footer>
        </el-container>
    </el-container>

    <avatar ref="avatarRef" @update-ok="getAvatar" ></avatar>

    <pwd ref="pwdRef"></pwd>

</template>

<script setup>
import { ArrowDown, ArrowRight } from '@element-plus/icons-vue'
import { ref } from "vue"
import req from '@/request'
import { useRouter, useRoute } from "vue-router"
import { apiBaseUrl ,localTokenName} from '@/constants'
import {ElMessage} from 'element-plus'

import Avatar from './Avatar.vue'//引入一个自定义组件
const avatarRef = ref(null);//avatarRef这个变量就表示页面上以标签形式引入的Avatar组件对象

import Pwd from './Pwd.vue'
const pwdRef = ref(null);


const router = useRouter();
const route = useRoute();//获得当前路由

const mainMenuList = ref([]);
const getMainMenuList = async () => {
    const res = await req.get('/security/home/menus');
    mainMenuList.value = res.data;
}
getMainMenuList();

const goUrl = url => {
    router.push({ path: url });
}

const currentUser = ref({});
const getCurrentUser = async()=>{
    const res = await req.get('/security/home/currentuser');
    currentUser.value = res.data;
    getAvatar();
}
getCurrentUser();

const handleCommand = command=>{
    switch(command){
        case 'avatar':
            console.log('点击了修改头像！');
            avatarRef.value.open(currentUser.value);//调用组件对象的open方法
            break;
        case 'pwd':
            console.log('点击了修改密码！');
            pwdRef.value.open();
            break;
        case 'exit':
            console.log('点击了退出系统！');
            localStorage.removeItem(localTokenName);
            ElMessage.success("您已成功退出系统！");
            router.replace({path:'/security/login'});
            break;
        
    }
}

const avatarUrl = ref('');//当前头像的url
const getAvatar = async()=>{
    try{
        const blob = await req.get('/user/avatars/'+currentUser.value.userId,{
            responseType:'blob'
        });

        avatarUrl.value = URL.createObjectURL(blob);
    }catch(err){
        console.log(err);
        avatarUrl.value = '/avatar.jpeg'
    }
}



</script>

<style scoped>
.currentuser {
    color: white;
    border: none;
    outline: none;
    cursor: pointer;
}
</style>