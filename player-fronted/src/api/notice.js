import request from "@/api/index";

export function getComment(){
    return request({
        url:'/message/comment',
        method:'GET'
    })
}

export function getFollow(){
    return request({
        url:'/message/follow',
        method:'GET'
    })
}

export function getLike(){
    return request({
        url:'/message/like',
        method:'GET'
    })
}

export function readMessage(messageId){
    return request({
        url:'/message/read',
        params:{
            messageId:messageId
        },
        method:'GET'
    })
}