<template>
    <el-dialog v-model="visible" title="新建请假申请" width="50%" style="padding-right:50px;" >
        <el-form label-width="100px">

            <el-form-item label="事由：">
                <el-input v-model="data.l_cause" type="textarea" />
            </el-form-item>

            <el-form-item label="开始时间：">
                <el-date-picker v-model="data.l_start" type="datetime" format="YYYY-MM-DD HH:mm"
                                value-format="YYYY-MM-DD HH:mm" data-format="YYYY-MM-DD" time-format="HH:mm"
                                style="width:100"
                />
            </el-form-item>

            <el-form-item label="结束时间：">
                <el-date-picker v-model="data.l_end" type="datetime" format="YYYY-MM-DD HH:mm"
                                value-format="YYYY-MM-DD HH:mm" data-format="YYYY-MM-DD" time-format="HH:mm"
                                style="width:100"
                />
            </el-form-item>

        </el-form>

        <template #footer >
            <el-button type="primary" @click="exec" >确定</el-button>
            <el-button @click="visible=false" >取消</el-button>
        </template>

    </el-dialog>
</template>
<script setup>
import {ref,reactive} from 'vue'
import req from '@/request'
import {ElMessage} from 'element-plus'

const emit = defineEmits(['ok']);

const visible = ref(false);


const open = ()=>{
    visible.value = true;
}

const data = reactive({
    l_cause:'',
    l_start:'',
    l_end:''
});

const exec = async()=>{
    await req.post('/leave/apprev',data);
    emit('ok');
    data.l_cause = '';
    data.l_start = '';
    data.l_end = '';
    visible.value = false;
    ElMessage.success('新建请假申请成功！');
}


defineExpose({
    open
});
</script>
<style scoped>
</style>