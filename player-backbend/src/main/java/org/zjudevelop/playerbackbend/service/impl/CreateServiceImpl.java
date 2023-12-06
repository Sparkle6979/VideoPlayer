package org.zjudevelop.playerbackbend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.zjudevelop.playerbackbend.dao.CreatesMapper;
import org.zjudevelop.playerbackbend.domain.po.Creates;
import org.zjudevelop.playerbackbend.service.CreateService;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/12/6 21:01
 */
@Service
public class CreateServiceImpl extends ServiceImpl<CreatesMapper, Creates> implements CreateService {
}
