import Vue from 'vue'
import App from './App.vue'
import store from './store'

import VueRouter from "vue-router";
import router from "@/router";
Vue.use(VueRouter)

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(ElementUI)

import "@/components/SvgIcon/svgicon"

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
  store,
  router:router,
}).$mount('#app')
