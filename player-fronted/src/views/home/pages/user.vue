<template>
  <div>
    <el-page-header @back="goBack" :content="$route.meta.title">
    </el-page-header>
    <div style="margin: 20px">
      <el-tabs v-model="activeIndex" type="card" :key="activeIndex">
        <el-tab-pane label="我的喜欢" name="1">
          <div v-loading="this.loading.likeVideo"
               element-loading-text="拼命加载中"
               element-loading-spinner="el-icon-loading">
            <el-row justify="space-around" :gutter="20">
              <el-col :span="6" v-for="(video,index) in videoList" :key="index" :offset="1">
                <myVideo :info="video"></myVideo>
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
                    :limit="1"
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
            <el-tabs tab-position="left" type="border-card" style="height: 650px;overflow-y: auto;">
              <el-tab-pane label="赞">
                <MyMessage :messageList="zanList" :type="1"></MyMessage>
              </el-tab-pane>
              <el-tab-pane label="回复">
                <div>
                  <el-table :data="tableData" style="margin-bottom: 20px;"
                            row-key="id" :cell-style="msgTableRowClass" :show-header="true"
                            :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
                    <el-table-column prop="date" label="时间" align="center" width="200" sortable>
                    </el-table-column>
                    <el-table-column prop="name" label="用户名" width="180"></el-table-column>
                    <el-table-column prop="createdAt" label="消息时间" align="center" ></el-table-column>
                    <el-table-column prop="message" label="消息"></el-table-column>
                    <el-table-column label="状态">
                      <template slot-scope="scope">
                        <el-tag v-if="scope.row.status == 0" type="danger">未读</el-tag>
                        <el-tag v-if="scope.row.status == 1" type="success">已读</el-tag>
                        <el-tag v-if="scope.row.status == 2">已回复</el-tag>
                      </template>
                    </el-table-column>
                    <el-table-column label="操作" width="100px" fixed="right">
                      <template slot-scope="scope">
                        <el-button v-if="scope.row.message && scope.row.status != 2" type="text" size="small"
                                   @click="reviewMsg(scope.row.name,scope.row.message)">回复</el-button>
                        <!--                  <el-button v-if="scope.row.message && scope.row.status == 2" type="text" size="small">查看</el-button>-->
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </el-tab-pane>
              <el-tab-pane label="@我的">
                <MyMessage :messageList="zanList" :type="2"></MyMessage>
              </el-tab-pane>
              <el-tab-pane label="私信">
                <Im></Im>
              </el-tab-pane>
            </el-tabs>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>

    <!--dialog-->
    <el-dialog
        :title="msg.sender"
        :visible.sync="msgVisible"
        width="40%"
        :show-close="false"
        center>
      <!--  回复框  -->
      <div v-html="commentContent">
      </div>
      <el-input
          type="textarea"
          style="padding: 5px 0"
          :rows="2"
          placeholder="请输入内容"
          v-model="textarea">
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="msgVisible = false;commentContent=''">返 回</el-button>
        <el-button type="primary" @click="replayComment">发 送</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import myVideo from "@/views/home/components/video";
import MyMessage from '@/views/home/components/message';
import Im from "@/views/home/components/im";
import {mapGetters, mapMutations, mapState} from "vuex";
import {getUserInfo, getUserLike, updateUserInfo} from "@/api/user";
import {getVideoById} from "@/api/video";
import md5 from "js-md5";

export default {
  name: "user",
  components:{
    myVideo,MyMessage,Im
  },
  computed:{
    ...mapState(['user'])
  },
  data(){
    const activeIndex = "1"
    const isInput = false
    const form_2 = {
      avatarFile:'',
      avatarPath:this.avatarPath(),
      username:this.username(),
      password:""
    }
    const loading = {
      likeVideo:false
    }
    const msgVisible = false
    let msg = {
      sender : '',
      content : '',
    }
    const videoList = []
    let textarea = '' // 评论输入框
    let commentContent = ''
    const zanList = [
      {
        id:1,
        name:"王二虎",
        avatar:"https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
        created_at:"2023-11-3 16:38:00",
        videoId: 1,
        videoCover: "https://fuss10.elemecdn.com/1/34/19aa98b1fcb2781c4fba33d850549jpeg.jpeg"
      },
      {
        id:2,
        name:"王二虎",
        avatar:"https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
        created_at:"2023-11-3 16:38:00",
        videoId: 1,
        videoCover: "https://fuss10.elemecdn.com/1/34/19aa98b1fcb2781c4fba33d850549jpeg.jpeg"
      }
    ]
    return {
      activeIndex,
      isInput,
      form_2,
      tableData: [{
        id:1,
        date: '2016-05-02',
      }, {
        id:2,
        date: '2016-05-03',
      }, {
        id:3,
        date: '2016-05-04',
        children: [{
          id: 31,
          createdAt: '2016-05-04 08:00:00',
          name: '王小虎',
          message: '上海市普陀区金沙江路 1519 弄',
          status: 0
        }, {
          id: 32,
          createdAt: '2016-05-04 08:00:01',
          name: '王小虎',
          message: '上海市普陀区金沙江路 1519 弄',
          status: 1
        },{
          id: 33,
          createdAt: '2016-05-04 08:00:01',
          name: '王小虎',
          message: '上海市普陀区金沙江路 1519 弄',
          status: 2
        }]
      }, {
        id: 4,
        date: '2016-05-05',
      }],
      msgVisible,
      msg,
      videoList,
      textarea,
      commentContent,
      zanList,
      loading
    }
  },
  methods:{
    ...mapGetters({avatarPath:'getUserAvatarPath',username:'getUserUsername'}),
    ...mapMutations({updateUser:"UPDATE_USER"}),
    goBack(){
      this.$router.back()
    },
    getLikeVideoList(){
      this.loading.likeVideo = true
      getUserLike().then((res)=>{
        this.videoList = []
        let count = 0
        res.data.videoIds.forEach((id,index,arr)=>{
          getVideoById(id).then((res)=>{
            // 额外加入
            res.data.isLike = true
            this.videoList.push(res.data)
          }).finally(()=>{
            count++
            if (count === arr.length) {
              this.loading.likeVideo = false
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
    msgTableRowClass({row, rowIndex}){
      if(row.children) { //如果此行children存在，也就是一级菜单
        return { "height":"4vh !important ","color":"blue"}
      }else{
        return { "height":"8vh !important" }
      }
    },
    reviewMsg(sender,content){
      this.msg.sender = sender
      this.msg.content = content
      this.msgVisible = true
      let html = "<div class=\"el-row\" style=\"padding: 5px 0\">\n" +
          "  <div class=\"el-col el-col-2\" style=\"text-align: right\">\n" +
          "  <span class=\"el-avatar el-avatar--circle\" style=\"height: 40px; width: 40px; line-height: 40px;\">\n" +
          "    <img src=\"https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png\" style=\"object-fit: cover;\">\n" +
          "  </span>\n" +
          "  </div>\n" +
          "  <div class=\"el-col el-col-22\" style=\"text-align: left; padding-left: 10px\">\n" +
          "    <div class=\"tip right\">" + content + "</div>\n" +
          "  </div>\n" +
          "</div>";
      this.commentContent += html
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
    }
  },
  activated() {
    this.activeIndex = this.$route.params.index ? this.$route.params.index : "1"
    console.log(this.activeIndex)
    if (this.activeIndex === "1") {
      this.getLikeVideoList()
    }
  },
  watch:{
    activeIndex(newV,oldV){
      if (newV === "1"){
        this.getLikeVideoList()
      }
    }
  }
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
</style>