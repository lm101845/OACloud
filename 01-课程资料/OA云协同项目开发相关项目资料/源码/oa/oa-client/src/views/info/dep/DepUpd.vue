<template>
    <el-dialog v-model="visible" title="修改部门" width="50%" style="padding-right:50px;" >
        <el-form label-width="100px">

            <el-form-item label="部门名称">
                <el-input v-model="data.d_name" />
            </el-form-item>

            <el-form-item label="部门备注">
                <el-input type="textarea" v-model="data.d_remark" />
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
    data.d_id = row.d_id;
    data.d_name = row.d_name;
    data.d_remark = row.d_remark;
    visible.value = true;
}

const data = reactive({
    d_id:'',
    d_name:'',
    d_remark:''
});

const execUpd = async()=>{
    await req.put('/info/dep',data);
    emit('upd-ok');
    data.d_name = '';
    data.d_reamrk = '';
    visible.value = false;
    ElMessage.success('修改部门成功！');
}


defineExpose({
    open
});
</script>
<style scoped>
</style>