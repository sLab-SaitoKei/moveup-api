package jp.co.vermore.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import jp.co.vermore.common.util.StringUtil;
import jp.co.vermore.entity.Pic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.vermore.entity.NewsDetail;
import jp.co.vermore.form.admin.NewsListForm;
import jp.co.vermore.common.Constant;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.News;
import jp.co.vermore.form.admin.NewsForm;
import jp.co.vermore.mapper.NewsDetailMapper;
import jp.co.vermore.mapper.NewsMapper;

/**
 * NewsService
 * Created by wubin.
 * <p>
 * DateTime: 2018/03/03 11:13
 * Copyright: sLab, Corp
 */

@Service

public class NewsService {

    @Autowired
    private NewsMapper newsMapper;

    public News getNewsByUuid(String uuid) {
        News entity = newsMapper.getNewsByUuid(uuid);
        return entity;
    }

    public List<News> getNewsAll() {
        List<News> newsList = newsMapper.getNewsAll();
        return newsList;
    }

    public List<News> getNewsAllForTop() {
        String nowMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(0);
        String nextMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(1);
//        String now= DateUtil.dateToStringyyyy_MM_dd_HH_mm(new Date(System.currentTimeMillis()));
        List<News> newsList = newsMapper.getNewsAllForTop(nowMin,nextMin);
        return newsList;
    }

    public List<News> getNewsCategory(int type,int limit,int offset) {
        List<News> newsList = newsMapper.getNewsCategory(type,limit,offset);
        return newsList;
    }

    public List<News>getNewsPre(Date date) {
        String nowMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(0);
        String nextMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(1);
        List<News> news = newsMapper.getNewsPre(date,nowMin,nextMin);
        return news;
    }

    public List<News> getNewsNext(Date date) {
        String nowMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(0);
        String nextMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(1);
        List<News> news = newsMapper.getNewsNext(date,nowMin,nextMin);
        return news;
    }

    public List<News> getNewsAll(int type,int limit,int offset) {
        String nowMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(0);
        String nextMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(1);
        List<News> newsList = newsMapper.getNewsJsonAll(type,nowMin,nextMin,limit, offset);
        return newsList;
    }

    public List<News> getNewsAllByType(int type) {
        String nowMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(0);
        String nextMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(1);
        List<News> newsList = newsMapper.getNewsJsonAllByType(type,nowMin,nextMin);
        return newsList;
    }

    public News getNewsByIdAndType(long id,int type) {
        News news = newsMapper.getNewsByIdAndType(id,type);
        return news;
    }

    public List<News> getNewsEventAll(int type1,int type2,int limit,int offset) {
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<News> newsList = newsMapper.getNewsEventAll(type1,type2,tomorrow,today,limit, offset);
        return newsList;
    }

    private List<News> convertTo(List<News> demoList) {
        List<News> resultList = new LinkedList<News>();
        for (News entity : demoList) {
            resultList.add(entity);
        }
        return resultList;
    }

    @Autowired
    private NewsMapper addNewsMapper;

    public long insertNews(NewsForm newsForm) {
        News news = new News();
        String uuid = "";
        int flagUuid = 0;
        int cntSelect = 0;
        while (flagUuid != 1 && cntSelect < 100){
            uuid = StringUtil.getUuid();
            if (getNewsByUuid(uuid) == null){
                flagUuid = 1;
            }
            cntSelect++;
        }

        news.setUuid(uuid);
        String date = newsForm.getDate();
        news.setDate(DateUtil.stringToDateyyyy_MM_dd_HH_mm(date.replace("T"," ")));
        news.setTitle(newsForm.getTitle());
        news.setType(newsForm.getType());
        news.setSortScore(newsForm.getSortScore());
        news.setExcerpt(newsForm.getExcerpt());
        if(newsForm.getPublishStart() == null || "".equals(newsForm.getPublishStart())){
            news.setPublishStart(DateUtil.getDefaultDate());
        }else{
            news.setPublishStart(DateUtil.stringToDateyyyy_MM_dd_HH_mm(newsForm.getPublishStart().replace("T"," ")));
        }
        if(newsForm.getPublishEnd() == null || "".equals(newsForm.getPublishEnd())){
            news.setPublishEnd(DateUtil.getDefaultPublishEnd());
        }else{
            news.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd_HH_mm(newsForm.getPublishEnd().replace("T"," ")));
        }
        news.setCreateDatetime(new Date(System.currentTimeMillis()));
        news.setDelFlg(Boolean.FALSE);
        news.setNote(Constant.EMPTY_STRING);
        addNewsMapper.insertNews(news);
        return news.getId();
    }

    public long insertStudioNews(NewsForm newsForm) {
        News news = new News();
        String uuid = "";
        int flagUuid = 0;
        int cntSelect = 0;
        while (flagUuid != 1 && cntSelect < 100){
            uuid = StringUtil.getUuid();
            if (getNewsByUuid(uuid) == null){
                flagUuid = 1;
            }
            cntSelect++;
        }

        news.setUuid(uuid);
        String date = newsForm.getDate();
        news.setDate(DateUtil.stringToDateyyyy_MM_dd(date));
        news.setTitle(newsForm.getTitle());
        news.setType(newsForm.getType());
        news.setSortScore(newsForm.getSortScore());
        news.setExcerpt(newsForm.getExcerpt());
        if(newsForm.getPublishStart() == null || "".equals(newsForm.getPublishStart())){
            news.setPublishStart(DateUtil.getDefaultDate());
        }else{
            news.setPublishStart(DateUtil.stringToDateyyyy_MM_dd(newsForm.getPublishStart()));
        }
        if(newsForm.getPublishEnd() == null || "".equals(newsForm.getPublishEnd())){
            news.setPublishEnd(DateUtil.getDefaultPublishEnd());
        }else{
            news.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd(newsForm.getPublishEnd()));
        }
        news.setCreateDatetime(new Date(System.currentTimeMillis()));
        news.setDelFlg(Boolean.FALSE);
        news.setNote(Constant.EMPTY_STRING);
        addNewsMapper.insertNews(news);
        return news.getId();
    }

    @Autowired
    private NewsDetailMapper newsDetailMapper;

    public long insertDetailNews(NewsForm newsForm,long newsId) {
        NewsDetail newsDetail = new NewsDetail();
        newsDetail.setNewsId(newsId);
        String date = newsForm.getDate();
        newsDetail.setDate(DateUtil.stringToDateyyyy_MM_dd_HH_mm(date.replace("T"," ")));
        newsDetail.setTitle(newsForm.getTitle());
        newsDetail.setType(newsForm.getType());
        newsDetail.setDetail(newsForm.getDetail());
        newsDetail.setCreateDatetime(new Date(System.currentTimeMillis()));
        newsDetail.setDelFlg(Boolean.FALSE);
        newsDetail.setNote(Constant.EMPTY_STRING);
        newsDetailMapper.insertDetailNews(newsDetail);
        return newsDetail.getId();
    }

    public long insertDetailStudioNews(NewsForm newsForm,long newsId) {
        NewsDetail newsDetail = new NewsDetail();
        newsDetail.setNewsId(newsId);
        String date = newsForm.getDate();
        newsDetail.setDate(DateUtil.stringToDateyyyy_MM_dd(date));
        newsDetail.setTitle(newsForm.getTitle());
        newsDetail.setType(newsForm.getType());
        newsDetail.setDetail(newsForm.getDetail());
        newsDetail.setCreateDatetime(new Date(System.currentTimeMillis()));
        newsDetail.setDelFlg(Boolean.FALSE);
        newsDetail.setNote(Constant.EMPTY_STRING);
        newsDetailMapper.insertDetailNews(newsDetail);
        return newsDetail.getId();
    }

    public int deleteNews(NewsForm newsForm) {
        News news = new News();
        news.setId(newsForm.getId());
        news.setDelFlg(Boolean.TRUE);
        int count = newsMapper.deleteNews(news);
        System.out.println(count);
        return count;
    }

    public int deleteDetailNews(NewsForm newsForm) {
        NewsDetail newsDetail = new NewsDetail();
        newsDetail.setNewsId(newsForm.getId());
        newsDetail.setDelFlg(Boolean.TRUE);
        int count = newsDetailMapper.deleteDetailNews(newsDetail);
        return count;
    }

    public int updateNews(NewsForm newsForm) {
        News news = new News();
        news.setId(newsForm.getId());
        String date = newsForm.getDate();
        news.setDate(DateUtil.stringToDateyyyy_MM_dd_HH_mm(date.replace("T"," ")));
        news.setTitle(newsForm.getTitle());
        news.setType(newsForm.getType());
        news.setSortScore(newsForm.getSortScore());
        news.setExcerpt(newsForm.getExcerpt());
        if(newsForm.getPublishStart() == null || "".equals(newsForm.getPublishStart())){
            news.setPublishStart(DateUtil.getDefaultDate());
        }else{
            news.setPublishStart(DateUtil.stringToDateyyyy_MM_dd_HH_mm(newsForm.getPublishStart().replace("T"," ")));
        }
        if(newsForm.getPublishEnd() == null || "".equals(newsForm.getPublishEnd())){
            news.setPublishEnd(DateUtil.getDefaultPublishEnd());
        }else{
            news.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd_HH_mm(newsForm.getPublishEnd().replace("T"," ")));
        }
        news.setUpdateDatetime(new Date(System.currentTimeMillis()));
        news.setDelFlg(Boolean.FALSE);
        news.setNote(Constant.EMPTY_STRING);
        int count = newsMapper.updateNews(news);
        return count;
    }

    public int updateStudioNews(NewsForm newsForm) {
        News news = new News();
        news.setId(newsForm.getId());
        String date = newsForm.getDate();
        news.setDate(DateUtil.stringToDateyyyy_MM_dd(date));
        news.setTitle(newsForm.getTitle());
        news.setType(newsForm.getType());
        news.setSortScore(newsForm.getSortScore());
        news.setExcerpt(newsForm.getExcerpt());
        if(newsForm.getPublishStart() == null || "".equals(newsForm.getPublishStart())){
            news.setPublishStart(DateUtil.getDefaultDate());
        }else{
            news.setPublishStart(DateUtil.stringToDateyyyy_MM_dd(newsForm.getPublishStart()));
        }
        if(newsForm.getPublishEnd() == null || "".equals(newsForm.getPublishEnd())){
            news.setPublishEnd(DateUtil.getDefaultPublishEnd());
        }else{
            news.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd(newsForm.getPublishEnd()));
        }
        news.setUpdateDatetime(new Date(System.currentTimeMillis()));
        news.setDelFlg(Boolean.FALSE);
        news.setNote(Constant.EMPTY_STRING);
        int count = newsMapper.updateNews(news);
        return count;
    }

    public int updateDetailNews(NewsForm newsForm) {
        NewsDetail newsDetail = new NewsDetail();
        newsDetail.setNewsId(newsForm.getId());
        String date = newsForm.getDate();
        newsDetail.setDate(DateUtil.stringToDateyyyy_MM_dd_HH_mm(date.replace("T"," ")));
        newsDetail.setTitle(newsForm.getTitle());
        newsDetail.setType(newsForm.getType());
        newsDetail.setDetail(newsForm.getDetail());
        newsDetail.setUpdateDatetime(new Date(System.currentTimeMillis()));
        newsDetail.setDelFlg(Boolean.FALSE);
        newsDetail.setNote(Constant.EMPTY_STRING);
        int count = newsDetailMapper.updateDetailNews(newsDetail);
        return count;
    }

    public int updateDetailStudioNews(NewsForm newsForm) {
        NewsDetail newsDetail = new NewsDetail();
        newsDetail.setNewsId(newsForm.getId());
        String date = newsForm.getDate();
        newsDetail.setDate(DateUtil.stringToDateyyyy_MM_dd(date));
        newsDetail.setTitle(newsForm.getTitle());
        newsDetail.setType(newsForm.getType());
        newsDetail.setDetail(newsForm.getDetail());
        newsDetail.setUpdateDatetime(new Date(System.currentTimeMillis()));
        newsDetail.setDelFlg(Boolean.FALSE);
        newsDetail.setNote(Constant.EMPTY_STRING);
        int count = newsDetailMapper.updateDetailNews(newsDetail);
        return count;
    }

    public List<News> getNewsList(long id) {
        List<News> newsList = newsMapper.getNewsList(id);
        return newsList;
    }

    public String getNewsDetail(long id) {
        String detail = newsDetailMapper.getNewsDetail(id);
        return detail;
    }

    public List<NewsDetail> getNewsDetailAll(Long id) {
        List<NewsDetail> newsDetail = newsDetailMapper.getNewsDetailAll(id);
        List<NewsDetail> resultList = convertToDetail(newsDetail);
        return resultList;
    }

    private List<NewsDetail> convertToDetail(List<NewsDetail> demoList) {
        List<NewsDetail> resultList = new LinkedList<NewsDetail>();
        for (NewsDetail entity : demoList) {
            resultList.add(entity);
        }
        return resultList;
    }

    public List<News> getNewsAllByCondition(NewsListForm form) {
        List<News> news = newsMapper.getNewsAllByCondition(form);
        return news;
    }

    public int getNewsCountByCondition(NewsListForm form) {
        return newsMapper.getNewsCountByCondition(form);
    }

    public int getNewsCount() {
        return newsMapper.getNewsCount();
    }

    public List<News> getStudioNewsALL(int type) {
        List<News> news = newsMapper.getStudioNewsALL(type);
        return news;
    }
}