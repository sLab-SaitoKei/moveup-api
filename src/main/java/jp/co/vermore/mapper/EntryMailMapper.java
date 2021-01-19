package jp.co.vermore.mapper;

import jp.co.vermore.entity.EntryMail;
import jp.co.vermore.form.admin.EntryMailListForm;

import java.util.List;

public interface EntryMailMapper {

    int insertEntryMail(EntryMail entryMail);

    int updateEntryMail(EntryMail entryMail);

    int deleteEntryMail(EntryMail entryMail);

    List<EntryMail> getEntryMailAllByCondition(EntryMailListForm form);

    int getEntryMailCountByCondition(EntryMailListForm form);

    int getEntryMailCount();

    EntryMail getEntryMailById(long id);

    EntryMail getEntryMailByEntryIdAndType(long id,int type);
}