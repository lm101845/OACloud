<template>
  <div style="font-size: 50px;color: pink">这是一个测试</div>
  <el-form :model="formInline">
    <el-form-item label="操作员:" prop="userId">
      <el-select v-model="formInline.userId" placeholder="请选择操作员" @change="changeOperatorId">
        <el-option v-for="item in resultList" :key=item.key></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="姓名" prop="NAME">
      <div>{{formInline.NAME}}</div>
    </el-form-item>
    <el-form-item label="联系方式" prop="DEL">
      <div>{{formInline.DEL}}</div>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  data() {
    return {
      formInline:{
        NAME:'北京科技有限公司',
        DEL:'13211223344',
        resultList: [
          {key: '01', label: '测试001', value: '001'},
          {key: '02', label: '测试002', value: '002'},
          {key: '03', label: '测试003', value: '003'}
        ]
      },
      resultList: [
        {key: '01', label: '测试001', value: '001'},
        {key: '02', label: '测试002', value: '002'},
        {key: '03', label: '测试003', value: '003'}
      ],
      tableList:[
        {NAME:"北京科技有限公司",NO:'123456',}
      ]
    }
  },
  methods:{
    changeOperatorId(){

    }
  }
}
</script>

<style scoped>

</style>