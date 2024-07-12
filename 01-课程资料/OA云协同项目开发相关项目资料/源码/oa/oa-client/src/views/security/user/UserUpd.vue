<template>
    <el-dialog v-model="visible" title="修改用户" width="50%" style="padding-right:50px;" >
        <el-form label-width="100px">

            <el-form-item label="用户姓名">
                <el-input v-model="data.u_name" />
            </el-form-item>
            

        </el-form>

        <template #footer >
            <el-button type="primary" @click="execUpd" >确定</el-button>
            <el-button @click="visible=false" >取消</el-button>
        </template>

    </el-dialog>
</template>
<script setup>
import {ref,reactive} from 'vue'
import req from '@/request'
import {ElMessage} from 'element-plus'

const emit = defineEmits(['upd-ok']);

const visible = ref(false);


const open = (row)=>{
    data.u_id = row.u_id;
    data.u_name = row.u_name;
    visible.value = true;
}

const data = reactive({
    u_id:'',
    u_name:''
});

const execUpd = async()=>{
    await req.put('/security/user',data);
    emit('upd-ok');
    data.u_name = ''
    visible.value = false;
    ElMessage.success('修改用户成功！');
}


defineExpose({
    open
});
</script>
<style scoped>
</style>