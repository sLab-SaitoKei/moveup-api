package jp.co.vermore.mapper;

import jp.co.vermore.entity.TvDetail;

import java.util.List;

public interface TvDetailMapper {

    TvDetail getTvDetailById(Long id);

    TvDetail getTvDetail(Long shopId);

    void insertDetail(TvDetail entity);

    void updateTvDetail(TvDetail entity);

    void deleteTvDetail(TvDetail entity);
}