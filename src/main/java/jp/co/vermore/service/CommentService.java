package jp.co.vermore.service;

import jp.co.vermore.entity.Comment;
import jp.co.vermore.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CommentService
 * Created by Wangyan.
 * <p>
 * DateTime: 2018/03/19 14:58
 * Copyright: sLab, Corp
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    public List<Comment> getCommentRiseForTop() {
        return commentMapper.getCommentRiseForTop();
    }

    public List<Comment> getCommentAll(int limit, int offset) {
        return commentMapper.getCommentAll(limit, offset);
    }

    public List<Comment> getCommentByItem(int itemId, int commentType) {
        return commentMapper.getCommentByItem(itemId, commentType);
    }

    public Comment getById(int id) {
        return commentMapper.selectByPrimaryKey(id);
    }

    public int addComment(Comment comment) {
        return commentMapper.insert(comment);
    }

    public int updateCommentByEntity(Comment comment) {
        return commentMapper.updateByPrimaryKeySelective(comment);
    }

    public int deleteCommentById(int commentId) {
        return commentMapper.deleteByPrimaryKey(commentId);
    }

}
