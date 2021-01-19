package jp.co.vermore.service;

import jp.co.vermore.entity.FavDetail;
import jp.co.vermore.mapper.FavDetailMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * FavDetailService
 * Created by wubin.
 *
 * DateTime: 2018/04/12 11:05
 * Copyright: sLab, Corp
 */
@Service
public class FavDetailService {

    @Autowired
    private FavDetailMapper favDetailMapper;

    public  List<FavDetail> getFavByUserId(long userId){
        List<FavDetail> list =  favDetailMapper.getFavByUserId(userId);
        return  list;
    }

    public  List<FavDetail> getFavByShopId(long shopId){
        List<FavDetail> list =  favDetailMapper.getFavByShopId(shopId);
        return  list;
    }

    public  List<FavDetail> getFavByUserIdAndType(long userId,int type){
        List<FavDetail> list =  favDetailMapper.getFavByUserIdAndType(userId,type);
        return  list;
    }

    public FavDetail getFavDetailByUserId(long userId,long favId,int type){
        FavDetail entity =  favDetailMapper.getFavDetailByUserId(userId,favId,type);
        return  entity;
    }

    public List<FavDetail> getFavDetailByUserIdAndByType(long userId,int type){
        List<FavDetail> list =  favDetailMapper.getFavDetailByUserIdAndByType(userId,type);
        return  list;
    }

    public List<FavDetail> getFavDetail(long id,int type,int limit,int offset){
       List<FavDetail> list =  favDetailMapper.getFavDetailById(id,type,limit,offset);
       return  list;
    }

    public FavDetail getFavorite(long userId,long favid,int type){
        FavDetail entity =  favDetailMapper.getFavorite(userId,favid,type);
        return  entity;
    }

    public List<FavDetail> getFavDetailAll(long id,int type){
        List<FavDetail> list =  favDetailMapper.getFavDetailByIdAll(id,type);
        return  list;
    }

    public int insertFavDetail(FavDetail favDetail){
        return favDetailMapper.insertFavDetail( favDetail);
    }

    public int deleteFavDetailByUserId(long userId){
        return favDetailMapper.deleteFavDetailByUserId(userId);
    }

    public int deleteFavDetail(FavDetail favDetail){
        return favDetailMapper.deleteFavDetail( favDetail);
    }

    public int deletefavDetail(Long tvDetailId, int type) {
        return favDetailMapper.deletefavDetail(tvDetailId, type);
    }
}
