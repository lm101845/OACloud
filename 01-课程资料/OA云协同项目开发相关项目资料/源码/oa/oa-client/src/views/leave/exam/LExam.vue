<template>
    <el-dialog v-model="visible" :title="`${data.e_name}的请假申请`" width="50%" style="padding-right:50px;">
        <el-form label-width="100px">
            <el-form-item label="状态：" style="color:#337ecc;font-weight:bold;">
                {{ statusMap[data.l_status] }}
            </el-form-item>
            <el-form-item label="所在部门：" style="color:#337ecc;font-weight:bold;">
                {{ data.d_name }}
            </el-form-item>
            <el-form-item label="起止时间：" style="color:#337ecc;font-weight:bold;">
                {{ data.l_start }} 到 {{ data.l_end }}
            </el-form-item>
            <el-form-item label="事由：" style="color:#337ecc;font-weight:bold;">
                {{ data.l_cause }}
            </el-form-item>

            <el-form-item label="提交时间：" style="color:#337ecc;font-weight:bold;">
                {{ data.l_submit_date }}
            </el-form-item>
            <el-form-item v-if="data.l_status === 1" label="审批意见：" style="font-weight:bold;">
                <el-input v-model="data.l_sp_advice" type="textarea" />
            </el-form-item>
            <template v-else>
                <el-form-item label="审批意见：" style="color:#337ecc;font-weight:bold;">
                    {{ data.l_sp_advice }}
                </el-form-item>
                <el-form-item label="审批时间：" style="color:#337ecc;font-weight:bold;">
                    {{ data.l_sp_date }}
                </el-form-item>
                <el-form-item label="审批人：" style="color:#337ecc;font-weight:bold;">
                    {{ data.l_spr }}
                </el-form-item>
                <el-form-item v-if="data.l_status === 4" label="销假时间：" style="color:#337ecc;font-weight:bold;">
                    {{ data.l_back_date }}
                </el-form-item>
            </template>

        </el-form>

        <template #footer>
            <div style="text-align: center;">
                <el-button v-if="data.l_status === 1" type="success" @click="execPass">同意</el-button>
                <el-button v-if="data.l_status === 1" type="danger" @click="execReject">驳回</el-button>
                <el-button @click="visible = false">取消</el-button>
            </div>
            
        </template>

    </el-dialog>
</template>
<script setup>
import { ref, reactive } from 'vue'
import req from '@/request'
import { ElMessage } from 'element-plus'

const emit = defineEmits(['ok']);


const statusMap = {
    0: '未提交',
    1: '已提交',
    2: '已驳回',
    3: '已批准',
    4: '已销假'
};

const visible = ref(false);


const open = (row) => {
    for (let prop in data) {
        data[prop] = row[prop];
    }
    visible.value = true;
}

const data = reactive({
    l_id: '',
    l_cause: '',
    l_start: '',
    l_end: '',
    e_name: '',
    l_submit_date: '',
    l_status: '',
    d_name: '',
    l_sp_advice: '',
    l_sp_date: '',
    l_spr: '',
    l_back_date: ''
});

const execPass = async () => {
    await req.put('/leave/exam/pass', data);
    emit('ok');
    visible.value = false;
    ElMessage.success('同意请假申请成功!');
}

const execReject = async () => {
    await req.put('/leave/exam/reject', data);
    emit('ok');
    visible.value = false;
    ElMessage.success('驳回请假申请成功!');
}


defineExpose({
    open
});
</script>
<style scoped></style>