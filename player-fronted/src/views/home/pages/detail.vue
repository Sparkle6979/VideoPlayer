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
              <el-avatar :src="creater.avatarPath ? creater.avatarPath : defaultUserAvatar" :size="70"></el-avatar>
            </el-col>
            <el-col :span="12">
              <h4 class="author">{{ creater.username }}</h4>
            </el-col>
            <el-col :span="6">
              <div v-if="creater.username != user.username">
                <el-button type="danger" v-if="showFollow" @click.native="follow">关注</el-button>
                <div v-else>
                  <el-popconfirm title="确认取消关注吗?" @confirm="unFollow">
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
        <el-card shadow="never" style="margin-top: 20px">
            <h4>TA的视频</h4>
            <div v-for="(item,index) in createrWorks" :key="index" style="cursor: pointer" @click="toDetailPage(item.videoId)">
              <el-row :gutter="20" type="flex"  style="margin-bottom: 20px">
                <el-col>
                  <img :src="item.coverUrl"
                  width="225" height="112.5">
                </el-col>
                <el-col>
                  <span style="font-size: 16px;position: absolute;top: 5px">{{item.title}}</span>
                  <br>
                  <span style="font-size: 13px;position: absolute;bottom: 25px">{{item.createTime}}</span>
                  <br>
                  <span style="font-size: 13px;position: absolute;bottom: 5px">点赞量：{{item.likeCount}}</span>
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
    <el-button type="danger" @click="changeLike(videoInfo)">{{ videoInfo.isLike ? "取消点赞" : "点赞" }}</el-button>
    <el-button type="warning" @click="isCollected ? unCollected() : dialogVisible = true;">{{ isCollected ? "取消收藏" : "收藏" }}</el-button>

    <el-dialog
        title="选择收藏夹"
        :visible.sync="dialogVisible"
        width="30%">
      <el-checkbox-group
          v-model="checkedDirs">
        <el-checkbox v-for="dir in collectionData" :key="dir.id" :label="dir.id">{{dir.collectionName}}</el-checkbox>>
      </el-checkbox-group>

      <span slot="footer" class="dialog-footer">
              <el-button @click="dialogVisible = false">取 消</el-button>
              <el-button type="primary" @click="collection(videoInfo);dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>

    <el-row>
      <Comment :comments="commentList" @update="updateComment"></Comment>
    </el-row>
  </div>
</template>

<script>
import 'vue-video-player/src/custom-theme.css';
import 'video.js/dist/video-js.css'
import { videoPlayer } from 'vue-video-player'
import {dislikeVideo, getVideoById, getVideoComment, likeVideo} from "@/api/video";
import {mapState} from "vuex";

import Comment from "@/views/home/components/comment";
import {
  commentToComment,
  commentToVideo, doCollection,
  followListById,
  followUser, getCollection,
  getUserInfo, getVideoByCollectionId, undoCollection,
  unFollowUser
} from "@/api/user";

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
      poster: ''
    }
    const commentInput = ''
    return {
      checkedDirs:[],
      dialogVisible:false,
      playerOptions,
      videoInfo,
      loading,
      commentInput,
      creater:'',
      userFollows:[],
      createrWorks : [],
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
      commentList:[],
      collectionData:[],
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
        this.createrWorks = res.data.createrWorks
        this.loading = false
        getUserInfo(res.data.createrId).then((user_res)=>{
          this.creater = user_res.data
          if (this.isLogin) {
            followListById(this.user.id,1,100).then((res) => {
              this.userFollows = res.data.records
            }).catch(err => {
              console.log("followListById",err)
            })
          }else{
            this.userFollows = []
          }
        })
      })
      getVideoComment(id,0,100).then((res)=>{
        // console.log(res)
        this.commentList = res.data.records
      })
      if (this.isLogin) {
        this.getCollectionDir()
      }
    },
    updateComment(content,entityId,targetId){
      commentToComment(entityId,"comment",content,targetId).then((res)=>{
        // console.log(res)
        if(res.code === 200){
          this.$message.success("评论成功！")
        }
        if(res.code === 401){
          this.$router.push('/login')
          return
        }
        getVideoComment(this.videoInfo.videoId,0,100).then((res)=>{
          this.commentList = res.data.records
        })
      }).catch(err=> {
        console.log(err)
      })
    },
    // 发表评论
    submitComment(){
      commentToVideo(this.videoInfo.videoId,"video",this.commentInput).then((res)=>{
        if (res.code === 200) {
          this.$message.success(`评论成功！`)
          getVideoComment(this.videoInfo.videoId,0,100).then((res)=>{
            this.commentList = res.data.records
          })
        }
      }).catch(err=>err)
      this.commentInput = ''
    },
    toDetailPage(videoId){
      this.$router.push({
        name:'Detail',
        params:{
          id:videoId
        }
      }).catch(err=>{
        this.$message.warning({
          message:"已经到当前页面了",
          duration:800,
        })
      })
    },
    follow(){
      followUser(this.creater.id).then((res)=>{
        console.log(res)
        if (res.code === 200) {
          this.$message.success(`关注${this.creater.username}成功！`)
        }
        followListById(this.user.id).then((res) => {
          console.log(res)
          this.userFollows = res.data.followingIds
        }).catch(err => {
          console.log("showFollow",err)
        })
      }).catch(err=>{
        console.log("follow",err)
      })
    },
    unFollow(){
      unFollowUser(this.creater.id).then((res)=>{
        console.log(res)
        if (res.code === 200) {
          this.$message.success("取关成功！")
        }
        followListById(this.user.id).then((res) => {
          console.log(res)
          this.userFollows = res.data.followingIds
        }).catch(err => {
          console.log("showFollow",err)
        })
      }).catch(err=>{
        console.log("follow",err)
      })
    },
    changeLike(videoInfo){
      if (!videoInfo.isLike) {
        likeVideo(videoInfo.videoId).then((res)=>{
          if(res.code === 200) {
            this.$message.success({
              message:'点赞成功',
              duration:400
            })
            videoInfo.isLike = !videoInfo.isLike
          }
        }).catch(err=>err)
      }else{
        dislikeVideo(videoInfo.videoId).then((res)=>{
          if(res.code === 200) {
            this.$message.warning({
              message:'取消点赞',
              duration:400
            })
            videoInfo.isLike = !videoInfo.isLike
          }
        }).catch(err=>err)
      }
      this.info.isLike = !this.info.isLike
    },
    collection(videoInfo){
      console.log(this.checkedDirs)
      this.checkedDirs.forEach((id)=>{
        doCollection(id,videoInfo.videoId).then((res)=>{
          if(res.code === 200) {
            this.$message.success({
              message:"收藏成功",
              duration:500
            })
          }
        })
      })
    },
    getCollectionDir(){
      this.collectionData = []
      getCollection(1,100).then((res)=>{
        res.data.records.forEach((item)=>{
          getVideoByCollectionId(item.id,1,100).then((res)=>{
            item.videos = res.data.records
            this.collectionData.push(item)
          })
        })
      })
    },
    unCollected(){
      let foundId;
      this.collectionData.forEach((dir)=>{
        dir.videos.forEach((video)=>{
          console.log(video.videoId,this.videoInfo.videoId)
          if (video.videoId === this.videoInfo.videoId) {
            foundId = dir.id
          }
        })
      })
      undoCollection(foundId,this.videoInfo.videoId).then((res)=>{
        if(res.code === 200) {
          this.$message.success({
            message:"取消收藏成功",
            duration:500
          })
        }
        this.getCollectionDir()
      })
    }
  },
  computed:{
    ...mapState(['user']),
    showFollow(){
      return !this.userFollows.includes(this.creater.id)
    },
    isCollected(){
      let found = false
      this.collectionData.forEach((dir)=>{
        dir.videos.forEach((video)=>{
          console.log(video.videoId,this.videoInfo.videoId)
          if (video.videoId === this.videoInfo.videoId) {
            found = true
          }
        })
      })
      return found
    },
  },
  mounted() {
    this.initData(this.$route.params.id)
  },
  watch:{
    "$route.params.id":{
      handler(){
        this.initData(this.$route.params.id)
      }
    }
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