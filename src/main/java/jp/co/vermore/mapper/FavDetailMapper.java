package jp.co.vermore.mapper;

import java.util.List;
import jp.co.vermore.entity.FavDetail;
import jp.co.vermore.entity.Person;

public interface FavDetailMapper {

   List<FavDetail> getFavDetailById(long id,int type,int limit,int offset);

   List<FavDetail> getFavDetailByIdAll(long id,int type);

   List<FavDetail> getFavByUserId(long userId);

   List<FavDetail> getFavByShopId(long shopId);

   List<FavDetail> getFavByUserIdAndType(long userId,int type);

   FavDetail getFavDetailByUserId(long userId,long favId,int type);

   List<FavDetail> getFavDetailByUserIdAndByType(long userId,int type);

   FavDetail getFavorite(long userId,long favId,int type);

   int insertFavDetail(FavDetail favDetail);

   int deleteFavDetailByUserId(long userId);

   int deleteFavDetail(FavDetail favDetail);

    int deletefavDetail(Long tvDetailId, int type);
}