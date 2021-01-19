package jp.co.vermore.mapper;

import jp.co.vermore.entity.RiseDetail;

public interface RiseDetailMapper {

    int insertRiseDetail(RiseDetail riseDetail);

    RiseDetail getRiseDetail(int riseId);
}