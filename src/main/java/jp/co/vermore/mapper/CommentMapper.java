package jp.co.vermore.mapper;


import jp.co.vermore.entity.Comment;


import java.util.List;

public interface CommentMapper {

    List<Comment> getCommentRiseForTop();

    List<Comment> getCommentAll(int limit, int offset);

    List<Comment> getCommentByItem(int itemId, int commentType);

    int insert(Comment record);

    int updateByPrimaryKeySelective(Comment record);

    int deleteByPrimaryKey(int id);

    Comment selectByPrimaryKey(int id);

}