<template>
  <div v-loading="loading">
    <el-page-header @back="goBack" :content="videoInfo.title">
    </el-page-header>
    <el-divider></el-divider>
    <el-row :gutter="30">
      <el-col :span="17">
        <videoPlayer
            class="video-player vjs-custom-skin video"
            ref="videoPlayer"
            :options="playerOptions"
            :playsinline="true"
        ></videoPlayer>
      </el-col>
      <el-col :span="7">
        <el-card style="background-color: #eeeeee">
          <el-row :gutter="20" type="flex" align="middle">
            <el-col :span="6">
              <el-avatar :src="user.url ? user.url : defaultUserAvatar" :size="70"></el-avatar>
            </el-col>
            <el-col :span="12">
              <h4 class="author" :style="{'top':description.length <= 15 ? '-12px' : '0px'}">作者</h4>
              <el-row>
                <span class="description_text">{{description}}</span>
              </el-row>
            </el-col>
            <el-col :span="6">
              <el-button type="danger">关注</el-button>
            </el-col>
          </el-row>
        </el-card>
        <el-card shadow="never" style="margin-top: 20px">
            <h4>TA的视频</h4>
            <div v-for="(item,index) in items" :key="index">
              <el-row :gutter="20" type="flex"  style="margin-bottom: 20px">
                <el-col>
                  <img :src="item.url"
                  width="225" height="112.5">
                </el-col>
                <el-col>
                  <span style="font-size: 16px;position: absolute;top: 5px">{{item.title}}</span>
                  <br>
                  <span style="font-size: 13px;position: absolute;bottom: 25px">{{item.time}}</span>
                  <br>
                  <span style="font-size: 13px;position: absolute;bottom: 5px">播放量：{{item.watch}}</span>
                </el-col>
              </el-row>
            </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="30">
      <el-col :span="17">
        <el-input v-model="commentInput" placeholder="输入评论内容" clearable>
          <template slot="prepend">评论</template>
          <el-button slot="append" icon="el-icon-s-comment" @click="submitComment"></el-button>
        </el-input>
      </el-col>
    </el-row>
    <!--  视频详细信息   -->
    <h2>{{ videoInfo.title }}</h2>
    <el-row>
      <Comment :comments="comments" @update="updateComment"></Comment>
    </el-row>
  </div>
</template>

<script>
import 'vue-video-player/src/custom-theme.css';
import 'video.js/dist/video-js.css'
import { videoPlayer } from 'vue-video-player'
import {getVideoById} from "@/api/video";
import {mapState} from "vuex";

import Comment from "@/views/home/components/comment";

export default {
  name: "Detail",
  components:{
    videoPlayer,
    Comment
  },
  data(){
    const videoInfo = ''
    const loading = false
    const playerOptions = {
      // videojs options
      controls:true,
      muted: false,
      autoplay: false,
      language: 'zh-CN',
      preload: 'auto',
      playbackRates: [0.5, 1.0, 1.5, 2.0],
      aspectRatio: "16:9",
      fluid:true,
      notSupportedMessage: '此视频暂无法播放，请稍后再试',
      sources: [{
        type: "video/mp4",
        src: ''
      }],
      poster: ''
    }
    const description = "个人简介介个人简介介人介"
    const commentInput = ''
    const item = {
      url:"http://s33fgajdq.hd-bkt.clouddn.com/%E4%BF%AE%E4%BB%99%E6%B8%B8%E6%88%8F%E5%93%AA%E5%AE%B6%E5%BC%BA%EF%BC%9F%E9%AB%98%E5%93%81%E8%B4%A8%E4%BF%AE%E4%BB%99%E6%B8%B8%E6%88%8F%E6%8E%A8%E8%8D%90%EF%BC%81.jpg",
      title:'日本小姐姐教你怎么读介绍',
      time:'2023-11-2',
      watch:1432,
    }
    return {
      playerOptions,
      videoInfo,
      loading,
      description,
      commentInput,
      items : Array(3).fill(item),
      comments:[{
        username:'Champion',
        date:'2023年11月2日   13:58:58',
        content:'今日事今日毕',
        delete:true,	//删除按钮显示
        flag:false,
        like:'',
        display:false,	//显示评论区
        numbers:2,		//点赞数
        sons:[{
          username:'张二噶',
          date:'2023年11月2日   13:58:58',
          content:'春有百花秋有月，夏有凉风冬有雪',
          delete:true,	//删除按钮是否显示
          like:1,
          display:false,
          picture:'https://fuss10.elemecdn.com/1/34/19aa98b1fcb2781c4fba33d850549jpeg.jpeg',
          to_username:'Champion',
          numbers:1,		//点赞数
        }]
      }],
    }

  },
  methods:{
    goBack(){
      this.$router.back()
    },
    initData(id){
      this.loading = true
      getVideoById(id).then((res)=>{
        this.videoInfo = res.data
        this.playerOptions.sources[0].src = res.data.videoUrl
        this.playerOptions.poster = res.data.coverUrl
        this.loading = false
      })
    },
    updateComment(value){
      console.log(value)
      this.comments = value
    },
    // 发表评论
    submitComment(){
      this.current = new Date();
      this.comments.push({
        username:this.user.username,
        date:this.current.getFullYear() + '年' + (this.current.getMonth() + 1) + '月' + this.current.getDate() + '日' + this.current.getHours() + ':' + this.current.getMinutes() + ':' + this.current.getSeconds(),
        content:this.commentInput,
        delete:true,
        flag:false,
        like:'',
        display:false,	//显示评论区
        sons:[]
      })
      this.commentInput = ''
    },
  },
  computed:{
    ...mapState(['user'])
  },
  mounted() {
    this.initData(this.$route.params.id)
  }
}
</script>

<style scoped>
.author{
  position: relative;
  margin: 0px;
}
.description_text{
  font-size: 13px;
  color: #777777;
}
</style>