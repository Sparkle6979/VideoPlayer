<template>
  <el-container>
    <el-aside width="200px">
      <myMenu></myMenu>
    </el-aside>
    <el-container>
      <el-header style="text-align: center; font-size: 12px">
        <el-row>
          <el-col :span="4" :offset="8">
            <el-input
                v-model="searchInput"
                placeholder="请输入搜索内容"
                clearable
                class="search-input"
            >
              <el-button slot="append" icon="el-icon-search"></el-button>
            </el-input>
          </el-col>
          <el-col :span="this.isLogin ? 4 : 2" :offset="isLogin ? 8 : 10">
            <el-button type="danger" v-if="!isLogin" round icon="el-icon-user" @click="toLogin">去登录</el-button>
            <div v-else>
              <el-tooltip effect="light" content="视频上传" placement="bottom">
                <svg-icon name="upload" class="header-icon" width="25" height="25" @click.native="toUploadPage"></svg-icon>
              </el-tooltip>
              <el-tooltip effect="light" content="消息" placement="bottom">
<!--                <svg-icon name="notice" class="header-icon" width="25" height="25" @click.native="toUserPage(3)"></svg-icon>-->
                <svg-icon name="notice-dot" class="header-icon" width="23" height="23" @click.native="toUserPage(3)"></svg-icon>
              </el-tooltip>
              <el-dropdown>
                <div>
                  <el-avatar :src="this.user.avatarPath"  style="vertical-align: middle"></el-avatar>
                  <span style="margin: 10px">{{ this.user.username }}</span>
                </div>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item @click.native="dialogVisible = true">关注&粉丝</el-dropdown-item>
                  <el-dropdown-item @click.native="toUserPage(1)">我的喜欢</el-dropdown-item>
                  <el-dropdown-item @click.native="toUserPage(2)">账号设置</el-dropdown-item>
                  <el-dropdown-item @click.native="toLogin" divided>退出登录</el-dropdown-item>
                </el-dropdown-menu>

                <div v-if="dialogVisible">
                  <el-dialog :visible.sync="dialogVisible" width="35%">
                    <Follower></Follower>
                  </el-dialog>
                </div>
              </el-dropdown>
            </div>
          </el-col>
        </el-row>
      </el-header>

      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import {mapMutations, mapState} from "vuex";
import myMenu from "@/views/home/components/menu";
import Follower from "@/views/home/pages/follower";

export default {
  name: "index",
  components:{
    myMenu,Follower
  },
  data(){
    const searchInput = ''
    const dialogVisible = false
    return {
      searchInput,
      dialogVisible
    }
  },
  computed:{
    ...mapState(['user','isLogin']),
  },
  methods:{
    ...mapMutations({updateUser:"UPDATE_USER",updateLogin:'UPDATE_ISLOGIN'}),
    toLogin(){
      this.updateLogin(false)
      this.updateUser({})
      localStorage.removeItem("token")
      this.$router.push('/login')
    },
    toUploadPage(){
      this.$router.push('/home/upload').catch(err => err)
    },
    toUserPage(id = "1"){
      this.$router.push({
        name:'User',
        params:{
          index:id.toString()
        }
      }).catch(err => err)
    }
  },
  mounted() {

  }
}
</script>

<style scoped>
.el-header {
  background: #66b1ff;
  color: #fff;
  line-height: 60px;
}

.el-aside {
  color: #333;
  background: linear-gradient(to bottom, #f0f2f5, #ffffff);
}

.search-input{
  width: 500px;
}

.header-icon{
  position: relative;
  top: 7px;
  margin-right: 15px;
  cursor: pointer;
}

.el-main {
  background-color: #fff;
  padding: 20px;
}
</style>