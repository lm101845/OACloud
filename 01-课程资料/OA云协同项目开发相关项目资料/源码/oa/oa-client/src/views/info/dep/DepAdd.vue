<template>
    <el-dialog v-model="visible" title="新增部门" width="50%" style="padding-right:50px;" >
        <el-form label-width="100px">

            <el-form-item label="部门名称">
                <el-input v-model="data.d_name" />
            </el-form-item>

            <el-form-item label="部门备注">
                <el-input type="textarea" v-model="data.d_remark" />
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
    d_name:'',
    d_remark:''
});

const execAdd = async()=>{
    await req.post('/info/dep',data);
    emit('add-ok');
    data.d_name = '';
    data.d_reamrk = '';
    visible.value = false;
    ElMessage.success('新增部门成功！');
}


defineExpose({
    open
});
</script>
<style scoped>
</style>