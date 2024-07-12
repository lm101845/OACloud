<template>

    <el-form :inline="true" size="small">
        <el-form-item label="状态">
            <el-select v-model="query.l_status" style="width:100px;">
                <el-option label="全部" value="" />
                <el-option v-for="(name,status) in statusMap" :label="name" :value="status" />
            </el-select>
        </el-form-item>
        <el-form-item label="编号">
            <el-input v-model="query.l_id"/>
        </el-form-item>
        <el-form-item label="事由">
            <el-input v-model="query.l_cause"/>
        </el-form-item>

        <el-form-item>
            <el-button :icon="Search" type="primary" @click="getPage(1)" >查询</el-button>
            <el-button :icon="Plus" type="primary" @click="lAddRef.open()" >新增</el-button>
            <el-button :icon="Delete" type="danger" @click="execMultiDel" >删除</el-button>
        </el-form-item>

    </el-form>

    <oa-table :page="page" :query="query" :getPage="getPage">
        <el-table :data="page.list" @selection-change="onSelectionChange">
            <el-table-column type="selection"/>
            <el-table-column prop="l_id" label="编号" width="70" />
            <el-table-column prop="e_name" label="申请人" width="100" />
            <el-table-column label="起止时间" width="280" >
                <template #default="{row}">
                    {{ row.l_start }} ~ {{ row.l_end }}
                </template>
            </el-table-column>
            <el-table-column prop="l_cause" label="事由" />
            <el-table-column prop="l_submit_date" label="提交时间" width="180" />
            <el-table-column prop="l_status_name" label="状态" width="70" />

            <el-table-column label="操作" width="130" fixed="right" >
                <template #default="{row}">
                    <template v-if="row.l_status === 0" >
                        <el-tooltip content="修改" effect="light">
                            <el-button @click="lUpdRef.open(row)" :icon="Edit" type="primary" size="small" circle ></el-button>
                        </el-tooltip>
                        <el-tooltip content="删除" effect="light">
                            <el-button @click="execDel(row.l_id)" :icon="Delete" type="danger" size="small" circle ></el-button>
                        </el-tooltip>
                        <el-tooltip content="提交" effect="light">
                            <el-button @click="execSubmit(row.l_id)" :icon="Finished" type="success" size="small" circle ></el-button>
                        </el-tooltip>
                    </template>
                    <template v-if="row.l_status === 3" >
                        <el-tooltip content="销假" effect="light">
                            <el-button @click="execBack(row.l_id)" :icon="Back" type="primary" size="small" circle ></el-button>
                        </el-tooltip>                        
                    </template>
                    
                </template>
            </el-table-column>
        </el-table>
    </oa-table>

    <l-add ref="lAddRef" @ok="getPage(1)"></l-add>

    <l-upd ref="lUpdRef" @ok="getPage(page.current)"></l-upd>

</template>

<script setup>
import {ref,reactive} from 'vue'
import req from '@/request'
import OaTable from '@/components/OaTable.vue'
import { Edit,Delete,Finished, Back, Search, Plus } from '@element-plus/icons-vue';
import { ElMessage,ElMessageBox } from 'element-plus';


const statusMap={
    0:'未提交',
    1:'已提交',
    2:'已驳回',
    3:'已批准',
    4:'已销假'
};

const query = reactive({
    pageNum:1,
    pageSize:5,
    l_id:'',
    l_cause:'',
    l_status:''
});

const page = ref({
    total:0,
    list:[]
});

const getPage = async (pageNo = 1)=>{
    query.pageNum = pageNo;
    const res = await req.get("/leave/apprev",{params:query});
    page.value = res.data;  
    
    //使用获取的分页数据相关值覆盖 query.pageNum  和 query.pageSize
    query.pageNum = page.value.current;
    query.pageSize = page.value.pageSize;
}

getPage(1);

const ids = ref([]);
const onSelectionChange = selections =>{
    ids.value = selections.map(item=>item.l_id);
}


//新增
import LAdd from './LAdd.vue'
const lAddRef = ref(null);


//修改
import LUpd from './LUpd.vue'
const lUpdRef = ref(null);

//单删除
const execDel = async id=>{
    await ElMessageBox.confirm(
        "您确定要删除该请假申请吗？",
        "警告",
        {
            confirmButtonText:'确定删除',
            cancelButtonText:'不删了',
            type:'warning'
        }

    );

    await req.delete('/leave/apprev/'+id);
    getPage(page.value.current);
    ElMessage.success('删除请假申请成功！');
}

//多删除
const execMultiDel = async()=>{
    if(!ids.value || !ids.value.length){
        ElMessage.error('请选择至少一条数据！');
        return;
    }

    await ElMessageBox.confirm(
        "您确定要删除选中的请假申请吗？",
        "警告",
        {
            confirmButtonText:'确定删除',
            cancelButtonText:'不删了',
            type:'warning'
        }

    );

    await req.delete("/leave/apprev",{data:ids.value})
    getPage(page.value.current);
    ElMessage.success('删除请假申请成功！');

}

//提交
const execSubmit = async id=>{
    await ElMessageBox.confirm(
        "您确定要提交该请假申请吗？一旦提交将不能修改。",
        "警告",
        {
            confirmButtonText:'确定',
            cancelButtonText:'取消',
            type:'warning'
        }

    );
        
    await req.put("/leave/apprev/submit/"+id)
    getPage(page.value.current);
    ElMessage.success('提交请假申请成功！');
    
}

//执行销假
const execBack = async id=>{
    await ElMessageBox.confirm(
        "您确定要对该请假申请进行销假吗？",
        "警告",
        {
            confirmButtonText:'确定',
            cancelButtonText:'取消',
            type:'warning'
        }

    );
    await req.put("/leave/apprev/back/"+id)
    getPage(page.value.current);
    ElMessage.success('请假申请销假成功！');
}

</script>

<style scoped>
</style>