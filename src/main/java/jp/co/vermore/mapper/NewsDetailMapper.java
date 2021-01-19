package jp.co.vermore.mapper;

import java.util.List;
import jp.co.vermore.entity.NewsDetail;

public interface NewsDetailMapper {

    int insertDetailNews(NewsDetail newsDetail);

    int deleteDetailNews(NewsDetail newsDetail);

    int updateDetailNews(NewsDetail newsDetail);

    String getNewsDetail(long id);

    List<NewsDetail> getNewsDetailAll(Long id);

    NewsDetail getStudioNewsDetail(Long id);
}