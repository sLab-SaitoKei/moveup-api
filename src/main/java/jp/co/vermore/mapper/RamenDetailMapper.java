package jp.co.vermore.mapper;

import jp.co.vermore.entity.RamenDetail;

import java.util.List;

public interface RamenDetailMapper {

    int insertDetailRamen(RamenDetail ramenDetail);

    int deleteDetailRamen(RamenDetail ramenDetail);

    int updateDetailRamen(RamenDetail ramenDetail);

    String getRamenDetail(long id);

    List<RamenDetail> getRamenDetailAll(Long id);

    RamenDetail getStudioRamenDetail(Long id);
}