<template>
    <el-dialog v-model="visible" title="修改员工" width="50%" style="padding-right:50px;" >
        <el-form label-width="100px">

            <el-form-item label="姓名">
                <el-input v-model="data.e_name" />
            </el-form-item>

            <el-form-item label="性别">
                <el-select v-model="data.e_sex">
                    <el-option label="男" :value="1"/>
                    <el-option label="女" :value="2"/>
                </el-select>
            </el-form-item>

            <el-form-item label="出生日期">
               <el-date-picker v-model="data.e_birth" type="date" value-format="YYYY-MM-DD" style="width:100%" />
            </el-form-item>

            <el-form-item label="部门">
            <el-select v-model="data.d_id" >
                <el-option v-for="item in depList" :key="item.d_id" :label="item.d_name" :value="item.d_id"/>
            </el-select>
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

const depList = ref([]);

const open = (_depList,row)=>{
    for(let prop in data){
        data[prop] = row[prop];
    }

    depList.value = _depList;
    visible.value = true;
}

const data = reactive({
    e_id:'',
    e_name:'',
    e_sex:'',
    e_birth:'',
    d_id:''
});

const execUpd = async()=>{
    await req.put('/info/emp',data);
    emit('upd-ok');
    visible.value = false;
    ElMessage.success('修改员工成功！');
}


defineExpose({
    open
});
</script>
<style scoped>
</style>