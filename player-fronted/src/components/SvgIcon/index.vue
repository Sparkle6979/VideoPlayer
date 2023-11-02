<template>
  <div :style="{ width: width, height: height, display: 'inline-block' }">
      <svg :style="{ width: width, height: height }">
        <use :xlink:href="prefix + name" :fill="color"></use>
      </svg>
  </div>
</template>

<script>
export default {
  name: "index",
  props:{
    prefix:{
      type:String,
      default:"#icon-"
    },
    name:{
      type:String,
      required:true
    },
    color:{
      type:String,
      default: ""
    },
    width:{
      type: String,
      default: "20px",
    },
    height:{
      type: String,
      default: "20px",
    }
  }
}
</script>

<style scoped>

</style>