package jp.co.vermore.service;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.common.util.StringUtil;
import jp.co.vermore.entity.*;
import jp.co.vermore.form.admin.AdminEntryForm;
import jp.co.vermore.form.admin.EntryListForm;
import jp.co.vermore.form.admin.EntryMailForm;
import jp.co.vermore.form.admin.EntryMailListForm;
import jp.co.vermore.mapper.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by xieyoujun on 2018/02/03.
 */
@Service
public class EntryService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserForeignMapper userForeignMapper;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private EntryMapper entryMapper;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private EntryMailMapper entryMailMapper;


    public long insertUser(jp.co.vermore.form.entry.EntryForm entryForm) {
        User user = new User();

        user.setStatus(Constant.USER_STATUS.EFFECT);
        user.setUuid(StringUtil.getUuid());
        user.setExpireDatetime(new Date(System.currentTimeMillis()));
        user.setCreateDatetime(new Date(System.currentTimeMillis()));
        user.setUpdateDatetime(new Date(System.currentTimeMillis()));
        user.setDelFlg(Boolean.FALSE);
        user.setNote(Constant.EMPTY_STRING);
        userMapper.insertUser(user);
        return user.getId();
    }

    public int insertUserForeign(jp.co.vermore.form.entry.EntryForm entryForm, long userId) {
        UserForeign userForeign = new UserForeign();

        userForeign.setUserId(userId);
        userForeign.setForeignType(Constant.USER_FOREIGN_TYPE.MAIL);
        userForeign.setForeignId(entryForm.getMail());
        userForeign.setIsLogin(0);
        userForeign.setAccessToken(Constant.EMPTY_STRING);
        userForeign.setRefreshToken(Constant.EMPTY_STRING);
        userForeign.setTokenSecret(Constant.EMPTY_STRING);
        userForeign.setPassword(StringUtil.sha256(entryForm.getPassword()));
        userForeign.setExpireDatetime(new Date(System.currentTimeMillis()));
        userForeign.setCreateDatetime(new Date(System.currentTimeMillis()));
        userForeign.setUpdateDatetime(new Date(System.currentTimeMillis()));
        userForeign.setDelFlg(Boolean.FALSE);
        userForeign.setNote(Constant.EMPTY_STRING);
        int count = userForeignMapper.insertUserForeign(userForeign);
        return count;
    }

    public int insertPerson(jp.co.vermore.form.entry.EntryForm entryForm, long userId) {
        Person person = new Person();

        person.setUserId(userId);
        person.setNickname(entryForm.getNickname());
        person.setFirstName(entryForm.getFirstName());
        person.setSecondName(entryForm.getSecondName());
        person.setFirstNameKana(entryForm.getFirstNameKana());
        person.setSecondNameKana(entryForm.getSecondNameKana());
        person.setGender(entryForm.getGender());
        person.setMailAddress(entryForm.getMail());

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, entryForm.getBirthyear());
        cal.set(Calendar.MONTH, entryForm.getBirthmonth() - 1);
        cal.set(Calendar.DAY_OF_MONTH, entryForm.getBirthday());

        person.setBirthday(cal.getTime());
        person.setArea(0);
        person.setThumbnailUrl(Constant.EMPTY_STRING);
        person.setCareer(entryForm.getOccupation());
        person.setZipcode(entryForm.getZipcode1());
        person.setAddress(entryForm.getAddress());
        person.setCreateDatetime(new Date(System.currentTimeMillis()));
        person.setUpdateDatetime(new Date(System.currentTimeMillis()));
        person.setDelFlg(Boolean.FALSE);
        person.setNote("");
        int count = personMapper.insertPerson(person);
        return count;
    }

    public int insertEntry(jp.co.vermore.form.entry.EntryForm entryForm, long userId) {
        Entry entry = new Entry();

        entry.setUserId(userId);
        entry.setEntryId(entryForm.getIsEntry() ? 1L : 0); // todo: to constant
        entry.setEntryType(1); // todo: to constant
        entry.setCreateDatetime(new Date(System.currentTimeMillis()));
        entry.setUpdateDatetime(new Date(System.currentTimeMillis()));
        entry.setDelFlg(Boolean.FALSE);
        entry.setNote(Constant.EMPTY_STRING);
        int count = entryMapper.insertEntry(entry);
        return count;
    }


    public UserForeign getUserForeignByMail(String mail) {

        UserForeign entity = userForeignMapper.getByMail(mail);

        return entity;
    }

    public User getUserById(long userId) {
        User entity = userMapper.getUserById(userId);
        return entity;
    }

    public Entry getEntryById(long userId) {
        Entry entity = entryMapper.getEntryById(userId);
        return entity;
    }

    public Entry getEntryByUserId(long userId,long newsId,int type) {
        Entry entity = entryMapper.getEntryByUserId(userId,newsId,type);
        return entity;
    }

    public int insertentry(Entry entry) {
        entry.setCreateDatetime(new Date(System.currentTimeMillis()));
        entry.setDelFlg(Boolean.FALSE);
        entry.setNote(Constant.EMPTY_STRING);
        int count = entryMapper.insertAdminEntry(entry);
        return count;
    }

    public List<Entry> getEntryAllByCondition(EntryListForm form) {
        List<Entry> entity = entryMapper.getEntryAllByCondition(form);
        return entity;
    }

    public int getEntryCountByCondition(EntryListForm form) {
        return entryMapper.getEntryCountByCondition(form);
    }

    public int getEntryCount() {
        return entryMapper.getEntryCount();
    }

    public int deleteEntry(AdminEntryForm form) {
        Entry entry = new Entry();
        entry.setId(form.getId());
        entry.setDelFlg(Boolean.TRUE);
        return entryMapper.deleteEntry(entry);
    }


    public AdminEntryForm getAdminEntryFormById(Long id) {
        AdminEntryForm aef = new AdminEntryForm();
        Entry entry = entryMapper.getEntryById(id);
        Person person = personMapper.getPersonById(entry.getUserId());
        News news = newsMapper.getNewsById(entry.getEntryId());
        aef.setEntry(entry);
        aef.setPerson(person);
        aef.setNews(news);
        return aef;

    }

    public int deleteEntryByID(long id) {
        Entry entry = new Entry();
        entry.setId(id);
        entry.setDelFlg(Boolean.TRUE);
        return entryMapper.deleteEntry(entry);
    }

    public int insertEntryMail(EntryMailForm form) {

        EntryMail entryMail = new EntryMail();
        entryMail.setEntryId(form.getEntryId());
        entryMail.setCategory((byte)0);
        entryMail.setType(form.getType());
        entryMail.setTitle(form.getTitle());
        entryMail.setSubject(form.getSubject());
        entryMail.setDetail(form.getDetail());
        if(form.getPublishStart() == null || "".equals(form.getPublishStart())){
            entryMail.setPublishStart(DateUtil.getDefaultDate());
        }else{
            entryMail.setPublishStart(DateUtil.stringToDateyyyy_MM_dd_HH_mm(form.getPublishStart().replace("T"," ")));
        }
        if(form.getPublishEnd() == null || "".equals(form.getPublishEnd())){
            entryMail.setPublishEnd(DateUtil.getDefaultPublishEnd());
        }else{
            entryMail.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd_HH_mm(form.getPublishEnd().replace("T"," ")));
        }
        entryMail.setCreateDatetime(new Date(System.currentTimeMillis()));
        entryMail.setDelFlg(Boolean.FALSE);
        entryMail.setNote(Constant.EMPTY_STRING);
        int count = entryMailMapper.insertEntryMail(entryMail);
        return count;
    }

    public int updateEntryMail(EntryMailForm form) {
        EntryMail entryMail = new EntryMail();
        entryMail.setId(form.getId());
        entryMail.setSubject(form.getSubject());
        entryMail.setDetail(form.getDetail());
        if(form.getPublishStart() == null || "".equals(form.getPublishStart())){
            entryMail.setPublishStart(DateUtil.getDefaultDate());
        }else{
            entryMail.setPublishStart(DateUtil.stringToDateyyyy_MM_dd_HH_mm(form.getPublishStart().replace("T"," ")));
        }
        if(form.getPublishEnd() == null || "".equals(form.getPublishEnd())){
            entryMail.setPublishEnd(DateUtil.getDefaultPublishEnd());
        }else{
            entryMail.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd_HH_mm(form.getPublishEnd().replace("T"," ")));
        }
        entryMail.setUpdateDatetime(new Date(System.currentTimeMillis()));
        entryMail.setDelFlg(Boolean.FALSE);
        entryMail.setNote(Constant.EMPTY_STRING);
        int count = entryMailMapper.updateEntryMail(entryMail);
        return count;
    }

    public int deleteEntryMail(EntryMailForm form) {
        EntryMail entry = new EntryMail();
        entry.setId(form.getId());
        entry.setDelFlg(Boolean.TRUE);
        return entryMailMapper.deleteEntryMail(entry);
    }

    public List<EntryMail> getEntryMailAllByCondition(EntryMailListForm form) {
        List<EntryMail> list = entryMailMapper.getEntryMailAllByCondition(form);
        return list;
    }

    public int getEntryMailCountByCondition(EntryMailListForm form) {
        return entryMailMapper.getEntryMailCountByCondition(form);
    }

    public int getEntryMailCount() {
        return entryMailMapper.getEntryMailCount();
    }

    public EntryMail getEntryMailById(long id) {
        EntryMail entity = entryMailMapper.getEntryMailById(id);
        return entity;
    }

    public EntryMail getEntryMailByEntryIdAndType(long id,int type) {
        EntryMail entity = entryMailMapper.getEntryMailByEntryIdAndType(id ,type);
        if(entity != null){
            return entity;
        }else {
            return null;
        }
    }
}
