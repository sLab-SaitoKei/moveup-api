package jp.co.vermore.service;


import jp.co.vermore.common.Constant;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.common.util.StringUtil;
import jp.co.vermore.entity.Event;
import jp.co.vermore.entity.Tv;
import jp.co.vermore.form.admin.TVListForm;
import jp.co.vermore.form.admin.TvRegistForm;
import jp.co.vermore.mapper.PicMapper;
import jp.co.vermore.mapper.TvMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * TvService
 * Created by wangnannan.
 * <p>
 * DateTime: 2018/06/08 14:35
 * Copyright: sLab, Corp
 */
@Service
public class TvService {

    @Autowired
    private TvMapper tvMapper;

    public List<Tv> getTvForTop() {
        String now= DateUtil.dateToStringyyyy_MM_dd_HH_mm(new Date(System.currentTimeMillis()));
        String nowMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(0);
        String nextMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(1);
        return tvMapper.getTvForTop(nowMin,nextMin);
    }

    public List<Tv> getTvListAll(int type, int limit, int offset) {
        String nowMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(0);
        String nextMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(1);
        return tvMapper.getTvListAll(type, nowMin,nextMin, limit, offset);
    }

    public List<Tv> getTvListAllByType(int type) {
        String nowMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(0);
        String nextMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(1);
        return tvMapper.getTvListAllByType(type,nowMin,nextMin);
    }

    public List<Tv> getTvList(TVListForm form) {
        return  tvMapper.getTvList(form);
    }

    public int getTotalCountFiltered(TVListForm form) {
        return tvMapper.getTotalCountFiltered(form);
    }

    public int getTotalCount() {
        return tvMapper.getTotalCount();
    }

    public Tv getTvByuuid(String uuid) { return tvMapper.getTvByuuid(uuid);
    }

    public Tv getTvFavorite(Long tvId) {
        return tvMapper.getTvFavorite(tvId);
    }

    public Long insertTv(TvRegistForm form) {
        Tv entity = new Tv();
        String uuid = "";
        int flagUuid = 0;
        int cntSelect = 0;
        while (flagUuid != 1 && cntSelect < 100){
            uuid = StringUtil.getUuid();
            if (getTvByuuid(uuid) == null){
                flagUuid = 1;
            }
            cntSelect++;
        }

        entity.setUuid(uuid);
        entity.setTitle(form.getTitle());
        entity.setOffice(form.getOffice());
        if(form.getPicUrl() ==null){
            entity.setUrl("");
        }else{
            entity.setUrl(form.getPicUrl());
        }
        entity.setTvType(form.getTvType());
        entity.setSortScore(Integer.parseInt(form.getSortScore()));
        entity.setDate(DateUtil.stringToDateyyyy_MM_dd(form.getDate()));
        if(form.getPublishStart() == null || "".equals(form.getPublishStart())){
            entity.setPublishStart(DateUtil.getDefaultDate());
        }else{
            entity.setPublishStart(DateUtil.stringToDateyyyy_MM_dd_HH_mm(form.getPublishStart().replace("T"," ")));
        }
        if(form.getPublishEnd() == null || "".equals(form.getPublishEnd())){
            entity.setPublishEnd(DateUtil.getDefaultPublishEnd());
        }else{
            entity.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd_HH_mm(form.getPublishEnd().replace("T"," ")));
        }
        entity.setCategory(Byte.valueOf("0"));
        entity.setCreateDatetime(new Date(System.currentTimeMillis()));
        entity.setDelFlg(Boolean.FALSE);
        entity.setNote(Constant.EMPTY_STRING);

        tvMapper.insert(entity);
        return entity.getId();
    }

    public Tv getById(Long id) {
        return tvMapper.getById(id);
    }

    public Long updateTv(TvRegistForm form) {

        Tv entity = new Tv();
        entity.setId(form.getTvId());
        entity.setTitle(form.getTitle());
        entity.setOffice(form.getOffice());
        entity.setDate(DateUtil.stringToDateyyyy_MM_dd(form.getDate()));
        entity.setUrl(form.getPicUrl());
        entity.setTvType(form.getTvType());
        entity.setSortScore(Integer.parseInt(form.getSortScore()));
        if(form.getPublishStart() == null || "".equals(form.getPublishStart())){
            entity.setPublishStart(DateUtil.getDefaultDate());
        }else{
            entity.setPublishStart(DateUtil.stringToDateyyyy_MM_dd_HH_mm(form.getPublishStart().replace("T"," ")));
        }
        if(form.getPublishEnd() == null || "".equals(form.getPublishEnd())){
            entity.setPublishEnd(DateUtil.getDefaultPublishEnd());
        }else{
            entity.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd_HH_mm(form.getPublishEnd().replace("T"," ")));
        }

        entity.setUpdateDatetime(new Date(System.currentTimeMillis()));

        tvMapper.updateTV(entity);
        return entity.getId();
    }

    public Long deleteTv(TvRegistForm form) {

        Tv entity = new Tv();
        entity.setId(form.getId());
        entity.setDelFlg(Boolean.TRUE);

        tvMapper.deleteTv(entity);
        return entity.getId();
    }
}
