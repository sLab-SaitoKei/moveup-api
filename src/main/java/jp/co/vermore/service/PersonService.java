package jp.co.vermore.service;

import jp.co.vermore.entity.Person;
import jp.co.vermore.entity.UserSetting;
import jp.co.vermore.form.PersonRegist;
import jp.co.vermore.mapper.PersonMapper;
import jp.co.vermore.mapper.UserSettingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * PersonService
 * Created by wubin.
 *
 * DateTime: 2018/04/10 9:28
 * Copyright: sLab, Corp
 */
@Service
public class PersonService {

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private UserSettingMapper userSettingMapper;

    public Person getPersonById(Long id) {
        Person entity = personMapper.getPersonById(id);
        return entity;
    }


    public Person getPersonByIdAndCondition(Long id,int gender,int career) {
        Person entity = personMapper.getPersonByIdAndCondition(id,gender,career);
        return entity;
    }



    public String getPersonMailByUid(long userId){
        String mail = personMapper.getPersonMailByUid(userId);
        return mail;
    }

    public Person getPersonByMail(String mail){
        return personMapper.getPersonByMail(mail);
    }


    public List<UserSetting> getUserSettingById(long userId) {
        List<UserSetting> list = userSettingMapper.getUserSettingById(userId);
        return list;
    }

    public int insertPerson(Person person){
        return personMapper.insertPerson( person);
    }

    public int updatePerson(Person person){
       return personMapper.updatePerson( person);
    }

    public int insertUserSetting(UserSetting userSetting){
        return userSettingMapper.insertUserSetting( userSetting);
    }


    public int deletePerson(Long userId) {
        return personMapper.deletePerson(userId);
    }

    public List<Person> getPersonAll() {
        return personMapper.getPersonAll();
    }

    public List<Person> getPersonByMailNull() {
        return personMapper.getPersonByMailNull();
    }
}
