<template>
  <div>
    <el-page-header @back="goBack" :content="$route.meta.title">
    </el-page-header>
    <div style="margin: 20px;">
      <el-steps :active="active" finish-status="success">
        <el-step title="视频信息"></el-step>
        <el-step title="视频上传"></el-step>
        <el-step title="视频审核"></el-step>
        <el-step title="完成上传"></el-step>
      </el-steps>
      <div style="width: 50%">
        <el-form :model="videoForm" label-position="left" style="margin: 20px;" label-width="120px">
          <el-form-item label="分类" prop="categoryId">
            <el-select placeholder="请选择分类" v-model="videoForm.categoryId">
              <el-option v-for="c in categoryList" :label="c.categoryName" :value="c.categoryId" :key="c.categoryId"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="视频标题" prop="title">
            <el-input :value="videoForm.title"></el-input>
          </el-form-item>
          <el-form-item label="视频简介" prop="description">
            <el-input type="textarea" :value="videoForm.description"></el-input>
          </el-form-item>
          <el-form-item>
            <el-upload
                drag
                action="https://jsonplaceholder.typicode.com/posts/"
                accept=".mp4">
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
              <div class="el-upload__tip" slot="tip">只能上传视频文件</div>
            </el-upload>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" >立即上传</el-button>
            <el-button style="margin-top: 12px;" @click="next">下一步</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import {getCategoryInfoList} from "@/api/category";

export default {
  name: "upload",
  data(){
    return {
      active: 0,
      videoForm:{
        categoryId:'',
        title:'',
        description:'',
        file:'',
      },
      categoryList:[],
    }
  },
  methods:{
    goBack(){
      this.$router.back()
    },
    next() {
      if (this.active++ > 3) this.active = 0;
    }
  },
  mounted() {
    getCategoryInfoList().then((res) => {
      this.categoryList = res.data
    });
  }
}
</script>

<style scoped>

</style>