<template>
  <div>
    <el-page-header @back="goBack" :content="$route.meta.title">
    </el-page-header>
    <div style="margin: 20px">
      <el-tabs v-model="activeIndex" type="card" :key="activeIndex">
        <el-tab-pane label="我的喜欢" name="1">
          <div v-loading="this.loading.likeVideo"
               element-loading-text="拼命加载中"
               element-loading-spinner="el-icon-loading" style="width: 100%">
            <el-row justify="space-around" :gutter="20">
              <el-col :span="6" v-for="(video,index) in videoList" :key="index" :offset="1">
                <myVideo :info="video"></myVideo>
              </el-col>
            </el-row>
            <el-row justify="space-around" :gutter="20">
              <el-col>
                <el-button type="primary" @click="getLikeVideoList" v-if="!allLikeLoaded">更多视频</el-button>
                <p v-else>没有数据了</p>
              </el-col>
            </el-row>
          </div>
        </el-tab-pane>
        <el-tab-pane label="账号设置" name="2">
          <div>
            <el-form :model="form_2" ref="form_2" label-width="120px" style="width: 30%">
              <el-form-item label="头像" prop="avatarPath">
                <el-upload
                    :disabled="!isInput"
                    :show-file-list="false"
                    action=""
                    :auto-upload="false"
                    accept=".jpg,.png"
                    :on-change="picturePreview">
                  <el-avatar :src="form_2.avatarPath ? form_2.avatarPath : defaultUserAvatar" :size="150" :fit="'cover'" ></el-avatar>
                </el-upload>
              </el-form-item>
              <el-form-item label="账号" prop="username">
                <el-input
                    v-model.trim="form_2.username"
                    :disabled="!isInput"
                ></el-input>
              </el-form-item>
              <el-form-item label="新密码" prop="password">
                <el-input
                    v-model.trim="form_2.password"
                    :disabled="!isInput"
                ></el-input>
              </el-form-item>

              <el-form-item>
                <el-button type="primary" v-if="isInput" @click="update">确定修改</el-button>
                <el-button type="primary" v-if="isInput" @click="cancel">取消</el-button>
                <el-button v-if="isInput" @click="resetForm('form_2')">重置</el-button>
                <el-button v-else @click="isInput = !isInput">编辑</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-tab-pane>
        <el-tab-pane label="消息中心" name="3">
          <div>
            <el-tabs v-model="subActiveIndex" tab-position="left" type="border-card" style="height: 650px;overflow-y: auto;">
              <el-tab-pane label="赞" name="31">
                <el-button type="primary" @click="allRead(31)">一键已读</el-button>
                <div v-loading="this.loading.likeMessage"
                     element-loading-text="拼命加载中"
                     element-loading-spinner="el-icon-loading"  >
                  <MyMessage v-if="likeMessageList.length" :messageList="likeMessageList" :type="1"></MyMessage>
                  <el-empty description="没有数据" v-else></el-empty>
                </div>
              </el-tab-pane>
              <el-tab-pane label="评论" name="32">
                <el-button type="primary" @click="allRead(32)">一键已读</el-button>
                <div v-loading="this.loading.commentMessage"
                     element-loading-text="拼命加载中"
                     element-loading-spinner="el-icon-loading">
                  <MyMessage v-if="commentMessageList.length" :messageList="commentMessageList" :type="3"></MyMessage>
                  <el-empty description="没有数据" v-else></el-empty>
                </div>
              </el-tab-pane>
              <el-tab-pane label="关注" name="33">
                <el-button type="primary" @click="allRead(33)">一键已读</el-button>
                <div v-loading="this.loading.followMessage"
                     element-loading-text="拼命加载中"
                     element-loading-spinner="el-icon-loading">
                  <MyMessage v-if="followMessageList.length" :messageList="followMessageList" :type="2"></MyMessage>
                  <el-empty description="没有数据" v-else></el-empty>
                </div>
              </el-tab-pane>
            </el-tabs>
          </div>
        </el-tab-pane>
        <el-tab-pane label="我的视频" name="4">
          <div v-loading="this.loading.myVideo"
               element-loading-text="拼命加载中"
               element-loading-spinner="el-icon-loading" style="width: 100%">
            <el-row justify="space-around" :gutter="20" v-if="myVideoList.length">
              <el-col :span="6" v-for="(video,index) in myVideoList" :key="index" :offset="1">
                <myVideo :info="video"></myVideo>
              </el-col>
            </el-row>
            <el-empty v-else description="没有符合条件的数据">
              <el-button type="primary" @click="$router.push('/home/upload')">上传视频</el-button>
            </el-empty>
          </div>
        </el-tab-pane>
        <el-tab-pane label="我的收藏" name="5">
          <div v-loading="this.loading.myFavorite"
               element-loading-text="拼命加载中"
               element-loading-spinner="el-icon-loading" style="width: 100%">
            <div v-if="collectionData.length">
              <el-button type="primary" @click="dialogVisible = true">创建收藏夹</el-button>
              <div>
                <el-row justify="space-around" :gutter="20" class="card-row">
                  <el-card class="box-card" v-for="(collection,index) in collectionData" :key="index" style="width: auto;margin: 20px">
                    <div slot="header" class="clearfix">
                      <span>{{ collection.collectionName }}</span>
                      <el-button style="float: right; padding: 3px 0" type="text" @click="removeCollection(collection.id)">删除</el-button>
                    </div>
                    <div v-if="collection.videos.length">
                      <div v-for="(v,index) in collection.videos" :key="index" class="text item">
                        <el-button type="text" @click="toDetailPage(v.videoId)">{{v.title}}</el-button>
                      </div>
                    </div>
                    <el-empty v-else description="暂无数据"></el-empty>
                  </el-card>
                </el-row>
              </div>
            </div>
            <el-empty description="暂无收藏" v-else>
              <el-button type="primary" @click="dialogVisible = true">创建收藏夹</el-button>
            </el-empty>
          </div>

          <el-dialog
              title="创建收藏夹"
              :visible.sync="dialogVisible"
              width="30%">
            <el-form :model="form_5" ref="form_5" :rules="rules">
              <el-form-item label="收藏夹名字" prop="collectionName">
                <el-input
                    v-model.trim="form_5.collectionName"
                ></el-input>
              </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
              <el-button @click="resetForm('form_5');dialogVisible = false">取 消</el-button>
              <el-button type="primary" @click="createCollectionDir('form_5')">确 定</el-button>
            </span>
          </el-dialog>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import myVideo from "@/views/home/components/video";
import MyMessage from '@/views/home/components/message';
import {mapGetters, mapMutations, mapState} from "vuex";
import {
  createCollection,
  deleteCollection,
  getCollection,
  getUserInfo,
  getUserLike,
  getUserVideo, getVideoByCollectionId,
  updateUserInfo
} from "@/api/user";
import {getVideoById} from "@/api/video";
import md5 from "js-md5";
import {getComment, getFollow, getLike, readMessage} from "@/api/notice";

export default {
  name: "user",
  components:{
    myVideo,MyMessage
  },
  computed:{
    ...mapState(['user'])
  },
  data(){
    const activeIndex = "1"
    const subActiveIndex = "1"
    const isInput = false
    const form_2 = {
      avatarFile:'',
      avatarPath:this.avatarPath(),
      username:this.username(),
      password:""
    }
    const form_5 = {
      collectionName :''
    }
    const loading = {
      likeVideo:false,
      myVideo:false,
      myFavorite:false,
      commentMessage:false,
      likeMessage:false,
      followMessage:false,
    }
    const myVideoList = []
    const videoList = [] // 点赞视频列表
    const curLikePage = 1
    const pageSize = 6
    const allLikeLoaded = false
    const likeMessageList = []
    const commentMessageList = []
    const followMessageList = []
    const collectionData = []
    const defaultProps =  {
      children: 'children',
      label: 'label'
    }
    const rules = {
      collectionName:[{required:true,message:"请输入收藏夹名字",trigger:'blur'}],
    }
    return {
      activeIndex,
      subActiveIndex,
      isInput,
      form_2,
      form_5,
      videoList,
      curLikePage,
      pageSize,
      allLikeLoaded,
      likeMessageList,
      followMessageList,
      commentMessageList,
      loading,
      myVideoList,
      collectionData,
      defaultProps,
      dialogVisible:false,
      rules,
    }
  },
  methods:{
    ...mapGetters({avatarPath:'getUserAvatarPath',username:'getUserUsername'}),
    ...mapMutations({updateUser:"UPDATE_USER"}),
    goBack(){
      this.$router.back()
    },
    getLikeVideoList() {
      this.loading.likeVideo = true
      getUserLike(this.curLikePage++, this.pageSize).then((res) => {
        if (res.data.records.length === 0) {
          this.loading.likeVideo = false
          return
        }
        const videoPromises = res.data.records.map((id) => {
          return getVideoById(id).then((res) => {
            this.videoList.push(res.data);
          });
        });
        Promise.all(videoPromises)
            .then(() => {
              this.loading.likeVideo = false;
              if (this.videoList.length === res.data.total) {
                this.allLikeLoaded = true;
              }
            })
            .catch((err) => {
              console.log(err);
            });
      }).catch((err) => {
        console.log(err)
      })
    },
    getMyVideoList(){
      this.myVideoList = []
      this.loading.myVideo = true
      getUserVideo().then((res)=>{
        let count = 0
        if (res.data.videoIds.length === 0){
          this.loading.myVideo = false
          return
        }
        res.data.videoIds.forEach((id,index,arr)=>{
          getVideoById(id).then((res)=>{
            // console.log(res)
            this.myVideoList.push(res.data)
          }).finally(()=>{
            count++
            if (count === arr.length) {
              this.loading.myVideo = false
            }
          })
        })
      })
    },
    picturePreview(file,fileList){
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
        return
      }
      if (window.createObjectURL != undefined) {
        this.form_2.avatarPath = window.createObjectURL(file.raw);
      } else if (window.URL != undefined) {
        this.form_2.avatarPath = window.URL.createObjectURL(file.raw);
      } else if (window.webkitURL != undefined) {
        this.form_2.avatarPath = window.webkitURL.createObjectURL(file.raw);
      }
      this.form_2.avatarFile = file.raw
      if (fileList.length > 1){
        fileList.splice(0,1)
      }
    },
    update(){
      const data = new FormData()
      let reloadUserInfo = false
      if(this.form_2.avatarFile){
        data.set("avatarFile",this.form_2.avatarFile)
        console.log(this.form_2.avatarFile)
        reloadUserInfo = true
      }
      if (this.form_2.username !== this.username()){
        data.set("username",this.form_2.username)
        reloadUserInfo = true
      }
      if(this.form_2.password){
        data.set("password",md5(this.form_2.password))
      }
      for(let pair of data.entries()) {
        console.log(pair[0]+ ', '+ pair[1]);
      }
      updateUserInfo(data).then((res)=>{
        console.log("updateUserInfo",res)
        if (reloadUserInfo) {
          getUserInfo(this.user.id).then((res)=>{
            console.log("getUserInfo",res)
            this.updateUser(res.data)
            this.$message.success('更新用户信息成功！')
          }).catch((error)=>{
            console.log(error)
            this.$message.error('获取用户信息失败！')
            this.resetForm('form_2');
          })
        }else{
          if(res.code === 200) {
            this.$message.success('修改成功！')
            this.resetForm('form_2');
          }
        }
      }).catch((error)=>{
        console.log(error)
        this.$message.error('更新用户信息失败！')
        this.resetForm('form_2');
      }).finally(()=>{
        this.isInput = false
      })
    },
    cancel(){
      this.resetForm('form_2');
      this.isInput = false
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    async processLikeMessageItems(data) {
      for (const item of data) {
        try {
          const userInfo = await getUserInfo(item.eventUserId);
          item.eventUserAvatarPath = userInfo.data.avatarPath;
        } catch (error) {
          console.error(`Error getting user info for user ID ${item.eventUserId}:`, error);
        }

        try {
          const videoInfo = await getVideoById(item.eventEntityId);
          item.video = videoInfo.data;
        } catch (error) {
          console.error(`Error getting video info for video ID ${item.eventEntityId}:`, error);
        }
        this.likeMessageList.push(item);
      }
      this.loading.likeMessage = false
    },
    getLikeMessageList(){
      this.loading.likeMessage = true
      this.likeMessageList = []
      getLike().then((res)=>{
        this.processLikeMessageItems(res.data)
      }).catch(err=>{
        console.log("getLikeMessageList",err)
        this.loading.likeMessage = false
      })
    },
    async processCommentMessageItems(data){
      for (const item of data) {
        try {
          const userInfo = await getUserInfo(item.eventUserId);
          item.eventUserAvatarPath = userInfo.data.avatarPath;
        } catch (error) {
          console.error(`Error getting user info for user ID ${item.eventUserId}:`, error);
        }

        this.commentMessageList.push(item);
      }
      this.loading.commentMessage = false
    },
    getCommentMessageList(){
      this.loading.commentMessage = true
      this.commentMessageList = []
      getComment().then((res)=>{
        this.processCommentMessageItems(res.data)
      }).catch(err=>{
        console.log("getCommentMessageList",err)
        this.loading.commentMessage = false
      })
    },
    async processFollowMessageItems(data) {
      for (const item of data) {
        try {
          const userInfo = await getUserInfo(item.eventUserId);
          item.eventUserAvatarPath = userInfo.data.avatarPath;
        } catch (error) {
          console.error(`Error getting user info for user ID ${item.eventUserId}:`, error);
        }
        this.followMessageList.push(item);
      }
      this.loading.followMessage = false
    },
    getFollowMessageList(){
      this.loading.followMessage = true
      this.followMessageList = []
      getFollow().then((res)=>{
        this.processFollowMessageItems(res.data)
      }).catch(err=>{
        console.log("getFollowMessageList",err)
        this.loading.followMessage = false
      })
    },
    async allRead(type){
      if (type === 31){
        for (const item of this.likeMessageList) {
          await readMessage(item.messageId);
        }
        await this.getLikeMessageList();
      }else if (type === 33){
        for (const item of this.likeMessageList) {
          await readMessage(item.messageId)
        }
        await this.getFollowMessageList()
      }else if (type === 32) {
        for (const item of this.commentMessageList) {
          await readMessage(item.messageId)
        }
        await this.getCommentMessageList()
      }
    },
    reviewMsg(sender,content){

    },
    replayComment(){
      let html;
      html = "<div class=\"el-row\" style=\"padding: 5px 0\">\n" +
          "  <div class=\"el-col el-col-22\" style=\"text-align: right; padding-right: 10px\">\n" +
          "    <div class=\"tip left\">" + this.textarea + "</div>\n" +
          "  </div>\n" +
          "  <div class=\"el-col el-col-2\">\n" +
          "  <span class=\"el-avatar el-avatar--circle\" style=\"height: 40px; width: 40px; line-height: 40px;\">\n" +
          "    <img src=\"https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png\" style=\"object-fit: cover;\">\n" +
          "  </span>\n" +
          "  </div>\n" +
          "</div>";
      this.commentContent += html
      this.textarea = ''
    },
    renderContent(h, { node, data, store }){
      // console.log(node)
      if (node.data.children) {
        return (
            <span className="custom-tree-node">
              <span>{node.label}</span>
            </span>
        )
      }else{
        return (
            <span className="custom-tree-node">
              <span>{node.label}</span>
              <span>
                <el-button size="mini" type="text">Append</el-button>
                <el-button size="mini" type="text">Delete</el-button>
              </span>
            </span>
        );
      }
    } ,
    getCollectionDir(){
      this.collectionData = []
      getCollection(1,100).then((res)=>{
        res.data.records.forEach((item)=>{
          getVideoByCollectionId(item.id,1,100).then((res)=>{
            console.log(item.collectionName,res)
            item.videos = res.data.records
            this.collectionData.push(item)
          })
        })
      })
    },
    createCollectionDir(formName){
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const data = new FormData()
          data.set("collectionName",this.form_5.collectionName)
          createCollection(data).then((res)=>{
            if (res.code === 200) {
              this.$message.success({
                message:"创建成功",
                duration:500
              })
            }
            this.getCollectionDir()
          }).finally(()=>{
            this.dialogVisible = false
          })
        } else {
          return false;
        }
      });
    },
    removeCollection(id){
      deleteCollection(id).then((res)=>{
        if (res.code === 200) {
          this.$message.success({
            message:"删除成功",
            duration:500
          })
        }
        this.getCollectionDir()
      })
    },
    toDetailPage(videoId){
      this.$router.push({
        name:'Detail',
        params:{
          id:videoId
        }
      })
    }
  },
  watch:{
    "$route.params.index":{
      immediate:true,
      handler(newV){
        this.activeIndex = newV
      }
    },
    "activeIndex":{
      immediate:true,
      handler(newV){
        if (newV === "1"){
          this.videoList = []
          this.getLikeVideoList()
        }
        if (newV === "4"){
          this.myVideoList = []
          this.getMyVideoList()
        }
        if (newV === "3"){
          this.subActiveIndex = "31"
        }
        if (newV === "5"){
          this.getCollectionDir()
        }
      }
    },
    "subActiveIndex":{
      handler(newV){
        if(newV === "31"){
          this.likeMessageList = []
          this.getLikeMessageList()
        }
        if(newV === "32"){
          this.commentMessageList = []
          this.getCommentMessageList()
        }
        if(newV === "33"){
          this.followMessageList = []
          this.getFollowMessageList()
        }
      }
    }
  },
}
</script>

<style>
.tip {
  color: white;
  text-align: center;
  border-radius: 10px;
  font-family: sans-serif;
  padding: 10px;
  width:auto;
  display:inline-block !important;
  display:inline;
}

.right {
  background-color: deepskyblue;
}
.left {
  background-color: forestgreen;
}
ul {
  list-style-type: none; /* 移除默认的圆点样式 */
  padding: 0; /* 移除默认的内边距 */
}

.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}

.card-row {
  display: flex;
  flex-wrap: wrap;
  margin: -10px; /* 负 margin 以消除 gutter 的影响 */
}

.box-card {
  flex: 0 0 calc(20% - 20px); /* 设置卡片的宽度，20% 表示每个卡片的宽度占父元素的 20% */
  margin: 10px; /* gutter 的一半，使得卡片之间有间隔 */
  box-sizing: border-box; /* 让 margin 不会撑大卡片的宽度 */
}
</style>