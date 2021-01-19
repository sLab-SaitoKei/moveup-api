package jp.co.vermore.mapper;

import java.util.List;
import jp.co.vermore.entity.FreePaper;
import jp.co.vermore.entity.Shop;
import jp.co.vermore.form.admin.FreePaperListForm;
import org.apache.ibatis.annotations.Param;

public interface FreePaperMapper {

    int updateByPrimaryKey(FreePaper record);

    List<FreePaper> getFreePaperJsonAll(String tomorrow,String today,int limit, int offset);

    List<FreePaper> getFreePaperByYear(String year, String tomorrow,String today,int limit, int offset);

    List<FreePaper> getFreePaperForTop();

    List<FreePaper> getFreePaperAll();

    FreePaper getFreePaperById(Long id);

    FreePaper getFreePaperByUuid(String Uuid);

    int updateFreePaper(FreePaper freePaper);

    int insertFreePaper(FreePaper freePaper);

    int deleteFreePaper(Long id);

    List<FreePaper> getFreePaperAllByCondition(FreePaperListForm form);

    int getFreePaperCountByCondition(FreePaperListForm form);

    int getFreePaperCount();
}