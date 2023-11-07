package org.zjudevelop.playerbackbend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zjudevelop.playerbackbend.dto.UploadFileInfoDTO;
import org.zjudevelop.playerbackbend.pojo.QNDataServer;
import org.zjudevelop.playerbackbend.service.impl.UploadServiceImpl;
import org.zjudevelop.playerbackbend.utils.RestResult;

/**
 * @author sparkle6979l
 * @version 1.0
 * @date 2023/10/26 10:35
 */
@RestController
@RequestMapping(value = "/test")
@Slf4j
public class TestController {

    @Autowired
    QNDataServer qnDataServer;

    @Autowired
    UploadServiceImpl uploadService;


    @RequestMapping(value = "/upload",method = RequestMethod.GET)
    public RestResult<UploadFileInfoDTO> uploadtest(@RequestParam String filepath){
        return RestResult.success(uploadService.uploadfile(filepath,qnDataServer));
    }


}
