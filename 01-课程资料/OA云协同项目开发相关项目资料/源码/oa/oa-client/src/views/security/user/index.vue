<template>

    <el-form :inline="true" size="small">
        

        <el-form-item label="账号">
            <el-input v-model="query.u_id" />
        </el-form-item>

        <el-form-item label="姓名">
            <el-input v-model="query.u_name" />
        </el-form-item>

       

        <el-form-item>
            <el-button :icon="Search" type="primary" @click="getPage(1)" >查询</el-button>
            <el-button :icon="Plus" type="primary" @click="userAddRef.open()" >新增</el-button>
            <el-button :icon="Delete" type="danger" @click="execMultiDel" >删除</el-button>
            
        </el-form-item>
    </el-form>


    <el-table :data="page.list" @selection-change="onSelectionChange" >
        <el-table-column type="selection"/>
        <el-table-column prop="u_id" label="账号" >
            <template #default="{row}">
                {{ row.u_id }}
                【<span style="color:#911111">{{ row.u_type=='emp'?'员工':(row.u_type=='normal'?'普通':'内置') }}用户</span>】
            </template>
        </el-table-column>
        <el-table-column prop="u_name" label="姓名" />

        <el-table-column  label="角色" >
            <template #default="{row}">
                <el-tag v-for="name in row.roleNameList" :key="name" style="margin:5px;" type="success" effect="dark" >{{ name }}</el-tag>
            </template>
        </el-table-column>
        
       
        <el-table-column label="操作">
            <template #default="{row}">
                <el-button v-if="row.u_type==='normal'" size="small" @click="userUpdRef.open(row)" >修改</el-button>
                <el-button v-if="row.u_type==='normal'" type="danger" size="small" @click="execDel(row.u_id)" >删除</el-button>
                <el-button type="primary" size="small" @click="userRoleRef.open(row)" >角色</el-button>
            </template>         
        </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <el-pagination style="margin-top:5px;"
        :page-sizes="[2,3,5,10,15,20,30,100]"
        layout="sizes,prev,pager,next,jumper,->,total"
        :total="page.total" 
        v-model:page-size="query.pageSize" 
        v-model:current-page="query.pageNum" 
        @current-change="getPage"
        @size-change="getPage(1)"
        />

    <!-- 新增用户 -->
    <user-add ref="userAddRef" @add-ok="getPage(page.last+1)"></user-add>

    <!-- 用户修改-->
    <user-upd ref="userUpdRef" @upd-ok="getPage(page.current)"></user-upd>

    <!-- 角色 -->
    <user-role ref="userRoleRef" @exec-ok="getPage(page.current)" ></user-role>

</template>
<script setup>
import {ref,reactive} from 'vue'
import req from '@/request'
import { Search , Plus, Delete} from '@element-plus/icons-vue';
import {ElMessage,ElMessageBox} from 'element-plus'



//查询条件
const query = reactive({
    pageNum:1,
    pageSize:5,
    u_id:'',
    u_name:''
});

//查询结果
const page = ref({
    total:0,//总记录数
    list:[]
});

const getPage = async (pageNo = 1)=>{
    query.pageNum = pageNo;
    const res = await req.get("/security/user",{params:query});
    page.value = res.data;  
    
    //使用获取的分页数据相关值覆盖 query.pageNum  和 query.pageSize
    query.pageNum = page.value.current;
    query.pageSize = page.value.pageSize;
}
getPage(1);

const ids = ref([]);//选择数据的编号数组
const onSelectionChange = selections =>{
    console.log(selections);
    ids.value = selections.map(item=>item.u_id);
}

//新增
import UserAdd from './UserAdd.vue'
const userAddRef = ref(null);

//修改
import UserUpd from './UserUpd.vue'
const userUpdRef = ref(null);

//单删除
const execDel = async id=>{
    await ElMessageBox.confirm(
        "您确定要删除该用户信息吗？",
        "警告",
        {
            confirmButtonText:'确定删除',
            cancelButtonText:'不删了',
            type:'warning'
        }

    );

    await req.delete('/security/user/'+id);
    getPage(page.value.current);
    ElMessage.success('删除用户成功！');
}

//多删除
const execMultiDel = async()=>{
    if(!ids.value || !ids.value.length){
        ElMessage.error('请选择至少一条数据！');
        return;
    }

    await ElMessageBox.confirm(
        "您确定要删除选中的用户信息吗？",
        "警告",
        {
            confirmButtonText:'确定删除',
            cancelButtonText:'不删了',
            type:'warning'
        }

    );

    await req.delete("/security/user",{data:ids.value})
    getPage(page.value.current);
    ElMessage.success('删除用户成功！');

}

//角色
import UserRole from './UserRole.vue'
const userRoleRef = ref(null);


</script>