package jp.co.vermore.mapper;


import jp.co.vermore.entity.News;
import jp.co.vermore.entity.Person;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * Created by xieyoujun on 2017/11/20.
 */
public interface PersonMapper {

    int insertPerson(Person person);

    Person getPersonById(Long id);

    Person getPersonByIdAndCondition(@Param("id") Long id, @Param("gender") int gender, @Param("career") int career);

    int updatePerson(Person person);

    int deletePerson(long userId);

    String getPersonMailByUid(long userId);

    Person getPersonByMail(String mail);

    List<Person> getPersonAll();

    List<Person> getPersonByMailNull();
}
