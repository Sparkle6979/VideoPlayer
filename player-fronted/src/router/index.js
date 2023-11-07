import VueRouter from "vue-router";
import Login from "@/views/login";
import Home from "@/views/home";
import Detail from "@/views/home/pages/detail.vue";
import All from "@/views/home/pages/all.vue";
import Category from "@/views/home/pages/category";
import Discover from "@/views/home/pages/discover";
import Upload from "@/views/home/pages/upload";
import User from "@/views/home/pages/user";

const router = new VueRouter({
    routes:[
        {
            path:'/',
            redirect:'/home',
        },
        {
            path:'/login',
            component:Login,
        },
        {
            path:'/home',
            component:Home,
            children:[
                {
                    path:'detail/:id',
                    name:'Detail',
                    component: Detail
                },
                {
                    path:'',
                    component: All
                },
                {
                    path:'category/:id',
                    name:'Category',
                    component: Category
                },
                {
                    path:'discover',
                    component:Discover
                },
                {
                    path:'upload',
                    component:Upload,
                    meta:{title:'视频上传'}
                },
                {
                    path:'user',
                    name:'User',
                    component:User,
                    meta:{title: '个人中心'}
                }
            ]
        },

    ]
})

export default router