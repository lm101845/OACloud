<template>

    <el-form :inline="true" size="small">
        <el-form-item label="状态">
            <el-select v-model="query.l_status" style="width:100px;" @change="getPage(1)">
                <el-option label="全部" value="" />
                <el-option v-for="(name, status) in statusMap" :label="name" :value="status" />
            </el-select>
        </el-form-item>
        <el-form-item label="编号">
            <el-input v-model="query.l_id" />
        </el-form-item>
        <el-form-item label="申请人">
            <el-input v-model="query.e_name" />
        </el-form-item>
        <el-form-item label="事由">
            <el-input v-model="query.l_cause" />
        </el-form-item>

        <el-form-item>
            <el-button :icon="Search" type="primary" @click="getPage(1)">查询</el-button>
        </el-form-item>

    </el-form>

    <oa-table :page="page" :query="query" :getPage="getPage">
        <el-table :data="page.list">
            <el-table-column prop="l_id" label="编号" width="70" />
            <el-table-column prop="d_name" label="部门" width="100" />
            <el-table-column prop="e_name" label="申请人" width="100" />
            <el-table-column label="起止时间" width="280">
                <template #default="{ row }">
                    {{ row.l_start }} ~ {{ row.l_end }}
                </template>
            </el-table-column>
            <el-table-column prop="l_cause" label="事由" />
            <el-table-column prop="l_submit_date" label="提交时间" width="180" />

            <el-table-column label="审批意见" width="180">
                <template #default="{ row }">
                    <span v-if="row.l_sp_advice?.length <= 20">{{ row.l_sp_advice }}</span>
                    <span v-else-if="!row.l_sp_advice?.length"></span>
                    <span v-else>
                        <el-popover effect="dark" placement="top-start" :width="400" :content="row.l_sp_advice"
                            trigger="hover">
                            <template #reference>
                                {{ row.l_sp_advice?.slice(0, 20) }}...
                            </template>
                        </el-popover>
                    </span>
                </template>
            </el-table-column>
            <el-table-column prop="l_spr" label="审批人" width="180" />
            <el-table-column prop="l_back_date" label="销假时间" width="180" />

            <el-table-column prop="l_status_name" label="状态" width="70" fixed="right" />

            <el-table-column label="操作" width="130" fixed="right">
                <template #default="{ row }">                    
                        
                    <el-tooltip v-if="row.l_status === 1" content="审批" effect="light">
                        <el-button @click="lExamRef.open(row)" :icon="CircleCheck" type="danger" size="small"
                            circle></el-button>
                    </el-tooltip>  
                    <el-tooltip v-else content="详情" effect="light">
                        <el-button @click="lExamRef.open(row)" :icon="Document" type="danger" size="small"
                            circle></el-button>
                    </el-tooltip>                  

                </template>
            </el-table-column>
        </el-table>
    </oa-table>

    <l-exam ref="lExamRef" @ok="getPage(1)"></l-exam>

</template>

<script setup>
import { ref, reactive } from 'vue'
import req from '@/request'
import OaTable from '@/components/OaTable.vue'
import {   Search, CircleCheck, Document} from '@element-plus/icons-vue';
import { ElMessage, ElMessageBox } from 'element-plus';


const statusMap = {
    0: '未提交',
    1: '已提交',
    2: '已驳回',
    3: '已批准',
    4: '已销假'
};

const query = reactive({
    pageNum: 1,
    pageSize: 5,
    l_id: '',
    e_name:'',
    l_cause: '',
    l_status: ''
});

const page = ref({
    total: 0,
    list: []
});

const getPage = async (pageNo = 1) => {
    query.pageNum = pageNo;
    const res = await req.get("/leave/exam", { params: query });
    page.value = res.data;

    //使用获取的分页数据相关值覆盖 query.pageNum  和 query.pageSize
    query.pageNum = page.value.current;
    query.pageSize = page.value.pageSize;
}

getPage(1);

//审批
import LExam from './LExam.vue';
const lExamRef = ref(null);



</script>

<style scoped></style>