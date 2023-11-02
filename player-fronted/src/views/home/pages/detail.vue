<template>
  <div v-loading="loading">
    <el-page-header @back="goBack" :content="videoInfo.title">
    </el-page-header>
    <el-divider></el-divider>
    <videoPlayer
        class="video-player vjs-custom-skin video"
        ref="videoPlayer"
        :options="playerOptions"
        :playsinline="true"
    ></videoPlayer>
  </div>
</template>

<script>
import 'vue-video-player/src/custom-theme.css';
import 'video.js/dist/video-js.css'
import { videoPlayer } from 'vue-video-player'
import {getVideoById} from "@/api/video";

export default {
  name: "Detail",
  components:{
    videoPlayer
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
    return {
      playerOptions,
      videoInfo,
      loading
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
  },
  mounted() {
    this.initData(this.$route.params.id)
  }
}
</script>

<style scoped>
.video{
  width: 70%;
}
</style>