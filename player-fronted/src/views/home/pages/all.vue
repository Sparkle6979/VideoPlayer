<template>
  <div ref="myDiv" tabindex="0" @keydown="handleKeyDown" @keydown.prevent="handleKeyDown2" v-loading="loading">
    <Swiper
        v-if="playerVideoList.length"
        ref="mySwiper"
        :options="swiperOption"
        @slideChange="handleSlideChange">
      <SwiperSlide
          v-for="(video,index) in playerVideoList"
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
              <h4>{{video.title}}</h4>
              <span>{{video.description}}</span>
              <el-card style="background-color: #eeeeee;margin-top: 20px">
                <el-row :gutter="20" type="flex" align="middle">
                  <el-col :span="6">
                    <el-avatar :src="video.creater.avatarPath ? video.creater.avatarPath : defaultUserAvatar" :size="70"></el-avatar>
                  </el-col>
                  <el-col :span="12">
                    <h4 class="author">{{ video.creater.username }}</h4>
                  </el-col>
                  <el-col :span="6">
                    <div v-if="video.creater.username != user.username">
                      <el-button type="danger" v-if="!video.userFollows.includes(video.creater.id)" @click.native="follow(video)">关注</el-button>
                      <div v-else>
                        <el-popconfirm title="确认取消关注吗?" @confirm="unFollow(video)">
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
    <el-empty description="没有可以播放的视频" v-else></el-empty>
  </div>
</template>

<script>
import 'vue-video-player/src/custom-theme.css';
import 'video.js/dist/video-js.css'
import { videoPlayer } from 'vue-video-player'

import { Swiper, SwiperSlide } from "vue-awesome-swiper";
import 'swiper/css/swiper.min.css';
import {getPlayerVideo, getVideoById} from "@/api/video";
import {followListById, followUser, getUserInfo, unFollowUser} from "@/api/user";
import {mapState} from "vuex";

export default {
  name: "all",
  components:{
    videoPlayer,
    Swiper,
    SwiperSlide,
  },
  data(){
    let curPage = 1;
    const pageSize = 10;
    const playerVideoList = []
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
      curPage,
      pageSize,
      playerVideoList,
      curIndex,
      swiperOption,
      currentSwiperSlide,
      loading:false,
    }
  },
  computed:{
    ...mapState(['user']),
  },
  methods:{
    getPlayerVideoList(){
      this.loading = true
      getPlayerVideo(this.curPage++,this.pageSize).then((res)=>{
        const itemPromises = res.data.records.map(async (item) => {
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
          playerOptions.sources[0].src = item.videoUrl
          item.playerOptions = playerOptions

          const videoData = await getVideoById(item.id);
          const userData = await getUserInfo(videoData.data.createrId);
          item.creater = userData.data;

          try {
            const followListRes = await followListById(this.user.id, 1, 100);
            item.userFollows = followListRes.data.records;
          } catch (err) {
            console.log("followListById",err);
          }

          return item;
        });

        Promise.all(itemPromises)
            .then((items) => {
              this.playerVideoList.push(...items);
              this.loading = false
            })
            .catch((err) => {
              console.error(err);
            });
      })
    },
    handleKeyDown(event){
      if(event.key === "ArrowDown"){
        if (this.curIndex === this.playerVideoList.length-1){
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
    },
    handleSlideChange(){
      const activeIndex = this.$refs.mySwiper.swiperInstance.activeIndex;
      console.log(`当前活动 slide 的索引是: ${activeIndex}`);
      // const length = this.playerVideoList.length
      // if(activeIndex/length > 0.6) {
      //   console.log("加载视频")
      //   this.getPlayerVideoList()
      // }
    },
    follow(video){
      followUser(video.creater.id).then((res)=>{
        // console.log(res)
        if (res.code === 200) {
          this.$message.success(`关注${video.creater.username}成功！`)
        }
        followListById(this.user.id).then((res) => {
          console.log(res)
          video.userFollows = res.data.followingIds
        }).catch(err => {
          console.log("showFollow",err)
        })
      }).catch(err=>{
        console.log("follow",err)
      })
    },
    unFollow(video){
      unFollowUser(video.creater.id).then((res)=>{
        console.log(res)
        if (res.code === 200) {
          this.$message.success("取关成功！")
        }
        followListById(this.user.id).then((res) => {
          console.log(res)
          video.userFollows = res.data.followingIds
        }).catch(err => {
          console.log("showFollow",err)
        })
      }).catch(err=>{
        console.log("follow",err)
      })
    },
  },
  mounted() {
    this.getPlayerVideoList()
    this.$refs.myDiv.focus()
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