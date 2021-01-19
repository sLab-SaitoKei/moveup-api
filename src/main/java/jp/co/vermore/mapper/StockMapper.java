package jp.co.vermore.mapper;

import java.util.List;

import jp.co.vermore.entity.Stock;

public interface StockMapper {

   List<Stock> getAmount (Stock entity);

   List<Stock> getColors(long goodsId);

   List<Stock> getSize(long goodsId);

   int insertStock(Stock stock);

}