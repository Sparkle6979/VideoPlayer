import Vue from "vue";
import Vuex from 'vuex';

Vue.use(Vuex)

const actions = {}
const mutations = {
    UPDATE_USER(state,value){
        state.user = value
    },
    UPDATE_ISLOGIN(state,value){
        state.isLogin = value
    },
}
let state = {
    user : '',
    isLogin : false,
}
const getters = {
    userName(state){
        return state.user.username
    }
}

export default new Vuex.Store({
    actions,
    mutations,
    state,
    getters,
})