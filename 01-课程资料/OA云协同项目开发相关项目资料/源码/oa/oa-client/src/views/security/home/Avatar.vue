<template>
    <el-dialog v-model="visible" width="500"  title="修改头像" >

        <div style="display: flex; flex-direction: column; align-items: center;" >
            <h3 style="color:#666;margin-bottom: 5px;">当前头像</h3>
            <el-avatar :src="avatarUrl" :size="190"></el-avatar>
        </div>

        <div style="display: flex;flex-direction: column;align-items: center;margin-top:5px;">
            <h3 style="color:#666;margin-bottom: 5px;">新头像</h3>
            <el-upload :on-change="onChange"
                    :show-file-list="false" :auto-upload="false" class="avatar-uploader" >
                <el-image v-if="imageUrl" :src="imageUrl" fit="scale-down" class="avatar"></el-image>
                <el-icon v-else class="avatar-uploader-icon" ><Plus/></el-icon>
            </el-upload>
        </div>

        <template #footer >
            <div style="text-align: center;margin:20px 0 5px;">
                <el-button type="primary" @click="uploadAvatar" >上传</el-button>
                <el-button @click="visible=false">关闭</el-button>
                
            </div>
        </template>



    </el-dialog>
</template>
<script setup>
import {ref} from 'vue'
import {Plus} from '@element-plus/icons-vue'
import req from '@/request'
import {ElMessage} from 'element-plus'


const emit = defineEmits(['update-ok']);

const visible = ref(false);

let currentUser;

const open = (user)=>{
    currentUser = user;
    getAvatar();
    visible.value = true;
}

const avatarUrl = ref('');//当前头像的url

const imageUrl = ref('');//待上传的新选择的头像url

const getAvatar = async()=>{
    try{
        const blob = await req.get('/user/avatars/'+currentUser.userId,{
            responseType:'blob'
        });

        avatarUrl.value = URL.createObjectURL(blob);
    }catch(err){
        avatarUrl.value = '/avatar.jpeg'
    }

}

let rawFile;//原始文件数据
const onChange = selectedFile=>{
    rawFile = selectedFile.raw;
    imageUrl.value = URL.createObjectURL(rawFile);
}

const uploadAvatar = async()=>{
    if(!rawFile){
        ElMessage.error("请选择新头像！");
        return;
    }
    const formData = new FormData();
    formData.append("avatar",rawFile);

    await req.post('/security/home/avatar',formData);
    emit('update-ok');
    await getAvatar();
    ElMessage.success('头像更新成功！');

    imageUrl.value = '';
    rawFile = false;
}

//对外开发访问接口
defineExpose({
    open,//对外开发open方法
});


</script>
<style scoped>
.avatar-uploader{
    --uploader-width:178px;
    --uploader-height:178px;
}

.avatar-uploader{
    width:var(--uploader-width);
    height:var(--uploader-height);
    border:1px dashed #DDD;
    cursor: pointer;
    overflow: hidden;
    transition: var(--el-transition-duration-fast);
}
.avatar-uploader:hover{
    border-color: #09E;
}

.avatar-uploader-icon{
    font-size: 28px;
    color:#8c939d;
    width:var(--uploader-width);
    height:var(--uploader-height);
    text-align: center;
}
.avatar-uploader .avatar{
    width:var(--uploader-width);
    height:var(--uploader-height);
}

</style>