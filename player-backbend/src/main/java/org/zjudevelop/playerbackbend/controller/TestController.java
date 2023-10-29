package org.zjudevelop.playerbackbend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zjudevelop.playerbackbend.dto.UploadVideoDTO;
import org.zjudevelop.playerbackbend.pojo.LocalFile;
import org.zjudevelop.playerbackbend.pojo.QNDataServer;
import org.zjudevelop.playerbackbend.service.impl.UploadServiceImpl;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/26 10:35
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
    public String uploadtest(@RequestParam String filepath){
        UploadVideoDTO uploadVideoDTO = new UploadVideoDTO(new LocalFile(filepath),qnDataServer);
        uploadService.uploadfile(uploadVideoDTO.getLocalFile(),uploadVideoDTO.getQnDataServer());
        return "success";
    }


}
