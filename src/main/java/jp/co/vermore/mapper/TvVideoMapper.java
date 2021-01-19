package jp.co.vermore.mapper;

import jp.co.vermore.entity.TvVideo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TvVideoMapper {

    List<TvVideo> getTvVideoById(Long id);

    int insertTvVideo(TvVideo entity);

    int updateTvVideo(TvVideo entity);

    int deleteTvVideo(TvVideo entity);
}