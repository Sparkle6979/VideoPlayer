<template>
  <div ref="mydiv" tabindex="0" @keydown="handleKeyDown" @keydown.prevent="handleKeyDown2">
    <Swiper
        ref="mySwiper"
        :options="swiperOption">
      <SwiperSlide
          v-for="(video, index) in videos"
          :key="index">
        <h2>{{video.titile}} 当前：{{ index }}</h2>
        <p>{{video.description}}</p>
        <videoPlayer
            class="video-player vjs-custom-skin video"
            ref="videoPlayer"
            :options="playerOptions"
            :playsinline="true"
        ></videoPlayer>
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
  name: "discover",
  components:{
    videoPlayer,
    Swiper,
    SwiperSlide,
  },
  data(){
    const item = {
      "videoId": 5,
      "titile": "修仙游戏哪家强？高品质修仙游戏推荐！",
      "description": "看一看有哪些值得玩的修仙游戏",
      "likeCount": 0,
      "categoryId": 2,
      "categoryName": "游戏",
      "createTime": "2023-10-27",
      "url": "http://s33fgajdq.hd-bkt.clouddn.com/%E4%BF%AE%E4%BB%99%E6%B8%B8%E6%88%8F%E5%93%AA%E5%AE%B6%E5%BC%BA%EF%BC%9F%E9%AB%98%E5%93%81%E8%B4%A8%E4%BF%AE%E4%BB%99%E6%B8%B8%E6%88%8F%E6%8E%A8%E8%8D%90%EF%BC%81.mp4"
    }
    const videos = Array(20).fill(item)
    const curIndex = 0

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
        src: videos[curIndex].url
      }],
      height: '100%'
    }

    const swiperOption = {
      // 设置slider容器能够同时显示的slides数量(carousel模式)
      slidesPerView: 1,
      // 设定为true时，active slide会居中，而不是默认状态下的居左(false)
      centeredSlides: true,
      // 滑动速度
      speed: 1500,
      // 在slide之间设置距离（单位px）
      spaceBetween: 10,
      // 设定初始化时slide的索引，Swiper默认初始化时显示第一个slide
      initialSlide: 0,
      // 循环播放
      loop: false,
      // 左右箭头
      navigation: {
        prevEl: ".swiper-button-prev",
        nextEl: ".swiper-button-next",
      },
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
      console.log("1111")
      if(event.key === "ArrowDown"){
        this.nextSlide()
      }else if (event.key === "ArrowUp"){
        this.prevSlide()
      }
    },
    handleKeyDown2(event){
      if(event.key === "ArrowDown"){
        event.key.preventDefault()
      }else if (event.key === "ArrowUp"){
        event.key.preventDefault()
      }
    },
    prevSlide() {
      console.log(this.$refs.mySwiper)
      this.$refs.mySwiper.$swiper.slidePrev();
    },
    nextSlide() {
      this.$refs.mySwiper.$swiper.slideNext();
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.$refs.mydiv.focus()
    });
    document.addEventListener('keydown',()=>{
      this.$refs.mydiv.focus()
    })
  }
}
</script>

<style scoped>
.video{
  width: 80%;
}
</style>