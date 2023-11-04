<template>
  <div class="wrapper">
    <div style="margin:200px auto;background-color: #fff;width: 450px;padding: 20px;border-radius: 10px;"
         :class="{'flip-horizontal-bottom': trigger}"
          v-loading="loading"
         :element-loading-text='isRegister ? "注册中" : "登录中"'
         element-loading-spinner="el-icon-loading">
      <div v-if="!isRegister">
        <div style="margin: 20px 0; text-align: center; font-size: 24px;"><b>登录</b></div>
        <el-form ref="loginForm" :model="loginForm" label-width="80px" label-position="left" :rules="rules">
          <el-form-item label="账号" prop="username">
            <el-input  prefix-icon="el-icon-user" v-model="loginForm.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input prefix-icon='el-icon-lock' show-password v-model="loginForm.password"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="small" autocomplete="off" @click="login">登录</el-button>
            <el-button type="warning" size="small" autocomplete="off"  @click="changeForm">前往注册</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div v-else>
        <div style="margin: 20px 0; text-align: center; font-size: 24px;"><b>注册</b></div>
        <el-form ref="registerForm" :model="registerForm" label-width="80px" label-position="left" :rules="rules">
          <el-form-item label="账号" prop="username">
            <el-input  prefix-icon="el-icon-user" v-model="registerForm.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input prefix-icon='el-icon-lock' show-password v-model="registerForm.password"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="small" autocomplete="off" @click="register">注册</el-button>
            <el-button type="warning" size="small" autocomplete="off"  @click="changeForm">去登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>

import {mapMutations} from "vuex";
import {getUserInfo, userLogin, userRegister} from "@/api/user";

export default {
  name: "index",
  data(){
    let loginForm = {
      username: '',
      password: '',
    }
    let registerForm = {
      username: '',
      password: '',
    }
    const rules = {
      username:[{required:true,message:"请输入用户名",trigger:'blur'}],
      password:[{required:true,message:"请输入用密码",trigger:'blur'}]
    }
    const isRegister = false
    const trigger = false
    const loading = false
    return{
      loginForm,registerForm,
      isRegister,trigger,
      loading,rules,
    }
  },
  methods:{
    ...mapMutations({updateUser :'UPDATE_USER',updateLogin:'UPDATE_ISLOGIN'}),
    login(){
      this.$refs["loginForm"].validate((valid)=>{
        if(valid){
          this.loading = true
          userLogin(this.loginForm).then((res)=>{
            localStorage.setItem("token",res.data.token)
            getUserInfo(res.data.id).then((res)=>{
              this.updateUser({
                id:res.data.id,
                username:res.data.username,
                avatarPath:res.data.avatarPath,
              })
              this.updateLogin(true)
              this.$router.push('/home')
            })
          }).catch(err => {
            console.log(err)
          }).finally(()=>{
            this.loading = false
          })
        }else{
          return false
        }
      })
    },
    register(){
      this.$refs["registerForm"].validate((valid)=>{
        if(valid){
          this.loading = true
          userRegister(this.registerForm).then((res)=>{
            this.$message({
              message:"注册成功，请前往登录",
              type:'success',
            })
            this.changeForm()
          }).catch(err => {
            console.log(err)
          }).finally(()=>{
            this.loading = false
            this.registerForm = {}
          })
        }else {
          return false;
        }
      })
    },
    changeForm(){
      this.trigger = false
      setTimeout(() => {
        this.trigger = true; // 设置为true以重新触发动画
      }, 10);
      setTimeout(()=>{
        this.isRegister = !this.isRegister
      },200)
    }
  }
}
</script>

<style scoped>
.wrapper {
  height: 100vh;
  background-image: linear-gradient(to bottom right, #FC466B, #3F5EFB);
  overflow: hidden;
}

.flip-horizontal-bottom {
  -webkit-animation: flip-horizontal-bottom 0.8s cubic-bezier(0.455, 0.030, 0.515, 0.955) both;
  animation: flip-horizontal-bottom 0.8s cubic-bezier(0.455, 0.030, 0.515, 0.955) both;
}

@-webkit-keyframes flip-horizontal-bottom {
  0% {
    -webkit-transform: rotateX(0);
    transform: rotateX(0);
  }
  100% {
    -webkit-transform: rotateX(-360deg);
    transform: rotateX(-360deg);
  }
}
@keyframes flip-horizontal-bottom {
  0% {
    -webkit-transform: rotateX(0);
    transform: rotateX(0);
  }
  100% {
    -webkit-transform: rotateX(-360deg);
    transform: rotateX(-360deg);
  }
}
</style>