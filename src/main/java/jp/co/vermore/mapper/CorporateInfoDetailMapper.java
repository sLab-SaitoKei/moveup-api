package jp.co.vermore.mapper;

import jp.co.vermore.entity.CorporateInfoDetail;
import jp.co.vermore.form.admin.CorporateInfoListForm;

import java.util.List;

public interface CorporateInfoDetailMapper {

    CorporateInfoDetail getDetailById(Long id);

    CorporateInfoDetail getDetailByShopId(Long id);

    Long getCorporateListId(Long id);

    int getCorporateType(Long shopListId);

    Long getCorporateDetailId(Long shopListId);

    Long getShopListId(Long id);

    int insertCorporateDetail(CorporateInfoDetail Detail);

    int updateCorporateDetail(CorporateInfoDetail Detail);

    int deleteCorporateDetail(Long id);

    List<CorporateInfoDetail> getCorporateInfoDetailAdmin();

    List<CorporateInfoDetail> getCorporateInfoDetailAll(Long shopListId);

    List<CorporateInfoDetail> getCorporateInfoDetailList(List<Long> idList);

    List<CorporateInfoDetail> getCorporateInfoAllByCondition(CorporateInfoListForm form);

    int getCorporateInfoCountByCondition(CorporateInfoListForm form);

    int getCorporateInfoCount();

    int deleteDetailVideo(Long shopId);

    int deleteDetailFlic(Long shopId);
}