package jp.co.vermore.service;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.common.util.StringUtil;
import jp.co.vermore.entity.*;
import jp.co.vermore.form.GoodsEditForm;
import jp.co.vermore.form.GoodsRegistForm;
import jp.co.vermore.form.admin.GoodsListForm;
import jp.co.vermore.form.admin.GoodsPurchaseListForm;
import jp.co.vermore.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * RiseService
 * Created by Wangyan.
 * <p>
 * DateTime: 2018/02/28 17:48
 * Copyright: sLab, Corp
 */
@Service
public class GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsDetailMapper goodsDetailMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private GoodsPurchaseMapper goodsPurchaseMapper;

    @Autowired
    private PurchaseInfoMapper purchaseInfoMapper;



    public Goods getGoodsByUuid(String uuid){
        Goods entity = goodsMapper.getGoodsByUuid(uuid);
        return entity;
    }

    public List<GoodsPurchase> getGoodsPurchaseCart(Long userId ,int status){
        List<GoodsPurchase> goodsPurchaseList = goodsPurchaseMapper.getGoodsPurchase(userId ,status);
        return goodsPurchaseList;
    }

    public List<GoodsPurchase> getGoodsPurchaseHistory(String serialNumber){
        List<GoodsPurchase> goodsPurchaseList = goodsPurchaseMapper.getGoodsPurchaseHistory(serialNumber);
        return goodsPurchaseList;
    }

    public List<GoodsPurchase> getGoodsSerialNumber(Long userId){
        List<GoodsPurchase> goodsPurchaseList = goodsPurchaseMapper.getGoodsSerialNumber(userId);
        return goodsPurchaseList;
    }

    public List<GoodsPurchase> getGoodsPurchaseHistoryAll(){
        List<GoodsPurchase> goodsPurchaseList = goodsPurchaseMapper.getGoodsPurchaseHistoryAll();
        return goodsPurchaseList;
    }

    public Long insertGoodsPurchase(GoodsPurchase goodsPurchase){
        goodsPurchase.setCreateDatetime(new Date(System.currentTimeMillis()));
        goodsPurchase.setDelFlg(Boolean.FALSE);
        goodsPurchase.setNote(Constant.EMPTY_STRING);
        goodsPurchaseMapper.insertGoodsPurchase(goodsPurchase);
        return goodsPurchase.getId();
    }

    public Long outGoodsPurchase(GoodsPurchase goodsPurchase){
        goodsPurchase.setDelFlg(Boolean.TRUE);
        goodsPurchaseMapper.updateByPrimaryKeySelective(goodsPurchase);
        return goodsPurchase.getId();
    }

    public Long updateGoodsPurchase(GoodsPurchase goodsPurchase){
        goodsPurchaseMapper.updateByPrimaryKeySelective(goodsPurchase);
        return goodsPurchase.getId();
    }

    public Long updateBySerialNumberSelective(GoodsPurchase goodsPurchase){
        goodsPurchase.setUpdateDatetime(new Date(System.currentTimeMillis()));
        goodsPurchaseMapper.updateBySerialNumberSelective(goodsPurchase);
        return goodsPurchase.getId();
    }

    public GoodsPurchase getGoodsPurchaseById(Long id){
        GoodsPurchase goodsPurchase = goodsPurchaseMapper.getGoodsPurchaseById(id);
        return goodsPurchase;
    }

    public int updateGoodsPurchase(String serialNumber,int status){
        int count = goodsPurchaseMapper.updateGoodsPurchase(serialNumber,status);
        return count;
    }

    public List<Stock>getAmount(Stock entity) {
        List<Stock> stock = stockMapper.getAmount(entity);
        return stock;
    }

    public  List<Stock> getColors(long goodsId){
        List<Stock> colorsList = stockMapper.getColors(goodsId);
        return colorsList;
    }

    public  List<Stock> getSize(long goodsId){
        List<Stock> sizeList = stockMapper.getSize(goodsId);
        return sizeList;
    }

    public GoodsDetail getGoodsDetailByGoodsId(long id){
        GoodsDetail goodsDetail = goodsDetailMapper.getGoodsDetailByGoodsId(id);
        return goodsDetail;
    }

    public GoodsDetail getGoodsDetailByGoodsListId(Long goodsListId){
        GoodsDetail goodsDetail = goodsDetailMapper.getGoodsDetailByGoodsListId(goodsListId);
        return goodsDetail;
    }

    public List<Goods> getGoodsAll(int limit, int offset){
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<Goods> goodsList = goodsMapper.getGoodsAll(tomorrow,today,limit, offset);
        return goodsList;
    }

    public List<Goods> getGoodsTotal(){
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<Goods> goodsList = goodsMapper.getGoodsTotal(tomorrow,today);
        return goodsList;
    }

    public Goods getGoodsById(long id){
        Goods goods = goodsMapper.getGoods(id);
        return goods;
    }

    public List<Goods> getGoodsAllForAdmin(){
        List<Goods> goodsList = goodsMapper.getGoodsAllForAdmin();
        return goodsList;
    }

    public List<Goods> getGoodsForTop(){
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<Goods> goodsList = goodsMapper.getGoodsForTop(tomorrow,today);
        return goodsList;
    }

    public List<Goods> searchGoodsList(String title,int goodsType,int maxPrice,int minPrice){
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<Goods> goodsList = goodsMapper.searchGoodsList(tomorrow,today,title,goodsType,maxPrice,minPrice);
        return goodsList;
    }

    public long insertGoods(GoodsRegistForm goodsRegistForm){
        Goods goods = new Goods();
        String uuid = "";
        int flagUuid = 0;
        int cntSelect = 0;
        while (flagUuid != 1 && cntSelect < 100){
            uuid = StringUtil.getUuid();
            if (getGoodsByUuid(uuid) == null){
                flagUuid = 1;
            }
            cntSelect++;
        }

        goods.setUuid(uuid);
        goods.setTitle(goodsRegistForm.getTitle());
        goods.setBrand(goodsRegistForm.getBrand());
        goods.setGoodsType(goodsRegistForm.getGoodsType());
        goods.setSortScore(goodsRegistForm.getSortScore());
        goods.setPrice(goodsRegistForm.getPrice());
        goods.setPicUrl(goodsRegistForm.getPicUrl1());
        if(goodsRegistForm.getPublishStart() == null || "".equals(goodsRegistForm.getPublishStart())){
            goods.setPublishStart(DateUtil.getDefaultDate());
        }else{
            goods.setPublishStart(DateUtil.stringToDateyyyy_MM_dd(goodsRegistForm.getPublishStart()));
        }
        if(goodsRegistForm.getPublishEnd() == null || "".equals(goodsRegistForm.getPublishEnd())){
            goods.setPublishEnd(DateUtil.getDefaultPublishEnd());
        }else{
            goods.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd(goodsRegistForm.getPublishEnd()));
        }
        goods.setCreateDatetime(new Date(System.currentTimeMillis()));
        goods.setDelFlg(Boolean.FALSE);
        goods.setNote(Constant.EMPTY_STRING);
        goodsMapper.insertGoods(goods);
        return goods.getId();
    }

    public long insertGoodsDetail(GoodsRegistForm goodsRegistForm, Long goodsId){
        GoodsDetail goodsDetail = new GoodsDetail();
        goodsDetail.setGoodsId(goodsId);
        goodsDetail.setName(goodsRegistForm.getTitle());
        goodsDetail.setTitle(goodsRegistForm.getTitle());
        goodsDetail.setBrand(goodsRegistForm.getBrand());
        goodsDetail.setPrice(goodsRegistForm.getPrice());
        goodsDetail.setDesc1(goodsRegistForm.getDesc1());
        goodsDetail.setDesc2(goodsRegistForm.getDesc2());
        goodsDetail.setDesc3(goodsRegistForm.getDesc3());
        goodsDetail.setPicUrl1(goodsRegistForm.getPicUrl1());
        goodsDetail.setPicUrl2(goodsRegistForm.getPicUrl2());
        goodsDetail.setPicUrl3(goodsRegistForm.getPicUrl3());
        goodsDetail.setPicUrl4(goodsRegistForm.getPicUrl4());
        goodsDetail.setPicUrl5(goodsRegistForm.getPicUrl5());
        goodsDetail.setPicUrl6(goodsRegistForm.getPicUrl6());
        goodsDetail.setPicUrl7(goodsRegistForm.getPicUrl7());
        goodsDetail.setPicUrl8(goodsRegistForm.getPicUrl8());
        goodsDetail.setPicUrl9(goodsRegistForm.getPicUrl9());
        goodsDetail.setPicUrl10(goodsRegistForm.getPicUrl10());
        goodsDetail.setCreateDatetime(new Date(System.currentTimeMillis()));
        goodsDetail.setDelFlg(Boolean.FALSE);
        goodsDetail.setNote(Constant.EMPTY_STRING);
        goodsDetailMapper.insertGoodsDetail(goodsDetail);
        return goodsDetail.getId();
    }

    public Long insertStock(Stock stock){
        stock.setCreateDatetime(new Date(System.currentTimeMillis()));
        stock.setDelFlg(Boolean.FALSE);
        stock.setNote(Constant.EMPTY_STRING);
        stockMapper.insertStock(stock);
        return stock.getId();
    }

    public long updateGoods(GoodsEditForm goodsEditForm){
        Goods goods = new Goods();
        goods.setId(goodsEditForm.getId());
        goods.setTitle(goodsEditForm.getTitle());
        goods.setBrand(goodsEditForm.getBrand());
        goods.setGoodsType(goodsEditForm.getGoodsType());
        goods.setSortScore(goodsEditForm.getSortScore());
        goods.setPrice(goodsEditForm.getPrice());
        goods.setPicUrl(goodsEditForm.getPicUrl1());
        if(goodsEditForm.getPublishStart() == null || "".equals(goodsEditForm.getPublishStart())){
            goods.setPublishStart(DateUtil.getDefaultDate());
        }else{
            goods.setPublishStart(DateUtil.stringToDateyyyy_MM_dd(goodsEditForm.getPublishStart()));
        }
        if(goodsEditForm.getPublishEnd() == null || "".equals(goodsEditForm.getPublishEnd())){
            goods.setPublishEnd(DateUtil.getDefaultPublishEnd());
        }else{
            goods.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd(goodsEditForm.getPublishEnd()));
        }
        goods.setUpdateDatetime(new Date(System.currentTimeMillis()));
        goods.setDelFlg(Boolean.FALSE);
        goods.setNote(Constant.EMPTY_STRING);
        goodsMapper.updateGoods(goods);
        return goodsEditForm.getId();
    }

    public long updateGoodsDetail(GoodsEditForm goodsEditForm){
        GoodsDetail goodsDetail = new GoodsDetail();
        goodsDetail.setGoodsId(goodsEditForm.getId());
        goodsDetail.setName(goodsEditForm.getTitle());
        goodsDetail.setTitle(goodsEditForm.getTitle());
        goodsDetail.setBrand(goodsEditForm.getBrand());
        goodsDetail.setPrice(goodsEditForm.getPrice());
        goodsDetail.setDesc1(goodsEditForm.getDesc1());
        goodsDetail.setDesc2(goodsEditForm.getDesc2());
        goodsDetail.setDesc3(goodsEditForm.getDesc3());
        goodsDetail.setPicUrl1(goodsEditForm.getPicUrl1());
        goodsDetail.setPicUrl2(goodsEditForm.getPicUrl2());
        goodsDetail.setPicUrl3(goodsEditForm.getPicUrl3());
        goodsDetail.setPicUrl4(goodsEditForm.getPicUrl4());
        goodsDetail.setPicUrl5(goodsEditForm.getPicUrl5());
        goodsDetail.setPicUrl6(goodsEditForm.getPicUrl6());
        goodsDetail.setPicUrl7(goodsEditForm.getPicUrl7());
        goodsDetail.setPicUrl8(goodsEditForm.getPicUrl8());
        goodsDetail.setPicUrl9(goodsEditForm.getPicUrl9());
        goodsDetail.setPicUrl10(goodsEditForm.getPicUrl10());
        goodsDetail.setUpdateDatetime(new Date(System.currentTimeMillis()));
        goodsDetail.setDelFlg(Boolean.FALSE);
        goodsDetail.setNote(Constant.EMPTY_STRING);
        goodsDetailMapper.updateGoodsDetail(goodsDetail);
        return goodsEditForm.getId();
    }

    public long deleteGoods(Long id){
        Goods goods = new Goods();
        goods.setId(id);
        goods.setUpdateDatetime(new Date(System.currentTimeMillis()));
        goods.setDelFlg(Boolean.TRUE);
        goodsMapper.updateGoods(goods);
        return id;
    }

    public long deleteGoodsDetail(Long id){
        GoodsDetail goodsDetail = new GoodsDetail();
        goodsDetail.setGoodsId(id);
        goodsDetail.setUpdateDatetime(new Date(System.currentTimeMillis()));
        goodsDetail.setDelFlg(Boolean.TRUE);
        goodsDetailMapper.updateGoodsDetail(goodsDetail);
        return id;
    }

    public long countGoods() { return goodsMapper.count();}




    public List<GoodsAmount> getGoodsAllByCondition(GoodsListForm form) {
        List<GoodsAmount> goodsAmountList = goodsMapper.getGoodsAllByCondition(form);
        return goodsAmountList;
    }

    public int getGoodsCountByCondition(GoodsListForm form) {
        return goodsMapper.getGoodsCountByCondition(form);
    }

    public int getGoodsCount() {
        return goodsMapper.getGoodsCount();
    }



    public List<GoodsPurchase> getGoodsPurchaseAllByCondition(GoodsPurchaseListForm form) {
        List<GoodsPurchase> goodsPurchaseList = goodsPurchaseMapper.getGoodsPurchaseAllByCondition(form);
        return goodsPurchaseList;
    }

    public int getGoodsPurchaseCountByCondition(GoodsPurchaseListForm form) {
        return goodsPurchaseMapper.getGoodsPurchaseCountByCondition(form);
    }

    public int getGoodsPurchaseCount() {
        return goodsPurchaseMapper.getGoodsPurchaseCount();
    }

    public Long insertPurchaseInfo(PurchaseInfo entity){
        purchaseInfoMapper.insert(entity);
        return entity.getId();
    }
    public Long updateByPurchaseInfo(PurchaseInfo entity){
        purchaseInfoMapper.updateByPrimaryKeySelective(entity);
        return entity.getId();
    }

    public List<PurchaseInfo> selectByUserId(Long userId){
        return purchaseInfoMapper.selectByUserId(userId);
    }

    public PurchaseInfo selectBySerialNumber(String serialNumber){
        return purchaseInfoMapper.selectBySerialNumber(serialNumber);
    }


}
