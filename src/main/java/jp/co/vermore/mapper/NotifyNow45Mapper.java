package jp.co.vermore.mapper;

import jp.co.vermore.entity.NotifyNow45;

import java.util.List;

public interface NotifyNow45Mapper {

    int insertNotifyNow45(NotifyNow45 record);

    int updateNotifyNow45(NotifyNow45 record);

    int deleteNotifyNow45(NotifyNow45 record);

    List<NotifyNow45> getBadgeNumForUser(long userId);

    NotifyNow45 getNotifyNow45ByUserIdAndItemId(long userId, long itemId, int notifyType);
}