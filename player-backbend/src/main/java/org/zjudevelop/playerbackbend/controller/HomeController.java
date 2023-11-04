package org.zjudevelop.playerbackbend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zjudevelop.playerbackbend.dto.CategoryInfoDTO;
import org.zjudevelop.playerbackbend.dto.VideoInfoDTO;
import org.zjudevelop.playerbackbend.pojo.CheckAuth;
import org.zjudevelop.playerbackbend.service.CategoryService;
import org.zjudevelop.playerbackbend.service.VideoService;
import org.zjudevelop.playerbackbend.utils.RestResult;

import java.util.List;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/27 21:00
 */

@Slf4j
@RestController
@RequestMapping(value = "/home")
@Api(tags = "分类页")
public class HomeController {
    @Autowired
    private VideoService videoService;

    @Autowired
    private CategoryService categoryService;

    @CheckAuth(check = false)
    @ApiOperation("获取所有分类信息")
    @RequestMapping(value = "/category/detail",method = RequestMethod.GET)
        public RestResult<List<CategoryInfoDTO>> getCategoryInfoList(){
        List<CategoryInfoDTO> categoryInfoList = categoryService.getCategoryInfoList();
        return RestResult.success(categoryInfoList);
    }

    @CheckAuth(check = false)
    @ApiOperation("根据categoryId获取视频信息")
    @RequestMapping(value = "/videolist", method = RequestMethod.GET)
    public RestResult<List<VideoInfoDTO>> getVideoInfoListByCategoryId(@RequestParam Long categoryId){
        List<VideoInfoDTO> videoInfoList = videoService.getVideoInfoListByCategoryId(categoryId);
        return RestResult.success(videoInfoList);
    }
    @CheckAuth(check = false)
    @ApiOperation("根据videoId获取视频信息")
    @RequestMapping(value = "/videoinfo", method = RequestMethod.GET)
    public RestResult<VideoInfoDTO> getVideoInfoByVideoId(@RequestParam Long videoId){
        VideoInfoDTO videoInfo = videoService.getVideoInfoById(videoId);
        return RestResult.success(videoInfo);
    }
    @CheckAuth(check = false)
    @ApiOperation("根据categoryId获取分类信息")
    @RequestMapping(value = "/categoryinfo", method = RequestMethod.GET)
    public RestResult<CategoryInfoDTO> getCategoryInfoByCategoryId(@RequestParam Long categoryId){
        CategoryInfoDTO categoryInfo = categoryService.getCategoryInfoById(categoryId);
        return RestResult.success(categoryInfo);
    }
}
