<template>
    <el-dialog v-model="visible" :title="`No.${currentRole.ro_id}角色【${currentRole.ro_name}】的权限`" width="60%">
       

        <div>
            <el-card v-for="mainFun in funList" :key="mainFun.menuId" style="margin-bottom:10px;">
                <template #header >
                    <div style="font-weight:bold;color:#666;">
                        {{ mainFun.menuName }}
                    </div>
                </template>

                <div style="display: flex;align-items: center;">

                    <div v-for="subFun in mainFun.children" :key="subFun.menuId" style="margin-right:20px;" >
                        <el-switch v-model="subFun.checked" style="vertical-align: middle;"/>
                        <label style="padding-left:6px;vertical-align: middle;">{{ subFun.menuName }}</label>
                    </div>

                </div>

            </el-card>
        </div>

        <template #footer >
            <el-button type="primary" @click="execUpdFuns" >确定</el-button>
            <el-button @click="visible=false" >取消</el-button>
        </template>

    </el-dialog>
</template>
<script setup>
import {ref,reactive} from 'vue'
import req from '@/request'
import {ElMessage,ElMessageBox} from 'element-plus'



const visible = ref(false);

const currentRole = ref({});
const open = async (row)=>{
    currentRole.value = row;

    //向服务端发出请求，获得当前角色所拥有权限编号
    const res = await req.get('/security/role/fun/'+row.ro_id);
    const currentRoleFunIds = res.data;//角色所拥有的权限编号


    //遍历所有（子）权限，如果权限编号在角色所拥有的权限编号中存在，则应当将该权限的checked属性置为true，否则置为false

    funList.value.forEach(main => {
        main.children.forEach(sub=>{
            if(currentRoleFunIds.find(funId=>funId === sub.menuId)){
                sub.checked = true;
            }else{
                sub.checked = false;
            }
        });
    });

    visible.value = true;
}

const funList = ref([]);//所有权限
const getFunList = async ()=>{
    const res = await req.get('/security/role/fun');
    funList.value = res.data;
}
getFunList();

const emit = defineEmits(['exec-ok']);

const execUpdFuns = async () => {

    await ElMessageBox.confirm(
        "您确定要更新该角色权限吗？",
        "警告",
        {
            confirmButtonText:'确定',
            cancelButtonText:'取消',
            type:'warning'
        }

    );

    const selectedFunIds = [];//选中权限的id
    funList.value.forEach(main=>{
        main.children.forEach(sub=>{
            sub.checked && selectedFunIds.push(sub.menuId);
        })
    });

    await req.put('/security/role/fun',{roleId:currentRole.value.ro_id,funIds:selectedFunIds});
    emit('exec-ok');
    visible.value = false;
    ElMessage.success('权限更新成功！');

    
}


defineExpose({
    open
});
</script>
<style scoped>
</style>