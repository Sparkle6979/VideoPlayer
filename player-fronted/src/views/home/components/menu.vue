<template>
  <el-container>
    <el-header style="text-align: center;display: flex;justify-content: center;align-items: center;">
          <span style="cursor: pointer" @click="$router.push('/home')">VideoPlayer</span>
    </el-header>
    <el-main>
      <el-button class="categoryBtn" style="margin-left: 10px" round @click="$router.push('/home/discover')" icon="el-icon-search">
        发现
      </el-button>
      <el-button class="categoryBtn" style="margin-left: 10px" round
                 v-for="c in categoryList" :key="c.categoryId" @click="toCategoryPage(c.categoryId)">{{ c.categoryName }}</el-button>
    </el-main>
  </el-container>
</template>

<script>
import {getCategoryInfoList} from "@/api/category";

export default {
  name: "myMenu",
  data(){
    const categoryList = ''
    return {
      categoryList,
    }
  },
  methods:{
    initData(){
      getCategoryInfoList().then((res) => {
        this.categoryList = res.data
      });
    },
    toCategoryPage(id){
      this.$router.push({
        name:'Category',
        params:{
          id : id,
        }
      }).catch(err => err)
    }
  },
  mounted() {
    this.initData()
  }
}
</script>

<style scoped>
.categoryBtn {
  width: 80%;
  margin-top: 10px;
  border: none;
}
</style>