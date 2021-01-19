package jp.co.vermore.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.vermore.entity.TagDetail;
import jp.co.vermore.mapper.TagDetailMapper;

/**
 * TagDetailService
 * Created by wubin.
 *
 * DateTime: 2018/03/12 17:20
 * Copyright: sLab, Corp
 */

@Service
public class TagDetailService {

    @Autowired
    private TagDetailMapper tagDetailMapper;

    public String getStation(Long shopId,int shopType){
        String station = tagDetailMapper.getShopStation(shopId,shopType);
        return station;
    }

    public String getCategory(Long shopListId){
        String category = tagDetailMapper.getShopCategory(shopListId);
        return category;
    }

    public List<TagDetail> getTagDetailByModuleAndType(int moduleType, int tagType){
        List<TagDetail> tagDetailList = tagDetailMapper.getTagDetailByModuleAndType(moduleType, tagType);
        return tagDetailList;
    }

    public List<TagDetail> getTagDetailBytagType(int tagType){
        List<TagDetail> tagDetailList = tagDetailMapper.getTagDetailBytagType( tagType);
        return tagDetailList;
    }

    public String  getDesc(int moduleType, int tagType,int content){
        String desc = tagDetailMapper.getDesc(moduleType, tagType,content);
        return desc;
    }

    public List<Map<String,Object>>  getTagDetailDesc(int moduleType, int tagType){
        List<Map<String,Object>> tagDetailList = tagDetailMapper.getTagDetailDesc(moduleType, tagType);
        return tagDetailList;
    }

    public List<TagDetail>  getTagDetailList(int moduleType, List<Integer> tagTypeList){
        List<TagDetail> list = tagDetailMapper.getTagDetailList(moduleType, tagTypeList);
        return list;
    }

    public List<TagDetail>  getTagDetailListByContent(int moduleType, int tagType,List<Integer> content){
        List<TagDetail> list = tagDetailMapper.getTagDetailListByContent(moduleType, tagType, content);
        return list;
    }

    public List<TagDetail>  getTagDetailListByContentForInfo( int tagType,List<Integer> content){
        List<TagDetail> list = tagDetailMapper.getTagDetailListByContentForInfo (tagType, content);
        return list;
    }

    public List<TagDetail>  getTagDetailListByContentForRecruit( int tagType,List<Integer> content){
        List<TagDetail> list = tagDetailMapper.getTagDetailListByContentForRecruit (tagType, content);
        return list;
    }
}
