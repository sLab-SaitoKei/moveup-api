package jp.co.vermore.mapper;

import java.util.Date;
import java.util.List;
import jp.co.vermore.entity.News;
import jp.co.vermore.entity.NewsDetail;
import jp.co.vermore.form.admin.NewsListForm;
import org.apache.ibatis.annotations.Param;

public interface NewsMapper {

    int insertNews(News news);

    int deleteNews(News news);

    int updateNews(News news);

    News getNewsByUuid(String uuid);

    List<News> getNewsAll();
    
    List<News> getNewsAllForTop( String nowMin,String nextMin);

    List<News> getNewsJsonAll(int type,String nowMin,String nextMin,int limit, int offset);

    List<News> getNewsJsonAllByType(int type,String nowMin,String nextMin);

    News getNewsByIdAndType(long id,int type);

    List<News> getNewsEventAll(int type1,int type2,String tomorrow,String today,int limit, int offset);

    List<News> getNewsList(long id);

    List<News> getNewsPre(Date date, String nowMin,String nextMin);

    List<News> getNewsNext(Date date,String nowMin,String nextMin);

    List<News> getNewsCategory(int type,int limit,int offset);

    List<News> getNewsAllByCondition(NewsListForm form);

    int getNewsCountByCondition(NewsListForm form);

    int getNewsCount();

    List<News> getStudioNewsList(int type, int sortScore, String tomorrow,String today);

    List<News> getStudioNewsListAll(Byte type, int limit, int offset);

    List<News> getStudioNewsALL(int type);

    List<News> getStudioAllByCondition(NewsListForm form);

    int getStudioCountByCondition(NewsListForm form);

    int getStudioCount();

    News getNewsById(@Param("id") Long id);

}