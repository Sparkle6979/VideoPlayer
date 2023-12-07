package org.zjudevelop.playerbackbend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zjudevelop.playerbackbend.domain.dto.PageQueryDTO;
import org.zjudevelop.playerbackbend.domain.po.Collection;
import org.zjudevelop.playerbackbend.utils.PageResult;

public interface CollectionService extends IService<Collection> {
    int createCollection(Long userId, String collectionName);

    int removeCollection(Long collectionId);

    PageResult getCollectionsByUserID(Long userId, PageQueryDTO pageQueryDTO);

    int collect(Long collectionId, Long videoId);

    int cancelCollect(Long collectionId, Long videoId);

    PageResult getCollectionVideoByCollectionId(Long collectionId, PageQueryDTO pageQueryDTO);
}
