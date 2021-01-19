package jp.co.vermore.service;

import java.util.Date;
import java.util.List;

import jp.co.vermore.common.Constant;
import jp.co.vermore.entity.ShopTag;
import jp.co.vermore.mapper.ShopTagMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ShopTagService
 * Created by wubin.
 *
 * DateTime: 2018/03/12 19:19
 * Copyright: sLab, Corp
 */
@Service
public class ShopTagService {
    @Autowired
    private ShopTagMapper shopTagMapper;

    public int getContent(Long shopId ,int moduleType, int tagType){
        int content = 0 ;
        List<Integer> contents = shopTagMapper.getContent(shopId,moduleType,tagType);
        if(contents.size()>0){
           content= contents.get(0) ;
        }
        return content;
    }

    public List<ShopTag> getShopTagList(List<Long> idList ,int moduleType, List<Integer> tagList ){
        List<ShopTag> list = shopTagMapper.getShopTagList(idList,moduleType,tagList);
        return list;
    }

    public List<Integer> getContents(Long shopId ,int shopType ,int tagType){
        List<Integer> contents = shopTagMapper.getContents(shopId,shopType,tagType);
        return contents;
    }

    public List<Long> getShopId(Integer shopType){
        List<Long> shopIdList = shopTagMapper.getShopId(shopType);
        return shopIdList;
    }

    public List<Long> getShopIdByContent(Integer shopType,int tagType,int content){
        List<Long> shopIdList = shopTagMapper.getShopIdByContent(shopType,tagType,content);
        return shopIdList;
    }

    public List<Long> getShopIdByContents(Integer shopType,int tagType,List<Integer> list){
        List<Long> shopIdList = shopTagMapper.getShopIdByContents(shopType,tagType,list);
        return shopIdList;
    }

    public int insertShopTag(Long shopId,int moduleType, int tagType, int content) {
        ShopTag shopTag = new ShopTag();
        shopTag.setShopId(shopId);
        shopTag.setModuleType((byte)moduleType);
        shopTag.setTagType((byte) tagType);
        shopTag.setContent(content);
        shopTag.setCreateDatetime(new Date(System.currentTimeMillis()));
        shopTag.setUpdateDatetime(new Date(System.currentTimeMillis()));
        shopTag.setDelFlg(Boolean.FALSE);
        shopTag.setNote(Constant.EMPTY_STRING);
        return shopTagMapper.insertShopTag(shopTag);
    }

    public int insertShopTags(Long shopId, int moduleType,int tagType, List<Integer> contentList) {
        ShopTag  shopTag = new ShopTag();
        for (Integer content:contentList) {
            shopTag.setShopId(shopId);
            shopTag.setModuleType((byte)moduleType);
            shopTag.setTagType((byte) tagType);
            shopTag.setContent(content);
            shopTag.setCreateDatetime(new Date(System.currentTimeMillis()));
            shopTag.setUpdateDatetime(new Date(System.currentTimeMillis()));
            shopTag.setDelFlg(Boolean.FALSE);
            shopTag.setNote(Constant.EMPTY_STRING);
            shopTagMapper.insertShopTag(shopTag);
        }
        return 0;
    }

    public int updateShopTag(Long shopId, int moduleType, int tagType, int content) {
        ShopTag shopTag = new ShopTag();
        shopTag.setShopId(shopId);
        shopTag.setModuleType((byte)moduleType);
        shopTag.setTagType((byte) tagType);
        shopTag.setContent(content);
        shopTag.setCreateDatetime(new Date(System.currentTimeMillis()));
        shopTag.setUpdateDatetime(new Date(System.currentTimeMillis()));
        shopTag.setDelFlg(Boolean.FALSE);
        shopTag.setNote(Constant.EMPTY_STRING);
        return shopTagMapper.updateShopTag(shopTag);
    }

    public int updateShopTags(Long shopId, int moduleType,int tagType, List<Integer> contentList) {
        ShopTag  shopTag= new ShopTag();
        for (Integer content:contentList) {
            shopTag.setShopId(shopId);
            shopTag.setModuleType((byte)moduleType);
            shopTag.setTagType((byte) tagType);
            shopTag.setContent(content);
            shopTag.setCreateDatetime(new Date(System.currentTimeMillis()));
            shopTag.setUpdateDatetime(new Date(System.currentTimeMillis()));
            shopTag.setDelFlg(Boolean.FALSE);
            shopTag.setNote(Constant.EMPTY_STRING);
            shopTagMapper.updateShopTag(shopTag);
        }
        return 0;
    }

    public int deleteShopTag(Long shopId) {
        return shopTagMapper.deleteShopTag(shopId);
    }

    public int deleteShopTags(Long shopId,int moduleType, int tagType) {
        return shopTagMapper.deleteShopTags(shopId,moduleType, tagType);
    }

    public List<ShopTag> getShopTagAllByShopIdListAndCondition(List<Long> shopIdList, int moduleType, int tagType, List<Integer> contentList) {
        return shopTagMapper.getShopTagAllByShopIdListAndCondition(shopIdList, moduleType, tagType, contentList);
    }
}
