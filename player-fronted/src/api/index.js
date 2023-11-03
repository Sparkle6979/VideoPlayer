import axios from "axios";
// 全局进度条，可以用来做复杂请求的标识
// import nprogress from 'nprogress';
// import "nprogress/nprogress.css"

const tokenType = 'Bearer'

const request = axios.create({
    baseURL: '/api',
    timeout: 5000
});

request.interceptors.request.use(
    (config)=>{
        config.headers['Authorization']=localStorage.getItem('token')
        return config
    },
    (error)=>{
        return Promise.reject(error)
    }
)

request.interceptors.response.use(
    res => {
        return res.data
    },
    error => {
        const {response} = error
        if (response) {
            const status = response.status
            console.log(status)
            switch (status) {
                case 401:
                    alert("用户没有权限")
                    break;
            }
        }else{
            if(!window.navigator.onLine) {
                alert('当前网络不可用,请检查你的网络连接')
                return
            }else {
                alert('连接服务器错误'+error?.message)
                return Promise.reject(error)
            }
        }
        return Promise.reject(error)
    }
)

export default request