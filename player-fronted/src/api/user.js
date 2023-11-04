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
        url:'/user/register',
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

export function getUserLike(){
    return request({
        url:'/user/likes',
        method:'GET',
    })
}