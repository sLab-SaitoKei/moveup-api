package jp.co.vermore.controller;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.Comment;
import jp.co.vermore.jsonparse.CommentRiseDetailJsonParse;
import jp.co.vermore.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * CommentController
 * Created by Wangyan.
 * <p>
 * DateTime: 2018/03/19 17:22
 * Copyright: sLab, Corp
 */
@Controller
public class CommentController extends BaseController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/api/rise/comment/{id}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getComment(@PathVariable int id) {

        List<Comment> commentList = commentService.getCommentByItem(id, Constant.COMMENT_TYPE.RISE);
        List<CommentRiseDetailJsonParse> crdjpList = new ArrayList<>();
        CommentRiseDetailJsonParse crdjp = new CommentRiseDetailJsonParse();
        for (Comment cd : commentList) {
            crdjp.setCreateDatetime(DateUtil.dateToStringyyyy_MM_dd(cd.getCreateDatetime()));
            crdjp.setDesc(cd.getDesc());
            crdjp.setPicUrl1(cd.getPicUrl1());
            crdjp.setPicUrl2(cd.getPicUrl2());
            crdjpList.add(crdjp);
        }
        jsonObject.setResultList(crdjpList);
        return jsonObject;

    }

}
