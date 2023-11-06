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