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

export function likeVideo(videoId){
    return request({
        url:`/user/likes/${videoId}`,
        method:'POST'
    })
}

export function dislikeVideo(videoId){
    return request({
        url:`/user/likes/${videoId}`,
        method:'DELETE'
    })
}

export function getVideoBySearch(keyword){
    return request({
        url:'/video/search',
        params:{
            keyword:keyword
        },
        method:'GET',
    })
}

export function uploadVideo(data){
    return request({
        url:'/video/upload',
        data:data,
        method:'POST'
    })
}