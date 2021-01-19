package jp.co.vermore.mapper;

import jp.co.vermore.entity.Goods;
import jp.co.vermore.entity.GoodsAmount;
import jp.co.vermore.form.admin.GoodsListForm;

import java.util.List;


public interface GoodsMapper {

    List<Goods> getGoodsAll(String tomorrow,String today,int limit, int offset);

    List<Goods> getGoodsTotal(String tomorrow,String today);

    Goods getGoods(long id);

    Goods getGoodsByUuid(String uuid);

    List<Goods> getGoodsAllForAdmin();

    List<Goods> getGoodsForTop(String tomorrow,String today);

    List<Goods> searchGoodsList(String tomorrow,String today,String title,int goodsType,int maxPrice,int minPrice);

    Long insertGoods(Goods goods);

    int updateGoods(Goods goods);

    Long count();

    List<GoodsAmount> getGoodsAllByCondition(GoodsListForm form);

    int getGoodsCountByCondition(GoodsListForm form);

    int getGoodsCount();
}