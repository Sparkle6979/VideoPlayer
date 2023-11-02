package org.zjudevelop.playerbackbend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zjudevelop.playerbackbend.dto.UploadVideoDTO;
import org.zjudevelop.playerbackbend.dto.VideoInfoDTO;
import org.zjudevelop.playerbackbend.service.UploadService;
import org.zjudevelop.playerbackbend.utils.RestResult;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/2 14:33
 */
@Slf4j
@RestController
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private UploadService uploadService;

    public RestResult<VideoInfoDTO> uploadVideo(UploadVideoDTO){

    }
}
