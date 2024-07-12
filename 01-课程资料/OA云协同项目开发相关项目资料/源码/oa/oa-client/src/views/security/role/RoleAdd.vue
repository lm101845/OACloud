<template>
    <el-dialog v-model="visible" title="新增角色" width="50%" style="padding-right:50px;" >
        <el-form label-width="100px">

            <el-form-item label="角色名称">
                <el-input v-model="data.ro_name" />
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
    ro_name:''
});

const execAdd = async()=>{
    await req.post('/security/role',data);
    emit('add-ok');
    data.ro_name = '';
    visible.value = false;
    ElMessage.success('新增角色成功！');
}


defineExpose({
    open
});
</script>
<style scoped>
</style>