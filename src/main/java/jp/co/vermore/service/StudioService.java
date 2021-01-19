package jp.co.vermore.service;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.common.util.StringUtil;
import jp.co.vermore.entity.News;
import jp.co.vermore.entity.NewsDetail;
import jp.co.vermore.entity.StudioGallery;
import jp.co.vermore.entity.Top;
import jp.co.vermore.form.StudioGalleryForm;
import jp.co.vermore.form.admin.NewsForm;
import jp.co.vermore.form.admin.NewsListForm;
import jp.co.vermore.mapper.NewsDetailMapper;
import jp.co.vermore.mapper.NewsMapper;
import jp.co.vermore.mapper.StudioGalleryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * StudioService
 * Created by wangnannan.
 * DateTime: 2018/05/24 18:40
 * Copyright: sLab, Corp
 */
@Service
public class StudioService {

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private NewsDetailMapper newsDetailMapper;

    @Autowired
    private StudioGalleryMapper studioGalleryMapper;

    public News getNewsByUuid(String uuid) {
        News entity = newsMapper.getNewsByUuid(uuid);
        return entity;
    }

    public List<News> getStudioNewsList(int type, int sortScore) {
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<News>  studioNews = newsMapper.getStudioNewsList(type, sortScore, tomorrow,today);
        return studioNews;
    }

    public List<StudioGallery> getStudioGalleryList() {

        List<StudioGallery> studioGallery = studioGalleryMapper.getStudioGalleryList();
        return studioGallery;
    }

    public List<News> getStudioNewsListAll(Byte type, int limit, int offset) {

        List<News>  studioNews = newsMapper.getStudioNewsListAll(type, limit, offset);
        return studioNews;
    }

    public NewsDetail getNewsDetail(Long id) {
        NewsDetail newsDetail = newsDetailMapper.getStudioNewsDetail(id);
        return newsDetail;
    }

    public List<News> getStudioAllByCondition(NewsListForm form) {
        List<News> dataList = newsMapper.getStudioAllByCondition(form);
        return dataList;
    }

    public int getStudioCountByCondition(NewsListForm form) {
        return newsMapper.getStudioCountByCondition(form);
    }

    public int getStudioCount() {
        return newsMapper.getStudioCount();
    }

    public List<StudioGallery> getStudioGalleryForAdmin() {
        List<StudioGallery> list = studioGalleryMapper.getStudioGalleryForAdmin();
        return list;
    }

    public int updateGallery(StudioGalleryForm studioGalleryForm) {
        int count = 0;
        List<String> urlList = new ArrayList<String>();
        urlList.add(studioGalleryForm.getUrl1());
        urlList.add(studioGalleryForm.getUrl2());
        urlList.add(studioGalleryForm.getUrl3());
        urlList.add(studioGalleryForm.getUrl4());
        urlList.add(studioGalleryForm.getUrl5());
        urlList.add(studioGalleryForm.getUrl6());
        urlList.add(studioGalleryForm.getUrl7());
        urlList.add(studioGalleryForm.getUrl8());

        for(long id=1; id<=8; id++){
            StudioGallery studioGallery = new StudioGallery();
            studioGallery.setId(id);
            studioGallery.setUrl(urlList.get((int) id-1));
            studioGallery.setScore((int) id);
            studioGallery.setType((byte) 1);
            studioGallery.setUpdateDatetime(new Date(System.currentTimeMillis()));
            count = studioGalleryMapper.updateGallery(studioGallery);
        }
        return count;
    }

}
