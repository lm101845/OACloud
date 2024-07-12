<template>
    <el-dialog v-model="visible" title="新增用户" width="50%" style="padding-right:50px;" >
        <el-form label-width="100px">

            <el-form-item label="用户姓名">
                <el-input v-model="data.u_name" />
            </el-form-item>

        </el-form>

        <template #footer >
            <el-button type="primary" @click="execAdd" >确定</el-button>
            <el-button @click="visible=false" >取消</el-button>
        </template>

    </el-dialog>
</template>
<script setup>
import {ref,reactive} from 'vue'
import req from '@/request'
import {ElMessage} from 'element-plus'

const emit = defineEmits(['add-ok']);

const visible = ref(false);


const open = ()=>{
    visible.value = true;
}

const data = reactive({
    u_name:''
});

const execAdd = async()=>{
    await req.post('/security/user',data);
    emit('add-ok');
    data.u_name = '';
    visible.value = false;
    ElMessage.success('新增用户成功！');
}


defineExpose({
    open
});
</script>
<style scoped>
</style>