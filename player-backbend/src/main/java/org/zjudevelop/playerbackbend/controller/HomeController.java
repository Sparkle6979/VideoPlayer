package org.zjudevelop.playerbackbend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zjudevelop.playerbackbend.dto.CategoryInfoDTO;
import org.zjudevelop.playerbackbend.dto.VideoInfoDTO;
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
public class HomeController {
    @Autowired
    private VideoService videoService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/category/detail",method = RequestMethod.GET)
    public RestResult<List<CategoryInfoDTO>> getCategoryInfoList(){
        List<CategoryInfoDTO> categoryInfoList = categoryService.getCategoryInfoList();
        return RestResult.success(categoryInfoList);
    }

    @RequestMapping(value = "/video")
    public RestResult<List<VideoInfoDTO>> getVideoInfoListByCategoryId(@RequestParam Long categoryId){
        List<VideoInfoDTO> videoInfoList = videoService.getVideoInfoListByCategoryId(categoryId);
        return RestResult.success(videoInfoList);
    }
}
