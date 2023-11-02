<template>
  <div>
    <el-row justify="space-around" :gutter="20">
        <el-col :span="6" v-for="(video,index) in videoList" :key="index" :offset="1">
        <myVideo :info="video"></myVideo>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import myVideo from "@/views/home/components/video";
export default {
  name: "All",
  components:{
    myVideo
  },
  data(){
    const videoList = [
      {
        "videoId": 1,
        "title": "test1",
        "description": "看一看有哪些值得玩的修仙游戏",
        "likeCount": 100,
        "createTime": "2023-10-27",
        like:false,
        "videoUrl":'http://s33fgajdq.hd-bkt.clouddn.com/test1.mp4'
      },
      {
        "videoId": 1,
        "title": "test1",
        "description": "看一看有哪些值得玩的修仙游戏",
        "likeCount": 100,
        "createTime": "2023-10-27",
        like:false,
        videoUrl:'http://s33fgajdq.hd-bkt.clouddn.com/test2.mp4'
      },
      {
        "videoId": 1,
        "title": "test1",
        "description": "看一看有哪些值得玩的修仙游戏",
        "likeCount": 100,
        "createTime": "2023-10-27",
        like:false,
        videoUrl:'http://s33fgajdq.hd-bkt.clouddn.com/test1.mp4'
      },
      {
        "videoId": 1,
        "title": "test1",
        "description": "看一看有哪些值得玩的修仙游戏",
        "likeCount": 100,
        "createTime": "2023-10-27",
        like:false,
        videoUrl:'http://s33fgajdq.hd-bkt.clouddn.com/test2.mp4'
      },
      {
        "videoId": 1,
        "title": "test1",
        "description": "看一看有哪些值得玩的修仙游戏",
        "likeCount": 100,
        "createTime": "2023-10-27",
        like:false,
        videoUrl:'http://s33fgajdq.hd-bkt.clouddn.com/test1.mp4'
      },
      {
        "videoId": 1,
        "title": "test1",
        "description": "看一看有哪些值得玩的修仙游戏",
        "likeCount": 100,
        "createTime": "2023-10-27",
        like:false,
        videoUrl:'http://s33fgajdq.hd-bkt.clouddn.com/test2.mp4'
      },
      {
        "videoId": 1,
        "title": "test1",
        "description": "看一看有哪些值得玩的修仙游戏",
        "likeCount": 100,
        "createTime": "2023-10-27",
        like:false,
        videoUrl:'http://s33fgajdq.hd-bkt.clouddn.com/test1.mp4'
      },
      {
        "videoId": 1,
        "title": "test1",
        "description": "看一看有哪些值得玩的修仙游戏",
        "likeCount": 100,
        "createTime": "2023-10-27",
        like:false,
        videoUrl:'http://s33fgajdq.hd-bkt.clouddn.com/test2.mp4'
      }
    ]
    const curWidth = 0;
    const curHeight = 0;
    return {
      videoList,
      curWidth,curHeight
    }
  },
  mounted() {
  }
}
</script>

<style scoped>

</style>