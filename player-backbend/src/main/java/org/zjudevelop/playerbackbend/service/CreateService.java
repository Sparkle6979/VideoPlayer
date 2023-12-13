package org.zjudevelop.playerbackbend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zjudevelop.playerbackbend.domain.po.Creates;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/12/6 20:58
 */
public interface CreateService extends IService<Creates> {
    Creates getCreatesInfoByVideoId(Long videoId);
}
