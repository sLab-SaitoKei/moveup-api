package jp.co.vermore.mapper;

import java.util.List;

import jp.co.vermore.entity.GoodsPurchase;
import jp.co.vermore.form.admin.GoodsPurchaseListForm;

public interface GoodsPurchaseMapper {

    Long insertGoodsPurchase(GoodsPurchase entity);

    Long updateByPrimaryKeySelective(GoodsPurchase record);

    Long updateBySerialNumberSelective(GoodsPurchase record);

    List<GoodsPurchase> getGoodsPurchase(Long userId ,int status);

    List<GoodsPurchase> getGoodsSerialNumber(Long userId);

    List<GoodsPurchase> getGoodsPurchaseHistory(String serialNumber);

    List<GoodsPurchase> getGoodsPurchaseHistoryAll();

    GoodsPurchase getGoodsPurchaseById(Long id);

    int updateGoodsPurchase(String serialNumber,int status);

    List<GoodsPurchase> getGoodsPurchaseAllByCondition(GoodsPurchaseListForm form);

    int getGoodsPurchaseCountByCondition(GoodsPurchaseListForm form);

    int getGoodsPurchaseCount();
}