<template>
  <div ref="myDiv" tabindex="0" @keydown="handleKeyDown" @keydown.prevent="handleKeyDown2">
    <Swiper
        ref="mySwiper"
        :options="swiperOption">
      <SwiperSlide
          v-for="(video,index) in videos()"
          :key="index"
      >
        <div>
          <el-row :gutter="20">
            <el-col :span="17">
              <videoPlayer
                  class="video-player vjs-custom-skin"
                  :ref="`videoPlayer_${index}`"
                  :options="video.playerOptions"
                  :playsinline="true"
              ></videoPlayer>
            </el-col>
            <el-col :span="7">
              <h4>{{video.titile}} 当前：{{ index }}</h4>
              <span>{{video.description}}</span>
              <el-card style="background-color: #eeeeee;margin-top: 20px">
                <el-row :gutter="20" type="flex" align="middle">
                  <el-col :span="6">
                    <el-avatar :src="defaultUserAvatar" :size="70"></el-avatar>
                  </el-col>
                  <el-col :span="12">
                    <h4 class="author">作者</h4>
                  </el-col>
                  <el-col :span="6">
                    <div v-if="true">
                      <el-button type="danger" v-if="true" >关注</el-button>
                      <div v-else>
                        <el-popconfirm title="确认取消关注吗?" >
                          <el-button type="info" slot="reference">已关注</el-button>
                        </el-popconfirm>
                      </div>
                    </div>
                    <div v-else>
                      <el-button type="primary">我的视频</el-button>
                    </div>
                  </el-col>
                </el-row>
              </el-card>
            </el-col>
          </el-row>
        </div>
      </SwiperSlide>
      <div class="swiper-pagination" slot="pagination"></div>
    </Swiper>
  </div>
</template>

<script>
import 'vue-video-player/src/custom-theme.css';
import 'video.js/dist/video-js.css'
import { videoPlayer } from 'vue-video-player'

import { Swiper, SwiperSlide } from "vue-awesome-swiper";
import 'swiper/css/swiper.min.css';

export default {
  name: "all",
  components:{
    videoPlayer,
    Swiper,
    SwiperSlide,
  },
  data(){
    const playerOptions = {
      // videojs options
      controls:true,
      muted: true,
      autoplay: true,
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
      height: '100%'
    }

    const item = {
      "videoId": 5,
      "titile": "修仙游戏哪家强？高品质修仙游戏推荐！",
      "description": "看一看有哪些值得玩的修仙游戏",
      "likeCount": 0,
      "categoryId": 2,
      "categoryName": "游戏",
      "createTime": "2023-10-27",
      'playerOptions':'',
      "url": "http://s33fgajdq.hd-bkt.clouddn.com/%E4%BF%AE%E4%BB%99%E6%B8%B8%E6%88%8F%E5%93%AA%E5%AE%B6%E5%BC%BA%EF%BC%9F%E9%AB%98%E5%93%81%E8%B4%A8%E4%BF%AE%E4%BB%99%E6%B8%B8%E6%88%8F%E6%8E%A8%E8%8D%90%EF%BC%81.mp4"
    }
    const videos = ()=>{
      playerOptions.sources[0].src = item.url
      item.playerOptions = playerOptions
      return Array(5).fill(item)
    }
    const curIndex = 0

    const swiperOption = {
      // 设置slider容器能够同时显示的slides数量(carousel模式)
      slidesPerView: 1,
      // 设定为true时，active slide会居中，而不是默认状态下的居左(false)
      centeredSlides: false,
      // 滑动速度
      speed: 600,
      // 设定初始化时slide的索引，Swiper默认初始化时显示第一个slide
      initialSlide: 0,
      // 循环播放
      loop: false,
      // 左右箭头
      navigation: {
        prevEl: ".swiper-button-prev",
        nextEl: ".swiper-button-next",
      },
      direction: 'vertical'
    }
    const currentSwiperSlide = 0
    return {
      videos,
      curIndex,
      playerOptions,
      swiperOption,
      currentSwiperSlide
    }
  },
  methods:{
    handleKeyDown(event){
      if(event.key === "ArrowDown"){
        if (this.curIndex === this.videos().length-1){
          this.$message.warning({
            message:"没有更多的视频了",
            duration:1000
          })
          return
        }
        this.nextSlide()
      }else if (event.key === "ArrowUp"){
        if (this.curIndex === 0){
          this.$message.warning({
            message:"当前是第一个视频",
            duration:1000
          })
          return
        }
        this.prevSlide()
      }
    },
    handleKeyDown2(event){
      if(event.key === "ArrowDown"){
        event.preventDefault()
      }else if (event.key === "ArrowUp"){
        event.preventDefault()
      }
    },
    prevSlide() {
      const videoPlayer = this.$refs[`videoPlayer_${this.curIndex}`][0];
      videoPlayer.player.pause()
      this.curIndex--
      this.$refs.mySwiper.$swiper.slidePrev();
      this.handleVideoMute(this.curIndex)
    },
    nextSlide() {
      const videoPlayer = this.$refs[`videoPlayer_${this.curIndex}`][0];
      videoPlayer.player.pause()
      this.curIndex++
      this.$refs.mySwiper.$swiper.slideNext();
      this.handleVideoMute(this.curIndex)
    },
    handleVideoMute(index){
      const videoPlayer = this.$refs[`videoPlayer_${index}`][0];
      if (videoPlayer) {
        videoPlayer.player.muted(false); // 取消静音
        videoPlayer.player.play()
      }
    }
  },
  mounted() {
    // this.$nextTick(() => {
    //   setTimeout(()=>{
    //     if (this.$refs.myDiv !== document.activeElement) {
    //       document.addEventListener('keydown',(event)=>{
    //         if(event.key === "ArrowDown" || event.key === "ArrowUp"){
    //           this.$refs.myDiv.focus()
    //         }
    //       })
    //     }
    //   },100)
    // })
    let _this = this
    this.$refs.myDiv.focus()
    // document.addEventListener('keydown', function(event) {
    //   if (event.key === "ArrowDown") {
    //     _this.$refs.myDiv.focus()
    //     event.preventDefault();
    //   }
    // });

    this.$notify({
      title: '提示',
      message: '首次播放需要取消视频静音',
      duration: 2000
    });
  },
}
</script>

<style scoped>
.video{
  width: 80%;
}
</style>