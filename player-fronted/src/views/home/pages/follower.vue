<template>
  <div>
    <el-tabs v-model="activeName" type="card" :key="activeName">
      <el-tab-pane label="关注" name="follower">
        <div style="overflow-y:auto;height: 500px">
          <ul v-infinite-scroll="loadFollower" :infinite-scroll-disabled="disabled">
            <li v-for="(f,index) in follower.list">
              <el-card style="margin: 20px">
                <el-row :gutter="20" type="flex" align="middle">
                  <el-col :span="6">
                    <el-avatar :src="f.avatarPath ? f.avatarPath : defaultUserAvatar" :size="70"></el-avatar>
                  </el-col>
                  <el-col :span="12">
                    <h4 class="author" :style="{'top':'-12px'}">{{f.username}}</h4>
                    <el-row>
                      <span class="description_text">七牛云NB七牛云NB七牛云NB</span>
                    </el-row>
                  </el-col>
                  <el-col :span="6">
                    <el-popconfirm title="确认取消关注吗?" @confirm="unFollow(f.id,index)">
                      <el-button type="info" slot="reference">已关注</el-button>
                    </el-popconfirm>
                  </el-col>
                </el-row>
              </el-card>
            </li>
            <p v-if="follower.loading">加载中...</p>
            <p v-if="follower.finished">没有数据了</p>
          </ul>
        </div>
      </el-tab-pane>
      <el-tab-pane label="粉丝" name="fan">
        <div style="overflow-y:auto;height: 500px">
          <ul v-infinite-scroll="loadFans" :infinite-scroll-disabled="disabled2">
            <li v-for="f in fan.list">
              <el-card style="margin: 20px">
                <el-row :gutter="20" type="flex" align="middle">
                  <el-col :span="6">
                    <el-avatar :src="f.avatarPath ? f.avatarPath : defaultUserAvatar" :size="70"></el-avatar>
                  </el-col>
                  <el-col :span="12">
                    <h4 class="author" :style="{'top':'-12px'}">{{f.username}}</h4>
                    <el-row>
                      <span class="description_text">七牛云NB七牛云NB七牛云NB</span>
                    </el-row>
                  </el-col>
                  <el-col :span="6">
                  </el-col>
                </el-row>
              </el-card>
            </li>
            <p v-if="fan.loading">加载中...</p>
            <p v-if="fan.finished">没有数据了</p>
          </ul>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import {fanListById, followListById, getUserInfo, unFollowUser} from "@/api/user";
import {mapState} from "vuex";

export default {
  name: "follower",
  data() {
    const GlobalPageSize = 5
    const follower = {
      finished:false,
      loading:false,
      list:[],
      curPage:1,
      pageSize:GlobalPageSize
    }
    const fan = {
      finished:false,
      loading:false,
      list:[],
      curPage:1,
      pageSize:GlobalPageSize
    }
    return {
      activeName: 'follower',
      fan,
      follower,
    };
  },
  computed:{
    ...mapState(['user']),
    disabled(){
      return this.follower.loading || this.follower.finished
    },
    disabled2(){
      return this.fan.loading || this.fan.finished
    },
  },
  methods:{
    loadFollower () {
      console.log("loadFollower",this.follower.list.length,this.follower.curPage,this.follower.pageSize)
      this.follower.loading = true
      followListById(this.user.id,this.follower.curPage,this.follower.pageSize).then((res)=>{
        console.log(res)
        if(res.code === 401) {
          this.$router.push("/login")
        }
        if (res.data.records.length === 0){
          this.follower.finished = true
          return
        }
        res.data.records.forEach((id)=>{
          getUserInfo(id).then((res)=>{
            this.follower.list.push(res.data)
          })
        })
        if (res.data.records.length < this.follower.pageSize) {
          this.follower.finished = true
        }else{
          this.follower.curPage++
        }
      }).catch(err=>{
        console.log("followListById",err)
      }).finally(()=>{
        this.follower.loading = false
        console.log(this.follower)
      })
    },
    loadFans(){
      console.log("loadFans",this.fan.list.length,this.fan.curPage,this.fan.pageSize)
      this.fan.loading = true
      fanListById(this.user.id,this.fan.curPage,this.fan.pageSize).then((res)=>{
        console.log(res)
        if(res.code === 401) {
          this.$router.push("/login")
        }
        if (res.data.records.length === 0){
          this.fan.finished = true
          return
        }
        res.data.records.forEach((id)=>{
          getUserInfo(id).then((res)=>{
            this.fan.list.push(res.data)
          })
        })
        if (res.data.records.length < this.fan.pageSize) {
          this.fan.finished = true
        }else{
          this.fan.curPage++
        }
      }).catch(err=>{
        console.log("followListById",err)
      }).finally(()=>{
        this.fan.loading = false
      })
    },
    unFollow(id,index){
      unFollowUser(id).then((res)=>{
        console.log(res)
        if (res.code === 200) {
          this.$message.success("取关成功！")
        }
        this.follower.list.splice(index,1)
      }).catch(err=>{
        console.log("follow",err)
      })
    },
  },
}
</script>

<style scoped>
ul {
  list-style-type: none; /* 移除默认的圆点样式 */
  padding: 0; /* 移除默认的内边距 */
}
.author{
  position: relative;
  margin: 0px;
}
</style>