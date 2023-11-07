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
          <el-form-item label="视频" prop="videoFile">
            <el-upload
                drag
                action=""
                :auto-upload="false"
                :show-file-list="true"
                :on-change="beforeVideoUpload"
                :on-remove="handleVideoRemove"
                accept=".mp4">
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            </el-upload>
          </el-form-item>
          <el-form-item label="封面" prop="coverFile" v-show="moreForm">
            <el-upload
                drag
                :auto-upload="false"
                :show-file-list="true"
                :on-change="beforeCoverUpload"
                :on-remove="handleCoverRemove"
                action=""
                accept=".jpg,.png">
              <div v-if="videoForm.coverPath">
                <el-image :src="videoForm.coverPath" fit="fit"></el-image>
              </div>
              <div v-else>
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
              </div>
            </el-upload>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click.native="upload">立即上传</el-button>
<!--            <el-button style="margin-top: 12px;" @click="moreForm = !moreForm">{{ !moreForm ? "更多信息" : "简要信息" }}</el-button>-->
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
      videoFile:[{required:true,message:"请选择视频",trigger:'change'}],
      coverFile:[{required:true,message:"请选择封面",trigger:'change'}],
    }
    return {
      videoForm:{
        categoryId:'',
        title:'',
        description:'',
        coverFile:'',
        coverPath:'',
        videoFile:'',
      },
      categoryList:[],
      moreForm:true,
      rules,
    }
  },
  methods:{
    resetForm(){
      this.videoForm = {
        categoryId: '',
        title: '',
        description: '',
        coverFile: '',
        coverPath: '',
        videoFile: '',
      }
    },
    goBack(){
      this.$router.back()
    },
    beforeVideoUpload(file,fileList){
      this.videoForm.videoFile = file.raw
      console.log(file)
    },
    handleVideoRemove(){
      this.videoForm.videoFile = ''
    },
    beforeCoverUpload(file,fileList){
      if (window.createObjectURL != undefined) {
        this.videoForm.coverPath = window.createObjectURL(file.raw);
      } else if (window.URL != undefined) {
        this.videoForm.coverPath = window.URL.createObjectURL(file.raw);
      } else if (window.webkitURL != undefined) {
        this.videoForm.coverPath = window.webkitURL.createObjectURL(file.raw);
      }
      this.videoForm.coverFile = file.raw
    },
    handleCoverRemove(){
      this.videoForm.coverFile = ''
      this.videoForm.coverPath = ''
    },
    upload(){
      const data = new FormData()
      if (this.videoForm.title) {
        data.set("title",this.videoForm.title)
      }
      if (this.videoForm.description) {
        data.set("description",this.videoForm.description)
      }
      if (this.videoForm.videoFile) {
        data.set("videoFile",this.videoForm.videoFile)
      }
      if (this.videoForm.coverFile) {
        data.set("coverFile",this.videoForm.coverFile)
      }
      if (this.videoForm.categoryId) {
        data.set("categoryId",this.videoForm.categoryId)
      }
      uploadVideo(data).then((res)=>{
        if(res.code === 200){
          this.$message.success("上传成功！")
          this.resetForm()
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