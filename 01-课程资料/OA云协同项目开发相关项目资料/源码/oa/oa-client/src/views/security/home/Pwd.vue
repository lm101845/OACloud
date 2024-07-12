<template>
    <el-dialog v-model="visible" title="修改密码" width="500" style="padding-bottom:25px;">
        <el-form ref="formRef" :model="data" :rules="rules" label-width="95" style="padding:10px 45px 10px 0px;" >
            <el-form-item label="原密码" prop="old_pwd">
                <el-input v-model="data.old_pwd" type="password" show-password />
            </el-form-item>
            <el-form-item label="新密码" prop="new_pwd">
                <el-input v-model="data.new_pwd" type="password" show-password />
            </el-form-item>
            <el-form-item label="确认密码" prop="re_pwd">
                <el-input v-model="data.re_pwd" type="password" show-password />
            </el-form-item>
        </el-form>

        <template #footer>
            <el-button type="primary" @click="confirmUpdatePassword">确认修改</el-button>
            <el-button @click="visible = false">关闭</el-button>
        </template>

    </el-dialog>
</template>
<script setup>
import {ref,reactive} from 'vue';
import {ElMessage} from 'element-plus'
import req from '@/request'
import {localTokenName} from '@/constants'
import {useRouter} from 'vue-router'

const visible = ref(false);

const open = ()=>{
    visible.value = true;
}

const data = reactive({
    old_pwd:'',
    new_pwd:'',
    re_pwd:''
})

const checkRePwd = (rule,value,callback)=>{
    if(value !== data.new_pwd ){
        callback(new Error('两次输入新密码不一致！'));
    }else{
        callback();
    }
}

const rules = {
    old_pwd:[
        {required:true,message:'请输入原密码！',trigger:'blur'}
    ],
    new_pwd:[
        {required:true,message:'请输入新密码！',trigger:'blur'}
    ],
    re_pwd:[
        {required:true,message:'请输入确认密码！',trigger:'blur'},
        {validator:checkRePwd,trigger:'blur'}
    ],

};

const router = useRouter();

const formRef = ref(null);

const confirmUpdatePassword = ()=>{
    if(!formRef.value)return;
    formRef.value.validate(async(valid,fields)=>{
        if(valid){//通过
            await req.put("/security/home/pwd",data);
            localStorage.removeItem(localTokenName);
            ElMessage.success('密码修改成功！请重新登录。');
            router.replace({path:'/security/login'});
        }else{//未通过
            ElMessage.error("输入错误，请检查！");
        }
    });
}

defineExpose({
    open
});
</script>

<style scoped>

</style>