<template>
    <slot></slot>
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
</template>

<script setup>
const props = defineProps({
    query:{
        type:Object,
        required:true
    },
    page:{
        type:Object,
        required:true
    },
    getPage:{
        type:Function,
        required:true
    }
});
</script>
<style scoped>
</style>