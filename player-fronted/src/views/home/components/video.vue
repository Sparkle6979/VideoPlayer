<template>
  <el-card class="vedio-card" :body-style="{padding: '0px'}">
    <video-player
        class="video-player vjs-custom-skin"
        ref="videoPlayer"
        :options="playerOptions"
        :playsinline="true"
        @click.native="toDetailPage"
        @canplay="handleMountedFunc"
    ></video-player>
    <div style="padding: 14px;">
      <span>{{ info.title }}</span>
      <div class="bottom clearfix">
        <el-row :gutter="20">
          <el-col :span="12" >
            <time class="time">{{ info.createTime }}</time>
          </el-col>
          <el-col :span="6" :offset="6">
            <el-statistic :value="info.isLike ? info.likeCount+1 : info.likeCount">
              <template slot="prefix">
                <span @click="changeLike" class="like">
                  <svg-icon name="like-on" v-show="info.isLike"></svg-icon>
                  <svg-icon name="like-off" v-show="!info.isLike"></svg-icon>
                </span>
              </template>
            </el-statistic>
          </el-col>
        </el-row>
      </div>
    </div>
  </el-card>
</template>

<script>
import 'vue-video-player/src/custom-theme.css';
import 'video.js/dist/video-js.css'
import { videoPlayer } from 'vue-video-player'
import {dislikeVideo, likeVideo} from "@/api/video";

export default {
  name: "myVideo",
  components:{
    videoPlayer
  },
  props:['info'],
  data(){
    let playerOptions = {
      // videojs options
      controls:false,
      muted: true,
      autoplay: false,
      language: 'zh-CN',
      preload: 'auto',
      playbackRates: [0.5, 1.0, 1.5, 2.0],
      aspectRatio: "16:9",
      fluid:true,
      notSupportedMessage: '此视频暂无法播放，请稍后再试',
      sources: [{
        type: "video/mp4",
        src: this.info.videoUrl
      }],
      poster:this.info.coverUrl,
    }
    const isInPlay = false
    return {
      playerOptions,
      isInPlay
    }
  },
  methods:{
    mouseEnter(){
      this.$refs.videoPlayer.player.play()
    },
    mouseLeave(){
      //todo 判断视频有没有开始进入播放的状态。直接执行暂停会导致页面报错，用户不友好
      setTimeout(()=>{
        try {
          this.$refs.videoPlayer.player.pause()
        }catch (error){
          console.log(error)
        }
      },50)
    },
    toDetailPage(){
      this.$router.push({
        name:'Detail',
        params:{
          id:this.info.videoId.toString()
        }
      })
    },
    handleMountedFunc(){
      this.$refs.videoPlayer.$el.addEventListener("mouseenter",this.mouseEnter)
      this.$refs.videoPlayer.$el.addEventListener("mouseleave",this.mouseLeave)
    },
    changeLike(){
      if (!this.info.isLike) {
        likeVideo(this.info.videoId).then((res)=>{
          console.log(res)
          this.$message.success({
            message:'点赞成功',
            duration:400
          })
        }).catch(err=>err)
      }else{
        dislikeVideo(this.info.videoId).then((res)=>{
          console.log(res)
          this.$message.warning({
            message:'取消点赞',
            duration:400
          })
        }).catch(err=>err)
      }
      this.info.isLike = !this.info.isLike
    },
  },
  watch:{
    info(newValue,oldValue){
      this.playerOptions.sources[0].src = newValue.videoUrl
      this.playerOptions.poster = newValue.coverUrl
      setTimeout(()=>{
        this.$refs.videoPlayer.$el.addEventListener("mouseenter",this.mouseEnter)
      },100)
      setTimeout(()=>{
        this.$refs.videoPlayer.$el.addEventListener("mouseleave",this.mouseLeave)
      },100)
    }
  }
}
</script>

<style scoped>
.vedio-card{
  width: 450px;
  margin-top: 20px;
  background-color: #dddddd;
  cursor: pointer;
}
.bottom {
  margin-top: 13px;
  line-height: 12px;
}
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}

.like {
  cursor: pointer;
  font-size: 20px;
  display: inline-block;
}

.time {
  font-size: 15px;
  color: #999;
}

</style>