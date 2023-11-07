import Vue from "vue";
import SvgIcon from "@/components/SvgIcon/index";

Vue.component("svg-icon",SvgIcon)

const requireAll=(requireContext)=>{
    requireContext.keys().map(requireContext)
}

const req = require.context('@/assets/icons',false,/.svg$/);

requireAll(req)