<template>
  <div>
    <el-page-header @back="goBack" :content="$route.meta.title">
    </el-page-header>
    <div style="margin: 20px">
      <el-form style="margin: 20px">
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
        <el-button type="primary" >立即上传</el-button>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "upload",
  data(){
    return {

    }
  },
  methods:{
    goBack(){
      this.$router.back()
    }
  }
}
</script>

<style scoped>

</style>