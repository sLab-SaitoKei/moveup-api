package jp.co.vermore.mapper;


import jp.co.vermore.entity.Place;

import java.util.List;

public interface PlaceMapper {

    int insertPlace(Place place);

    int updatePlace(Place place);

    int deletePlace(Place place);

    Place selectById (Long id);

    Place selectByUuid (String uuid);

    List<Place> getPlaceAll(int limit, int offset ,int area);

    List<Place> getPlaceAllForRandom();

    List<Place> getPlaceAllByArea(int area, int limit, int offset);

    List<Place> getPlaceAllCount(int area,String tomorrow,String today);

    List<Place> findByUuidArea(String uuid,String tomorrow,String today);

    List<Place> findPlaceByUuidList(List<String> uuid);

    String getShopUuidByShopIdPlace(Long id);

    Place getPlace(Long shopId);

    List<Place> getPlaceByUuidList(List<String> list);

    List<Place> getPlaceForSettlementBatch(String monthOfFirst,String LastmonthFirst);

}