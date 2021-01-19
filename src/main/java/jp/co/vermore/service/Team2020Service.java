package jp.co.vermore.service;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.util.StringUtil;
import jp.co.vermore.entity.Pic;
import jp.co.vermore.entity.Team2020;
import jp.co.vermore.form.admin.Team2020Form;
import jp.co.vermore.form.admin.Team2020ListForm;
import jp.co.vermore.mapper.PicMapper;
import jp.co.vermore.mapper.Team2020Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Team2020Service
 * Created by wangnannan.
 * <p>
 * DateTime: 2018/05/18 16:50
 * Copyright: sLab, Corp
 */
@Service
public class Team2020Service {

    @Autowired
    private Team2020Mapper team2020Mapper;

    @Autowired
    private PicMapper picMapper;

    public Team2020 getTeam2020ByUuid(String uuid) {
        Team2020 entity = team2020Mapper.getTeam2020ByUuid(uuid);
        return entity;
    }

    public List<Team2020> getTeam2020All(int type, int limit, int offset) {
        List<Team2020> team2020List = team2020Mapper.getTeam2020All(type, limit, offset);
        return team2020List;
    }


    public List<Team2020> getTeam2020Count() {
        List<Team2020> count = team2020Mapper.getTeam2020Count();
        return count;
    }

    public List<Team2020> getTeam2020AllByCondition(Team2020ListForm form) {
        List<Team2020> team = team2020Mapper.getTeam2020AllByCondition(form);
        return team;
    }

    public int getTeam2020CountByCondition(Team2020ListForm form) {
        return team2020Mapper.getTeam2020CountByCondition(form);
    }

    public int getTeamCount() {
        return team2020Mapper.getNewsCount();
    }

    public int deleteTeam2020(Team2020Form form) {
        Team2020 team2020 = new Team2020();
        team2020.setId(form.getId());
        team2020.setDelFlg(Boolean.TRUE);
        int count = team2020Mapper.deleteTeam2020(team2020);
        System.out.println(count);
        return count;
    }

    public int deleteTeam2020Url(Team2020Form form) {
        Pic pic = new Pic();
        pic.setItemId(form.getId());
        pic.setDelFlg(Boolean.TRUE);
        int count = picMapper.deleteTeam2020Url(pic);
        System.out.println(count);
        return count;
    }

    public Long insertTeam2020(Team2020Form form) {
        Team2020 entity = new Team2020();
        String uuid = "";
        int flagUuid = 0;
        int cntSelect = 0;
        while (flagUuid != 1 && cntSelect < 100) {
            uuid = StringUtil.getUuid();
            if (getTeam2020ByUuid(uuid) == null) {
                flagUuid = 1;
            }
            cntSelect++;
        }

        entity.setUuid(uuid);
        entity.setName(form.getName());
        entity.setPseudonym(form.getPseudonym());
        entity.setCategory(Byte.valueOf("0"));
        entity.setCreateDatetime(new Date(System.currentTimeMillis()));
        entity.setDelFlg(Boolean.FALSE);
        entity.setNote(Constant.EMPTY_STRING);
        team2020Mapper.insertTeam2020(entity);
        return entity.getId();
    }

    public Team2020 getTeam2020ById(Long id) {
        Team2020 entity = team2020Mapper.getTeam2020ById(id);
        return entity;
    }

    public long updateTeam2020(Team2020Form form) {
        Team2020 entity = new Team2020();
        entity.setId(form.getId());
        entity.setName(form.getName());
        entity.setPseudonym(form.getPseudonym());
        entity.setCategory(Byte.valueOf("0"));
        entity.setUpdateDatetime(new Date(System.currentTimeMillis()));
        entity.setDelFlg(Boolean.FALSE);
        entity.setNote(Constant.EMPTY_STRING);
        team2020Mapper.updateTeam2020(entity);
        return entity.getId();
    }
}
