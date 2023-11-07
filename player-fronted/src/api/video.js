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
        data: data,
        method:'PUT',
    })
}

export function getPlayerVideo(page,pageSize){
    return request({
        url:'/video',
        params:{
            page:page,
            pageSize:pageSize
        },
        method:'GET'
    })
}

export function getVideoComment(videoId,page,pageSize){
    return request({
        url:'/video/comment',
        data:{
            videoId:videoId,
            page:page,
            pageSize:pageSize
        },
        method:'POST'
    })
}