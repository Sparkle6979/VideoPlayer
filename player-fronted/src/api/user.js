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