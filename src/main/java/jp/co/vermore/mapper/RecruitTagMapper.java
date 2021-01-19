package jp.co.vermore.mapper;

import jp.co.vermore.entity.Invite;
import jp.co.vermore.entity.RecruitTag;

import java.util.List;

public interface RecruitTagMapper {

    List<Integer> getContent(Long recruitId, Integer tagType);

    List<String> getDescByRecruit(Long recruitId, Integer tagType);

    List<Integer> getRecruitId(Integer tagType, Integer content);

    int deleteTag(Long recruitId, int tagType, int content);

    int deleteTags(Long recruitId, int tagType);

    int insertSelective(RecruitTag recruitTag);

    List<RecruitTag> getRecruitTagList(List<Long> idList, List<Integer> tagList);

    List<RecruitTag> getRecruitTagAllByRecruitIdListAndCondition(List<Long> recruitIdList, int tagType, List<Integer> contentList);

    List<RecruitTag> getRecruitTagAllByRecruitIdListAndConditionTimeStart(List<Long> recruitIdList, int tagType, int workingTimeStart);

    List<RecruitTag> getRecruitTagAllByRecruitIdListAndConditionTimeEnd(List<Long> recruitIdList, int tagType, int workingTimeEnd);

    List<RecruitTag> getShopTagAllByShopIdListAndCondition(List<Long> shopIdList, int moduleType, int tagType, List<Integer> contentList);
}