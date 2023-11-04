<template>
  <router-view></router-view>
</template>

<script>

export default {
  name: 'App',
  created() {
    if(localStorage.getItem("player-fronted")) {
      this.$store.state.user = JSON.parse(localStorage.getItem("player-fronted")).user
      this.$store.state.isLogin = JSON.parse(localStorage.getItem("player-fronted")).isLogin
      localStorage.removeItem("player-fronted")
    }
    window.addEventListener("beforeunload",() => {
      localStorage.setItem("player-fronted",JSON.stringify(this.$store.state))
    })
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
