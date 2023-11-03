<template>
  <div class="msg-container">
    <div class="msg-item" v-for="(item, index) in messageList" :key="index">
      <div class="item-left">
        <img :src="item.avatar" alt="Avatar" class="avatar" />
      </div>
      <div class="item-center">
        <p class="title">
          <span class="user-name">{{ item.name }}</span>
          <span v-if="type === 1">赞了我的视频</span>
          <span v-if="type === 2">在评论中提到了你</span>
        </p>
        <div class="msg-time">{{ new Date(item.created_at) }}</div>
      </div>
      <div class="item-right">
        <img :src="item.videoCover" alt="封面" class="video-cover" />
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name:'MyMessage',
  props:['messageList','type'],
  data() {
    return {
      page: 1,
      pageSize: 10,
      noMore: false,
      loading: false,
    };
  },
  methods: {

  },
};
</script>

<style scoped>
.msg-container {
  height: 620px;
  overflow-y: auto;
  margin: 16px 20px 30px;
  scrollbar-width: thin;
  scrollbar-color: #e4e6eb transparent;
}

.msg-container::-webkit-scrollbar {
  width: 6px;
}

.msg-container::-webkit-scrollbar-thumb {
  background-color: #e4e6eb;
  border-radius: 2px;
}

.msg-item {
  height: 60px;
  padding: 8px 0 16px;
  margin-right: 10px;
  border-bottom: 1px solid #e1e2e3;
}

.item-left {
  float: left;
  width: 60px;
  margin-right: 10px;
}

.item-center {
  float: left;
  width: calc(100% - 170px);
}

.title {
  font-size: 14px;
  color: #212121;
  line-height: 18px;
  margin: 2px 0 12px;
}

.user-name {
  font-weight: 600;
  cursor: pointer;
}

.msg-time {
  font-size: 12px;
  color: #999;
}

.item-right {
  float: left;
  width: 90px;
  height: 100%;
  text-align: center;
}

.avatar,
.video-cover {
  cursor: pointer;
  width: 100%;
  height: 100%;
  border-radius: 2px;
}
</style>
