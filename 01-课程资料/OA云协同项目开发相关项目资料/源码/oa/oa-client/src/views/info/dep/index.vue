<template>

    <el-form :inline="true" size="small">
        <el-form-item label="状态">
            <el-select v-model="query.d_status" style="width:90px;" @change="getPage(1)">
                <el-option label="全部" value=""/>
                <el-option v-for="(item,status) in statusMap" :key="status" :label="item.text" :value="status"/>
            </el-select>
        </el-form-item>

        <el-form-item label="编号">
            <el-input v-model="query.d_id" />
        </el-form-item>

        <el-form-item label="名称">
            <el-input v-model="query.d_name" />
        </el-form-item>

        <el-form-item label="备注">
            <el-input v-model="query.d_remark" />
        </el-form-item>

        <el-form-item>
            <el-button :icon="Search" type="primary" @click="getPage(1)" >查询</el-button>
            <el-button :icon="Plus" type="primary" @click="depAddRef.open()" >新增</el-button>
            <el-button :icon="Delete" type="danger" @click="execMultiDel" >删除</el-button>
            
        </el-form-item>
    </el-form>


    <el-table :data="page.list" @selection-change="onSelectionChange" >
        <el-table-column type="selection"/>
        <el-table-column prop="d_id" label="编号" />
        <el-table-column prop="d_name" label="名称" />
        <el-table-column label="备注" >
            <template #default="{row}">
               <span v-if="row.d_remark?.length <= 20">{{ row.d_remark }}</span>
               <span v-else-if="!row.d_remark?.length">---</span>
               <span v-else>
                    <el-popover effect="dark" placement="top-start" :width="400" :content="row.d_remark" trigger="hover">
                        <template #reference >
                            {{ row.d_remark?.slice(0,20) }}...
                        </template>                        
                    </el-popover>
               </span>
            </template>
        </el-table-column>
        <el-table-column label="状态" >
            <template #default="{row}">
               <el-tag :type="statusMap[row.d_status].type">{{statusMap[row.d_status].text}}</el-tag> 
            </template>
        </el-table-column>
        <el-table-column label="操作">
            <template #default="{row}">
                <el-button v-if="row.d_status === 0" size="small" @click="depUpdRef.open(row)" >修改</el-button>
                <el-button v-if="row.d_status === 0" type="danger" size="small" @click="execDel(row.d_id)" >删除</el-button>
                <el-button v-if="row.d_status === 0" type="primary" size="small" @click="changeStatus(row.d_id,1)" >确定</el-button>
                <el-button v-if="row.d_status === 1 || row.d_status === 3" type="success" size="small" @click="changeStatus(row.d_id,2)" >启用</el-button>
                <el-button v-if="row.d_status === 2" type="danger" size="small" @click="changeStatus(row.d_id,3)" >禁用</el-button>   
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

    <!-- 部门新增 -->
    <dep-add ref="depAddRef" @add-ok="getPage(page.last+1)"></dep-add>

    <!-- 部门修改-->
    <dep-upd ref="depUpdRef" @upd-ok="getPage(page.current)"></dep-upd>

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
    d_id:'',
    d_name:'',
    d_remark:'',
    d_status:''
});

//查询结果
const page = ref({
    total:0,//总记录数
    list:[]
});

const getPage = async (pageNo = 1)=>{
    query.pageNum = pageNo;
    const res = await req.get("/info/dep",{params:query});
    page.value = res.data;  
    
    //使用获取的分页数据相关值覆盖 query.pageNum  和 query.pageSize
    query.pageNum = page.value.current;
    query.pageSize = page.value.pageSize;
}
getPage(1);

const ids = ref([]);//选择数据的编号数组
const onSelectionChange = selections =>{
    console.log(selections);
    ids.value = selections.map(item=>item.d_id);
}

//新增
import DepAdd from './DepAdd.vue'
const depAddRef = ref(null);

//修改
import DepUpd from './DepUpd.vue'
const depUpdRef = ref(null);

//单删除
const execDel = async id=>{
    await ElMessageBox.confirm(
        "您确定要删除该部门信息吗？",
        "警告",
        {
            confirmButtonText:'确定删除',
            cancelButtonText:'不删了',
            type:'warning'
        }

    );

    await req.delete('/info/dep/'+id);
    getPage(page.value.current);
    ElMessage.success('删除部门成功！');
}

//多删除
const execMultiDel = async()=>{
    if(!ids.value || !ids.value.length){
        ElMessage.error('请选择至少一条数据！');
        return;
    }

    await ElMessageBox.confirm(
        "您确定要删除选中的部门信息吗？",
        "警告",
        {
            confirmButtonText:'确定删除',
            cancelButtonText:'不删了',
            type:'warning'
        }

    );

    await req.delete("/info/dep",{data:ids.value})
    getPage(page.value.current);
    ElMessage.success('删除部门成功！');

}

//改变部门状态
const changeStatus = async (id,status)=>{
    await ElMessageBox.confirm(
        "您确认要"+statusMap[status].statusOpr+"部门信息吗？",
        "警告",
        {
            confirmButtonText:'确定',
            cancelButtonText:'取消',
            type:'warning'
        }
    );

    await req.put('/info/dep/'+id+"/"+status);
    getPage(page.value.current);
    ElMessage.success(statusMap[status].statusOpr+"部门信息成功！");
}


</script>