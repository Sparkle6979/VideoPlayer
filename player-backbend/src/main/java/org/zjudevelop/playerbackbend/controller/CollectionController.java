package org.zjudevelop.playerbackbend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zjudevelop.playerbackbend.common.context.BaseContext;
import org.zjudevelop.playerbackbend.dto.CollectionInfoDTO;
import org.zjudevelop.playerbackbend.dto.PageQueryDTO;
import org.zjudevelop.playerbackbend.service.CollectionService;
import org.zjudevelop.playerbackbend.utils.PageResult;
import org.zjudevelop.playerbackbend.utils.RestResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "collection")
@Api(tags = "收藏夹")
public class CollectionController {

    @Autowired
    CollectionService collectionService;

    /**
     * 创建收藏夹
     * @param collectionName 收藏夹名字
     * @return org.zjudevelop.playerbackbend.utils.RestResult
     * */
    @PostMapping
    @ApiOperation("创建收藏夹")
    public RestResult createCollection(@ApiParam("收藏夹名字") @RequestParam(required = true) String collectionName) {
        int result = collectionService.createCollection(BaseContext.getCurrentUserId(), collectionName);
        if (result <= 0) {
            return RestResult.fail("创建失败");
        }
        return RestResult.success("创建成功");
    }

    /**
     * 获取收藏夹信息（包括其中视频的详细信息）
     * @param pageQueryDTO
     * @return org.zjudevelop.playerbackbend.utils.RestResult<org.zjudevelop.playerbackbend.dto.CollectionInfoDTO>
     * */
    @GetMapping
    @ApiOperation("获取收藏夹列表")
    public RestResult<PageResult> getCollection(PageQueryDTO pageQueryDTO) {
        PageResult pageResult = collectionService.getCollectionsByUserID(BaseContext.getCurrentUserId(), pageQueryDTO);
        List<Collection> collectionList = pageResult.getRecords();
        List<CollectionInfoDTO> collectionInfoDTOList = new ArrayList<>();
        for (int i = 0; i < collectionList.size(); i++) {
            CollectionInfoDTO collectionInfoDTO = new CollectionInfoDTO();
            BeanUtils.copyProperties(collectionList.get(i), collectionInfoDTO);
            collectionInfoDTOList.add(collectionInfoDTO);
        }
        pageResult.setRecords(collectionInfoDTOList);
        return RestResult.success(pageResult);
    }

    @DeleteMapping("")
    @ApiOperation("删除收藏夹")
    public RestResult removeCollection(@RequestParam Long id) {
        collectionService.removeCollection(id);
        return RestResult.success();
    }

    /**
     * 根据收藏夹id分页获取收藏视频
     * @param collectionId 收藏夹Id
     * @return org.zjudevelop.playerbackbend.utils.RestResult<java.util.List<org.zjudevelop.playerbackbend.dto.VideoInfoDTO>>
     * */
    @GetMapping("/{id}")
    @ApiOperation("获取指定收藏夹对应视频")
    public RestResult<PageResult> getVideosByCollectionId(@PathVariable("id") Long collectionId,
                                                                  PageQueryDTO pageQueryDTO) {
        PageResult pageResult = collectionService.getCollectionVideoByCollectionId(collectionId, pageQueryDTO);
        return RestResult.success(pageResult);
    }


    /**
     * 收藏视频到收藏夹中
     * @param collectionId 收藏夹id
	 * @param videoId 视频id
     * @return org.zjudevelop.playerbackbend.utils.RestResult
     * */
    @PostMapping("/{id}")
    @ApiOperation("收藏视频")
    public RestResult collect(@PathVariable("id") Long collectionId, @RequestParam(required = true) Long videoId) {
        int result = 0;
        try {
            collectionService.collect(collectionId, videoId);
        } catch (RuntimeException ex) {
            return RestResult.fail("收藏失败");
        }
        if (result <= 0) {
            RestResult.fail("收藏失败");
        }
        return RestResult.success("收藏成功");
    }

    /**
     * 取消收藏视频到收藏夹中
     * @param collectionId
	 * @param videoId
     * @return org.zjudevelop.playerbackbend.utils.RestResult
     * */
    @DeleteMapping("/{id}")
    @ApiOperation("取消收藏")
    public RestResult cancelCollect(@PathVariable("id") Long collectionId, @RequestParam(required = true) Long videoId) {
        int result = collectionService.cancelCollect(collectionId, videoId);
        if (result <= 0) {
            RestResult.fail("取消收藏失败");
        }
        return RestResult.success("取消收藏成功");
    }
}
