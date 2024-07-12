<template>
    <div style="position: fixed;top:0;bottom: 0;left:0;right:0;background-color: #EEE;">

        <div style="position: absolute;top:calc(50% - 350px);left:calc(50% - 450px);
                    height:700px;width:900px;background-color: white;border-radius: 5px;
                    box-shadow: 0 0 5px 1px #DDD;box-sizing:border-box;
                    display: flex;flex-direction: column;align-items: center;">

            <h1 style="font-size: 80px;color:#777;letter-spacing: 5px;font-family: 隶书;margin-top:120px;">
                文都OA云协同
            </h1>

            <el-form label-width="70px" style="width:350px;padding-right: 15px;">
                <el-form-item label="账户：">
                    <el-input v-model="data.u_id" />
                </el-form-item>
                <el-form-item label="密码：">
                    <el-input v-model="data.u_pwd" type="password" show-password />
                </el-form-item>
                <el-form-item label="验证码：">
                    <el-input v-model="data.u_verify_code" style="width:195px;" />
                    <div style="height:40px;margin-left:5px;" @click="getVerifyUrl">
                        <el-image :src="u_verify_url" style="height:100%;border-radius: 3px;" fit="scale-down" />
                    </div>
                </el-form-item>
                <el-form-item style="margin-top:30px;">
                    <el-button type="primary" style="flex:auto;" @click="login" >登录</el-button>
                </el-form-item>
            </el-form>

        </div>

    </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { v4 as uuidv4 } from 'uuid'
import { apiBaseUrl ,localTokenName} from '@/constants'
import req from '@/request' //引入异步访问服务端接口的封装对象
import {ElMessage} from 'element-plus'
import {useRouter} from 'vue-router'

const router = useRouter();//获得路由器

const u_verify_url = ref('');//获取验证码图片的地址

const getVerifyUrl = () => {
    data.u_verify_key = uuidv4();
    u_verify_url.value = apiBaseUrl + "/res/verifycode?u_verify_key=" + data.u_verify_key;
}

//登录数据
const data = reactive({
    u_id: '',
    u_pwd: '',
    u_verify_code: '',
    u_verify_key: ''
});

getVerifyUrl();//后的验证码图片地址

const login = async ()=>{
    const res = await req.post('/security/login',data);
    ElMessage.success('登录成功！');
    const token = res.data;//获取令牌
    localStorage.setItem(localTokenName,token);
    router.push({path:'/security/home'});
};



</script>

<style scoped></style>