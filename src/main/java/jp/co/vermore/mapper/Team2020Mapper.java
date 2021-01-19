package jp.co.vermore.mapper;

import jp.co.vermore.entity.Team2020;
import jp.co.vermore.form.admin.Team2020ListForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface Team2020Mapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Team2020 record);

    int insertSelective(Team2020 record);

    Team2020 selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Team2020 record);

    int updateByPrimaryKey(Team2020 record);

    List<Team2020> getTeam2020All(int type, @Param("limit") int limit,@Param("offset") int offset);

    List<Team2020> getTeam2020Count();

    List<Team2020> getTeam2020AllByCondition(Team2020ListForm form);

    int getTeam2020CountByCondition(Team2020ListForm form);

    int getNewsCount();

    int deleteTeam2020(Team2020 team2020);

    Team2020 getTeam2020ByUuid(String uuid);

    void insertTeam2020(Team2020 entity);

    Team2020 getTeam2020ById(Long id);

    void updateTeam2020(Team2020 entity);
}