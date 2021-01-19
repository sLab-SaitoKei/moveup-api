package jp.co.vermore.service;


import jp.co.vermore.common.Constant;
import jp.co.vermore.entity.Tv;
import jp.co.vermore.entity.TvVideo;
import jp.co.vermore.form.admin.TVListForm;
import jp.co.vermore.form.admin.TvRegistForm;
import jp.co.vermore.mapper.TvMapper;
import jp.co.vermore.mapper.TvVideoMapper;
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
public class TvVideoService {

    @Autowired
    private TvVideoMapper tvVideoMapper;

    public List<TvVideo> getTvVideoById(Long id) {
        return tvVideoMapper.getTvVideoById(id);
    }

    public int insertTvVideo(TvRegistForm form, Long tvDetailId) {
        TvVideo entity = new TvVideo();
        entity.setTvDetailId(tvDetailId);
        entity.setVideoType(Byte.valueOf("1"));
        if(form.getYouTubeVideoUrl()!=null && !form.getYouTubeVideoUrl().equals("")){
            entity.setFirstUrl(form.getYouTubeVideoUrl());
            entity.setCategory(Constant.TV_VIDEO_TYPE.YOUTUBE_TYPE);
        }else if(form.getVideoUrlLast() !=null && !form.getVideoUrlLast().equals("")){
            entity.setFirstUrl(form.getVideoUrlLast());
            entity.setCategory(Constant.TV_VIDEO_TYPE.AWS_TYPE);
        }else if(form.getVideoUrlFirst() !=null && !form.getVideoUrlFirst().equals("")){
            entity.setFirstUrl(form.getVideoUrlFirst());
            entity.setCategory(Constant.TV_VIDEO_TYPE.AWS_TYPE);
        }
        entity.setCreateDatetime(new Date(System.currentTimeMillis()));
        entity.setUpdateDatetime(new Date(System.currentTimeMillis()));
        entity.setDelFlg(Boolean.FALSE);
        entity.setNote(Constant.EMPTY_STRING);
        int count = tvVideoMapper.insertTvVideo(entity);
        return count;
    }

    public int updateTvVideo(TvRegistForm form, Long tvDetailId) {
        TvVideo entity = new TvVideo();
        entity.setTvDetailId(tvDetailId);
        if(form.getYouTubeVideoUrl()!=null && !form.getYouTubeVideoUrl().equals("")){
            entity.setFirstUrl(form.getYouTubeVideoUrl());
            entity.setCategory(Constant.TV_VIDEO_TYPE.YOUTUBE_TYPE);
        }else if(form.getVideoUrlLast() !=null && !form.getVideoUrlLast().equals("")){
            entity.setFirstUrl(form.getVideoUrlLast());
            entity.setCategory(Constant.TV_VIDEO_TYPE.AWS_TYPE);
        }else if(form.getVideoUrlFirst() !=null && !form.getVideoUrlFirst().equals("")){
            entity.setFirstUrl(form.getVideoUrlFirst());
            entity.setCategory(Constant.TV_VIDEO_TYPE.AWS_TYPE);
        }
        entity.setUpdateDatetime(new Date(System.currentTimeMillis()));
        int count = tvVideoMapper.updateTvVideo(entity);
        System.out.println(count);
        return count;
    }

    public int deleteTvVideo(Long tvDetailId) {
        TvVideo entity = new TvVideo();
        entity.setTvDetailId(tvDetailId);
        entity.setDelFlg(Boolean.TRUE);
        int count = tvVideoMapper.deleteTvVideo(entity);
        System.out.println(count);
        return count;

    }
}
