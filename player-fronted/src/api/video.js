import request from "@/api/index";

export function getVideoListByCId(categoryId){
    return request({
        url:'/home/videolist',
        params:{
            categoryId:categoryId
        },
        methods:'GET'
    })
}

export function getVideoById(videoId){
    return request({
        url:'/home/videoinfo',
        params:{
            videoId :videoId
        },
        methods:'GET'
    })
}