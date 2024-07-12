<template>

    <el-form :inline="true" size="small">
        

        <el-form-item label="编号">
            <el-input v-model="query.ro_id" />
        </el-form-item>

        <el-form-item label="名称">
            <el-input v-model="query.ro_name" />
        </el-form-item>

       

        <el-form-item>
            <el-button :icon="Search" type="primary" @click="getPage(1)" >查询</el-button>
            <el-button :icon="Plus" type="primary" @click="roleAddRef.open()" >新增</el-button>
            <el-button :icon="Delete" type="danger" @click="execMultiDel" >删除</el-button>
            
        </el-form-item>
    </el-form>


    <el-table :data="page.list" @selection-change="onSelectionChange" >
        <el-table-column type="selection"/>
        <el-table-column prop="ro_id" label="编号" />
        <el-table-column prop="ro_name" label="名称" />

        <el-table-column  label="权限" >
            <template #default="{row}">
                <el-tag v-for="name in row.funNameList" :key="name" style="margin:5px;" type="success" effect="dark" >{{ name }}</el-tag>
            </template>
        </el-table-column>
        
       
        <el-table-column label="操作">
            <template #default="{row}">
                <el-button size="small" @click="roleUpdRef.open(row)" >修改</el-button>
                <el-button type="danger" size="small" @click="execDel(row.ro_id)" >删除</el-button>
                <el-button type="primary" size="small" @click="roleFunRef.open(row)" >权限</el-button>
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

    <!-- 角色新增 -->
    <role-add ref="roleAddRef" @add-ok="getPage(page.last+1)"></role-add>

    <!-- 角色修改-->
    <role-upd ref="roleUpdRef" @upd-ok="getPage(page.current)"></role-upd>

    <!-- 权限 -->
    <role-fun ref="roleFunRef" @exec-ok="getPage(page.current)" ></role-fun>

</template>
<script setup>
import {ref,reactive} from 'vue'
import req from '@/request'
import { Search , Plus, Delete} from '@element-plus/icons-vue';
import {ElMessage,ElMessageBox} from 'element-plus'

//定义状态映射
const statusMap = {
    0:{text:'未确定',type:'info'},
    1:{text:'已确定',type:'primary',statusOpr:'确定'},
    2:{text:'已启用',type:'success',statusOpr:'启用'},
    3:{text:'已禁用',type:'danger',statusOpr:'禁用'}    
};

//查询条件
const query = reactive({
    pageNum:1,
    pageSize:5,
    ro_id:'',
    ro_name:''
});

//查询结果
const page = ref({
    total:0,//总记录数
    list:[]
});

const getPage = async (pageNo = 1)=>{
    query.pageNum = pageNo;
    const res = await req.get("/security/role",{params:query});
    page.value = res.data;  
    
    //使用获取的分页数据相关值覆盖 query.pageNum  和 query.pageSize
    query.pageNum = page.value.current;
    query.pageSize = page.value.pageSize;
}
getPage(1);

const ids = ref([]);//选择数据的编号数组
const onSelectionChange = selections =>{
    console.log(selections);
    ids.value = selections.map(item=>item.ro_id);
}

//新增
import RoleAdd from './RoleAdd.vue'
const roleAddRef = ref(null);

//修改
import RoleUpd from './RoleUpd.vue'
const roleUpdRef = ref(null);

//单删除
const execDel = async id=>{
    await ElMessageBox.confirm(
        "您确定要删除该角色信息吗？",
        "警告",
        {
            confirmButtonText:'确定删除',
            cancelButtonText:'不删了',
            type:'warning'
        }

    );

    await req.delete('/security/role/'+id);
    getPage(page.value.current);
    ElMessage.success('删除角色成功！');
}

//多删除
const execMultiDel = async()=>{
    if(!ids.value || !ids.value.length){
        ElMessage.error('请选择至少一条数据！');
        return;
    }

    await ElMessageBox.confirm(
        "您确定要删除选中的角色信息吗？",
        "警告",
        {
            confirmButtonText:'确定删除',
            cancelButtonText:'不删了',
            type:'warning'
        }

    );

    await req.delete("/security/role",{data:ids.value})
    getPage(page.value.current);
    ElMessage.success('删除角色成功！');

}

//权限
import RoleFun from './RoleFun.vue'
const roleFunRef = ref(null);


</script>