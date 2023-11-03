<template>
  <div>
    <el-tabs v-model="activeName" type="card" :key="activeName">
      <el-tab-pane label="关注" name="follower">
        <div style="overflow-y:auto;height: 500px">
          <ul v-infinite-scroll="loadFollower">
            <li v-for="follower in followerList">
              <el-card style="margin: 20px">
                <el-row :gutter="20" type="flex" align="middle">
                  <el-col :span="6">
                    <el-avatar :src="follower.url ? follower.url : defaultUserAvatar" :size="70"></el-avatar>
                  </el-col>
                  <el-col :span="12">
                    <h4 class="author" :style="{'top':follower.description.length <= 15 ? '-12px' : '0px'}">{{follower.name}}</h4>
                    <el-row>
                      <span class="description_text">{{follower.description}}</span>
                    </el-row>
                  </el-col>
                  <el-col :span="6">
                    <el-button type="info">已关注</el-button>
<!--                    <el-button type="warning">相互关注</el-button>-->
                  </el-col>
                </el-row>
              </el-card>
            </li>
            <p v-if="followerLoading">加载中...</p>
          </ul>
        </div>
      </el-tab-pane>
      <el-tab-pane label="粉丝" name="fan">
        <div style="overflow-y:auto;height: 500px">
          <ul v-infinite-scroll="loadFans">
            <li v-for="fan in fanList">
              <el-card style="margin: 20px">
                <el-row :gutter="20" type="flex" align="middle">
                  <el-col :span="6">
                    <el-avatar :src="fan.url ? fan.url : defaultUserAvatar" :size="70"></el-avatar>
                  </el-col>
                  <el-col :span="12">
                    <h4 class="author" :style="{'top':fan.description.length <= 15 ? '-12px' : '0px'}">{{fan.name}}</h4>
                    <el-row>
                      <span class="description_text">{{fan.description}}</span>
                    </el-row>
                  </el-col>
                  <el-col :span="6">
                    <el-button type="info">移除</el-button>
                  </el-col>
                </el-row>
              </el-card>
            </li>
            <p v-if="fanLoading">加载中...</p>
          </ul>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
  name: "follower",
  data() {
    const user = {
      url:this.defaultUserAvatar,
      name:"123",
      description:"简介",
    }
    return {
      activeName: 'follower',
      followerLoading: false,
      fanLoading:false,
      followerList:[],
      fanList:[],
      user
    };
  },
  methods:{
    loadFollower () {
      console.log("loadFollower",this.followerList.length,this.fanList.length)
      this.followerLoading = true
      setTimeout(() => {
        this.followerList = this.followerList.concat(Array(5).fill(this.user))
        this.followerLoading = false
      }, 2000)
    },
    loadFans(){
      console.log("loadFans",this.followerList.length,this.fanList.length)
      this.fanLoading = true
      setTimeout(() => {
        this.fanList = this.fanList.concat(Array(5).fill(this.user))
        this.fanLoading = false
      }, 2000)
    }
  }
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