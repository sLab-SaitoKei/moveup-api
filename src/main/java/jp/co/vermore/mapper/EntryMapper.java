package jp.co.vermore.mapper;

import jp.co.vermore.entity.Entry;
import jp.co.vermore.form.admin.EntryListForm;

import java.util.List;

/**
 * Created by xieyoujun on 2017/11/20.
 */
public interface EntryMapper {

    int insertEntry(Entry entry);

    int insertAdminEntry(Entry entry);

    int deleteEntry(Entry entry);

    Entry getEntryById(Long id);

    Entry getEntryByUserId(Long userId,Long newsId,int type);

    List<Entry> getEntryAllByCondition(EntryListForm form);

    int getEntryCountByCondition(EntryListForm form);

    int getEntryCount();
}

