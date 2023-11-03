import request from "@/api/index";

export function getCategoryInfoList(){
    return request({
        url: '/home/category/detail',
        method: 'GET'
    })
}

export function getCategoryInfoById(categoryId){
    return request({
        url:'/home/categoryinfo',
        params:{
            categoryId:categoryId
        },
        method:'GET'
    })
}