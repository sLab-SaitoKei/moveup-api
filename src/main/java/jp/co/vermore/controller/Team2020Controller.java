package jp.co.vermore.controller;

import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.entity.Pic;
import jp.co.vermore.entity.Team2020;
import jp.co.vermore.jsonparse.Team2020JsonParse;
import jp.co.vermore.service.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import jp.co.vermore.service.Team2020Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Team2020Controller
 * Created by wangnannan.
 * <p>
 * DateTime: 2018/05/18 16:50
 * Copyright: sLab, Corp
 */
@Controller
public class Team2020Controller extends BaseController {

    @Autowired
    private Team2020Service team2020Service;

    @Autowired
    private PicService picService;

    @RequestMapping(value = "/api/team2020/list/{type}/{limit}/{offset}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getTeam2020List(@PathVariable int type, @PathVariable int limit, @PathVariable int offset) {
        List<Team2020> teamList = team2020Service.getTeam2020All(type, limit, offset);
        List<Team2020> count = team2020Service.getTeam2020Count();
        List<Team2020JsonParse> list  = new ArrayList<>();
        if (teamList != null) {
            for (Team2020 team : teamList) {
                Team2020JsonParse tjp = new Team2020JsonParse();
                tjp.setId(team.getId());
                tjp.setName(team.getName());
                List<Pic> picUrl = picService.getPicUrl(team.getId());
                List<String> pic = new ArrayList<>();
                for (Pic url : picUrl) {
                    pic.add(url.getPicUrl());
                }
                tjp.setPic(pic);
                list.add(tjp);
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("team2020List", list);
        map.put("team2020Count", count.size());
        jsonObject.setResultList(map);
        return jsonObject;
    }
}
