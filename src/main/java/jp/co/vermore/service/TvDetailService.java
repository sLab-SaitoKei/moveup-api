package jp.co.vermore.service;


import jp.co.vermore.common.Constant;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.EventDetail;
import jp.co.vermore.entity.Tv;
import jp.co.vermore.entity.TvDetail;
import jp.co.vermore.form.admin.TvRegistForm;
import jp.co.vermore.mapper.TvDetailMapper;
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
public class TvDetailService {

    @Autowired
    private TvDetailMapper tvDetailMapper;


    public TvDetail getTvDetailById(Long id) {
        return tvDetailMapper.getTvDetailById(id);
    }

    public TvDetail getTvDetail(Long shopId) {
        return tvDetailMapper.getTvDetail(shopId);
    }

    public Long insertTvDetail(TvRegistForm form, Long tvId) {
        TvDetail entity = new TvDetail();
        entity.setTvId(tvId);
        entity.setTitle(form.getTitle());
        entity.setDetail(form.getDesc1());
        entity.setType(form.getType());
        entity.setDate(DateUtil.stringToDateyyyy_MM_dd(form.getDate()));
        if (form.getPublishStart() == null || "".equals(form.getPublishStart())) {
            entity.setPublishStart(DateUtil.getDefaultDate());
        } else {
            entity.setPublishStart(DateUtil.stringToDateyyyy_MM_dd(form.getPublishStart()));
        }
        if (form.getPublishEnd() == null || "".equals(form.getPublishEnd())) {
            entity.setPublishEnd(DateUtil.getDefaultPublishEnd());
        } else {
            entity.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd(form.getPublishEnd()));
        }
        entity.setCategory(Byte.valueOf("0"));
        entity.setCreateDatetime(new Date(System.currentTimeMillis()));
        entity.setDelFlg(Boolean.FALSE);
        entity.setNote(Constant.EMPTY_STRING);

        tvDetailMapper.insertDetail(entity);

        return entity.getId();
    }

    public TvDetail updateTvDetail(TvRegistForm form, Long tvId) {
        TvDetail entity = new TvDetail();
        entity.setId(form.getTvDetailId());
        entity.setTvId(tvId);
        entity.setTitle(form.getTitle());
        entity.setDetail(form.getDesc1());
        entity.setType(form.getType());
        entity.setDate(DateUtil.stringToDateyyyy_MM_dd(form.getDate()));
        if (form.getPublishStart() == null || "".equals(form.getPublishStart())) {
            entity.setPublishStart(DateUtil.getDefaultDate());
        } else {
            entity.setPublishStart(DateUtil.stringToDateyyyy_MM_dd(form.getPublishStart()));
        }
        if (form.getPublishEnd() == null || "".equals(form.getPublishEnd())) {
            entity.setPublishEnd(DateUtil.getDefaultPublishEnd());
        } else {
            entity.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd(form.getPublishEnd()));
        }

        entity.setUpdateDatetime(new Date(System.currentTimeMillis()));

        tvDetailMapper.updateTvDetail(entity);

        return entity;
    }

    public void deleteTvDetail(Long tvId) {

        TvDetail entity = new TvDetail();

        entity.setTvId(tvId);
        entity.setDelFlg(Boolean.TRUE);
        tvDetailMapper.deleteTvDetail(entity);
    }
}
