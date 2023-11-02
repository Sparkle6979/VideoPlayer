<template>
  <div>
    <el-page-header @back="goBack" :content="$route.meta.title">
    </el-page-header>
    <div style="margin: 20px">
      <el-tabs v-model="activeIndex" type="card">
        <el-tab-pane label="我的喜欢" name="1">
          <div>
            <el-row justify="space-around" :gutter="20">
              <el-col :span="6" v-for="(video,index) in videoList" :key="index" :offset="1">
                <myVideo :info="video"></myVideo>
              </el-col>
            </el-row>
          </div>
        </el-tab-pane>
        <el-tab-pane label="账号设置" name="2">
          <div>
            <el-form :model="form_2" ref="form_2" label-width="auto" style="width: 30%">
              <el-form-item label="头像" prop="url">
                <el-upload
                    :disabled="!isInput"
                    class="avatar-uploader"
                    action="https://jsonplaceholder.typicode.com/posts/"
                    :show-file-list="false"
                    accept=".jpg"
                    :before-upload="beforeAvatarUpload">
                  <el-avatar v-if="form_2.url" :src="form_2.url" class="avatar"></el-avatar>
                  <el-avatar v-else src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" :size="150" :fit="'cover'" ></el-avatar>
                </el-upload>
              </el-form-item>
              <el-form-item label="账号" prop="username">
                <el-input
                    v-model.trim="form_2.username"
                    disabled
                ></el-input>
              </el-form-item>
              <el-form-item label="新密码" prop="password">
                <el-input
                    v-model.trim="form_2.password"
                    :disabled="!isInput"
                ></el-input>
              </el-form-item>

              <el-form-item>
                <el-button type="primary" v-if="isInput">确定</el-button>
                <el-button type="primary" v-if="isInput" @click="cancel">取消</el-button>
                <el-button v-if="isInput" @click="resetForm('form_2')">重置</el-button>
                <el-button v-else @click="isInput = !isInput">编辑</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-tab-pane>
        <el-tab-pane label="消息" name="3">
          <div>·
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
                  <el-button v-if="scope.row.message && scope.row.status == 2" type="text" size="small">查看</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>

    <!--dialog-->
    <el-dialog
        title="消息"
        :visible.sync="msgVisible"
        width="40%"
        center>
      <div>
        <el-tooltip effect="light" :content="msg.content" placement="right" value="true">
          <span>{{ msg.sender }}</span>
        </el-tooltip>
        <el-divider></el-divider>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button @click="msgVisible = false">取 消</el-button>
    <el-button type="primary" @click="msgVisible = false">发 送</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
import myVideo from "@/views/home/components/video";
import {mapGetters} from "vuex";

export default {
  name: "user",
  components:{
    myVideo
  },
  data(){
    const activeIndex = '1'
    const isInput = false
    const form_2 = {
      url:'',
      username:this.getName(),
      password:""
    }
    const msgVisible = false
    let msg = {
      sender : '',
      content : '',
    }
    const videoList = [
      {
        "videoId": 5,
        "title": "修仙游戏哪家强？高品质修仙游戏推荐！",
        "description": "看一看有哪些值得玩的修仙游戏",
        "likeCount": 0,
        "createTime": "2023-10-27",
        like:true,
        "videoUrl": "http://s33fgajdq.hd-bkt.clouddn.com/%E4%BF%AE%E4%BB%99%E6%B8%B8%E6%88%8F%E5%93%AA%E5%AE%B6%E5%BC%BA%EF%BC%9F%E9%AB%98%E5%93%81%E8%B4%A8%E4%BF%AE%E4%BB%99%E6%B8%B8%E6%88%8F%E6%8E%A8%E8%8D%90%EF%BC%81.mp4"
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
      videoList
    }
  },
  methods:{
    goBack(){
      this.$router.back()
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
    },
    ...mapGetters({getName:'userName'})
  },
  activated() {
    this.activeIndex = this.$route.params.index ? this.$route.params.index : "1"
  },
}
</script>

<style>
</style>