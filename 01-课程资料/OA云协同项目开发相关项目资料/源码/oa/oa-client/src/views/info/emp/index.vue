<template>

    <el-form :inline="true" size="small">

        <el-form-item label="部门">
            <el-select v-model="query.d_id" style="width:100px;" @change="getPage(1)">
                <el-option label="全部" value="" />
                <el-option v-for="item in depList" :key="item.d_id" :label="item.d_name" :value="item.d_id" />
            </el-select>
        </el-form-item>

        <el-form-item label="状态">
            <el-select v-model="query.e_status" style="width:90px;" @change="getPage(1)">
                <el-option label="全部" value="" />
                <el-option v-for="(item, status) in statusMap" :key="status" :label="item.text" :value="status" />
            </el-select>
        </el-form-item>

        <el-form-item label="性别">
            <el-select v-model="query.e_sex" style="width:60px;" @change="getPage(1)">
                <el-option label="全部" value="" />
                <el-option label="男" :value="1" />
                <el-option label="女" :value="2" />
            </el-select>
        </el-form-item>

        <el-form-item label="工号">
            <el-input v-model="query.e_id" />
        </el-form-item>

        <el-form-item label="姓名">
            <el-input v-model="query.e_name" />
        </el-form-item>

        <el-form-item label="出生日期">
            <el-col :span="11">
                <el-date-picker v-model="query.e_birth_start" type="date" value="YYYY-MM-DD" placeholder="开始日期"
                    style="width:100%" />
            </el-col>
            <el-col :span="2" style="text-align:center;">至</el-col>
            <el-col :span="11">
                <el-date-picker v-model="query.e_birth_end" type="date" value="YYYY-MM-DD" placeholder="截至日期"
                    style="width:100%" />
            </el-col>
        </el-form-item>


        <el-form-item>
            <el-button :icon="Search" type="primary" @click="getPage(1)">查询</el-button>
            <el-button :icon="Plus" type="primary" @click="empAddRef.open(depList)">新增</el-button>
            <el-button :icon="Delete" type="danger" @click="execMultiDel">删除</el-button>

        </el-form-item>
    </el-form>


    <el-table :data="page.list" @selection-change="onSelectionChange">
        <el-table-column type="selection" />
        <el-table-column label="工号" width="170">
            <template #default="{ row }">
                {{ row.e_id }}
                <span v-if="row.account" style="color:#9a1122;">（登录账号）</span>
            </template>
        </el-table-column>
        <el-table-column prop="e_name" label="姓名" width="190" >
            <template #default="{ row }">
                {{ row.e_name }}
                <span v-if="row.leader" style="color:#119911;">（部门领导）</span>
            </template>
        </el-table-column>
        <el-table-column prop="e_sex" label="性别" width="60">
            <template #default="{ row }">
                {{ row.e_sex == 1 ? "男" : "女" }}
            </template>
        </el-table-column>
        <el-table-column prop="e_birth" label="出生日期" width="150" />
        <el-table-column prop="d_name" label="部门" />
        <el-table-column label="状态">
            <template #default="{ row }">
                <el-tag :type="statusMap[row.e_status].type">{{ row.e_status_name }}</el-tag>
            </template>
        </el-table-column>
        <el-table-column label="操作" width="260">
            <template #default="{ row }">
                <el-button v-if="row.e_status === 0" size="small" @click="empUpdRef.open(depList, row)">修改</el-button>
                <el-button v-if="row.e_status === 0" type="danger" size="small"
                    @click="execDel(row.e_id)">删除</el-button>
                <el-button v-if="row.e_status === 0" type="primary" size="small"
                    @click="changeStatus(row.e_id, 1)">确定</el-button>
                <el-button v-if="row.e_status === 1 || row.e_status === 3" type="success" size="small"
                    @click="changeStatus(row.e_id, 2)">启用</el-button>
                <el-button v-if="row.e_status === 2" type="danger" size="small"
                    @click="changeStatus(row.e_id, 3)">禁用</el-button>
                <el-button v-if="row.e_status == 2 && !row.account" type="primary" size="small"
                    @click="creatUser(row.e_id)">生成账号</el-button>
                <el-button v-if="row.e_status > 0 && row.account" type="warning" size="small"
                    @click="cancelUser(row.e_id)">撤销账号</el-button>

                <el-button v-if="row.e_status === 2 && !row.leader" type="primary" size="small"
                    @click="setLeader(row.e_id)">设置为领导</el-button>
            </template>
        </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <el-pagination style="margin-top:5px;" :page-sizes="[2, 3, 5, 10, 15, 20, 30, 100]"
        layout="sizes,prev,pager,next,jumper,->,total" :total="page.total" v-model:page-size="query.pageSize"
        v-model:current-page="query.pageNum" @current-change="getPage" @size-change="getPage(1)" />

    <!--新增-->
    <emp-add ref="empAddRef" @add-ok="getPage(page.last + 1)"></emp-add>

    <!--修改-->
    <emp-upd ref="empUpdRef" @upd-ok="getPage(page.current)"></emp-upd>

</template>
<script setup>
import { ref, reactive } from 'vue'
import req from '@/request'
import { Search, Plus, Delete } from '@element-plus/icons-vue';
import { ElMessage, ElMessageBox } from 'element-plus'

//定义状态映射
const statusMap = {
    0: { text: '未确定', type: 'info' },
    1: { text: '已确定', type: 'primary', statusOpr: '确定' },
    2: { text: '已启用', type: 'success', statusOpr: '启用' },
    3: { text: '已禁用', type: 'danger', statusOpr: '禁用' }
};

//查询条件
const query = reactive({
    pageNum: 1,
    pageSize: 5,
    e_id: '',
    e_name: '',
    e_sex: '',
    e_birth_start: '',
    e_birth_end: '',
    e_status: '',
    d_id: ''
});

//查询结果
const page = ref({
    total: 0,//总记录数
    list: []
});

const getPage = async (pageNo = 1) => {
    query.pageNum = pageNo;
    const res = await req.get("/info/emp", { params: query });
    page.value = res.data;

    //使用获取的分页数据相关值覆盖 query.pageNum  和 query.pageSize
    query.pageNum = page.value.current;
    query.pageSize = page.value.pageSize;
}
getPage(1);


const depList = ref([]);
const getDepList = async () => {
    const res = await req.get('/info/emp/dep');
    depList.value = res.data;
}
getDepList();

const ids = ref([]);//选择数据的编号数组
const onSelectionChange = selections => {
    console.log(selections);
    ids.value = selections.map(item => item.e_id);
}

//新增
import EmpAdd from './EmpAdd.vue'
const empAddRef = ref(null);

//修改
import EmpUpd from './EmpUpd.vue'
const empUpdRef = ref(null);


//单删除
const execDel = async id => {
    await ElMessageBox.confirm(
        "您确定要删除该员工信息吗？",
        "警告",
        {
            confirmButtonText: '确定删除',
            cancelButtonText: '不删了',
            type: 'warning'
        }

    );

    await req.delete('/info/emp/' + id);
    getPage(page.value.current);
    ElMessage.success('删除员工成功！');
}

//多删除
const execMultiDel = async () => {
    if (!ids.value || !ids.value.length) {
        ElMessage.error('请选择至少一条数据！');
        return;
    }

    await ElMessageBox.confirm(
        "您确定要删除选中的员工信息吗？",
        "警告",
        {
            confirmButtonText: '确定删除',
            cancelButtonText: '不删了',
            type: 'warning'
        }

    );

    await req.delete("/info/emp", { data: ids.value })
    getPage(page.value.current);
    ElMessage.success('删除员工成功！');

}

//改变员工状态
const changeStatus = async (id, status) => {
    await ElMessageBox.confirm(
        "您确认要" + statusMap[status].statusOpr + "部门信息吗？",
        "警告",
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }
    );

    await req.put('/info/emp/' + id + "/" + status);
    getPage(page.value.current);
    ElMessage.success(statusMap[status].statusOpr + "员工信息成功！");
}

//生成账号
const creatUser = async empId => {

    await ElMessageBox.confirm(
        "您确认要为工号为" + empId + "的员工生成账号吗？",
        "警告",
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning'
        }
    );

    await req.post('/info/emp/user', { empId });
    getPage(page.value.current);
    ElMessage.success("生成账号成功！");

}

//撤销账号
const cancelUser = async empId => {

    await ElMessageBox.confirm(
        "您确认要撤销工号为" + empId + "的员工的账号吗？",
        "警告",
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning'
        }
    );

    await req.delete('/info/emp/user', {data:{ empId }});
    getPage(page.value.current);
    ElMessage.success("撤销账号成功！");

}

//设置为所在部门领导
const setLeader = async empId=>{
    await ElMessageBox.confirm(
        "您确认要将工号为" + empId + "的员工设置为部门领导吗？",
        "警告",
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning'
        }
    );

    await req.put('/info/emp/leader', { empId });
    getPage(page.value.current);
    ElMessage.success("设置领导成功！");
}
</script>