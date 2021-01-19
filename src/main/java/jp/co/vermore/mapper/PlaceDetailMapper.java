package jp.co.vermore.mapper;

import jp.co.vermore.entity.Place;
import jp.co.vermore.entity.PlaceDetail;
import jp.co.vermore.form.admin.PlaceListForm;
import java.util.List;

public interface PlaceDetailMapper {

    PlaceDetail selectByPlaceListId (Long placeListId);

    List<PlaceDetail> getPlaceDetailAllByCondition(PlaceListForm form);

    int getPlaceDetailCountByCondition(PlaceListForm form);

    int getPlaceDetailCount();

    int insertPlaceDetail(PlaceDetail placeDetail);

    int updatePlaceDetail(PlaceDetail placeDetail);

    int deletePlaceDetail(PlaceDetail placeDetail);

    int deleteDetailVideo(Long placeId);

    int deleteDetailFlic(Long placeId);

    List<String> getPlaceDetail();
}