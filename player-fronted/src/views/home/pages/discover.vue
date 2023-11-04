<template>
  <div v-loading="this.videoList.loading">
    <el-card>
      <div slot="header" class="clearfix">
        <span>按视频名字匹配</span>
      </div>
      <div>
        <el-row justify="space-around" :gutter="20">
          <div v-if="this.videoList.hasTitle">
            <el-col :span="6" v-for="(video,index) in videoList.byTitle" :key="index" :offset="1">
              <myVideo :info="video"></myVideo>
            </el-col>
          </div>
          <el-empty v-else description="没有符合条件的数据"></el-empty>
        </el-row>
      </div>
    </el-card>
    <el-card>
      <div slot="header" class="clearfix">
        <span>按视频分类匹配</span>
      </div>
      <div>
        <el-row justify="space-around" :gutter="20">
          <div v-if="this.videoList.hasCategoryName">
            <el-col :span="6" v-for="(video,index) in videoList.byCategoryName" :key="index" :offset="1">
              <myVideo :info="video"></myVideo>
            </el-col>
          </div>
          <el-empty v-else description="没有符合条件的数据"></el-empty>
        </el-row>
      </div>
    </el-card>
    <el-card>
      <div slot="header" class="clearfix">
        <span>按视频简介匹配</span>
      </div>
      <div>
        <el-row justify="space-around" :gutter="20">
          <div v-if="this.videoList.hasDescription">
            <el-col :span="6" v-for="(video,index) in videoList.byDescription" :key="index" :offset="1">
              <myVideo :info="video"></myVideo>
            </el-col>
          </div>
          <el-empty v-else description="没有符合条件的数据"></el-empty>
        </el-row>
      </div>
    </el-card>
  </div>
</template>

<script>
import myVideo from "@/views/home/components/video";
import {getVideoBySearch} from "@/api/video";

export default {
  name: "discover",
  components:{
    myVideo
  },
  data(){
    const videoList = {
      loading:false,
      byTitle:[],
      hasTitle:false,
      byCategoryName:[],
      hasCategoryName:false,
      byDescription:[],
      hasDescription:false
    }
    return {
      keyword:'',
      videoList,
    }
  },
  methods:{
    search(){
      this.videoList = {
        loading:false,
        byTitle:[],
        hasTitle:false,
        byCategoryName:[],
        hasCategoryName:false,
        byDescription:[],
        hasDescription:false
      }
      this.videoList.loading = true
      getVideoBySearch(this.keyword).then((res)=>{
        res.data.forEach((item)=>{
          if (item.findTitle) {
            if (!this.videoList.hasTitle) {
              this.videoList.hasTitle = true
            }
            this.videoList.byTitle.push(item)
          }else if(item.findCategoryName){
            if (!this.videoList.hasCategoryName) {
              this.videoList.hasCategoryName = true
            }
            this.videoList.byCategoryName.push(item)
          }else{
            if (!this.videoList.hasDescription) {
              this.videoList.hasDescription = true
            }
            this.videoList.byDescription.push(item)
          }
        })
      }).finally(()=>{
        this.videoList.loading = false
      })
    }
  },
  watch:{
    "$route.query.kw":{
      handler(){
        this.keyword = this.$route.query.kw
        this.search()
      }
    }
  },
  mounted() {
    this.keyword = this.$route.query.kw
    this.search()
  }
}
</script>

<style scoped>
</style>