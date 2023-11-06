package org.zjudevelop.playerbackbend.service;

import org.zjudevelop.playerbackbend.domain.Collection;
import org.zjudevelop.playerbackbend.dto.CollectionInfoDTO;
import org.zjudevelop.playerbackbend.dto.PageQueryDTO;
import org.zjudevelop.playerbackbend.utils.PageResult;

import java.util.List;

public interface CollectionService {
    int createCollection(Long userId, String collectionName);

    int removeCollection(Long collectionId);

    PageResult getCollectionsByUserID(Long userId, PageQueryDTO pageQueryDTO);

    int collect(Long collectionId, Long videoId);

    int cancelCollect(Long collectionId, Long videoId);

    PageResult getCollectionVideoByCollectionId(Long collectionId, PageQueryDTO pageQueryDTO);
}