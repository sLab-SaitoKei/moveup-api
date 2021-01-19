package jp.co.vermore.mapper;

import jp.co.vermore.entity.StudioGallery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudioGalleryMapper {

    int countByExample(StudioGallery example);


    int deleteByExample(StudioGallery example);


    int deleteByPrimaryKey(Integer id);

    int insert(StudioGallery record);


    int insertSelective(StudioGallery record);

    StudioGallery selectByPrimaryKey(Integer id);


    int updateByExampleSelective(@Param("record") StudioGallery record, @Param("example") StudioGallery example);

    int updateByExample(@Param("record") StudioGallery record, @Param("example") StudioGallery example);


    int updateByPrimaryKeySelective(StudioGallery record);


    int updateByPrimaryKey(StudioGallery record);

    List<StudioGallery> getStudioGalleryList();

    List<StudioGallery> getStudioGalleryForAdmin();

    int updateGallery(StudioGallery studioGallery);
}