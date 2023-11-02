const path = require("path")
const { defineConfig } = require('@vue/cli-service')
const resolve = (dir) => path.join(__dirname,dir)
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave:false,
  devServer:{
    port:8081,
    proxy:{
      '/api':{
        target:'http://localhost:8080',
        changeOrigin:true,
        pathRewrite:{
          '^/api': ''
        }
      }
    }
  },
  configureWebpack:{
    resolve:{
      alias:{
        '@':resolve('src')
      }
    }
  },
  chainWebpack(config){
    config.module.rule('svg').exclude.add(resolve("./src/assets/icons")).end();
    config.module
        .rule("icons")
        .test(/\.svg$/)
        .include.add(resolve("./src/assets/icons"))
        .end()
        .use("svg-sprite-loader")
        .loader("svg-sprite-loader")
        .options({
          symbolId:"icon-[name]",
        })
        .end()
  }
})
