import request from '@/api/index';
import md5 from 'js-md5'

export function userLogin(form){
    return request({
        url:'/user/login',
        data:{
            "username": form.username,
            "password": md5(form.password)
        },
        method:'POST'
    })
}

export function userRegister(form){
    return request({
        url:'/user',
        data:{
            username : form.username,
            password : md5(form.password)
        },
        method:'POST'
    })
}

export function getUserInfo(userId){
    return request({
        url:`/user/${userId}`,
        method:'GET',
    })
}

export function updateUserInfo(data){
    return request({
        url:'/user',
        data:data,
        method:'PUT'
    })
}

export function getUserLike(page,pageSize){
    return request({
        url:'/user/likes',
        params:{
          page:page,
          pageSize:pageSize
        },
        method:'GET',
    })
}

export function getUserVideo(){
    return request({
        url:'/user/creates',
        method:'GET'
    })
}

export function followListById(id,page,pageSize){
    return request({
        url:`/user/follows/following`,
        params:{
            id:id,
            page:page,
            pageSize:pageSize,
        },
        method:'GET'
    })
}

export function fanListById(id,page,pageSize){
    return request({
        url:`/user/follows/follower`,
        params:{
            id:id,
            page:page,
            pageSize:pageSize,
        },
        method:'GET'
    })
}

export function followUser(followingId){
    return request({
        url:`/user/follows/${followingId}`,
        method:'POST'
    })
}

export function unFollowUser(followingId){
    return request({
        url:`/user/follows/${followingId}`,
        method:'DELETE'
    })
}

export function commentToVideo(entityId,entityType,content){

    return request({
        url:'/user/comment',
        data:{
            content:content,
            entityId:entityId,
            entityType:entityType,
        },
        method:'POST'
    })
}

export function commentToComment(entityId,entityType,content,targetId){
    // targetID 指向对最上层评论用户的ID
    return request({
        url:'/user/comment',
        data:{
            content:content,
            entityId:entityId,
            entityType:entityType,
            targetId:targetId,
        },
        method:'POST'
    })
}

export function getCollection(page,pageSize){
    return request({
        url:'/collection',
        params:{
            page:page,
            pageSize:pageSize,
        },
        method:'GET'
    })
}

export function createCollection(data){
    return request({
        url:'/collection',
        data:data,
        method:'POST'
    })
}

export function deleteCollection(id){
    return request({
        url:'/collection',
        params:{
            id:id
        },
        method:'DELETE'
    })
}

export function getVideoByCollectionId(id,page,pageSize){
    return request({
        url:`/collection/${id}`,
        params:{
            page:page,
            pageSize:pageSize
        },
        method:'GET'
    })
}

export function doCollection(id,videoId){
    return request({
        url:`/collection/${id}`,
        params:{
            videoId:videoId,
        },
        method:'POST'
    })
}

export function undoCollection(id,videoId){
    return request({
        url:`/collection/${id}`,
        params:{
            videoId:videoId,
        },
        method:'DELETE'
    })
}