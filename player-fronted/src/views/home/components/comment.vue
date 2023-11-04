<template>
  <div>
    <div class="content">
      <!-- 一级评论 -->
      <div v-if="show">
        <div  class="first" v-for="(item, index) in comments" :key="index" >
          <a href="javascript:;" class="first-img">
            <img v-if="item.picture" :src="`http://` + item.picture" alt="" />
            <img v-else :src="defaultUserAvatar" alt="" />
          </a>
          <div class="first-content">
            <h3 class="first-username">{{ item.username }}</h3>
            <span class="first-time">{{ item.date }}</span>
            <p class="first-comment">{{ item.content }}</p>
            <!-- 一级评论评论点赞 -->
            <div class="first-right">
              <span class="delete" v-if="item.delete" @click="comment_delete(item, '父级', index)">删除</span>
              <span class="comments" @click="comment_input(item)">评论</span>
              <!-- 一级评论点赞数 -->
              <span class="praise">
                <svg-icon name="like-on"></svg-icon>
                <span v-if="item.numbers" style="margin-left: 3px">{{item.numbers }}</span>
              </span>
            </div>
            <!-- 回复一级评论 -->
            <div class="reply-comment" v-if="item.display">
              <input type="text" placeholder="请输入评论 . . ." v-model="childComments"
                     @keyup.enter="reply_submit(item, '父级', index)"/>
              <button @click="reply_submit(item, '父级', index)">发表评论</button>
            </div>
            <!-- 次级评论 -->
            <div class="second">
              <ul>
                <li v-for="(sons, sons_index) in item.sons" :key="sons_index" >
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
                        <span class="delete" v-if="sons.delete"
                              @click="comment_delete(sons, '子级',index,sons_index)">删除</span>
                        <span class="comments" @click="comment_input(sons)">评论</span>
                          <!-- 次级评论点赞数 -->
                        <span class="praise">
                          <svg-icon name="like-on"></svg-icon>
                          <span v-if="sons.numbers" style="margin-left: 3px">{{sons.numbers }}</span>
                        </span>

                      </div>
                    </div>
                  </div>
                  <!-- 回复次级评论 -->
                  <div class="reply-comment reply_li" v-if="sons.display">
                    <input type="text" placeholder="请输入评论 . . ." v-model="childComments"
                           @keyup.enter="reply_submit(sons,'子级',index,sons_index)"/>
                    <button @click="reply_submit(sons,'子级',index,sons_index)">发表评论</button>
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
    comment_delete(m,n,w,t){
      if(n == '子级'){
        this.comments[w].sons.splice(t,1);
      }else if(n == '父级'){
        this.comments.splice(w,1)
      }
      this.$emit('update',this.comments)
    },
    reply_submit(m,n,w,t){
      console.log(m,n,w,t)
      let index;
      if(n == '父级'){
        index = t
      }else if( n == '子级'){
        index = t+ 1;
      }
      this.comments[w].sons.splice(index, 0, {
        username:this.user.username,
        date:this.current.getFullYear() + '年' + (this.current.getMonth() + 1) + '月' + this.current.getDate() + '日' + this.current.getHours() + ':' + this.current.getMinutes() + ':' + this.current.getSeconds(),
        content:this.childComments,
        delete:true,
        flag:false,
        like:'',
        display:false,	//显示评论区
        to_username:m.username,
      })
      this.childComments = ''
      m.display = false
      this.$emit('update',this.comments)
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
