package jp.co.vermore.mapper;

import java.util.List;
import jp.co.vermore.entity.FreePaperDetail;
import org.apache.ibatis.annotations.Param;

public interface FreePaperDetailMapper {

    int insertFreePaperDetail(FreePaperDetail freePaperDetail);

    int deleteFreePaperDetail(Long id);

    List<FreePaperDetail> getFreePaperDetailJsonAll(Long freePaperId, Byte picType);

    List<FreePaperDetail> getFreePaperDetailAll();
}