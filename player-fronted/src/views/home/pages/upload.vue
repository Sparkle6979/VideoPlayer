<template>
  <div>
    <el-page-header @back="goBack" :content="$route.meta.title">
    </el-page-header>
    <div style="margin: 20px;">
      <div style="width: 50%">
        <el-form :ref="videoForm" :model="videoForm" :rules="rules" label-position="left" style="margin: 20px;" label-width="120px">
          <el-form-item label="分类" prop="categoryId">
            <el-select placeholder="请选择分类" v-model="videoForm.categoryId">
              <el-option v-for="c in categoryList" :label="c.categoryName" :value="c.categoryId" :key="c.categoryId"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="视频标题" prop="title">
            <el-input v-model="videoForm.title"></el-input>
          </el-form-item>
          <el-form-item label="视频简介" prop="description" v-show="moreForm">
            <el-input type="textarea" v-model="videoForm.description"></el-input>
          </el-form-item>
          <el-form-item label="视频" prop="videoUrl">
            <el-upload
                drag
                action=""
                :auto-upload="false"
                :on-change="beforeVideoUpload"
                accept=".mp4">
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            </el-upload>
          </el-form-item>
          <el-form-item label="封面" prop="coverUrl" v-show="moreForm">
            <el-upload
                drag
                :auto-upload="false"
                :on-change="beforeCoverUpload"
                action=""
                accept=".jpg,.png">
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            </el-upload>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click.native="upload">立即上传</el-button>
            <el-button style="margin-top: 12px;" @click="moreForm = !moreForm">{{ !moreForm ? "更多信息" : "简要信息" }}</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import {getCategoryInfoList} from "@/api/category";
import {uploadVideo} from "@/api/video";

export default {
  name: "upload",
  data(){
    const rules = {
      categoryId:[{required:true,message:"请选择分类",trigger: 'change'}],
      title:[{required:true,message:"请输入视频标题",trigger:'blur'}],
      description:[
        { min: 0, max: 150, message: '长度在 0 到 150 个字符', trigger: 'blur' }
      ],
      videoUrl:[{required:true,message:"请选择视频",trigger:'change'}],
    }
    return {
      videoForm:{
        categoryId:'',
        title:'',
        description:'',
        coverUrl:'',
        videoUrl:'',
      },
      categoryList:[],
      moreForm:false,
      rules,
    }
  },
  methods:{
    goBack(){
      this.$router.back()
    },
    beforeVideoUpload(event){
      if (window.createObjectURL != undefined) {
        this.videoForm.videoUrl = window.createObjectURL(event.raw);
      } else if (window.URL != undefined) {
        this.videoForm.videoUrl = window.URL.createObjectURL(event.raw);
      } else if (window.webkitURL != undefined) {
        this.videoForm.videoUrl = window.webkitURL.createObjectURL(event.raw);
      }

      this.videoForm.videoUrl = URL.createObjectURL(event.raw)
    },
    beforeCoverUpload(event){
      if (window.createObjectURL != undefined) {
        this.videoForm.coverUrl = window.createObjectURL(event.raw);
      } else if (window.URL != undefined) {
        this.videoForm.coverUrl = window.URL.createObjectURL(event.raw);
      } else if (window.webkitURL != undefined) {
        this.videoForm.coverUrl = window.webkitURL.createObjectURL(event.raw);
      }
    },
    upload(){
      uploadVideo(this.videoForm).then((res)=>{
        if(res.code === 200){
          this.$message.success("上传成功！")
          this.$refs["videoForm"].resetFields();
        }
      }).catch(err=>{
        console.log(err)
        this.$message.error("上传失败！")
      })
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