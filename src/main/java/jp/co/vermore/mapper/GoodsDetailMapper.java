package jp.co.vermore.mapper;

import java.util.List;

import jp.co.vermore.entity.GoodsDetail;

public interface GoodsDetailMapper {

    GoodsDetail getGoodsDetailByGoodsId(long id);

    GoodsDetail getGoodsDetailByGoodsListId(long goodsId);

    int insertGoodsDetail(GoodsDetail goodsDetail);

    int updateGoodsDetail(GoodsDetail goodsDetail);

}