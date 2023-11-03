package org.zjudevelop.playerbackbend.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zjudevelop.playerbackbend.dto.*;
import org.zjudevelop.playerbackbend.pojo.QNDataServer;
import org.zjudevelop.playerbackbend.service.UploadService;
import org.zjudevelop.playerbackbend.service.VideoService;
import org.zjudevelop.playerbackbend.utils.RestResult;
import org.zjudevelop.playerbackbend.utils.FileProcessUtil;
import org.zjudevelop.playerbackbend.utils.VideoProcessUtil;

import java.util.List;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/2 14:33
 */
@Slf4j
@RestController
@RequestMapping(value = "/video")
public class VideoController {
    @Autowired
    private QNDataServer qnDataServer;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private VideoService videoService;

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public RestResult<VideoInfoDTO> uploadVideo(@RequestBody VideoUploadDTO videoUploadDTO){
        UploadFileInfoDTO videoServerFile = uploadService.uploadfile(videoUploadDTO.getVideoUrl(), qnDataServer);

        UploadFileInfoDTO coverServerFile =  new UploadFileInfoDTO();
        if(StringUtils.isBlank(videoUploadDTO.getCoverUrl())){
            byte[] coverFromVideoPath = VideoProcessUtil.getCoverFromVideoPath(videoUploadDTO.getVideoUrl(),1146,717);
            String coverName = StringUtils.join(FileProcessUtil.getFileOriginName(videoUploadDTO.getVideoUrl()),".jpg");
            coverServerFile = uploadService.uploadfile(coverName, coverFromVideoPath, qnDataServer);
        }else{
            coverServerFile = uploadService.uploadfile(videoUploadDTO.getCoverUrl(), qnDataServer);
        }

        String videoTitle = StringUtils.isBlank(videoUploadDTO.getTitle()) ?
                FileProcessUtil.getFileOriginName(videoServerFile.getServerFileUrl()) :
                videoUploadDTO.getTitle();

        VideoInsertDTO videoInsertDTO = VideoInsertDTO.builder()
                .title(videoTitle)
                .categoryId(videoUploadDTO.getCategoryId())
                .description(videoUploadDTO.getDescription())
                .videoUrl(videoServerFile.getServerFileUrl())
                .coverUrl(coverServerFile.getServerFileUrl())
                .build();


        Long videoId = videoService.addVideoInfo(videoInsertDTO);
        VideoInfoDTO videoInfoById = videoService.getVideoInfoById(videoId);
        return RestResult.success(videoInfoById);

    }

    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public RestResult<List<VideoSearchInfoDTO>> getVideoSearchInfoByKeyword(@RequestParam String keyword) {
        List<VideoSearchInfoDTO> videoInfoByKeyword = videoService.getVideoInfoByKeyword(keyword);
        return RestResult.success(videoInfoByKeyword);
    }


}
