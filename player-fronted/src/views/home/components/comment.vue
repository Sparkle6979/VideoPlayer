<template>
  <div>
    <div class="content">
      <!-- 一级评论 -->
      <div v-if="show">
        <div  class="first" v-for="(item, index) in comments" :key="index" >
          <a href="javascript:;" class="first-img">
            <img :src="item.commentUserAvatarPath ? item.commentUserAvatarPath : defaultUserAvatar" alt="" />
          </a>
          <div class="first-content">
            <h3 class="first-username">{{ item.commentUserName }}</h3>
            <span class="first-time">{{ item.createTime }}</span>
            <p class="first-comment">{{ item.content }}</p>
            <div class="first-right">
              <span class="comments" @click="comment_input(item)">评论</span>
            </div>
            <!-- 回复一级评论 -->
            <div class="reply-comment" v-if="item.display">
              <input type="text" placeholder="请输入评论 . . ." v-model="childComments"
                     @keyup.enter="reply_submit(item)"/>
              <button @click="reply_submit(item)">发表评论</button>
            </div>
            <!-- 次级评论 -->
            <div class="second">
              <ul>
                <li v-for="(sons, sons_index) in item.commentReply" :key="sons_index" >
                  <div class="top">
                    <!-- 次级评论头像,该用户没有头像则显示默认头像 -->
                    <a href="JavaScript:;" class="second-img">
                      <img v-if="sons.picture" :src="sons.picture" />
                      <img v-else :src="defaultUserAvatar" />
                    </a>
                    <div class="second-content">
                      <!-- 次级评论用户昵称 -->
                      <h3 class="second-username">{{ sons.username }}</h3>
                      <!-- 次级评论评论时间 -->
                      <span class="second-time">{{ sons.date }}</span>
                      <!-- 次级评论内容 xxx回复xxx：评论内容-->
                      <p class="second-comment">
	                      <span class="second-reply">
	                        <span class="to_reply">{{ sons.username }}</span>回复
                          <span class="to_reply">{{ sons.to_username }}</span>：
	                      </span>
                        {{ sons.content }}
                      </p>
                      <!-- 次级评论评论点赞 -->
                      <div class="second-right">
                        <span class="comments" @click="comment_input(sons)">评论</span>
                      </div>
                    </div>
                  </div>
                  <!-- 回复次级评论 -->
                  <div class="reply-comment reply_li" v-if="sons.display">
                    <input type="text" placeholder="请输入评论 . . ." v-model="childComments"
                           @keyup.enter="reply_submit(sons)"/>
                    <button @click="reply_submit(sons)">发表评论</button>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
      <!-- 暂无评论的空状态 -->
      <el-empty description="暂无评论" v-else></el-empty>
    </div>
  </div>
</template>

<script>
import {mapState} from "vuex";

export default{
  name:'comment',
  computed:{
    ...mapState(['user']),
  },
  props:['comments'],
  data(){
    return{
      current:new Date(),	//时间
      picture:'',
      childComments:'',
      show:true	//暂无条件显示
    }
  },
  methods:{
    // 评价框失焦
    lose(m){
      m.display = false
    },
    // 评价展示
    comment_input(m){
      m.display = true
    },
    reply_submit(m){
      console.log(m)
      this.$emit('update',this.childComments,m.commentId,m.commentUserId)
      this.childComments = ''
      m.display = false
    }
  },
  watch:{
    comments(newValue,oldValue){
      if(newValue.length === 0){
        this.show = false
      }else{
        this.show = true
      }
    }
  }
}
</script>

<style scoped>
.head img {
  width: 55px;
  height: 55px;
  border-radius: 50%;
  position: absolute;
  top: 10px;
  left: 13px;
}
/* 评论框 */
.head input {
  position: absolute;
  top: 13px;
  left: 80px;
  height: 45px;
  border-radius: 5px;
  outline: none;
  width: 65%;
  font-size: 20px;
  padding: 0 20px;
  border: 2px solid #f8f8f8;
}
/* 发布评论按钮 */
.head button {
  position: absolute;
  top: 13px;
  right: 20px;
  width: 120px;
  height: 48px;
  border: 0;
  border-radius: 5px;
  font-size: 20px;
  font-weight: 500;
  color: #fff;
  background-color: rgb(118, 211, 248);
  cursor: pointer;
  letter-spacing: 2px;
}
/* 鼠标经过字体加粗 */
.head button:hover {
  font-weight: 600;
}

/* 评论内容区域 */
.content .first {
  display: flex;
  position: relative;
  padding: 10px 10px 0px 0;
  text-align: left;
}
.first .first-img {
  flex: 1;
  text-align: center;
}
.first img {
  width: 50px;
  height: 50px;
  border-radius: 50%;
}
.first-username,
.second-username {
  margin: 0px;
  color: #504f4f;
}
.first-content {
  flex: 18;
}
.first-time,
.second-time {
  color: #767575;
}
.first-comment,
.second-comment {
  margin-top: 5px;
}
/* 右边点赞和评论 */
.first-right,
.second-right {
  position: absolute;
  right: 3%;
  top: 10px;
}
.first-right span,
.second-right span {
  margin-right: 20px;
  cursor: pointer;
}
/* 删除评论 */
.delete:hover {
  color: red;
}
/* 评论字体图标 */
.comments:hover {
  color: greenyellow;
}
/* 点赞字体图标 */
.praise::before {
  margin-right: 4px;
  font-size: 19px;
}
.second {
  background-color: #f3f3f3;
  margin-top: 10px;
}
.second li {
  padding: 10px 10px 10px 0;
  border-bottom: 1px solid rgb(237, 237, 237);
}
.second .top {
  display: flex;
  position: relative;
}
.second-img {
  flex: 1;
  text-align: center;
}
.to_reply {
  color: rgb(106, 106, 106);
}
.second-content {
  flex: 18;
}
.second .reply_li {
  margin-left: 70px;
}
/* 评论框 */
.reply-comment {
  margin: 10px 0 0 0;
}
.reply-comment input {
  height: 40px;
  border-radius: 5px;
  outline: none;
  width: 70%;
  font-size: 18px;
  padding: 0 20px;
  /* border: 2px solid #f8f8f8; */
  border: 2px solid skyblue;
}
/* 发布评论按钮 */
.reply-comment button {
  width: 15%;
  height: 43px;
  border: 0;
  border-radius: 5px;
  font-size: 18px;
  font-weight: 500;
  color: #fff;
  background-color: rgb(118, 211, 248);
  cursor: pointer;
  letter-spacing: 2px;
  margin-left: 20px;
}
/* 鼠标经过字体加粗 */
.reply-comment button:hover {
  font-weight: 600;
}
/* 评论点赞颜色 */
.comment-like {
  color: red;
}
ul,li{list-style:none;}
</style>
