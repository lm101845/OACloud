<template>
    <el-dialog v-model="visible" :title="`${currentUser.u_id}【${currentUser.u_name}】的角色`" width="60%">
       

        <div style="display: flex;flex-direction: column; align-items: center;padding:20px 50px;">
           <div v-for="role in roleList" :key="role.ro_id"
                style="box-sizing: border-box;width:100%;border-bottom: 1px solid #EEE;padding:20px 5px;font-size:16px;
                        display: flex;justify-content: space-between;"
           
           >
                <label 
                    :style="{
                        color:role.checked?'#409EFF':'#888',
                        fontWeight:role.checked?'bold':'normal'
                    }"
                >{{role.ro_name}}</label>
                <el-switch v-model="role.checked"/>
           </div>
        </div>

        <template #footer >
            <el-button type="primary" @click="execUpdRoles" >确定</el-button>
            <el-button @click="visible=false" >取消</el-button>
        </template>

    </el-dialog>
</template>
<script setup>
import {ref,reactive} from 'vue'
import req from '@/request'
import {ElMessage,ElMessageBox} from 'element-plus'



const visible = ref(false);

const currentUser = ref({});
const open = async (row)=>{
    currentUser.value = row;

    //向服务端发出请求，获得当前用户所拥有角色编号
    const res = await req.get('/security/user/role/'+row.u_id);
    const currentUserRoleIds = res.data;//用户所拥有的角色编号


    //遍历所有（子）角色，如果角色编号在用户所拥有的角色编号中存在，则应当将该角色的checked属性置为true，否则置为false

    roleList.value.forEach(role => {
       
        if(currentUserRoleIds.find(roleId=>roleId === role.ro_id)){
            role.checked = true;
        }else{
            role.checked = false;
        }
       
    });

    visible.value = true;
}

const roleList = ref([]);//所有角色
const getRoleList = async ()=>{
    const res = await req.get('/security/user/role');
    roleList.value = res.data;
}
getRoleList();

const emit = defineEmits(['exec-ok']);

const execUpdRoles = async () => {

    await ElMessageBox.confirm(
        "您确定要更新该用户角色吗？",
        "警告",
        {
            confirmButtonText:'确定',
            cancelButtonText:'取消',
            type:'warning'
        }

    );

    const selectedRoleIds = [];//选中角色的id
    roleList.value.forEach(role=>{        
        role.checked && selectedRoleIds.push(role.ro_id);        
    });

    await req.put('/security/user/role',{userId:currentUser.value.u_id,roleIds:selectedRoleIds});
    emit('exec-ok');
    visible.value = false;
    ElMessage.success('用户的角色更新成功！');

    
}


defineExpose({
    open
});
</script>
<style scoped>
</style>