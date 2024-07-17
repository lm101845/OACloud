<template>
  <!--  最外层容器-->
  <el-container style="position: fixed;left: 0;right:0;top: 0;bottom: 0">
    <!--    侧边栏-->
    <el-aside width="200px" style="background-color: #337ecc">
      <div style="display: flex;justify-content: center;margin-top:20px">
        <el-avatar src="/avatar.jpeg" :size="150"></el-avatar>
      </div>
      <el-menu active-text-color="#1111AA" background-color="#226dbb" text-color="white"
               style="margin-top:30px;border:none;">
        <el-sub-menu>
          <template #title>
            <span>安全管理</span>
          </template>
          <el-menu-item>用户管理</el-menu-item>
          <el-menu-item>角色管理</el-menu-item>
          <el-menu-item>密码重置</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    <!--    右侧容器-->
    <el-container style="position: absolute;left: 200px;right: 0;top: 0;bottom: 0">
      <el-header height="50px" style="padding:0 5px;background-color: #79bbff;position: relative;">
<!--        头像-->
        <div style="font-family: 隶书;font-size: 24px;font-weight: bold;color: white;line-height: 50px">
          文都OA云协同信息系统
        </div>
<!--        下拉菜单-->
        <div style="position: absolute;right: 5px;bottom:5px">
          <el-dropdown>
            <span class="currentuser">
              当前用户：XXX
              <el-icon class="el-icon--right">
                <arrow-down></arrow-down>
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
      <el-main style="padding:0 3px;position: relative;">Main</el-main>
      <el-footer height="30px" style="padding:0 5px;background-color: #79bbff;
                        color:white;font-size: 13px;line-height: 30px;text-align: center;">技术支持：&copy;XXXXX科技有限公司
      </el-footer>
    </el-container>
  </el-container>
</template>

<script setup>
  import {ArrowDown} from "@element-plus/icons-vue";
</script>


<style scoped>
  .currentuser {
    color: white;
    outline: none;
    cursor: pointer;
  }
</style>