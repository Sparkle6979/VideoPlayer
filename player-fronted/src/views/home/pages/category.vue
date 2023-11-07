<template>
  <div v-loading="loading" element-loading-text="拼命加载中"
       element-loading-spinner="el-icon-loading">
    <el-page-header @back="goBack" :content="categoryName">
    </el-page-header>
    <div>
      <el-row justify="space-around" :gutter="20">
        <el-col :span="6" v-for="(video,index) in videoList" :key="index" :offset="1">
          <myVideo :info="video"></myVideo>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import myVideo from "@/views/home/components/video";
import {getVideoListByCId} from "@/api/video";
import {getCategoryInfoById} from "@/api/category";

export default {
  name: "category",
  components:{
    myVideo
  },
  data(){
    const videoList = ''
    const loading = false
    const categoryName = ''
    return {
      videoList,
      loading,
      categoryName
    }
  },
  methods:{
    initData(id){
      this.loading = true
      getCategoryInfoById(id).then((res)=>{
        this.categoryName = res.data.categoryName
      })
      getVideoListByCId(id).then((res) => {
        this.videoList = res.data
        console.log("getVideoListByCId",res)
        this.loading = false
      })
    },
    goBack(){
      this.$router.back()
    }
  },
  beforeRouteUpdate(to,from,next){
    this.initData(to.params.id)
    next()
  },
  mounted() {
    this.initData(this.$route.params.id)
  }
}
</script>

<style scoped>

</style>